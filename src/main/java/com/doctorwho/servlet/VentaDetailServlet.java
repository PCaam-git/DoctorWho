package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.model.Venta;
import com.doctorwho.model.Usuario;
import com.doctorwho.model.Articulo;
import com.doctorwho.database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ventas/detalle")
public class VentaDetailServlet extends HttpServlet {

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
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de venta requerido");
                return;
            }

            int id = Integer.parseInt(idParam);
            
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            VentaDao ventaDao = new VentaDao(connection);
            Venta venta = ventaDao.getVentaById(id);

            if (venta == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
                return;
            }

            // Obtener información del usuario y artículo
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            ArticuloDao articuloDao = new ArticuloDao(connection);
            
            try {
                Usuario usuario = usuarioDao.get(venta.getUsuarioId());
                request.setAttribute("usuario", usuario);
            } catch (Exception e) {
                // Si no se encuentra el usuario, continuar sin él
                request.setAttribute("usuario", null);
            }
            
            Articulo articulo = articuloDao.getArticuloById(venta.getArticuloId());
            
            request.setAttribute("venta", venta);
            request.setAttribute("articulo", articulo);
            
            // Verificar si el usuario es admin
            request.setAttribute("esAdmin", true); // Ya verificado arriba
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ventas/detalle.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de venta inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los detalles de la venta</h2>");
        }
    }
}