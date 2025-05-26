package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/categorias/form")
public class CategoriaAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Database database = new Database();
            database.connect();
            Connection connection = database.getConnection();

            Categoria categoria = new Categoria();
            categoria.setNombre(request.getParameter("nombre"));
            categoria.setDescripcion(request.getParameter("descripcion"));
            categoria.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            categoria.setTieneProductos(Boolean.parseBoolean(request.getParameter("tiene_productos")));
            categoria.setFechaActualizacion(Date.valueOf(request.getParameter("fecha_actualizacion")));
            categoria.setPrecioMedio(new BigDecimal(request.getParameter("precio_medio")));
            categoria.setImagen(request.getParameter("imagen"));

            CategoriaDao dao = new CategoriaDao(connection);
            boolean exito = dao.addCategoria(categoria);

            if (exito) {
                response.sendRedirect("categorias.jsp?mensaje=añadido");
            } else {
                response.sendRedirect("formulario-categoria.jsp?error=true");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("formulario-categoria.jsp?error=true");
        }
    }
}
