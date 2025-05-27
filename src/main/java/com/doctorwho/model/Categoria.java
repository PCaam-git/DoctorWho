package com.doctorwho.model;

import java.sql.Date;
import java.math.BigDecimal;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private boolean tieneProductos;
    private Date fechaActualizacion;
    private BigDecimal precioMedio;
    private String imagen;

    // Constructor vacío
    public Categoria() {}

    // Constructor con parámetros
    public Categoria(String nombre, String descripcion, int cantidad, boolean tieneProductos, 
                    Date fechaActualizacion, BigDecimal precioMedio, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tieneProductos = tieneProductos;
        this.fechaActualizacion = fechaActualizacion;
        this.precioMedio = precioMedio;
        this.imagen = imagen;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isTieneProductos() {
        return tieneProductos;
    }

    public void setTieneProductos(boolean tieneProductos) {
        this.tieneProductos = tieneProductos;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public BigDecimal getPrecioMedio() {
        return precioMedio;
    }

    public void setPrecioMedio(BigDecimal precioMedio) {
        this.precioMedio = precioMedio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    // toString para debugging
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", tieneProductos=" + tieneProductos +
                ", fechaActualizacion=" + fechaActualizacion +
                ", precioMedio=" + precioMedio +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}