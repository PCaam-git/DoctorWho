package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/create_venta")
public class CreateVentaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Venta venta = new Venta();
            venta.setIdComprador(Integer.parseInt(request.getParameter("idComprador")));
            venta.setIdArticulo(Integer.parseInt(request.getParameter("idArticulo")));
            venta.setPrecio(Double.parseDouble(request.getParameter("precio")));
            venta.setEstadoVenta(request.getParameter("estadoVenta"));
            venta.setPagado(Boolean.parseBoolean(request.getParameter("pagado")));
            venta.setActivo(true);

            Database db = new Database();
            db.connect();
            VentaDao dao = new VentaDao(db.getConnection());
            dao.add(venta);
            db.close();

            response.sendRedirect("/doctorwho/ventas");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
