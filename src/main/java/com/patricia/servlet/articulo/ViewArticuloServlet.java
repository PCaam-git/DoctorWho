package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;
import com.patricia.exception.ArticuloNotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/view_articulo")
public class ViewArticuloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String articuloId = request.getParameter("articulo_id");
        if (articuloId == null || articuloId.isEmpty()) {
            response.sendRedirect("/doctorwho/articulos");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            Articulo articulo = articuloDao.get(Integer.parseInt(articuloId));
            
            request.setAttribute("articulo", articulo);
            request.getRequestDispatcher("/articulo-detail.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (ArticuloNotFoundException anfe) {
            response.sendRedirect("/doctorwho/articulos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}