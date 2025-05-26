package com.doctorwho.model;

import lombok.Data;
import java.sql.Date;
import java.math.BigDecimal;


@Data
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
}