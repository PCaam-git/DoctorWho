package com.patricia.model;

import java.time.LocalDate;
import org.jdbi.v3.core.mapper.reflect.ColumnName;


public class Venta {
    @ColumnName("id_transaccion")
    private int idTransaccion;
    @ColumnName("id_comprador")
    private int idComprador;
    @ColumnName("id_articulo")
    private int idArticulo;
    private double precio;
    @ColumnName("fecha_transaccion")
    private LocalDate fechaTransaccion;
    @ColumnName("estado_venta")
    private String estadoVenta;
    private boolean pagado;
    private boolean activo;

    public Venta() {}

    // Getters y setters
    public int getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(int idTransaccion) { this.idTransaccion = idTransaccion; }

    public int getIdComprador() { return idComprador; }
    public void setIdComprador(int idComprador) { this.idComprador = idComprador; }

    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public LocalDate getFechaTransaccion() { return fechaTransaccion; }
    public void setFechaTransaccion(LocalDate fechaTransaccion) { this.fechaTransaccion = fechaTransaccion; }

    public String getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(String estadoVenta) { this.estadoVenta = estadoVenta; }

    public boolean isPagado() { return pagado; }
    public void setPagado(boolean pagado) { this.pagado = pagado; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Venta{" +
                "idTransaccion=" + idTransaccion +
                ", idComprador=" + idComprador +
                ", idArticulo=" + idArticulo +
                ", precio=" + precio +
                ", fechaTransaccion=" + fechaTransaccion +
                ", estadoVenta='" + estadoVenta + '\'' +
                ", activo=" + activo +
                '}';
    }
}
