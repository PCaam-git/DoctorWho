package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/ventas")
public class ListVentasServlet extends HttpServlet {
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
            VentaDao ventaDao = new VentaDao(db.getConnection());
            
            ArrayList<Venta> ventas;
            int totalVentas;
            
            if (search != null && !search.isEmpty()) {
                ventas = ventaDao.getAll(page, search);
                totalVentas = ventaDao.getCount(search);
            } else {
                ventas = ventaDao.getAll(page);
                totalVentas = ventaDao.getCount("");
            }
            
            request.setAttribute("ventas", ventas);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil(totalVentas / 10.0)); // Asumiendo 10 elementos por página
            request.setAttribute("search", search);
     // Para indicar que se debe mostrar el enlace a búsqueda avanzada
            
            request.getRequestDispatcher("/ventas.jsp").forward(request, response);
            
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