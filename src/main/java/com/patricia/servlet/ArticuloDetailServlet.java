package com.patricia.servlet;

import com.patricia.dao.ArticuloDao;
import com.patricia.dao.Database;
import com.patricia.model.Articulo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/articulo/detalle")
public class ArticuloDetailServlet extends HttpServlet {
    private ArticuloDao articuloDao;
    
    @Override
    public void init() throws ServletException {
        articuloDao = new ArticuloDao(Database.getJdbi());
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Articulo articulo = articuloDao.obtenerArticulo(id);
        
        if (articulo != null) {
            req.setAttribute("articulo", articulo);
            req.getRequestDispatcher("/WEB-INF/jsp/articulo-detail.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/articulos");
        }
    }
}