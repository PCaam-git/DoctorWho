package com.patricia.model;

import java.time.LocalDate;

public class Articulo {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;
    private LocalDate fechaAñadido;

    public Articulo() {
    }

    public Articulo(int id, String nombre, String descripcion, double precio, boolean disponible,
            LocalDate fechaAñadido) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.fechaAñadido = fechaAñadido;
    }

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getFechaAñadido() {
        return fechaAñadido;
    }

    public void setFechaAñadido(LocalDate fechaAñadido) {
        this.fechaAñadido = fechaAñadido;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", disponible=" + disponible +
                ", fechaAñadido=" + fechaAñadido +
                '}';
    }
}
