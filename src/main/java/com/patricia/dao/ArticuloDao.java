package com.patricia.dao;

import com.patricia.model.Articulo;
import com.patricia.exception.ArticuloNotFoundException;
import static com.patricia.util.Constants.PAGE_SIZE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao {

    private Connection connection;

    public ArticuloDao(Connection connection) {
        this.connection = connection;
    }

    public boolean add(Articulo articulo) throws SQLException {
        String sql = "INSERT INTO articulos (nombre, descripcion, precio, disponible, fecha_añadido, imagen) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        statement = connection.prepareStatement(sql);
        statement.setString(1, articulo.getNombre());
        statement.setString(2, articulo.getDescripcion());
        statement.setDouble(3, articulo.getPrecio());
        statement.setBoolean(4, articulo.isDisponible());
        statement.setDate(5, articulo.getFechaAñadido());
        statement.setString(6, articulo.getImagen());

        int affectedRows = statement.executeUpdate();

        return affectedRows != 0;
    }

    public List<Articulo> getAll() throws SQLException {
        String sql = "SELECT * FROM articulos";
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Articulo> articuloList = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();

        while (result.next()) {
            Articulo articulo = new Articulo();
            articulo.setId(result.getInt("id"));
            articulo.setNombre(result.getString("nombre"));
            articulo.setDescripcion(result.getString("descripcion"));
            articulo.setPrecio(result.getDouble("precio"));
            articulo.setDisponible(result.getBoolean("disponible"));
            articulo.setFechaAñadido(result.getDate("fecha_añadido"));
            articulo.setImagen(result.getString("imagen"));
            articuloList.add(articulo);
        }

        statement.close();

        return articuloList;
    }

    public Articulo get(int id) throws SQLException, ArticuloNotFoundException {
        String sql = "SELECT * FROM articulos WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet result = null;

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();

        if (!result.next()) {
            throw new ArticuloNotFoundException();
        }

        Articulo articulo = new Articulo();
        articulo.setId(result.getInt("id"));
        articulo.setNombre(result.getString("nombre"));
        articulo.setDescripcion(result.getString("descripcion"));
        articulo.setPrecio(result.getDouble("precio"));
        articulo.setDisponible(result.getBoolean("disponible"));
        articulo.setFechaAñadido(result.getDate("fecha_añadido"));
        articulo.setImagen(result.getString("imagen"));

        statement.close();

        return articulo;
    }

    public boolean modify(Articulo articulo) throws SQLException {
        String sql = "UPDATE articulos SET nombre = ?, descripcion = ?, precio = ?, disponible = ?, fecha_añadido = ?, imagen = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, articulo.getNombre());
        statement.setString(2, articulo.getDescripcion());
        statement.setDouble(3, articulo.getPrecio());
        statement.setBoolean(4, articulo.isDisponible());
        statement.setDate(5, articulo.getFechaAñadido());
        statement.setString(6, articulo.getImagen());
        statement.setInt(7, articulo.getId());

        int affectedRows = statement.executeUpdate();

        return affectedRows != 0;
    }

    public boolean delete(int articuloId) throws SQLException {
        String sql = "DELETE FROM articulos WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, articuloId);
        int affectedRows = statement.executeUpdate();

        return affectedRows != 0;
    }

    public int getCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM articulos WHERE nombre LIKE ? OR descripcion LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + search + "%");
        statement.setString(2, "%" + search + "%");
        ResultSet result = statement.executeQuery();
        result.next();
        return result.getInt(1);
    }
    
    public ArrayList<Articulo> getAll(int page) throws SQLException {
        String sql = "SELECT * FROM articulos ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page);
    }
    
    public ArrayList<Articulo> getAll(int page, String search) throws SQLException {
        if (search == null || search.isEmpty()) {
            return getAll(page);
        }
    
        String sql = "SELECT * FROM articulos WHERE nombre LIKE ? OR descripcion LIKE ? ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page, search);
    }
    
    private ArrayList<Articulo> launchQuery(String query, int page, String ...search) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
    
    
        statement = connection.prepareStatement(query);
        if (search.length > 0) {
            // Listado de búsqueda
            statement.setString(1, "%" + search[0] + "%");
            statement.setString(2, "%" + search[0] + "%");
            statement.setInt(3, page * PAGE_SIZE);
            statement.setInt(4, PAGE_SIZE);
        } else {
            // Listado completo (sin búsqueda)
            statement.setInt(1, page * PAGE_SIZE);
            statement.setInt(2, PAGE_SIZE);
        }
        result = statement.executeQuery();
        ArrayList<Articulo> articuloList = new ArrayList<>();
        while (result.next()) {
            Articulo articulo = new Articulo();
            articulo.setId(result.getInt("id"));
            articulo.setNombre(result.getString("nombre"));
            articulo.setDescripcion(result.getString("descripcion"));
            articulo.setPrecio(result.getDouble("precio"));
            articulo.setDisponible(result.getBoolean("disponible"));
            articulo.setFechaAñadido(result.getDate("fecha_añadido"));
            articuloList.add(articulo);
        }
    
        statement.close();
    
        return articuloList;
    }
    
    public ArrayList<Articulo> search(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM articulos WHERE nombre LIKE ? OR descripcion LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + searchTerm + "%");
        statement.setString(2, "%" + searchTerm + "%");
        ResultSet result = statement.executeQuery();
        
        ArrayList<Articulo> articuloList = new ArrayList<>();
        while (result.next()) {
            Articulo articulo = new Articulo();
            articulo.setId(result.getInt("id"));
            articulo.setNombre(result.getString("nombre"));
            articulo.setDescripcion(result.getString("descripcion"));
            articulo.setPrecio(result.getDouble("precio"));
            articulo.setDisponible(result.getBoolean("disponible"));
            articulo.setFechaAñadido(result.getDate("fecha_añadido"));
            articuloList.add(articulo);
        }
        
        statement.close();
        return articuloList;
    }

    public ArrayList<Articulo> searchAdvanced(String nombre, Double precioMin, Double precioMax, Boolean disponible) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM articulos WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (precioMin != null) {
            sqlBuilder.append(" AND precio >= ?");
            params.add(precioMin);
        }
        
        if (precioMax != null) {
            sqlBuilder.append(" AND precio <= ?");
            params.add(precioMax);
        }
        
        if (disponible != null) {
            sqlBuilder.append(" AND disponible = ?");
            params.add(disponible);
        }
        
        sqlBuilder.append(" ORDER BY nombre");
        
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        
        // Establecer los parámetros
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Articulo> articuloList = new ArrayList<>();
        while (result.next()) {
            Articulo articulo = new Articulo();
            articulo.setId(result.getInt("id"));
            articulo.setNombre(result.getString("nombre"));
            articulo.setDescripcion(result.getString("descripcion"));
            articulo.setPrecio(result.getDouble("precio"));
            articulo.setDisponible(result.getBoolean("disponible"));
            articulo.setFechaAñadido(result.getDate("fecha_añadido"));
            articulo.setImagen(result.getString("imagen"));
            articuloList.add(articulo);
        }
        
        statement.close();
        return articuloList;
    }
    
    public int getCountAdvanced(String nombre, Double precioMin, Double precioMax, Boolean disponible) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM articulos WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (precioMin != null) {
            sqlBuilder.append(" AND precio >= ?");
            params.add(precioMin);
        }
        
        if (precioMax != null) {
            sqlBuilder.append(" AND precio <= ?");
            params.add(precioMax);
        }
        
        if (disponible != null) {
            sqlBuilder.append(" AND disponible = ?");
            params.add(disponible);
        }
        
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        
        // Establecer los parámetros
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        
        ResultSet result = statement.executeQuery();
        result.next();
        return result.getInt(1);
    }
}