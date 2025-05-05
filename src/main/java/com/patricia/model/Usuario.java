package com.patricia.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String rol;
    private Date fechaRegistro;
    private String estado;
    private boolean activo;
    private String imagen;
}