package com.doctorwho.model;

import java.sql.Date;
import java.math.BigDecimal;
import lombok.Data;

@Data

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contrasena;
    private Boolean esAdmin;
    private Date fechaRegistro;
    private BigDecimal credito;
    private String imagen;
}