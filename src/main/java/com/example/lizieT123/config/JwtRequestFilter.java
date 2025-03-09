package com.example.lizieT123.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.secret}") // Lee la clave secreta desde application.properties
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length < 2) {
                throw new IllegalArgumentException("Token inválido");
            }

            String headerJson = new String(Base64.getDecoder().decode(tokenParts[0]));
            JSONObject header = new JSONObject(headerJson);
            String alg = header.optString("alg");

            if ("HS256".equals(alg)) {
                System.out.println("Token JWT clásico (HS256) detectado.");
                Claims claims = validateAndParseHs256Token(token);
                processAuthentication(claims, token, request);
            } else {
                System.out.println("Token OAuth2 (probablemente RS256) detectado, delegando a Spring Security.");
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el token: " + e.getMessage());
        }

        System.out.println("Estado de autenticación después del filtro 1: " + SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request, response);
    }

    // Método para validar y parsear token HS256
    private Claims validateAndParseHs256Token(String token) {
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para procesar la autenticación
    private void processAuthentication(Claims claims, String token, HttpServletRequest request) {
        String username = claims.getSubject();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Token válido para usuario: " + username);

            // Crear UserDetails con autoridades mínimas
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetails = new User(username, "", authorities); // Incluye un rol por defecto

            // Validar el token
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Autenticación establecida para: " + username);
            } else {
                System.out.println("Token inválido o no coincide con usuario.");
            }
        }
    }
}