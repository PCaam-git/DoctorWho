package com.patricia.test;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;

import java.time.LocalDate;
import java.util.List;

public class CategoriaTest {
    public static void main(String[] args) {
        Database database = new Database();
        database.connect();

        CategoriaDao dao = new CategoriaDao(database.getJdbi());

        // 1. Insertar una categoría
        Categoria nueva = new Categoria();
        nueva.setNombre("Almacén Gallifrey");
        nueva.setDescripcion("Depósito central de merchandising del universo");
        nueva.setCantidad(200);
        nueva.setTieneProductos(true);
        nueva.setFechaActualizacion(LocalDate.now());
        nueva.setPrecioMedio(42.50);

        dao.insertarCategoria(nueva);
        System.out.println("✅ Categoría insertada.");

        // 2. Listar categorías
        System.out.println("📋 Listado de categorías:");
        List<Categoria> categorias = dao.listarCategorias();
        for (Categoria c : categorias) {
            System.out.println(c);
        }

        // 3. Modificar la primera categoría
        if (!categorias.isEmpty()) {
            Categoria primera = categorias.get(0);
            primera.setNombre("Almacén Gallifrey (actualizado)");
            primera.setPrecioMedio(38.90);
            dao.actualizarCategoria(primera);
            System.out.println("✏️ Categoría modificada.");
        }

        // 4. Baja lógica (desactivar)
        if (!categorias.isEmpty()) {
            dao.eliminarCategoria(categorias.get(0).getId());
            System.out.println("❌ Categoría desactivada.");
        }

        // 5. Listar otra vez para comprobar cambios
        System.out.println("\n🔁 Lista final de categorías:");
        List<Categoria> actualizadas = dao.listarCategorias();
        for (Categoria c : actualizadas) {
            System.out.println(c);
        }
    }
}
