package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.model.Usuario;
import com.patricia.database.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/busquedaAvanzadaUsuario")
public class BusquedaAvanzadaUsuarioServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/usuarios/busquedaAvanzada.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");
        String activoStr = request.getParameter("activo");
        
        Boolean activo = null;
        
        if (activoStr != null && !activoStr.isEmpty()) {
            activo = activoStr.equals("true");
        }
        
        Connection connection = null;
        ArrayList<Usuario> resultados = new ArrayList<>();
        
        try {
            Database database = new Database();
            database.connect();
            connection = database.getConnection();
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            resultados = usuarioDao.searchAdvanced(nombre, email, rol, activo);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        request.setAttribute("usuarios", resultados);
        request.setAttribute("nombre", nombre);
        request.setAttribute("email", email);
        request.setAttribute("rol", rol);
        request.setAttribute("activo", activoStr);
        request.getRequestDispatcher("/usuarios/listar.jsp").forward(request, response);
    }
}