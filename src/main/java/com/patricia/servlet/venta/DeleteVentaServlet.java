package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete_venta")
public class DeleteVentaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("venta_id"));
        try {
            Database db = new Database();
            db.connect();
            VentaDao dao = new VentaDao(db.getConnection());
            dao.delete(id);
            db.close();
            response.sendRedirect("/doctorwho/ventas");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
