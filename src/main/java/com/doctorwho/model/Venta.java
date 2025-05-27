package com.doctorwho.model;

import java.sql.Date;
import java.math.BigDecimal;

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

    // Constructor vacío
    public Venta() {}

    // Constructor con parámetros principales
    public Venta(int usuarioId, int articuloId, int cantidad, BigDecimal total, 
                Date fechaVenta, String estadoVenta, boolean pagado) {
        this.usuarioId = usuarioId;
        this.articuloId = articuloId;
        this.cantidad = cantidad;
        this.total = total;
        this.fechaVenta = fechaVenta;
        this.estadoVenta = estadoVenta;
        this.pagado = pagado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
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
        return "Venta{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", articuloId=" + articuloId +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", fechaVenta=" + fechaVenta +
                ", estadoVenta='" + estadoVenta + '\'' +
                ", pagado=" + pagado +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}