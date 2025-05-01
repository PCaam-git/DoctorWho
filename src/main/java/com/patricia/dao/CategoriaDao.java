package com.patricia.dao;

import com.patricia.model.Categoria;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class CategoriaDao {
    private final Jdbi jdbi;

    public CategoriaDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    // Insertar categoría
    public void insertarCategoria(Categoria categoria) {
        jdbi.useHandle(handle ->
            handle.createUpdate("INSERT INTO categorias (nombre, descripcion, cantidad, tiene_productos, fecha_actualizacion, precio_medio) " +
                    "VALUES (:nombre, :descripcion, :cantidad, :tieneProductos, :fechaActualizacion, :precioMedio)")
                .bind("nombre", categoria.getNombre())
                .bind("descripcion", categoria.getDescripcion())
                .bind("cantidad", categoria.getCantidad())
                .bind("tieneProductos", categoria.isTieneProductos())
                .bind("fechaActualizacion", categoria.getFechaActualizacion())
                .bind("precioMedio", categoria.getPrecioMedio())
                .execute()
        );
    }

    // Listar categorías
    public List<Categoria> listarCategorias() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM categorias")
                .mapToBean(Categoria.class)
                .list()
        );
    }

    // Actualizar categoría
    public boolean actualizarCategoria(Categoria categoria) {
        int filas = jdbi.withHandle(handle ->
            handle.createUpdate("UPDATE categorias SET nombre = :nombre, descripcion = :descripcion, cantidad = :cantidad, tiene_productos = :tieneProductos, fecha_actualizacion = :fechaActualizacion, precio_medio = :precioMedio WHERE id = :id")
                .bind("id", categoria.getId())
                .bind("nombre", categoria.getNombre())
                .bind("descripcion", categoria.getDescripcion())
                .bind("cantidad", categoria.getCantidad())
                .bind("tieneProductos", categoria.isTieneProductos())
                .bind("fechaActualizacion", categoria.getFechaActualizacion())
                .bind("precioMedio", categoria.getPrecioMedio())
                .execute()
        );
        return filas > 0;
    }

    // Baja lógica
    public boolean eliminarCategoria(int id) {
        int filas = jdbi.withHandle(handle ->
            handle.createUpdate("UPDATE categorias SET cantidad = 0, tiene_productos = false WHERE id = :id")
                .bind("id", id)
                .execute()
        );
        return filas > 0;
    }
}
