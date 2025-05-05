package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search_articulos")
public class SearchArticulosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        if (search == null || search.isEmpty()) {
            response.sendRedirect("/doctorwho/articulos");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            
            ArrayList<Articulo> articulos = articuloDao.search(search);
            
            request.setAttribute("articulos", articulos);
            request.setAttribute("search", search);
            
            request.getRequestDispatcher("/articulos.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}