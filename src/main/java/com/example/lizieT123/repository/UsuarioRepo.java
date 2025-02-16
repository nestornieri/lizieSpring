package com.example.lizieT123.repository;

import com.example.lizieT123.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UsuarioRepo extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}