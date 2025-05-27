package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;
import com.doctorwho.model.Usuario;
import com.doctorwho.model.Articulo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ventas/formulario")
public class VentaFormServlet extends HttpServlet {

    // Muestra el formulario
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
            // Cargar usuarios y artículos para los selects
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            ArticuloDao articuloDao = new ArticuloDao(connection);
            
            ArrayList<Usuario> usuarios = usuarioDao.getAll(1);
            List<Articulo> articulos = articuloDao.getAllArticulos();
            
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("articulos", articulos);
            
            db.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar usuarios y artículos: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/ventas/formulario.jsp").forward(request, response);
    }

    // Procesa el registro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String usuarioIdParam = request.getParameter("usuario_id");
        String articuloIdParam = request.getParameter("articulo_id");
        String cantidadParam = request.getParameter("cantidad");
        String totalParam = request.getParameter("total");
        String estadoVenta = request.getParameter("estado_venta");
        String pagadoParam = request.getParameter("pagado");

        // Validaciones
        if (usuarioIdParam == null || articuloIdParam == null || cantidadParam == null || 
            totalParam == null || estadoVenta == null ||
            usuarioIdParam.trim().isEmpty() || articuloIdParam.trim().isEmpty() || 
            cantidadParam.trim().isEmpty() || totalParam.trim().isEmpty() || 
            estadoVenta.trim().isEmpty()) {
            
            request.setAttribute("error", "❌ Todos los campos son obligatorios.");
            doGet(request, response);
            return;
        }

        try {
            int usuarioId = Integer.parseInt(usuarioIdParam);
            int articuloId = Integer.parseInt(articuloIdParam);
            int cantidad = Integer.parseInt(cantidadParam);
            BigDecimal total = new BigDecimal(totalParam);
            boolean pagado = "true".equals(pagadoParam);
            Date fechaVenta = new Date(System.currentTimeMillis());

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            Venta venta = new Venta();
            venta.setUsuarioId(usuarioId);
            venta.setArticuloId(articuloId);
            venta.setCantidad(cantidad);
            venta.setTotal(total);
            venta.setFechaVenta(fechaVenta);
            venta.setEstadoVenta(estadoVenta);
            venta.setPagado(pagado);

            VentaDao ventaDao = new VentaDao(connection);
            boolean exito = ventaDao.addVenta(venta);

            db.close();
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/ventas/lista");
            } else {
                request.setAttribute("error", "❌ Error al guardar la venta en la base de datos.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "❌ Los campos numéricos deben ser valores válidos.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Error interno al registrar la venta: " + e.getMessage());
            doGet(request, response);
        }
    }
}