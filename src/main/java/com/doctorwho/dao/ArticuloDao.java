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
        String sql = "SELECT a.*, c.nombre AS categoria_nombre, a.fecha_anadido AS fechaAnadido " +
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
                    articulo.setFechaAnadido(rs.getDate("fechaAnadido"));
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
        String sql = "SELECT a.*, c.nombre AS categoria_nombre, a.fecha_anadido AS fechaAnadido " +
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
                articulo.setFechaAnadido(rs.getDate("fechaAnadido"));
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

    // Métodos de filtrado para la lista
    public List<Articulo> getArticulosFiltrados(String busqueda, Integer categoriaId, 
                                               Boolean disponible, int offset, int limit) {
        List<Articulo> articulos = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, c.nombre AS categoria_nombre, a.fecha_anadido AS fechaAnadido, a.imagen ");
        sql.append("FROM articulos a ");
        sql.append("LEFT JOIN categorias c ON a.categoria_id = c.id ");
        sql.append("WHERE 1=1 ");

        List<Object> parametros = new ArrayList<>();

        // Filtro por búsqueda (nombre o descripción)
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append("AND (a.nombre LIKE ? OR a.descripcion LIKE ?) ");
            parametros.add("%" + busqueda + "%");
            parametros.add("%" + busqueda + "%");
        }

        // Filtro por categoría
        if (categoriaId != null) {
            sql.append("AND a.categoria_id = ? ");
            parametros.add(categoriaId);
        }

        // Filtro por disponibilidad
        if (disponible != null) {
            sql.append("AND a.disponible = ? ");
            parametros.add(disponible);
        }

        sql.append("ORDER BY a.nombre LIMIT ? OFFSET ?");
        parametros.add(limit);
        parametros.add(offset);

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            // Establecer parámetros
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setDisponible(rs.getBoolean("disponible"));
                    articulo.setPrecio(rs.getBigDecimal("precio"));
                    articulo.setFechaAnadido(rs.getDate("fechaAnadido"));
                    articulo.setCategoriaId(rs.getInt("categoria_id"));
                    articulo.setCategoriaNombre(rs.getString("categoria_nombre"));
                    articulo.setImagen(rs.getString("imagen"));
                    articulos.add(articulo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    // Método para contar artículos filtrados
    public int countArticulosFiltrados(String busqueda, Integer categoriaId, Boolean disponible) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM articulos a WHERE 1=1 ");

        List<Object> parametros = new ArrayList<>();

        // Mismos filtros que en getArticulosFiltrados
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append("AND (a.nombre LIKE ? OR a.descripcion LIKE ?) ");
            parametros.add("%" + busqueda + "%");
            parametros.add("%" + busqueda + "%");
        }

        if (categoriaId != null) {
            sql.append("AND a.categoria_id = ? ");
            parametros.add(categoriaId);
        }

        if (disponible != null) {
            sql.append("AND a.disponible = ? ");
            parametros.add(disponible);
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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