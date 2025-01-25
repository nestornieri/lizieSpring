package com.example.lizieT123.service;

import com.example.lizieT123.model.Producto;
import com.example.lizieT123.model.ProductoDTO;
import com.example.lizieT123.repository.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoDTOService {

    @Autowired
    private ProductoRepo productoRepo;

    /*
    *
    *
    @Transactional(readOnly = true)
    @Override
    public Collection<Producto> listarProductos() {
        return (Collection<Producto>)animalillo.findAll();
    }
    * */

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = (List<Producto>) productoRepo.findAll();
        return productos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    //Mappear el DTO con elementos del Objeto o Tabla.
    //Libreria MapStruct - Deprecado.
    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setIdProducto(producto.getIdProducto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setCodigoBarras(producto.getCodigoBarras());
        productoDTO.setIdCategoria(producto.getCategoria().getIdCategoria());
        productoDTO.setNombreCategoria(producto.getCategoria().getNomCategoria());
        return productoDTO;
    }

}
