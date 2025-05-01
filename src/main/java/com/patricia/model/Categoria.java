package com.patricia.model;

import java.time.LocalDate;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private boolean tieneProductos;
    private LocalDate fechaActualizacion;
    private double precioMedio;

    public Categoria() {}

    public Categoria(int id, String nombre, String descripcion, int cantidad, boolean tieneProductos, LocalDate fechaActualizacion, double precioMedio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tieneProductos = tieneProductos;
        this.fechaActualizacion = fechaActualizacion;
        this.precioMedio = precioMedio;
    }

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public boolean isTieneProductos() { return tieneProductos; }
    public void setTieneProductos(boolean tieneProductos) { this.tieneProductos = tieneProductos; }

    public LocalDate getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDate fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public double getPrecioMedio() { return precioMedio; }
    public void setPrecioMedio(double precioMedio) { this.precioMedio = precioMedio; }

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
                '}';
    }
}
