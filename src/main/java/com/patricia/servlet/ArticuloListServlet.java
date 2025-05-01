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
        // Conecta a la BD y crea el DAO
        Database db = new Database();
        db.connect();  // inicializa el Jdbi :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1}
        articuloDao = new ArticuloDao(db.getJdbi());  // usa listarArticulos() :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Obtiene la lista de artículos y la pasa a la JSP
        List<Articulo> lista = articuloDao.listarArticulos();
        req.setAttribute("articulos", lista);
        req.getRequestDispatcher("/WEB-INF/jsp/articulos.jsp")
           .forward(req, resp);
    }
}
