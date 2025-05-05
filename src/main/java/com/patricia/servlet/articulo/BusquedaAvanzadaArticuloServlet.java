package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.model.Articulo;
import com.patricia.database.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/busquedaAvanzadaArticulo")
public class BusquedaAvanzadaArticuloServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/articulos/busquedaAvanzada.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String precioMinStr = request.getParameter("precioMin");
        String precioMaxStr = request.getParameter("precioMax");
        String disponibleStr = request.getParameter("disponible");
        
        Double precioMin = null;
        Double precioMax = null;
        Boolean disponible = null;
        
        if (precioMinStr != null && !precioMinStr.isEmpty()) {
            try {
                precioMin = Double.parseDouble(precioMinStr);
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }
        }
        
        if (precioMaxStr != null && !precioMaxStr.isEmpty()) {
            try {
                precioMax = Double.parseDouble(precioMaxStr);
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }
        }
        
        if (disponibleStr != null && !disponibleStr.isEmpty()) {
            disponible = disponibleStr.equals("true");
        }
        
        Connection connection = null;
        ArrayList<Articulo> resultados = new ArrayList<>();
        
        try {
            Database database = new Database();
            database.connect();
            connection = database.getConnection();
            ArticuloDao articuloDao = new ArticuloDao(connection);
            resultados = articuloDao.searchAdvanced(nombre, precioMin, precioMax, disponible);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        request.setAttribute("articulos", resultados);
        request.setAttribute("nombre", nombre);
        request.setAttribute("precioMin", precioMinStr);
        request.setAttribute("precioMax", precioMaxStr);
        request.setAttribute("disponible", disponibleStr);
        request.getRequestDispatcher("/articulos/listar.jsp").forward(request, response);
    }
}