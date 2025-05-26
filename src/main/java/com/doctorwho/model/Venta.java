package com.doctorwho.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Data
public class Venta {
    private int id;
    private int usuarioId;  
    private int articuloId;  
    private int cantidad;
    private BigDecimal total;
    private Date fechaVenta;  
    private String estadoVenta;
    private boolean pagado;
    private String imagen;
}