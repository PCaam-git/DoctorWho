package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete_articulo")
public class DeleteArticuloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if (currentSession.getAttribute("role") == null) {
            response.sendRedirect("/doctorwho/login.jsp");
            return;
        }

        String articuloId = request.getParameter("articulo_id");
        // Validación
        if (articuloId == null || articuloId.isEmpty()) {
            response.sendRedirect("/doctorwho/articulos");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            articuloDao.delete(Integer.parseInt(articuloId));
            // TODO: Borrar la imagen si existe

            response.sendRedirect("/doctorwho/articulos");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}