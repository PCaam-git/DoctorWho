package com.patricia.dao;

import com.patricia.model.Usuario;
import com.patricia.exception.UsuarioNotFoundException;
import static com.patricia.util.Constants.PAGE_SIZE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    
    private Connection connection;

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    public boolean add(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, contraseña, rol, fecha_registro, estado, activo, imagen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getEmail());
        statement.setString(3, usuario.getContraseña());
        statement.setString(4, usuario.getRol());
        statement.setDate(5, usuario.getFechaRegistro());
        statement.setString(6, usuario.getEstado());
        statement.setBoolean(7, usuario.isActivo());
        statement.setString(8, usuario.getImagen());

        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public List<Usuario> getAll() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Usuario> usuarioList = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        
        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setContraseña(result.getString("contraseña"));
            usuario.setRol(result.getString("rol"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setEstado(result.getString("estado"));
            usuario.setActivo(result.getBoolean("activo"));
            usuario.setImagen(result.getString("imagen"));
            usuarioList.add(usuario);
        }

        statement.close();
        
        return usuarioList;
    }

    public Usuario get(int id) throws SQLException, UsuarioNotFoundException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet result = null;

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        
        if (!result.next()) {
            throw new UsuarioNotFoundException();
        }

        Usuario usuario = new Usuario();
        usuario.setId(result.getInt("id"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setEmail(result.getString("email"));
        usuario.setContraseña(result.getString("contraseña"));
        usuario.setRol(result.getString("rol"));
        usuario.setFechaRegistro(result.getDate("fecha_registro"));
        usuario.setEstado(result.getString("estado"));
        usuario.setActivo(result.getBoolean("activo"));
        usuario.setImagen(result.getString("imagen"));

        statement.close();
        
        return usuario;
    }

    public boolean modify(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, contraseña = ?, rol = ?, fecha_registro = ?, estado = ?, activo = ?, imagen = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getEmail());
        statement.setString(3, usuario.getContraseña());
        statement.setString(4, usuario.getRol());
        statement.setDate(5, usuario.getFechaRegistro());
        statement.setString(6, usuario.getEstado());
        statement.setBoolean(7, usuario.isActivo());
        statement.setString(8, usuario.getImagen());
        statement.setInt(9, usuario.getId());
        
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public boolean delete(int usuarioId) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, usuarioId);
        int affectedRows = statement.executeUpdate();
        
        return affectedRows != 0;
    }

    public String loginUser(String username, String password) throws SQLException, UsuarioNotFoundException {
        String sql = "SELECT rol FROM usuarios WHERE nombre = ? AND contraseña = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new UsuarioNotFoundException();
        }

        return result.getString("rol");
    }

    public int getCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre LIKE ? OR email LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + search + "%");
        statement.setString(2, "%" + search + "%");
        ResultSet result = statement.executeQuery();
        result.next();
        return result.getInt(1);
    }
    
    public ArrayList<Usuario> getAll(int page) throws SQLException {
        String sql = "SELECT * FROM usuarios ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page);
    }
    
    public ArrayList<Usuario> getAll(int page, String search) throws SQLException {
        if (search == null || search.isEmpty()) {
            return getAll(page);
        }
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? OR email LIKE ? ORDER BY nombre LIMIT ?, ?";
        return launchQuery(sql, page, search);
    }
    
    private ArrayList<Usuario> launchQuery(String query, int page, String ...search) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
    
        statement = connection.prepareStatement(query);
        if (search.length > 0) {
            statement.setString(1, "%" + search[0] + "%");
            statement.setString(2, "%" + search[0] + "%");
            statement.setInt(3, page * PAGE_SIZE);
            statement.setInt(4, PAGE_SIZE);
        } else {
            statement.setInt(1, page * PAGE_SIZE);
            statement.setInt(2, PAGE_SIZE);
        }
    
        result = statement.executeQuery();
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setRol(result.getString("rol"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setEstado(result.getString("estado"));
            usuario.setActivo(result.getBoolean("activo"));
            usuario.setImagen(result.getString("imagen"));
            usuarioList.add(usuario);
        }
    
        statement.close();
        return usuarioList;
    }
    
    public ArrayList<Usuario> search(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? OR email LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + searchTerm + "%");
        statement.setString(2, "%" + searchTerm + "%");
        ResultSet result = statement.executeQuery();
    
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setRol(result.getString("rol"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setEstado(result.getString("estado"));
            usuario.setActivo(result.getBoolean("activo"));
            usuario.setImagen(result.getString("imagen"));
            usuarioList.add(usuario);
        }
    
        statement.close();
        return usuarioList;
    }
    
    public ArrayList<Usuario> searchAdvanced(String nombre, String email, String rol, Boolean activo) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM usuarios WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (email != null && !email.isEmpty()) {
            sqlBuilder.append(" AND email LIKE ?");
            params.add("%" + email + "%");
        }
        
        if (rol != null && !rol.isEmpty()) {
            sqlBuilder.append(" AND rol = ?");
            params.add(rol);
        }
        
        if (activo != null) {
            sqlBuilder.append(" AND activo = ?");
            params.add(activo);
        }
        
        sqlBuilder.append(" ORDER BY nombre");
        
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        
        // Establecer los parámetros
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setRol(result.getString("rol"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setEstado(result.getString("estado"));
            usuario.setActivo(result.getBoolean("activo"));
            usuario.setImagen(result.getString("imagen"));
            usuarioList.add(usuario);
        }
        
        statement.close();
        return usuarioList;
    }

    public int getCountAdvanced(String nombre, String email, String rol, Boolean activo) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM usuarios WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        
        if (email != null && !email.isEmpty()) {
            sqlBuilder.append(" AND email LIKE ?");
            params.add("%" + email + "%");
        }
        
        if (rol != null && !rol.isEmpty()) {
            sqlBuilder.append(" AND rol = ?");
            params.add(rol);
        }
        
        if (activo != null) {
            sqlBuilder.append(" AND activo = ?");
            params.add(activo);
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