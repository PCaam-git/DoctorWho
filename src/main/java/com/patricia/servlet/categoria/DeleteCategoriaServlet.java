package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete_categoria")
public class DeleteCategoriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("categoria_id"));
        try {
            Database db = new Database();
            db.connect();
            CategoriaDao dao = new CategoriaDao(db.getConnection());
            dao.delete(id);
            response.sendRedirect("/doctorwho/categorias");
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}