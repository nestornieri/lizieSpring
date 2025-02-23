package com.example.lizieT123;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainTemp {
    public static void main(String[] args) {
        int numero = 10;
        Integer numeroInteger = 26;
        String nombre = "juanito";

        Animal animal1 = new Animal("juancillo", "humano");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("passnest");
        System.out.println("Contraseña encriptada: " + hashedPassword);

    }
}
