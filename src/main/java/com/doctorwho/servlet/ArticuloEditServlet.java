package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;
import com.doctorwho.model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/articulos/editar")
public class ArticuloEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            String currentUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                currentUrl += "?" + queryString;
            }
            response.sendRedirect(request.getContextPath() + "/login?returnUrl=" + 
                                java.net.URLEncoder.encode(currentUrl, "UTF-8"));
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || !idParam.matches("\\d+")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ ID de artículo inválido.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            ArticuloDao articuloDao = new ArticuloDao(connection);
            Articulo articulo = articuloDao.getArticuloById(id);

            if (articulo == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Artículo no encontrado.</h2>");
                db.close();
                return;
            }

            // Cargar categorías para el select
            CategoriaDao categoriaDao = new CategoriaDao(connection);
            List<Categoria> categorias = categoriaDao.getAllCategorias();

            request.setAttribute("articulo", articulo);
            request.setAttribute("categorias", categorias);

            db.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articulos/editar.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar el artículo para edición.</h2>");
        }
    }
}