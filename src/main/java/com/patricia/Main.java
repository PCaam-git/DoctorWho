package com.patricia;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Database database = new Database();
            database.connect();

            // Crear DAO con la conexión
            ArticuloDao articuloDao = new ArticuloDao(database.getConnection());

            // Crear nuevo artículo
            Articulo nuevoArticulo = new Articulo();
            nuevoArticulo.setNombre("Sonic Screwdriver");
            nuevoArticulo.setDescripcion("Destornillador sónico del Doctor");
            nuevoArticulo.setPrecio(29.99);
            nuevoArticulo.setDisponible(true);
            // Convertir LocalDate a java.sql.Date
            nuevoArticulo.setFechaAñadido(Date.valueOf(LocalDate.now()));
            nuevoArticulo.setImagen("default.jpg");

            // Insertar el artículo en la base de datos
            articuloDao.add(nuevoArticulo);
            System.out.println("✅ Artículo insertado correctamente.");

            // Listar todos los artículos
            System.out.println("📋 Listado de artículos:");
            List<Articulo> articulos = articuloDao.getAll();
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
            
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}