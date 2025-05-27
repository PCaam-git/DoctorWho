package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/articulos/eliminar")
public class ArticuloDeleteServlet extends HttpServlet {
    
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
                response.getWriter().println("❌ ID de artículo requerido.");
                return;
            }
            
            int id = Integer.parseInt(idParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            ArticuloDao dao = new ArticuloDao(connection);
            Articulo articulo = dao.getArticuloById(id);
            
            if (articulo != null) {
                boolean eliminado = dao.deleteArticulo(id);
                if (eliminado) {
                    request.getSession().setAttribute("deletedArticulo", articulo.getNombre());
                }
            }

            response.sendRedirect(request.getContextPath() + "/articulos/lista");
            
            db.close();

        } catch (NumberFormatException e) {
            response.getWriter().println("❌ ID de artículo inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error al eliminar el artículo.");
        }
    }
}