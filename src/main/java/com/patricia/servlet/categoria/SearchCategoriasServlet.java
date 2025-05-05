package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search_categorias")
public class SearchCategoriasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        try {
            Database db = new Database();
            db.connect();
            CategoriaDao dao = new CategoriaDao(db.getConnection());
            List<Categoria> resultados = dao.search(search);
            request.setAttribute("categorias", resultados);
            request.getRequestDispatcher("/categorias.jsp").forward(request, response);
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}