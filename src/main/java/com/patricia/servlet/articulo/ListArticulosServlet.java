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

@WebServlet("/articulos")
public class ListArticulosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        String pageParam = request.getParameter("page");
        int page = 0;
        
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException nfe) {
                // Si no es un número, se queda en página 0
            }
        }

        try {
            Database db = new Database();
            db.connect();
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            
            ArrayList<Articulo> articulos;
            int totalArticulos;
            
            if (search != null && !search.isEmpty()) {
                articulos = articuloDao.getAll(page, search);
                totalArticulos = articuloDao.getCount(search);
            } else {
                articulos = articuloDao.getAll(page);
                totalArticulos = articuloDao.getCount("");
            }
            
            request.setAttribute("articulos", articulos);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil(totalArticulos / 10.0)); // Asumiendo 10 elementos por página
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