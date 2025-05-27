package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ventas/lista")
public class VentaListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            
            VentaDao ventaDao = new VentaDao(connection);

            // Obtener parámetros de búsqueda
            String estado = request.getParameter("estado");
            String pagadoStr = request.getParameter("pagado");
            String fechaDesde = request.getParameter("fecha_desde");
            String fechaHasta = request.getParameter("fecha_hasta");

            // Paginación
            int page = 1;
            int limit = 15; // 15 ventas por página
            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
            }
            int offset = (page - 1) * limit;

            // Convertir parámetro de pagado
            Boolean pagado = null;
            if ("true".equals(pagadoStr)) {
                pagado = true;
            } else if ("false".equals(pagadoStr)) {
                pagado = false;
            }

            // Obtener ventas y total
            List<Venta> ventas;
            int totalVentas;
            
            boolean hayFiltros = (estado != null && !estado.trim().isEmpty()) || 
                                pagado != null || 
                                (fechaDesde != null && !fechaDesde.trim().isEmpty()) || 
                                (fechaHasta != null && !fechaHasta.trim().isEmpty());

            if (hayFiltros) {
                // Usar búsqueda filtrada con paginación
                ventas = ventaDao.searchVentasPaged(estado, pagado, fechaDesde, fechaHasta, offset, limit);
                totalVentas = ventaDao.countVentasFiltradas(estado, pagado, fechaDesde, fechaHasta);
            } else {
                // Sin filtros, usar paginación simple
                ventas = ventaDao.getAllVentasPaged(offset, limit);
                totalVentas = ventaDao.countAllVentas();
            }

            // Calcular páginas
            int totalPages = (int) Math.ceil((double) totalVentas / limit);

            // Atributos para la vista
            request.setAttribute("ventas", ventas);
            request.setAttribute("totalVentas", totalVentas);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            // Mantener filtros actuales
            request.setAttribute("estadoSeleccionado", estado);
            request.setAttribute("pagadoSeleccionado", pagadoStr);
            request.setAttribute("fechaDesde", fechaDesde);
            request.setAttribute("fechaHasta", fechaHasta);

            // Verificar si hay mensaje de eliminación
            String deletedVenta = (String) session.getAttribute("deletedVenta");
            if (deletedVenta != null) {
                request.setAttribute("mensaje", "Venta ID " + deletedVenta + " eliminada correctamente");
                session.removeAttribute("deletedVenta");
            }

            // Redirigir a la JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ventas/lista.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (Exception e) {
            System.err.println("❌ ERROR EN VentaListServlet:");
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar las ventas: " + e.getMessage() + "</h2>");
        }
    }
}