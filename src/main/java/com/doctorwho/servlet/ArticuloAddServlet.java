package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.model.Articulo;
import com.doctorwho.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException; // Añade esta importación

@WebServlet("/articulos/add-articulo")
public class ArticuloAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null; // Declara la variable aquí
        try {
            Database database = new Database();
            database.connect();
            connection = database.getConnection();

            Articulo articulo = new Articulo();
            articulo.setNombre(request.getParameter("nombre"));
            articulo.setDescripcion(request.getParameter("descripcion"));
            articulo.setDisponible(Boolean.parseBoolean(request.getParameter("disponible")));
            articulo.setPrecio(new BigDecimal(request.getParameter("precio")));
            articulo.setFechaAnadido(Date.valueOf(request.getParameter("fecha_anadido")));
            articulo.setCategoriaId(Integer.parseInt(request.getParameter("categoria_id")));
            articulo.setImagen(request.getParameter("imagen"));

            ArticuloDao dao = new ArticuloDao(connection);
            boolean exito = dao.addArticulo(articulo);

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/articulos/list.jsp?mensaje=añadido");
            } else {
                response.sendRedirect(request.getContextPath() + "/articulos/form.jsp?error=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/articulos/form.jsp?error=true");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}