package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/categorias")
public class ListCategoriasServlet extends HttpServlet {
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
            CategoriaDao categoriaDao = new CategoriaDao(db.getConnection());
            
            ArrayList<Categoria> categorias;
            int totalCategorias;
            
            if (search != null && !search.isEmpty()) {
                categorias = categoriaDao.getAll(page, search);
                totalCategorias = categoriaDao.getCount(search);
            } else {
                categorias = categoriaDao.getAll(page);
                totalCategorias = categoriaDao.getCount("");
            }
            
            request.setAttribute("categorias", categorias);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil(totalCategorias / 10.0)); // Asumiendo 10 elementos por página
            request.setAttribute("search", search);
            
            request.getRequestDispatcher("/categorias.jsp").forward(request, response);
            
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