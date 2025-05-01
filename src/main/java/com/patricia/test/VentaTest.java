package com.patricia.test;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;

import java.time.LocalDate;
import java.util.List;

public class VentaTest {
    public static void main(String[] args) {
        Database database = new Database();
        database.connect();

        VentaDao dao = new VentaDao(database.getJdbi());

        // Crear una nueva venta
        Venta nueva = new Venta();
        nueva.setIdComprador(1);               
        nueva.setIdArticulo(1);                
        nueva.setPrecio(49.99);
        nueva.setFechaTransaccion(LocalDate.now());
        nueva.setEstadoVenta("pendiente");
        nueva.setPagado(false);
        nueva.setActivo(true);

        dao.insertarVenta(nueva);
        System.out.println("✅ Venta insertada.");

        // Listar todas las ventas
        List<Venta> ventas = dao.listarVentas();
        System.out.println("📋 Lista de ventas:");
        for (Venta venta : ventas) {
            System.out.println(venta);
        }

        // Modificar la primera venta
        if (!ventas.isEmpty()) {
            System.out.println("\n✏️ Modificando venta con ID 1...");
            Venta modificado = ventas.get(0);
            modificado.setEstadoVenta("confirmada");
            dao.actualizarVenta(modificado);
        }

        // Baja lógica (desactivar)
        if (!ventas.isEmpty()) {
            System.out.println("❌ Desactivando venta con ID 1...");
            dao.eliminarVenta(ventas.get(0).getIdTransaccion());
        }

        // Mostrar ventas actualizadas
        System.out.println("\n🔁 Lista actualizada:");
        List<Venta> listaFinal = dao.listarVentas();
        for (Venta venta : listaFinal) {
            System.out.println(venta);
        }
    }
}
