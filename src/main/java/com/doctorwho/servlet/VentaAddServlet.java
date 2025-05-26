package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/add-venta")
public class VentaAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Database database = new Database();
            database.connect();
            Connection connection = database.getConnection();

            Venta venta = new Venta();
            venta.setUsuarioId(Integer.parseInt(request.getParameter("usuario_id")));
            venta.setArticuloId(Integer.parseInt(request.getParameter("articulo_id")));
            venta.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            venta.setTotal(new BigDecimal(request.getParameter("total")));
            venta.setFechaVenta(Date.valueOf(request.getParameter("fecha_venta")));
            venta.setEstadoVenta(request.getParameter("estado_venta"));
            venta.setPagado(Boolean.parseBoolean(request.getParameter("pagado")));

            VentaDao dao = new VentaDao(connection);
            boolean exito = dao.addVenta(venta);

            if (exito) {
                response.sendRedirect("ventas.jsp?mensaje=añadido");
            } else {
                response.sendRedirect("formulario-venta.jsp?error=true");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("formulario-venta.jsp?error=true");
        }
    }
}
