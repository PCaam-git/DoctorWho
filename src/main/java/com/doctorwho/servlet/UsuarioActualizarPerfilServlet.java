package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuario/actualizar")
public class UsuarioActualizarPerfilServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario está logueado
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena"); // Opcional

        // Validación de campos obligatorios
        if (nombre == null || email == null || 
            nombre.trim().isEmpty() || email.trim().isEmpty()) {
            
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ El nombre y email son obligatorios.</h2>");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            // Obtener usuario actual
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            Usuario usuario = usuarioDao.get(userId);

            // Actualizar solo los campos que el usuario puede modificar
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            
            // Solo actualizar contraseña si se proporciona una nueva
            if (contrasena != null && !contrasena.trim().isEmpty()) {
                usuario.setContrasena(contrasena.trim());
            } else {
                usuario.setContrasena(null); // Indica que no se debe cambiar
            }

            boolean exito = usuarioDao.modify(usuario);
            db.close();

            if (exito) {
                // Actualizar el nombre en la sesión si cambió
                session.setAttribute("user_name", nombre);
                response.sendRedirect(request.getContextPath() + "/usuario/perfil?actualizado=true");
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el perfil.</h2>");
            }

        } catch (UsuarioNotFoundException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Usuario no encontrado.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el perfil: " + e.getMessage() + "</h2>");
        }
    }
}