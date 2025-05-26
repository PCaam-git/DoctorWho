package com.doctorwho.model;

import java.sql.Date;
import lombok.Data;
import java.math.BigDecimal;


@Data

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private boolean tieneProductos;
    private Date fechaActualizacion;
    private BigDecimal precioMedio;
    private String imagen;
}