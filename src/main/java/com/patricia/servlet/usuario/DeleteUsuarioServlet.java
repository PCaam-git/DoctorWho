package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete_usuario")
public class DeleteUsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("usuario_id"));
        try {
            Database db = new Database();
            db.connect();
            UsuarioDao dao = new UsuarioDao(db.getConnection());
            dao.delete(id);
            db.close();
            response.sendRedirect("/doctorwho/usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
