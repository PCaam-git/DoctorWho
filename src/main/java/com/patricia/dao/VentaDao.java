package com.patricia.dao;

import com.patricia.model.Venta;
import com.patricia.exception.VentaNotFoundException;
import static com.patricia.util.Constants.PAGE_SIZE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDao {
    
    private Connection connection;

    public VentaDao(Connection connection) {
        this.connection = connection;
    }

    public boolean add(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (id_comprador, id_articulo, precio, fecha_transaccion, estado_venta, pagado, activo, imagen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        statement = connection.prepareStatement(sql);
        statement.setInt(1, venta.getIdComprador());
        statement.setInt(2, venta.getIdArticulo());
        statement.setDouble(3, venta.getPrecio());
        statement.setDate(4, venta.getFechaTransaccion());
        statement.setString(5, venta.getEstadoVenta());
        statement.setBoolean(6, venta.isPagado());
        statement.setBoolean(7, venta.isActivo());
        statement.setString(8, venta.getImagen());

        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public List<Venta> getAll() throws SQLException {
        String sql = "SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "ORDER BY v.fecha_transaccion DESC";
        
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Venta> ventaList = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        
        while (result.next()) {
            Venta venta = new Venta();
            venta.setIdTransaccion(result.getInt("id_transaccion"));
            venta.setIdComprador(result.getInt("id_comprador"));
            venta.setNombreComprador(result.getString("nombreComprador"));
            venta.setIdArticulo(result.getInt("id_articulo"));
            venta.setNombreArticulo(result.getString("nombreArticulo"));
            venta.setPrecio(result.getDouble("precio"));
            venta.setFechaTransaccion(result.getDate("fecha_transaccion"));
            venta.setEstadoVenta(result.getString("estado_venta"));
            venta.setPagado(result.getBoolean("pagado"));
            venta.setActivo(result.getBoolean("activo"));
            venta.setImagen(result.getString("imagen"));
            ventaList.add(venta);
        }

        statement.close();
        
        return ventaList;
    }

    public Venta get(int idTransaccion) throws SQLException, VentaNotFoundException {
        String sql = "SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "WHERE v.id_transaccion = ?";
        
        PreparedStatement statement = null;
        ResultSet result = null;

        statement = connection.prepareStatement(sql);
        statement.setInt(1, idTransaccion);
        result = statement.executeQuery();
        
        if (!result.next()) {
            throw new VentaNotFoundException();
        }

        Venta venta = new Venta();
        venta.setIdTransaccion(result.getInt("id_transaccion"));
        venta.setIdComprador(result.getInt("id_comprador"));
        venta.setNombreComprador(result.getString("nombreComprador"));
        venta.setIdArticulo(result.getInt("id_articulo"));
        venta.setNombreArticulo(result.getString("nombreArticulo"));
        venta.setPrecio(result.getDouble("precio"));
        venta.setFechaTransaccion(result.getDate("fecha_transaccion"));
        venta.setEstadoVenta(result.getString("estado_venta"));
        venta.setPagado(result.getBoolean("pagado"));
        venta.setActivo(result.getBoolean("activo"));
        venta.setImagen(result.getString("imagen"));

        statement.close();
        
        return venta;
    }

    public boolean modify(Venta venta) throws SQLException {
        String sql = "UPDATE ventas SET id_comprador = ?, id_articulo = ?, precio = ?, " +
                "fecha_transaccion = ?, estado_venta = ?, pagado = ?, activo = ?, imagen = ? WHERE id_transaccion = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, venta.getIdComprador());
        statement.setInt(2, venta.getIdArticulo());
        statement.setDouble(3, venta.getPrecio());
        statement.setDate(4, venta.getFechaTransaccion());
        statement.setString(5, venta.getEstadoVenta());
        statement.setBoolean(6, venta.isPagado());
        statement.setBoolean(7, venta.isActivo());
        statement.setString(8, venta.getImagen());
        statement.setInt(9, venta.getIdTransaccion());
        
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public boolean delete(int idTransaccion) throws SQLException {
        String sql = "UPDATE ventas SET activo = false WHERE id_transaccion = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idTransaccion);
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public int getCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas WHERE estado_venta LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + search + "%");
        ResultSet result = statement.executeQuery();
        result.next();
        return result.getInt(1);
    }
    
    public ArrayList<Venta> getAll(int page) throws SQLException {
        String sql = "SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "ORDER BY v.fecha_transaccion DESC LIMIT ?, ?";
        return launchQuery(sql, page);
    }
    
    public ArrayList<Venta> getAll(int page, String search) throws SQLException {
        if (search == null || search.isEmpty()) {
            return getAll(page);
        }
        String sql = "SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "WHERE v.estado_venta LIKE ? " +
                "ORDER BY v.fecha_transaccion DESC LIMIT ?, ?";
        return launchQuery(sql, page, search);
    }
    
    private ArrayList<Venta> launchQuery(String query, int page, String ...search) throws SQLException {
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
        ArrayList<Venta> ventaList = new ArrayList<>();
        while (result.next()) {
            Venta venta = new Venta();
            venta.setIdTransaccion(result.getInt("id_transaccion"));
            venta.setIdComprador(result.getInt("id_comprador"));
            venta.setNombreComprador(result.getString("nombreComprador"));
            venta.setIdArticulo(result.getInt("id_articulo"));
            venta.setNombreArticulo(result.getString("nombreArticulo"));
            venta.setPrecio(result.getDouble("precio"));
            venta.setFechaTransaccion(result.getDate("fecha_transaccion"));
            venta.setEstadoVenta(result.getString("estado_venta"));
            venta.setPagado(result.getBoolean("pagado"));
            venta.setActivo(result.getBoolean("activo"));
            venta.setImagen(result.getString("imagen"));
            ventaList.add(venta);
        }
    
        statement.close();
        return ventaList;
    }
    
    public ArrayList<Venta> search(String searchTerm) throws SQLException {
        String sql = "SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "WHERE v.estado_venta LIKE ? " +
                "ORDER BY v.fecha_transaccion DESC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + searchTerm + "%");
        ResultSet result = statement.executeQuery();
    
        ArrayList<Venta> ventaList = new ArrayList<>();
        while (result.next()) {
            Venta venta = new Venta();
            venta.setIdTransaccion(result.getInt("id_transaccion"));
            venta.setIdComprador(result.getInt("id_comprador"));
            venta.setNombreComprador(result.getString("nombreComprador"));
            venta.setIdArticulo(result.getInt("id_articulo"));
            venta.setNombreArticulo(result.getString("nombreArticulo"));
            venta.setPrecio(result.getDouble("precio"));
            venta.setFechaTransaccion(result.getDate("fecha_transaccion"));
            venta.setEstadoVenta(result.getString("estado_venta"));
            venta.setPagado(result.getBoolean("pagado"));
            venta.setActivo(result.getBoolean("activo"));
            venta.setImagen(result.getString("imagen"));
            ventaList.add(venta);
        }
    
        statement.close();
        return ventaList;
    }
    
    public ArrayList<Venta> searchAdvanced(String nombreComprador, String nombreArticulo, Double precioMin, Double precioMax, String estadoVenta) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT v.id_transaccion, v.id_comprador, u.nombre AS nombreComprador, " +
                "v.id_articulo, a.nombre AS nombreArticulo, v.precio, v.fecha_transaccion, " +
                "v.estado_venta, v.pagado, v.activo, v.imagen " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombreComprador != null && !nombreComprador.isEmpty()) {
            sqlBuilder.append(" AND u.nombre LIKE ?");
            params.add("%" + nombreComprador + "%");
        }
        
        if (nombreArticulo != null && !nombreArticulo.isEmpty()) {
            sqlBuilder.append(" AND a.nombre LIKE ?");
            params.add("%" + nombreArticulo + "%");
        }
        
        if (precioMin != null) {
            sqlBuilder.append(" AND v.precio >= ?");
            params.add(precioMin);
        }
        
        if (precioMax != null) {
            sqlBuilder.append(" AND v.precio <= ?");
            params.add(precioMax);
        }
        
        if (estadoVenta != null && !estadoVenta.isEmpty()) {
            sqlBuilder.append(" AND v.estado_venta = ?");
            params.add(estadoVenta);
        }
        
        sqlBuilder.append(" ORDER BY v.fecha_transaccion DESC");
        
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        
        // Establecer los parámetros
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Venta> ventaList = new ArrayList<>();
        while (result.next()) {
            Venta venta = new Venta();
            venta.setIdTransaccion(result.getInt("id_transaccion"));
            venta.setIdComprador(result.getInt("id_comprador"));
            venta.setNombreComprador(result.getString("nombreComprador"));
            venta.setIdArticulo(result.getInt("id_articulo"));
            venta.setNombreArticulo(result.getString("nombreArticulo"));
            venta.setPrecio(result.getDouble("precio"));
            venta.setFechaTransaccion(result.getDate("fecha_transaccion"));
            venta.setEstadoVenta(result.getString("estado_venta"));
            venta.setPagado(result.getBoolean("pagado"));
            venta.setActivo(result.getBoolean("activo"));
            venta.setImagen(result.getString("imagen"));
            ventaList.add(venta);
        }
        
        statement.close();
        return ventaList;
    }

    public int getCountAdvanced(String nombreComprador, String nombreArticulo, Double precioMin, Double precioMax, String estadoVenta) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) " +
                "FROM ventas v " +
                "JOIN usuarios u ON v.id_comprador = u.id " +
                "JOIN articulos a ON v.id_articulo = a.id " +
                "WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombreComprador != null && !nombreComprador.isEmpty()) {
            sqlBuilder.append(" AND u.nombre LIKE ?");
            params.add("%" + nombreComprador + "%");
        }
        
        if (nombreArticulo != null && !nombreArticulo.isEmpty()) {
            sqlBuilder.append(" AND a.nombre LIKE ?");
            params.add("%" + nombreArticulo + "%");
        }
        
        if (precioMin != null) {
            sqlBuilder.append(" AND v.precio >= ?");
            params.add(precioMin);
        }
        
        if (precioMax != null) {
            sqlBuilder.append(" AND v.precio <= ?");
            params.add(precioMax);
        }
        
        if (estadoVenta != null && !estadoVenta.isEmpty()) {
            sqlBuilder.append(" AND v.estado_venta = ?");
            params.add(estadoVenta);
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