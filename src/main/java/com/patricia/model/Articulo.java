package com.patricia.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articulo {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;
    private Date fechaAñadido;
    private String imagen; 
}