package com.patricia;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;


import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
            database.connect();

            // Crear DAO con JDBI
            ArticuloDao articuloDao = new ArticuloDao(database.getJdbi());

            // Crear nuevo artículo
            Articulo nuevoArticulo = new Articulo();
            nuevoArticulo.setNombre("Sonic Screwdriver");
            nuevoArticulo.setDescripcion("Destornillador sónico del Doctor");
            nuevoArticulo.setPrecio(29.99);
            nuevoArticulo.setDisponible(true);
            nuevoArticulo.setFechaAñadido(java.time.LocalDate.now());

            // Insertar el artículo en la base de datos
            articuloDao.insertarArticulo(nuevoArticulo);
            System.out.println("✅ Artículo insertado correctamente.");

            // Listar todos los artículos
            System.out.println("📋 Listado de artículos:");
            List<Articulo> articulos = articuloDao.listarArticulos();
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
    }
}
