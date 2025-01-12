package com.example.lizieT123.service;

import com.example.lizieT123.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarProductos();
    Optional<Producto> productosPorID(Integer id);
    //caja de rodinger ( gato vivo o muerto) - Optional guarda temporalmente cualquier tipo de dato.
    Producto addProducto(Producto producto);
    Producto actProducto(Integer id, Producto producto);
    boolean remProducto(Integer id);
}
