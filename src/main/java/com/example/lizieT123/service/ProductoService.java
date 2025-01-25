package com.example.lizieT123.service;

import com.example.lizieT123.model.Producto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//porque cremamos Interfac ObjetoService /// creamos Clase ObjetoServiceImplemtacion
public interface ProductoService {
    Collection<Producto> listarProductos();
    Optional<Producto> productosPorID(Integer id);
    //caja de rodinger ( gato vivo o muerto) - Optional guarda temporalmente cualquier tipo de dato.
    Producto addProducto(Producto producto);
    Producto actProducto(Integer id, Producto producto);
    boolean remProducto(Integer id);
}
