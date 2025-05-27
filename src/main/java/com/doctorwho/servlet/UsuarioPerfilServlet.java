package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.model.Usuario;
import com.doctorwho.database.Database;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuario/perfil")
public class UsuarioPerfilServlet extends HttpServlet {

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

            UsuarioDao dao = new UsuarioDao(connection);
            Usuario usuario = dao.get(userId);

            request.setAttribute("usuario", usuario);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/perfil.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (UsuarioNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar el perfil del usuario</h2>");
        }
    }
}