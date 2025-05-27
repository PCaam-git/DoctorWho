package com.doctorwho.servlet;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;
import com.doctorwho.model.Usuario;
import com.doctorwho.model.Articulo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ventas/editar")
public class VentaEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || !idParam.matches("\\d+")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ ID de venta inválido.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            VentaDao ventaDao = new VentaDao(connection);
            Venta venta = ventaDao.getVentaById(id);

            if (venta == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Venta no encontrada.</h2>");
                db.close();
                return;
            }

            // Cargar usuarios y artículos para los selects
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            ArticuloDao articuloDao = new ArticuloDao(connection);
            
            ArrayList<Usuario> usuarios = usuarioDao.getAll(1);
            List<Articulo> articulos = articuloDao.getAllArticulos();

            request.setAttribute("venta", venta);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("articulos", articulos);

            db.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("/ventas/editar.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar la venta para edición.</h2>");
        }
    }
}