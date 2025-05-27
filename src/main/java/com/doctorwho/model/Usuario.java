package com.doctorwho.model;

import java.sql.Date;
import java.math.BigDecimal;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contrasena;
    private Boolean esAdmin;
    private Date fechaRegistro;
    private BigDecimal credito;
    private String imagen;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros principales
    public Usuario(String nombre, String email, String contrasena, Boolean esAdmin, 
                  Date fechaRegistro, BigDecimal credito, String imagen) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
        this.fechaRegistro = fechaRegistro;
        this.credito = credito;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
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
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", esAdmin=" + esAdmin +
                ", fechaRegistro=" + fechaRegistro +
                ", credito=" + credito +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}