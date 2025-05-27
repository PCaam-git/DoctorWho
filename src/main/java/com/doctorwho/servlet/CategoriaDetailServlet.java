package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.model.Categoria;
import com.doctorwho.database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categorias/detalle")
public class CategoriaDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            CategoriaDao dao = new CategoriaDao(connection);
            Categoria categoria = dao.getCategoriaById(id);

            if (categoria == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoría no encontrada");
                return;
            }

            request.setAttribute("categoria", categoria);
            
            // Verificar si el usuario es admin
            HttpSession session = request.getSession();
            boolean esAdmin = session.getAttribute("es_admin") != null && (boolean) session.getAttribute("es_admin");
            request.setAttribute("esAdmin", esAdmin);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/categorias/detalle.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los detalles de la categoría</h2>");
        }
    }
}