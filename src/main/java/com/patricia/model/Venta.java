package com.patricia.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private int idTransaccion;
    private int idComprador;
    private String nombreComprador;
    private int idArticulo;
    private String nombreArticulo;
    private double precio;
    private Date fechaTransaccion;
    private String estadoVenta;
    private boolean pagado;
    private boolean activo;
    private String imagen;
}