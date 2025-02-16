package com.example.lizieT123.config;


import com.example.lizieT123.model.Usuario;
import com.example.lizieT123.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("testuser".equals(username)) {
            // Usar BCrypt para la contraseña. ¡Nunca guardes contraseñas en texto plano!
            String encodedPassword = new BCryptPasswordEncoder().encode("password");
            return new User(username, encodedPassword, new ArrayList<>()); // Usuario sin roles/authorities
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
    */


    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }

}