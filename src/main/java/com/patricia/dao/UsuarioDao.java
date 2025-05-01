package com.patricia.dao;

import com.patricia.model.Usuario;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class UsuarioDao {
    private final Jdbi jdbi;

    public UsuarioDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    // Insertar un usuario
    public void insertarUsuario(Usuario usuario) {
        jdbi.useHandle(handle ->
            handle.createUpdate("INSERT INTO usuarios (nombre, email, contraseña, rol, fecha_registro, estado, activo) " +
                    "VALUES (:nombre, :email, :contraseña, :rol, :fecha, :estado, :activo)")
                .bind("nombre", usuario.getNombre())
                .bind("email", usuario.getEmail())
                .bind("contraseña", usuario.getContraseña())
                .bind("rol", usuario.getRol())
                .bind("fecha", usuario.getFechaRegistro())
                .bind("estado", usuario.getEstado())
                .bind("activo", usuario.isActivo())
                .execute()
        );
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM usuarios")
                  .mapToBean(Usuario.class)
                  .list()
        );
    }

    // Actualizar usuario
public boolean actualizarUsuario(Usuario usuario) {
    int filas = jdbi.withHandle(handle ->
        handle.createUpdate("UPDATE usuarios SET nombre = :nombre, email = :email, contraseña = :contraseña, rol = :rol, fecha_registro = :fecha, estado = :estado, activo = :activo WHERE id = :id")
              .bind("id", usuario.getId())
              .bind("nombre", usuario.getNombre())
              .bind("email", usuario.getEmail())
              .bind("contraseña", usuario.getContraseña())
              .bind("rol", usuario.getRol())
              .bind("fecha", usuario.getFechaRegistro())
              .bind("estado", usuario.getEstado())
              .bind("activo", usuario.isActivo())
              .execute()
    );
    return filas > 0;
}
    
        // Eliminar usuario
        public boolean eliminarUsuario(int id) {
            int filas = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM usuarios WHERE id = :id")
                    .bind("id", id)
                    .execute()
            );
            return filas > 0;
        }
}









/*package com.patricia.dao;

import com.patricia.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private Connection connection;
    
  
    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setContraseña(resultSet.getString("contraseña"));
                usuario.setRol(resultSet.getString("rol"));
                usuario.setFechaRegistro(resultSet.getDate("fecha_registro").toLocalDate());
                usuario.setEstado(resultSet.getString("estado"));
                usuario.setActivo(resultSet.getBoolean("activo"));

                lista.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
            return lista;
        }

        public boolean insertarUsuario(Usuario usuario) {
            String sql = "INSERT INTO usuarios (nombre, email, contraseña, rol, fecha_registro, estado, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getEmail());
                statement.setString(3, usuario.getContraseña());
                statement.setString(4, usuario.getRol());
                statement.setDate(5,java.sql.Date.valueOf(usuario.getFechaRegistro()));
                statement.setString(6, usuario.getEstado());
                statement.setBoolean(7, usuario.isActivo());

                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Error al insertar usuario: " + e.getMessage());
                return false;
            }
            }

            public boolean updateUsuario(Usuario usuario) {
                String sql = "UPDATE usuarios SET nombre = ?, email = ?, contraseña = ?, rol = ?, fecha_registro = ?, estado = ?, activo = ? WHERE id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, usuario.getNombre());
                    statement.setString(2, usuario.getEmail());
                    statement.setString(3, usuario.getContraseña());
                    statement.setString(4, usuario.getRol());
                    statement.setDate(5, java.sql.Date.valueOf(usuario.getFechaRegistro()));
                    statement.setString(6, usuario.getEstado());
                    statement.setBoolean(7, usuario.isActivo());
                    statement.setInt(8, usuario.getId());

                    int filas = statement.executeUpdate();
                    return filas > 0;

                } catch (SQLException e) {
                    System.out.println("Error al actualizar usuario: " + e.getMessage());
                    return false;
                }
                }

                public boolean deleteUsuario(int id) {
                    String sql ="DELETE FROM usuarios WHERE id = ?";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, id);
                        int filas = statement.executeUpdate();
                        return filas > 0;

                    } catch (SQLException e) {
                        System.out.println("Error al eliminar usuario. " +e.getMessage());
                        return false;
                    }
                    }
                }*/
            
        
    

     

