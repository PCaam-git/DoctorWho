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
import java.time.LocalDate;

@WebServlet("/articulo/crear")
public class ArticuloCreateServlet extends HttpServlet {
    private ArticuloDao articuloDao;

    @Override
    public void init() throws ServletException {
        articuloDao = new ArticuloDao(Database.getJdbi());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/articulo-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Recuperar parámetros del formulario
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        double precio = Double.parseDouble(req.getParameter("precio"));
        boolean disponible = req.getParameter("disponible") != null;

        // Crear objeto Articulo
        Articulo articulo = new Articulo();
        articulo.setNombre(nombre);
        articulo.setDescripcion(descripcion);
        articulo.setPrecio(precio);
        articulo.setDisponible(disponible);
        articulo.setFechaAñadido(LocalDate.now());

        // Validación básica
        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre es obligatorio");
            req.setAttribute("articulo", articulo);
            req.getRequestDispatcher("/WEB-INF/jsp/articulo-form.jsp").forward(req, resp);
            return;
        }

        // Guardar en la base de datos
        articuloDao.crearArticulo(articulo);

        // Redirigir al listado
        resp.sendRedirect(req.getContextPath() + "/articulos");
    }
}