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
}

