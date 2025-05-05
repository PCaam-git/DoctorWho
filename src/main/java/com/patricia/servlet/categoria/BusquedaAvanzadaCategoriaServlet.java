package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.model.Categoria;
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

@WebServlet("/busquedaAvanzadaCategoria")
public class BusquedaAvanzadaCategoriaServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/categorias/busquedaAvanzada.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String cantidadMinStr = request.getParameter("cantidadMin");
        String cantidadMaxStr = request.getParameter("cantidadMax");
        String tieneProductosStr = request.getParameter("tieneProductos");
        
        Integer cantidadMin = null;
        Integer cantidadMax = null;
        Boolean tieneProductos = null;
        
        if (cantidadMinStr != null && !cantidadMinStr.isEmpty()) {
            try {
                cantidadMin = Integer.parseInt(cantidadMinStr);
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }
        }
        
        if (cantidadMaxStr != null && !cantidadMaxStr.isEmpty()) {
            try {
                cantidadMax = Integer.parseInt(cantidadMaxStr);
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }
        }
        
        if (tieneProductosStr != null && !tieneProductosStr.isEmpty()) {
            tieneProductos = tieneProductosStr.equals("true");
        }
        
        Connection connection = null;
        ArrayList<Categoria> resultados = new ArrayList<>();
        
        try {
            Database database = new Database();
            database.connect();
            connection = database.getConnection();
            CategoriaDao categoriaDao = new CategoriaDao(connection);
            resultados = categoriaDao.searchAdvanced(nombre, cantidadMin, cantidadMax, tieneProductos);
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
        
        request.setAttribute("categorias", resultados);
        request.setAttribute("nombre", nombre);
        request.setAttribute("cantidadMin", cantidadMinStr);
        request.setAttribute("cantidadMax", cantidadMaxStr);
        request.setAttribute("tieneProductos", tieneProductosStr);
        request.getRequestDispatcher("/categorias/listar.jsp").forward(request, response);
    }
}