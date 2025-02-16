package com.example.lizieT123.service;

import org.springframework.security.core.userdetails.User;  //Spring
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Como Funciona
        if ("lizie2000".equals(username)) {
            // Usar BCrypt para la contraseña. ¡Nunca guardes contraseñas en texto plano!
            String encodedPassword = new BCryptPasswordEncoder().encode("password");
            return new User(username, encodedPassword, new ArrayList<>()); // Usuario sin roles/authorities
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username); //Puntos de Control
        }
    }
}