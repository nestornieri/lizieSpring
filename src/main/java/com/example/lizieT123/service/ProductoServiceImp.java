package com.example.lizieT123.service;

import com.example.lizieT123.model.Producto;
import com.example.lizieT123.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductoServiceImp implements ProductoService{

    @Autowired
    private ProductoRepo animalillo;

    @Transactional(readOnly = true)
    @Override
    public Collection<Producto> listarProductos() {
        return (Collection<Producto>)animalillo.findAll();
    }

    @Override
    public Optional<Producto> productosPorID(Integer id) {
        return animalillo.findById(id);
    }

    @Override
    public Producto addProducto(Producto producto) {
        return animalillo.save(producto);
    }

    @Override
    public Producto actProducto(Integer id, Producto producto) {
        Optional<Producto> existingInstructor = animalillo.findById(id);
        if (existingInstructor.isPresent()) {
            Producto updatedInstructor = existingInstructor.get();
            updatedInstructor.setNombre(producto.getNombre());
            return animalillo.save(updatedInstructor);
        } else {
            throw new RuntimeException("Producto no encontrado " + id);
        }
    }

    @Override
    public boolean remProducto(Integer id) {
        Optional<Producto> instructor = animalillo.findById(id);
        if (instructor.isPresent()) {
            animalillo.delete(instructor.get());
            return true;
        } else {
            return false;
        }
    }
}
