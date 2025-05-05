package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/view_usuario")
public class ViewUsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("usuario_id"));
        try {
            Database db = new Database();
            db.connect();
            UsuarioDao dao = new UsuarioDao(db.getConnection());
            Usuario usuario = dao.get(id);
            db.close();

            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/usuario-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
