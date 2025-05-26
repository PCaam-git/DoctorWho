package com.doctorwho.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.doctorwho.exception.CategoriaNotFoundException;
import com.doctorwho.model.Categoria;

import java.math.BigDecimal;


public class CategoriaDao {

    private Connection connection;

    public CategoriaDao(Connection connection) {
        this.connection = connection;
    }

    // GET BY ID
    public Categoria getCategoriaById(int id) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setCantidad(rs.getInt("cantidad"));
                    categoria.setTieneProductos(rs.getBoolean("tiene_productos"));
                    categoria.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
                    categoria.setPrecioMedio(rs.getBigDecimal("precio_medio"));
                    categoria.setImagen(rs.getString("imagen"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }

    // GET ALL
    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setCantidad(rs.getInt("cantidad"));
                categoria.setTieneProductos(rs.getBoolean("tiene_productos"));
                categoria.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
                categoria.setPrecioMedio(rs.getBigDecimal("precio_medio"));
                categoria.setImagen(rs.getString("imagen"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    // ADD
    public boolean addCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre, descripcion, cantidad, tiene_productos, fecha_actualizacion, precio_medio, imagen) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getCantidad());
            stmt.setBoolean(4, categoria.isTieneProductos());
            stmt.setDate(5, categoria.getFechaActualizacion());
            stmt.setBigDecimal(6, categoria.getPrecioMedio());
            stmt.setString(7, categoria.getImagen());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE
    public boolean updateCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ?, cantidad = ?, tiene_productos = ?, fecha_actualizacion = ?, precio_medio = ?, imagen = ? " +
                     "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getCantidad());
            stmt.setBoolean(4, categoria.isTieneProductos());
            stmt.setDate(5, categoria.getFechaActualizacion());
            stmt.setBigDecimal(6, categoria.getPrecioMedio());
            stmt.setString(7, categoria.getImagen());
            stmt.setInt(8, categoria.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deleteCategoria(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     // SEARCH
    public List<Categoria> searchCategorias(String q, boolean mostrarSoloConProductos) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE 1=1"
                   + (q != null && !q.isEmpty() ? " AND (nombre LIKE ? OR descripcion LIKE ?)" : "")
                   + (mostrarSoloConProductos ? " AND tiene_productos = true" : "");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            if (q != null && !q.isEmpty()) {
                stmt.setString(index++, "%" + q + "%");
                stmt.setString(index++, "%" + q + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setCantidad(rs.getInt("cantidad"));
                    categoria.setTieneProductos(rs.getBoolean("tiene_productos"));
                    categoria.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
                    categoria.setPrecioMedio(rs.getBigDecimal("precio_medio"));
                    categoria.setImagen(rs.getString("imagen"));
                    categorias.add(categoria);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    // PAGINACIÓN
    public int countAllCategorias(String q, boolean mostrarSoloConProductos) {
        String sql = "SELECT COUNT(*) FROM categorias WHERE 1=1"
                + (q != null && !q.isEmpty() ? " AND (nombre LIKE ? OR descripcion LIKE ?)" : "")
                + (mostrarSoloConProductos ? " AND tiene_productos = true" : "");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            if (q != null && !q.isEmpty()) {
                stmt.setString(index++, "%" + q + "%");
                stmt.setString(index++, "%" + q + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Categoria> getCategoriasPaged(String q, boolean mostrarSoloConProductos, int offset, int limit) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE 1=1"
                + (q != null && !q.isEmpty() ? " AND (nombre LIKE ? OR descripcion LIKE ?)" : "")
                + (mostrarSoloConProductos ? " AND tiene_productos = true" : "")
                + " ORDER BY nombre LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            if (q != null && !q.isEmpty()) {
                stmt.setString(index++, "%" + q + "%");
                stmt.setString(index++, "%" + q + "%");
            }

            stmt.setInt(index++, limit);
            stmt.setInt(index++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setCantidad(rs.getInt("cantidad"));
                    categoria.setTieneProductos(rs.getBoolean("tiene_productos"));
                    categoria.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
                    categoria.setPrecioMedio(rs.getBigDecimal("precio_medio"));
                    categoria.setImagen(rs.getString("imagen"));
                    categorias.add(categoria);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}