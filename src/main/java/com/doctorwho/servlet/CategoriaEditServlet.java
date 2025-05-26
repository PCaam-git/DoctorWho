package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categorias/editar")
public class CategoriaEditServlet extends HttpServlet {

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
            response.getWriter().println("<h2 style='color:red'>❌ ID de categoría inválido.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            CategoriaDao categoriaDao = new CategoriaDao(connection);
            Categoria categoria = categoriaDao.getCategoriaById(id);

            db.close();

            if (categoria == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Categoría no encontrada.</h2>");
                return;
            }

            request.setAttribute("categoria", categoria);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/categorias/editar.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar la categoría para edición.</h2>");
        }
    }
}