package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuario/editar")
public class UsuarioEditarPerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario está logueado
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            UsuarioDao usuarioDao = new UsuarioDao(connection);
            Usuario usuario = usuarioDao.get(userId);

            request.setAttribute("usuario", usuario);

            db.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/editar.jsp");
            dispatcher.forward(request, response);

        } catch (UsuarioNotFoundException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Usuario no encontrado.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar el perfil para edición.</h2>");
        }
    }
}