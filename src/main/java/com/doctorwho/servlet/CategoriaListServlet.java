package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/categorias/lista")
public class CategoriaListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            CategoriaDao dao = new CategoriaDao(connection);

            String q = request.getParameter("q");
            String conProductosParam = request.getParameter("con_productos");
            boolean mostrarSoloConProductos = "on".equals(conProductosParam);

            // Paginación
            int page = 1;
            int limit = 5;

            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
            }

            int offset = (page - 1) * limit;

            // Contar el total de resultados
            int totalCategorias = dao.countAllCategorias(q, mostrarSoloConProductos);
            int totalPages = (int) Math.ceil((double) totalCategorias / limit);

            // Obtener categorías paginadas
            List<Categoria> categorias = dao.getCategoriasPaged(q, mostrarSoloConProductos, offset, limit);

            // Atributos para la vista
            request.setAttribute("categorias", categorias);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("q", q);
            request.setAttribute("mostrarSoloConProductos", mostrarSoloConProductos);

            // Verificar si el usuario es admin
            HttpSession session = request.getSession();
            boolean esAdmin = session.getAttribute("es_admin") != null && (boolean) session.getAttribute("es_admin");
            request.setAttribute("esAdmin", esAdmin);

            // Redirigir a la JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/categorias/lista.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (Exception e) {
            System.err.println("❌ ERROR DETECTADO EN CategoriaListServlet:");
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar las categorías</h2>");
        }
    }
}