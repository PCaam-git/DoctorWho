package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.model.Articulo;
import com.doctorwho.database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/articulos/detalle")
public class ArticuloDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de artículo requerido");
                return;
            }

            int id = Integer.parseInt(idParam);
            
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            ArticuloDao dao = new ArticuloDao(connection);
            Articulo articulo = dao.getArticuloById(id);

            if (articulo == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artículo no encontrado");
                return;
            }

            request.setAttribute("articulo", articulo);
            
            // Verificar si el usuario es admin
            HttpSession session = request.getSession();
            boolean esAdmin = session.getAttribute("es_admin") != null && (boolean) session.getAttribute("es_admin");
            request.setAttribute("esAdmin", esAdmin);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/articulos/detalle.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de artículo inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los detalles del artículo</h2>");
        }
    }
}