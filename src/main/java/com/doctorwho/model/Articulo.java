package com.doctorwho.model;

import java.sql.Date;
import java.math.BigDecimal;

public class Articulo {
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private boolean disponible;
    private Date fechaAnadido;
    private int categoriaId;
    private String categoriaNombre;
    private String imagen;

    // Constructor vacío
    public Articulo() {}

    // Constructor con parámetros principales
    public Articulo(String nombre, String descripcion, BigDecimal precio, boolean disponible, 
                   Date fechaAnadido, int categoriaId, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.fechaAnadido = fechaAnadido;
        this.categoriaId = categoriaId;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFechaAnadido() {
        return fechaAnadido;
    }

    public void setFechaAnadido(Date fechaAnadido) {
        this.fechaAnadido = fechaAnadido;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
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
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", disponible=" + disponible +
                ", fechaAnadido=" + fechaAnadido +
                ", categoriaId=" + categoriaId +
                ", categoriaNombre='" + categoriaNombre + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}