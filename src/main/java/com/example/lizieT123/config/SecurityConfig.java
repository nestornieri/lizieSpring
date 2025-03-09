package com.example.lizieT123.config;

import com.example.lizieT123.repository.UsuarioRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.atn.ActionTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return authentication -> {
            System.out.println("🚀 Solo autenticación con JWT, no se consulta la BD.");
            return authentication; // No llama a UserDetailsService
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationManagerResolver(request -> authentication -> {
                            Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
                            // Si ya hay autenticación del filtro JWT (HS256), respétala
                            if (existingAuth != null && existingAuth.isAuthenticated()) {
                                System.out.println("Autenticación previa detectada: " + existingAuth);
                                return existingAuth;
                            }
                            // Si no, valida como token OAuth2 (RS256)
                            try {
                                JwtDecoder jwtDecoder = jwtDecoder();
                                String token = ((BearerTokenAuthenticationToken) authentication).getToken();
                                Jwt jwt = jwtDecoder.decode(token);
                                List<GrantedAuthority> authorities = jwt.getClaimAsStringList("scope")
                                        .stream()
                                        .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                                        .collect(Collectors.toList());
                                return new JwtAuthenticationToken(jwt, authorities);
                            } catch (JwtException e) {
                                System.out.println("Error al validar token OAuth2: " + e.getMessage());
                                throw new BadCredentialsException("Token OAuth2 inválido", e);
                            }
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: " + authException.getMessage());
                        })
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/oauth2/jwks").build();
    }
}