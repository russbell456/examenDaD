package org.example.rdcmmatricula.entity;

import jakarta.persistence.*;
import org.example.rdcmmatricula.dato.Estudiante;

@Entity
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double cantidad;
    private Double precio;
    private Integer productoId;

    @Transient
    private Estudiante estudiante;

    public PedidoDetalle() {
        this.cantidad = 0.0;
        this.precio = 0.0;
    }

    public PedidoDetalle(Integer id, Double cantidad, Double precio, Integer productoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.productoId = productoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    // ✅ Métodos correctos para el campo 'producto'
    public Estudiante getProducto() {
        return estudiante;
    }

    public void setProducto(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
