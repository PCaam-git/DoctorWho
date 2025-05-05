package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;
import com.patricia.exception.CategoriaNotFoundException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/view_categoria")
public class ViewCategoriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("categoria_id"));
        try {
            Database db = new Database();
            db.connect();
            CategoriaDao dao = new CategoriaDao(db.getConnection());
            Categoria categoria = dao.get(id);
            request.setAttribute("categoria", categoria);
            request.getRequestDispatcher("/categoria-detail.jsp").forward(request, response);
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } catch (CategoriaNotFoundException e) {
            // Manejar el caso donde no se encuentra la categoría
            response.sendRedirect("/doctorwho/categorias");
        }
    }
}