package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet("/search_ventas")
public class SearchVentasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        try {
            Database db = new Database();
            db.connect();
            VentaDao dao = new VentaDao(db.getConnection());
            List<Venta> resultados = dao.search(search);
            db.close();

            request.setAttribute("ventas", resultados);
            request.getRequestDispatcher("/ventas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
