package com.example.lizieT123.controller;

import com.example.lizieT123.model.Producto;
import com.example.lizieT123.service.ProductoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired  //inyeccion de dependencias
    private ProductoServiceImp gatitos;

    @PostMapping("/addProducto")
    public ResponseEntity<Producto> createInstructor(@RequestBody Producto instructor) {
        Producto createdInstructor = gatitos.addProducto(instructor);
        return new ResponseEntity<>(createdInstructor, HttpStatus.CREATED);
    }

    @GetMapping("/listarProductos")
    public List<Producto> getAllInstructors() {
        return gatitos.listarProductos();
    }

}
