package com.example.lizieT123.service;

import com.example.lizieT123.model.Producto;
import com.example.lizieT123.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImp implements ProductoService{

    @Autowired
    private ProductoRepo animalillo;

    @Override       //real es sobrecarga, coloquial es sobreescribir (polimorfismo)
    public List<Producto> listarProductos() {
        return (List<Producto>)animalillo.findAll();
    }

    @Override
    public Optional<Producto> productosPorID(Integer id) {
        return Optional.empty();
    }

    @Override
    public Producto addProducto(Producto producto) {
        return animalillo.save(producto);
    }

    @Override
    public Producto actProducto(Integer id, Producto producto) {
        return null;
    }

    @Override
    public boolean remProducto(Integer id) {
        return false;
    }
}
