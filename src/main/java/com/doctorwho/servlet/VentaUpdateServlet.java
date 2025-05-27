package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/ventas/actualizar")
public class VentaUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        String usuarioIdParam = request.getParameter("usuario_id");
        String articuloIdParam = request.getParameter("articulo_id");
        String cantidadParam = request.getParameter("cantidad");
        String totalParam = request.getParameter("total");
        String fechaVentaParam = request.getParameter("fecha_venta");
        String estadoVenta = request.getParameter("estado_venta");
        String pagadoParam = request.getParameter("pagado");

        // Validación de campos obligatorios
        if (idParam == null || usuarioIdParam == null || articuloIdParam == null || 
            cantidadParam == null || totalParam == null || fechaVentaParam == null ||
            estadoVenta == null || idParam.trim().isEmpty() || usuarioIdParam.trim().isEmpty() || 
            articuloIdParam.trim().isEmpty() || cantidadParam.trim().isEmpty() || 
            totalParam.trim().isEmpty() || fechaVentaParam.trim().isEmpty() || 
            estadoVenta.trim().isEmpty()) {
            
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Todos los campos obligatorios deben ser completados.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            int usuarioId = Integer.parseInt(usuarioIdParam);
            int articuloId = Integer.parseInt(articuloIdParam);
            int cantidad = Integer.parseInt(cantidadParam);
            BigDecimal total = new BigDecimal(totalParam);
            Date fechaVenta = Date.valueOf(fechaVentaParam);
            boolean pagado = "true".equals(pagadoParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            // Obtener venta actual para verificar que existe
            VentaDao ventaDao = new VentaDao(connection);
            Venta venta = ventaDao.getVentaById(id);
            
            if (venta == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Venta no encontrada.</h2>");
                db.close();
                return;
            }

            // Actualizar campos
            venta.setUsuarioId(usuarioId);
            venta.setArticuloId(articuloId);
            venta.setCantidad(cantidad);
            venta.setTotal(total);
            venta.setFechaVenta(fechaVenta);
            venta.setEstadoVenta(estadoVenta);
            venta.setPagado(pagado);

            boolean exito = ventaDao.updateVenta(venta);
            db.close();

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/ventas/detalle?id=" + id);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar la venta.</h2>");
            }

        } catch (NumberFormatException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Formato de número inválido en cantidad, total o IDs.</h2>");
        } catch (IllegalArgumentException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Formato de fecha inválido. Use YYYY-MM-DD.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar la venta: " + e.getMessage() + "</h2>");
        }
    }
}