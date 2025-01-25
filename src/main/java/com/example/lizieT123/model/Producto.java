package com.example.lizieT123.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_producto")
    private Integer idProducto;
    private String nombre;
    //@Column(name = "id_categoria")
    //private Integer idCategoria;
    @Column(name = "codigo_barras")
    private String codigoBarras;
    @Column(name = "precio_venta")
    private String precioVenta;
    @Column(name = "cantidad_stock")
    private Integer cantidadStock;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_categoria") // Clave foránea
    private Categoria categoria;
            //ID, NOMBRE, LIST_PRODUCTOS
    /*
    @ManyToMany
    @JoinTable(
            name = "producto_proveedor", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_proveedor"))
    private List<Proveedor> proveedores;

    */
    public Producto(){

    }

    public Producto(Integer idProducto, String nombre, String codigoBarras, String precioVenta, Integer cantidadStock, Boolean estado, Categoria categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        //this.idCategoria = idCategoria;
        this.codigoBarras = codigoBarras;
        this.precioVenta = precioVenta;
        this.cantidadStock = cantidadStock;
        this.estado = estado;
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                //", idCategoria=" + idCategoria +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", precioVenta='" + precioVenta + '\'' +
                ", cantidadStock=" + cantidadStock +
                ", estado=" + estado +
                '}';
    }
}
