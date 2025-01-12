package com.example.lizieT123.controller;

import com.example.lizieT123.model.Producto;
import com.example.lizieT123.service.ProductoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductoController {

    @Autowired  //inyeccion de dependencias
    private ProductoServiceImp gatitos;

    @GetMapping("/all")
    public List<Producto> getAllProductos() {
        return (List<Producto>) gatitos.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        Optional<Producto> Producto = gatitos.productosPorID(id);
        return Producto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto Producto) {
        Producto createdProducto = gatitos.addProducto(Producto);
        return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto Producto) {
        Producto updatedProducto = gatitos.actProducto(id, Producto);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Integer id) {
        boolean isDeleted = gatitos.remProducto(id);

        if (isDeleted) {
            return ResponseEntity.ok("Producto eliminado con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no encontrado.");
        }
    }

}
