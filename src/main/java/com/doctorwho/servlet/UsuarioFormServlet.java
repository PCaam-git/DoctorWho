package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/usuarios/formulario")
public class UsuarioFormServlet extends HttpServlet {

    // Muestra el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        request.getRequestDispatcher("/usuarios/formulario.jsp").forward(request, response);
    }

    // Procesa el registro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String esAdminParam = request.getParameter("es_admin");
        String creditoParam = request.getParameter("credito");

        // Validaciones
        if (nombre == null || email == null || contrasena == null || creditoParam == null ||
            nombre.trim().isEmpty() || email.trim().isEmpty() || contrasena.trim().isEmpty() || 
            creditoParam.trim().isEmpty()) {
            
            request.setAttribute("error", "❌ Todos los campos son obligatorios.");
            doGet(request, response);
            return;
        }

        try {
            Boolean esAdmin = "true".equals(esAdminParam);
            BigDecimal credito = new BigDecimal(creditoParam);
            Date fechaRegistro = new Date(System.currentTimeMillis());

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasena);
            usuario.setEsAdmin(esAdmin);
            usuario.setFechaRegistro(fechaRegistro);
            usuario.setCredito(credito);
            usuario.setImagen("default.jpg"); // Imagen por defecto

            UsuarioDao usuarioDao = new UsuarioDao(connection);
            boolean exito = usuarioDao.add(usuario);

            db.close();
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/usuarios/lista");
            } else {
                request.setAttribute("error", "❌ Error al guardar el usuario en la base de datos.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "❌ El crédito debe ser un valor numérico válido.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Error interno al registrar el usuario: " + e.getMessage());
            doGet(request, response);
        }
    }
}