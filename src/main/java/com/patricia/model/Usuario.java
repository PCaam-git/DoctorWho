package com.patricia.model;

import java.time.LocalDate;
import org.jdbi.v3.core.mapper.reflect.ColumnName;


public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String rol;
    private LocalDate fechaRegistro;
    private String estado;
    private boolean activo;

    public Usuario() {}

    public Usuario(int id, String nombre, String email, String contraseña, String rol, LocalDate fechaRegistro, String estado, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.activo = activo;
    }

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() { return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() { return email;}
    public void setEmail(String email) {this.email = email;}

    public String getContraseña() { return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}

    public String getRol() { return rol;}
    public void setRol(String rol) {this.rol = rol;}

    public LocalDate getFechaRegistro() { return fechaRegistro;}
    public void setFechaRegistro(LocalDate fechaRegistro) {this.fechaRegistro = fechaRegistro;}

    public String getEstado() { return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public boolean isActivo() { return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol='" + rol + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado='" + estado + '\'' +
                ", activo=" + activo +
                '}';
    }
}
