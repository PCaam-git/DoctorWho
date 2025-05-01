package com.patricia.dao;

import com.patricia.model.Articulo;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDate;
import java.util.List;

public class ArticuloDao {
    private Jdbi jdbi;

    public ArticuloDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void insertarArticulo(Articulo articulo) {
        jdbi.useHandle(handle ->
            handle.createUpdate("INSERT INTO articulos (nombre, descripcion, precio, disponible, fecha_añadido) " +
                    "VALUES (:nombre, :descripcion, :precio, :disponible, :fechaAñadido)")
                .bind("nombre", articulo.getNombre())
                .bind("descripcion", articulo.getDescripcion())
                .bind("precio", articulo.getPrecio())
                .bind("disponible", articulo.isDisponible())
                .bind("fechaAñadido", articulo.getFechaAñadido())
                .execute()
        );
    }


    public List<Articulo> listarArticulos() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM articulos")
                .mapToBean(Articulo.class)
                .list()
        );
    }

    public void crearArticulo(Articulo articulo) {
        jdbi.withHandle(handle -> 
            handle.createUpdate("INSERT INTO articulos (nombre, descripcion, precio, disponible, fecha_añadido) VALUES (:nombre, :descripcion, :precio, :disponible, :fechaAñadido)")
                .bindBean(articulo)
                .execute()
        );
    }

    public void actualizarArticulo(Articulo articulo) {
        jdbi.withHandle(handle -> 
            handle.createUpdate("UPDATE articulos SET nombre = :nombre, descripcion = :descripcion, precio = :precio, disponible = :disponible WHERE id = :id")
                .bindBean(articulo)
                .execute()
        );
    }

    public void eliminarArticulo(int id) {
        jdbi.withHandle(handle -> 
            handle.createUpdate("DELETE FROM articulos WHERE id = :id")
                .bind("id", id)
                .execute()
        );
    }

    public Articulo obtenerArticulo(int id) {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM articulos WHERE id = :id")
                .bind("id", id)
                .mapToBean(Articulo.class)
                .findOne()
                .orElse(null)
        );
    }
}

