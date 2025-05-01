package com.patricia.servlet;

import com.patricia.dao.ArticuloDao;
import com.patricia.model.Articulo;
import com.patricia.database.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/articulos")
public class ArticuloListServlet extends HttpServlet {
    private ArticuloDao articuloDao;

    @Override
    public void init() throws ServletException {
        // Inicializa Jdbi y tu DAO
        Database db = new Database();
        db.connect();  // conecta a tu MariaDB :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1}  
        articuloDao = new ArticuloDao(db.getJdbi());  // tu DAO con listarArticulos() :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Recupera la lista
        List<Articulo> lista = articuloDao.listarArticulos();
        // La deja disponible en request
        req.setAttribute("articulos", lista);
        // Envía a la JSP de presentación
        req.getRequestDispatcher("/WEB-INF/jsp/articulos.jsp")
           .forward(req, resp);
    }
}
