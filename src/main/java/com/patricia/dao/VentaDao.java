package com.patricia.dao;

import com.patricia.model.Venta;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class VentaDao {
    private final Jdbi jdbi;

    public VentaDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    // Insertar venta
    public void insertarVenta(Venta venta) {
        jdbi.useHandle(handle ->
            handle.createUpdate("INSERT INTO ventas (id_comprador, id_articulo, precio, fecha_transaccion, estado_venta, pagado, activo) " +
                    "VALUES (:idComprador, :idArticulo, :precio, :fechaTransaccion, :estadoVenta, :pagado, :activo)")
                .bind("idComprador", venta.getIdComprador())
                .bind("idArticulo", venta.getIdArticulo())
                .bind("precio", venta.getPrecio())
                .bind("fechaTransaccion", venta.getFechaTransaccion())
                .bind("estadoVenta", venta.getEstadoVenta())
                .bind("pagado", venta.isPagado())
                .bind("activo", venta.isActivo())
                .execute()
        );
    }
    

    // Listar ventas
    public List<Venta> listarVentas() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM ventas")
                  .mapToBean(Venta.class)
                  .list()
        );
    }

    // Actualizar venta
    public boolean actualizarVenta(Venta venta) {
        int filas = jdbi.withHandle(handle ->
            handle.createUpdate("UPDATE ventas SET id_comprador = :idComprador, id_articulo = :idArticulo, precio = :precio, fecha_transaccion = :fechaTransaccion, estado_venta = :estadoVenta, activo = :activo WHERE id_transaccion = :idTransaccion")
                .bind("idTransaccion", venta.getIdTransaccion())
                .bind("idComprador", venta.getIdComprador())
                .bind("idArticulo", venta.getIdArticulo())
                .bind("precio", venta.getPrecio())
                .bind("fechaTransaccion", venta.getFechaTransaccion())
                .bind("estadoVenta", venta.getEstadoVenta())
                .bind("activo", venta.isActivo())
                .execute()
        );
        return filas > 0;
    }

    // Baja lógica
    public boolean eliminarVenta(int idTransaccion) {
        int filas = jdbi.withHandle(handle ->
            handle.createUpdate("UPDATE ventas SET activo = false WHERE id_transaccion = :idTransaccion")
                .bind("idTransaccion", idTransaccion)
                .execute()
        );
        return filas > 0;
    }
}
