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

@WebServlet("/articulo/editar")
public class ArticuloUpdateServlet extends HttpServlet {
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
            req.getRequestDispatcher("/WEB-INF/jsp/articulo-form.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/articulos");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Recuperar parámetros del formulario
        int id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        double precio = Double.parseDouble(req.getParameter("precio"));
        boolean disponible = req.getParameter("disponible") != null;
        
        // Recuperar el artículo original para mantener la fecha
        Articulo articulo = articuloDao.obtenerArticulo(id);
        
        // Actualizar los campos
        articulo.setNombre(nombre);
        articulo.setDescripcion(descripcion);
        articulo.setPrecio(precio);
        articulo.setDisponible(disponible);
        
        // Validación básica
        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre es obligatorio");
            req.setAttribute("articulo", articulo);
            req.getRequestDispatcher("/WEB-INF/jsp/articulo-form.jsp").forward(req, resp);
            return;
        }
        
        // Actualizar en la base de datos
        articuloDao.actualizarArticulo(articulo);
        
        // Redirigir al listado
        resp.sendRedirect(req.getContextPath() + "/articulos");
    }
}