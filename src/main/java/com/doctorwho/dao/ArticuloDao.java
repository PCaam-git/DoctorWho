package com.doctorwho.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.doctorwho.exception.ArticuloNotFoundException;
import com.doctorwho.model.Articulo;

import java.math.BigDecimal;

public class ArticuloDao {

    private Connection connection;

    public ArticuloDao(Connection connection) {
        this.connection = connection;
    }

    // GET BY ID
    public Articulo getArticuloById(int id) {
        Articulo articulo = null;
        String sql = "SELECT a.*, c.nombre AS categoria_nombre " +
                     "FROM articulos a " +
                     "LEFT JOIN categorias c ON a.categoria_id = c.id " +
                     "WHERE a.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setDisponible(rs.getBoolean("disponible"));
                    articulo.setPrecio(rs.getBigDecimal("precio"));
                    articulo.setFechaAnadido(rs.getDate("fecha_anadido"));
                    articulo.setCategoriaId(rs.getInt("categoria_id"));
                    articulo.setCategoriaNombre(rs.getString("categoria_nombre"));
                    articulo.setImagen(rs.getString("imagen"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulo;
    }

    // GET ALL
    public List<Articulo> getAllArticulos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.*, c.nombre AS categoria_nombre " +
                     "FROM articulos a " +
                     "LEFT JOIN categorias c ON a.categoria_id = c.id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setId(rs.getInt("id"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setDescripcion(rs.getString("descripcion"));
                articulo.setDisponible(rs.getBoolean("disponible"));
                articulo.setPrecio(rs.getBigDecimal("precio"));
                articulo.setFechaAnadido(rs.getDate("fecha_anadido"));
                articulo.setCategoriaId(rs.getInt("categoria_id"));
                articulo.setCategoriaNombre(rs.getString("categoria_nombre"));
                articulo.setImagen(rs.getString("imagen"));
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    // DELETE
    public boolean deleteArticulo(int id) {
        String sql = "DELETE FROM articulos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ADD
    public boolean addArticulo(Articulo articulo) {
        String sql = "INSERT INTO articulos (nombre, descripcion, disponible, precio, fecha_anadido, categoria_id, imagen) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, articulo.getNombre());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setBoolean(3, articulo.isDisponible());
            stmt.setBigDecimal(4, articulo.getPrecio());
            stmt.setDate(5, articulo.getFechaAnadido());
            stmt.setInt(6, articulo.getCategoriaId());
            stmt.setString(7, articulo.getImagen());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE
    public boolean updateArticulo(Articulo articulo) {
        String sql = "UPDATE articulos SET nombre = ?, descripcion = ?, disponible = ?, precio = ?, fecha_anadido = ?, categoria_id = ?, imagen = ? " +
                     "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, articulo.getNombre());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setBoolean(3, articulo.isDisponible());
            stmt.setBigDecimal(4, articulo.getPrecio());
            stmt.setDate(5, articulo.getFechaAnadido());
            stmt.setInt(6, articulo.getCategoriaId());
            stmt.setString(7, articulo.getImagen());
            stmt.setInt(8, articulo.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
