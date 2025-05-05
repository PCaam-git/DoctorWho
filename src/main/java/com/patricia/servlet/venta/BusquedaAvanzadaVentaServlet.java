package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.model.Venta;
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

@WebServlet("/busquedaAvanzadaVenta")
public class BusquedaAvanzadaVentaServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ventas/busquedaAvanzada.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreComprador = request.getParameter("nombreComprador");
        String nombreArticulo = request.getParameter("nombreArticulo");
        String precioMinStr = request.getParameter("precioMin");
        String precioMaxStr = request.getParameter("precioMax");
        String estadoVenta = request.getParameter("estadoVenta");
        
        Double precioMin = null;
        Double precioMax = null;
        
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
        
        Connection connection = null;
        ArrayList<Venta> resultados = new ArrayList<>();
        
        try {
            Database database = new Database();
            database.connect();
            connection = database.getConnection();
            VentaDao ventaDao = new VentaDao(connection);
            resultados = ventaDao.searchAdvanced(nombreComprador, nombreArticulo, precioMin, precioMax, estadoVenta);
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
        
        request.setAttribute("ventas", resultados);
        request.setAttribute("nombreComprador", nombreComprador);
        request.setAttribute("nombreArticulo", nombreArticulo);
        request.setAttribute("precioMin", precioMinStr);
        request.setAttribute("precioMax", precioMaxStr);
        request.setAttribute("estadoVenta", estadoVenta);
        request.getRequestDispatcher("/ventas/listar.jsp").forward(request, response);
    }
}