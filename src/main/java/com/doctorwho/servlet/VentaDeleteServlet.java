package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ventas/eliminar")
public class VentaDeleteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.getWriter().println("❌ ID de venta requerido.");
                return;
            }
            
            int id = Integer.parseInt(idParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            VentaDao dao = new VentaDao(connection);
            
            // Verificar que la venta existe antes de eliminarla
            Venta venta = dao.getVentaById(id);
            if (venta != null) {
                boolean eliminado = dao.deleteVenta(id);
                if (eliminado) {
                    request.getSession().setAttribute("deletedVenta", String.valueOf(id));
                }
            }

            response.sendRedirect(request.getContextPath() + "/ventas/lista");
            
            db.close();

        } catch (NumberFormatException e) {
            response.getWriter().println("❌ ID de venta inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error al eliminar la venta.");
        }
    }
}