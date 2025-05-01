package com.patricia.test;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;

import java.time.LocalDate;
import java.util.List;

public class ArticuloTest {
    public static void main(String[] args) {
        Database database = new Database();
        database.connect();

        ArticuloDao dao = new ArticuloDao(database.getJdbi());

        Articulo articulo = new Articulo();
        articulo.setNombre("Dalek");
        articulo.setDescripcion("¡Exterminate!");
        articulo.setPrecio(49.99);
        articulo.setDisponible(true);
        articulo.setFechaAñadido(LocalDate.now());

        dao.insertarArticulo(articulo);
        System.out.println("✅ Artículo insertado.");

        List<Articulo> articulos = dao.listarArticulos();
        System.out.println("📋 Artículos:");
        for (Articulo a : articulos) {
            System.out.println(a);
        }
    }
}
