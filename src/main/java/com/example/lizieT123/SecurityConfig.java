package com.example.lizieT123;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //Ofusca Todos los endpoint
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChainB(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                /*
                CSRF limita la modificacion de data si no viene de formularios HMTL.
                * */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("*/publico","/publico/items").permitAll()   //
                        .requestMatchers("*/privado").authenticated()
                        .requestMatchers("*/user").hasRole("USER")
                        .requestMatchers("*/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN")      //permite metodos post por roles
                        //.requestMatchers(HttpMethod.POST,"/create").hasRole("ADMIN")  //para limitar 1 endpoint no permite *
                        //.requestMatchers("/create").hasRole("ADMIN")  //podemos validar roles sin HttpMethod
                        .anyRequest().authenticated()// Restringir todos los demás ( spring lo hace por defecto)

                )
                .httpBasic(Customizer.withDefaults()); // Añadir Usuario y Contraseña
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password")) // Contraseña encriptada
                //.password("{noop}password") //sin encriptar para pruebas solo desa
                .roles("USER")
                .build();


        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("passadmin")) // Contraseña encriptada
                //.password("{noop}password") //sin encriptar para pruebas solo desa
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}

/*
lenguajes: Java, koltin, python
paradigmas programacion: programacion imperativa, pogramacion orientada a objetos, progracion funcional
Imperativa: Funcion Suma(a,b) => a+b    Funcion Exponente(a,b) => a^b
Objetos: clase->auto, atributos-> color y metodos-> encender
Funcional: Funcion Exponente(a,b) => a.pow(b) :
Lamdas O Arrow Function : funciones anonimas: ((a,b) -> a.pow(b)) -> sout el numero
*/
