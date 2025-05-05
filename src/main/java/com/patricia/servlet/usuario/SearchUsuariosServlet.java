package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet("/search_usuarios")
public class SearchUsuariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        try {
            Database db = new Database();
            db.connect();
            UsuarioDao dao = new UsuarioDao(db.getConnection());
            List<Usuario> resultados = dao.search(search);
            db.close();

            request.setAttribute("usuarios", resultados);
            request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
