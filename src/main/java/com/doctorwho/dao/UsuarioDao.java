package com.doctorwho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.doctorwho.exception.UsuarioNotFoundException;
import com.doctorwho.model.Usuario;

import java.math.BigDecimal;

public class UsuarioDao {

    private Connection connection;

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    //Login: devuelve el rol como string ("admin" o "user")
    public String loginUser(String email, String contrasena) throws SQLException, UsuarioNotFoundException {
        String sql = "SELECT es_admin FROM usuarios WHERE email = ? AND contrasena = SHA1(?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, contrasena);
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            throw new UsuarioNotFoundException();
        }

        // Convertir boolean es_admin a String "admin" o "user"
        return result.getBoolean("es_admin") ? "admin" : "user";
    }

    // Metodo Add. Solo para admin
    public boolean add(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, contrasena, es_admin, fecha_registro, credito, imagen) " +
                "VALUES (?, ?, SHA1(?), ?, ?, ?, ?)";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getEmail());
        statement.setString(3, usuario.getContrasena());
        statement.setBoolean(4, usuario.getEsAdmin());
        statement.setDate(5, usuario.getFechaRegistro());
        statement.setBigDecimal(6, usuario.getCredito());
        statement.setString(7, usuario.getImagen());
        
        int affectedRows = statement.executeUpdate();
        return affectedRows != 0;
    }

    // Agregar el método modify
    public boolean modify(Usuario usuario) throws SQLException {
        String sql;
        PreparedStatement statement;
        
        // Si se proporciona una nueva contraseña, actualizamos también la contraseña
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            sql = "UPDATE usuarios SET nombre = ?, email = ?, contrasena = SHA1(?), es_admin = ?, credito = ?, imagen = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getContrasena());
            statement.setBoolean(4, usuario.getEsAdmin());
            statement.setBigDecimal(5, usuario.getCredito());
            statement.setString(6, usuario.getImagen());
            statement.setInt(7, usuario.getId());
        } else {
            // Si no se proporciona una nueva contraseña, no actualizamos la contraseña
            sql = "UPDATE usuarios SET nombre = ?, email = ?, es_admin = ?, credito = ?, imagen = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setBoolean(3, usuario.getEsAdmin());
            statement.setBigDecimal(4, usuario.getCredito());
            statement.setString(5, usuario.getImagen());
            statement.setInt(6, usuario.getId());
        }
        
        int affectedRows = statement.executeUpdate();
        return affectedRows != 0;
    }

    
    public Usuario get(int id) throws SQLException, UsuarioNotFoundException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        
        if (!result.next()) {
            throw new UsuarioNotFoundException();
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(result.getInt("id"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setEmail(result.getString("email"));
        usuario.setEsAdmin(result.getBoolean("es_admin"));
        usuario.setFechaRegistro(result.getDate("fecha_registro"));
        usuario.setCredito(result.getBigDecimal("credito"));
        usuario.setImagen(result.getString("imagen"));
        
        return usuario;
    }

    // Metodo delete para admin.
    public boolean delete(int usuarioId) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, usuarioId);
        int affectedRows = statement.executeUpdate();

        return affectedRows != 0;
    }
    
    // Agregar métodos para listar usuarios
    public ArrayList<Usuario> getAll(int page) throws SQLException {
        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setEsAdmin(result.getBoolean("es_admin"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setCredito(result.getBigDecimal("credito"));
            usuario.setImagen(result.getString("imagen"));
            usuarios.add(usuario);
        }
        return usuarios;
    }
    
    public ArrayList<Usuario> search(String term) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? OR email LIKE ? ORDER BY nombre";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + term + "%");
        statement.setString(2, "%" + term + "%");
        ResultSet result = statement.executeQuery();
        
        ArrayList<Usuario> usuariosList = new ArrayList<>();
        
        while (result.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(result.getInt("id"));
            usuario.setNombre(result.getString("nombre"));
            usuario.setEmail(result.getString("email"));
            usuario.setEsAdmin(result.getBoolean("es_admin"));
            usuario.setFechaRegistro(result.getDate("fecha_registro"));
            usuario.setCredito(result.getBigDecimal("credito"));
            usuario.setImagen(result.getString("imagen"));
            usuariosList.add(usuario);
        }
        
        statement.close();
        return usuariosList;
    }
}
