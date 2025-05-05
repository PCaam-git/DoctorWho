package com.patricia.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private boolean tieneProductos;
    private Date fechaActualizacion;
    private double precioMedio;
    private String imagen;
}