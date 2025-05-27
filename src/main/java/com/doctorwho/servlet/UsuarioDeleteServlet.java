package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuarios/eliminar")
public class UsuarioDeleteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.getWriter().println("❌ ID de usuario requerido.");
                return;
            }
            
            int id = Integer.parseInt(idParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            UsuarioDao dao = new UsuarioDao(connection);
            
            // Obtener el usuario para guardar su nombre antes de eliminarlo
            Usuario usuario = dao.get(id);
            String nombreUsuario = usuario.getNombre();
            
            boolean eliminado = dao.delete(id);
            if (eliminado) {
                request.getSession().setAttribute("deletedUsuario", nombreUsuario);
            }

            response.sendRedirect(request.getContextPath() + "/usuarios/lista");
            
            db.close();

        } catch (NumberFormatException e) {
            response.getWriter().println("❌ ID de usuario inválido.");
        } catch (UsuarioNotFoundException e) {
            response.getWriter().println("❌ Usuario no encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error al eliminar el usuario.");
        }
    }
}