package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categorias/eliminar")
public class CategoriaDeleteServlet extends HttpServlet {
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
            int id = Integer.parseInt(request.getParameter("id"));

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            CategoriaDao dao = new CategoriaDao(connection);
            Categoria categoria = dao.getCategoriaById(id);
            
            if (categoria != null) {
                boolean eliminado = dao.deleteCategoria(id);
                if (eliminado) {
                    request.getSession().setAttribute("deletedCategoria", categoria.getNombre());
                }
            }

            response.sendRedirect(request.getContextPath() + "/categorias/lista");
            
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error al eliminar la categoría.");
        }
    }
}