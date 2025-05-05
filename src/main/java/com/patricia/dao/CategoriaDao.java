package com.patricia.dao;

import com.patricia.model.Categoria;
import com.patricia.exception.CategoriaNotFoundException;
import static com.patricia.util.Constants.PAGE_SIZE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    
    private Connection connection;

    public CategoriaDao(Connection connection) {
        this.connection = connection;
    }

    public boolean add(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nombre, descripcion, cantidad, tiene_productos, fecha_actualizacion, precio_medio, imagen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        statement = connection.prepareStatement(sql);
        statement.setString(1, categoria.getNombre());
        statement.setString(2, categoria.getDescripcion());
        statement.setInt(3, categoria.getCantidad());
        statement.setBoolean(4, categoria.isTieneProductos());
        statement.setDate(5, categoria.getFechaActualizacion());
        statement.setDouble(6, categoria.getPrecioMedio());
        statement.setString(7, categoria.getImagen());

        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public List<Categoria> getAll() throws SQLException {
        String sql = "SELECT * FROM categorias";
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Categoria> categoriaList = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        
        while (result.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(result.getInt("id"));
            categoria.setNombre(result.getString("nombre"));
            categoria.setDescripcion(result.getString("descripcion"));
            categoria.setCantidad(result.getInt("cantidad"));
            categoria.setTieneProductos(result.getBoolean("tiene_productos"));
            categoria.setFechaActualizacion(result.getDate("fecha_actualizacion"));
            categoria.setPrecioMedio(result.getDouble("precio_medio"));
            categoria.setImagen(result.getString("imagen"));
            categoriaList.add(categoria);
        }

        statement.close();
        
        return categoriaList;
    }

    public Categoria get(int id) throws SQLException, CategoriaNotFoundException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet result = null;

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        
        if (!result.next()) {
            throw new CategoriaNotFoundException();
        }

        Categoria categoria = new Categoria();
        categoria.setId(result.getInt("id"));
        categoria.setNombre(result.getString("nombre"));
        categoria.setDescripcion(result.getString("descripcion"));
        categoria.setCantidad(result.getInt("cantidad"));
        categoria.setTieneProductos(result.getBoolean("tiene_productos"));
        categoria.setFechaActualizacion(result.getDate("fecha_actualizacion"));
        categoria.setPrecioMedio(result.getDouble("precio_medio"));
        categoria.setImagen(result.getString("imagen"));

        statement.close();
        
        return categoria;
    }

    public boolean modify(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ?, cantidad = ?, tiene_productos = ?, fecha_actualizacion = ?, precio_medio = ?, imagen = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, categoria.getNombre());
        statement.setString(2, categoria.getDescripcion());
        statement.setInt(3, categoria.getCantidad());
        statement.setBoolean(4, categoria.isTieneProductos());
        statement.setDate(5, categoria.getFechaActualizacion());
        statement.setDouble(6, categoria.getPrecioMedio());
        statement.setString(7, categoria.getImagen());
        statement.setInt(8, categoria.getId());
        
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public boolean delete(int categoriaId) throws SQLException {
        String sql = "UPDATE categorias SET cantidad = 0, tiene_productos = false WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, categoriaId);
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public int getCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + search + "%");
        ResultSet result = statement.executeQuery();
        result.next();
        return result.getInt(1);
    }
    
    public ArrayList<Categoria> getAll(int page) throws SQLException {
        String sql = "SELECT * FROM categorias ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page);
    }
    
    public ArrayList<Categoria> getAll(int page, String search) throws SQLException {
        if (search == null || search.isEmpty()) {
            return getAll(page);
        }
        String sql = "SELECT * FROM categorias WHERE nombre LIKE ? ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page, search);
    }
    
    private ArrayList<Categoria> launchQuery(String query, int page, String ...search) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
    
        statement = connection.prepareStatement(query);
        if (search.length > 0) {
            statement.setString(1, "%" + search[0] + "%");
            statement.setInt(2, page * PAGE_SIZE);
            statement.setInt(3, PAGE_SIZE);
        } else {
            statement.setInt(1, page * PAGE_SIZE);
            statement.setInt(2, PAGE_SIZE);
        }
    
        result = statement.executeQuery();
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        while (result.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(result.getInt("id"));
            categoria.setNombre(result.getString("nombre"));
            categoria.setDescripcion(result.getString("descripcion"));
            categoria.setCantidad(result.getInt("cantidad"));
            categoria.setTieneProductos(result.getBoolean("tiene_productos"));
            categoria.setFechaActualizacion(result.getDate("fecha_actualizacion"));
            categoria.setPrecioMedio(result.getDouble("precio_medio"));
            categoria.setImagen(result.getString("imagen"));
            categoriaList.add(categoria);
        }
    
        statement.close();
        return categoriaList;
    }
    
    public ArrayList<Categoria> search(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE nombre LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + searchTerm + "%");
        ResultSet result = statement.executeQuery();
    
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        while (result.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(result.getInt("id"));
            categoria.setNombre(result.getString("nombre"));
            categoria.setDescripcion(result.getString("descripcion"));
            categoria.setCantidad(result.getInt("cantidad"));
            categoria.setTieneProductos(result.getBoolean("tiene_productos"));
            categoria.setFechaActualizacion(result.getDate("fecha_actualizacion"));
            categoria.setPrecioMedio(result.getDouble("precio_medio"));
            categoria.setImagen(result.getString("imagen"));
            categoriaList.add(categoria);
        }
    
        statement.close();
        return categoriaList;
    }
    
    public ArrayList<Categoria> searchAdvanced(String nombre, Integer cantidadMin, Integer cantidadMax, Boolean tieneProductos) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM categorias WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (cantidadMin != null) {
            sqlBuilder.append(" AND cantidad >= ?");
            params.add(cantidadMin);
        }
        
        if (cantidadMax != null) {
            sqlBuilder.append(" AND cantidad <= ?");
            params.add(cantidadMax);
        }
        
        if (tieneProductos != null) {
            sqlBuilder.append(" AND tiene_productos = ?");
            params.add(tieneProductos);
        }
        
        sqlBuilder.append(" ORDER BY nombre");
        
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        
        // Establecer los parámetros
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        while (result.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(result.getInt("id"));
            categoria.setNombre(result.getString("nombre"));
            categoria.setDescripcion(result.getString("descripcion"));
            categoria.setCantidad(result.getInt("cantidad"));
            categoria.setTieneProductos(result.getBoolean("tiene_productos"));
            categoria.setFechaActualizacion(result.getDate("fecha_actualizacion"));
            categoria.setPrecioMedio(result.getDouble("precio_medio"));
            categoria.setImagen(result.getString("imagen"));
            categoriaList.add(categoria);
        }
        
        statement.close();
        return categoriaList;
    }

    public int getCountAdvanced(String nombre, Integer cantidadMin, Integer cantidadMax, Boolean tieneProductos) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM categorias WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (cantidadMin != null) {
            sqlBuilder.append(" AND cantidad >= ?");
            params.add(cantidadMin);
        }
        
        if (cantidadMax != null) {
            sqlBuilder.append(" AND cantidad <= ?");
            params.add(cantidadMax);
        }
        
        if (tieneProductos != null) {
            sqlBuilder.append(" AND tiene_productos = ?");
            params.add(tieneProductos);
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