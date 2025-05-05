package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/view_venta")
public class ViewVentaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("venta_id"));
        try {
            Database db = new Database();
            db.connect();
            VentaDao dao = new VentaDao(db.getConnection());
            Venta venta = dao.get(id);
            db.close();

            request.setAttribute("venta", venta);
            request.getRequestDispatcher("/venta-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
