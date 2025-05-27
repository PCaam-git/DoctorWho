package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

@WebServlet("/usuarios/actualizar")
public class UsuarioUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener parámetros del formulario
        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena"); // Opcional
        String esAdminParam = request.getParameter("es_admin");
        String creditoParam = request.getParameter("credito");

        // Validación de campos obligatorios (la contraseña es opcional en edición)
        if (idParam == null || nombre == null || email == null || creditoParam == null ||
            idParam.trim().isEmpty() || nombre.trim().isEmpty() || 
            email.trim().isEmpty() || creditoParam.trim().isEmpty()) {
            
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Todos los campos obligatorios deben ser completados.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Boolean esAdmin = "true".equals(esAdminParam);
            BigDecimal credito = new BigDecimal(creditoParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            // Obtener usuario actual para verificar que existe
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            Usuario usuario = usuarioDao.get(id);

            // Actualizar campos
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setEsAdmin(esAdmin);
            usuario.setCredito(credito);
            
            // Solo actualizar contraseña si se proporciona una nueva
            if (contrasena != null && !contrasena.trim().isEmpty()) {
                usuario.setContrasena(contrasena.trim());
            } else {
                usuario.setContrasena(null); // Indica que no se debe cambiar
            }

            boolean exito = usuarioDao.modify(usuario);
            db.close();

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/usuarios/detalle?id=" + id);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el usuario.</h2>");
            }

        } catch (NumberFormatException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Formato de número inválido en crédito o ID.</h2>");
        } catch (UsuarioNotFoundException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Usuario no encontrado.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el usuario: " + e.getMessage() + "</h2>");
        }
    }
}