package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/create_categoria")
public class CreateCategoriaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        try {
            Database db = new Database();
            db.connect();
            CategoriaDao dao = new CategoriaDao(db.getConnection());
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            dao.add(categoria);
            response.sendRedirect("/doctorwho/categorias");
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}