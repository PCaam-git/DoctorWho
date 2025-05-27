package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;
import com.doctorwho.model.Categoria;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/articulos/lista")
public class ArticuloListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            
            ArticuloDao articuloDao = new ArticuloDao(connection);
            CategoriaDao categoriaDao = new CategoriaDao(connection);

            // Obtener parámetros de filtro
            String busqueda = request.getParameter("q");
            String categoriaIdStr = request.getParameter("categoria");
            String disponibilidadStr = request.getParameter("disponibilidad");

            // Paginación
            int page = 1;
            int limit = 8; // 8 artículos por página
            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
            }
            int offset = (page - 1) * limit;

            // Convertir parámetros
            Integer categoriaId = null;
            if (categoriaIdStr != null && !categoriaIdStr.isEmpty() && !categoriaIdStr.equals("0")) {
                try {
                    categoriaId = Integer.parseInt(categoriaIdStr);
                } catch (NumberFormatException e) {
                    // Ignorar si no es un número válido
                }
            }

            Boolean disponible = null;
            if ("true".equals(disponibilidadStr)) {
                disponible = true;
            } else if ("false".equals(disponibilidadStr)) {
                disponible = false;
            }

            // ACTIVAR BÚSQUEDA: Usar métodos de filtrado en lugar de getAllArticulos
            List<Articulo> articulos;
            int totalArticulos;

            if (busqueda != null && !busqueda.trim().isEmpty() || categoriaId != null || disponible != null) {
                // Usar búsqueda filtrada
                articulos = articuloDao.getArticulosFiltrados(busqueda, categoriaId, disponible, offset, limit);
                totalArticulos = articuloDao.countArticulosFiltrados(busqueda, categoriaId, disponible);
            } else {
                // Sin filtros, usar método original con paginación manual
                List<Articulo> todosArticulos = articuloDao.getAllArticulos();
                totalArticulos = todosArticulos.size();
                
                int fromIndex = Math.min(offset, todosArticulos.size());
                int toIndex = Math.min(offset + limit, todosArticulos.size());
                
                if (fromIndex < toIndex) {
                    articulos = todosArticulos.subList(fromIndex, toIndex);
                } else {
                    articulos = List.of(); // Lista vacía
                }
            }

            // Calcular páginas
            int totalPages = (int) Math.ceil((double) totalArticulos / limit);

            // Obtener todas las categorías para el filtro
            List<Categoria> categorias = categoriaDao.getAllCategorias();

            // Atributos para la vista
            request.setAttribute("articulos", articulos);
            request.setAttribute("categorias", categorias);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalArticulos", totalArticulos);
            
            // Mantener filtros actuales
            request.setAttribute("busqueda", busqueda);
            request.setAttribute("categoriaSeleccionada", categoriaId);
            request.setAttribute("disponibilidadSeleccionada", disponibilidadStr);

            // Verificar rol de admin
            HttpSession session = request.getSession();
            boolean esAdmin = session.getAttribute("es_admin") != null && 
                            (boolean) session.getAttribute("es_admin");
            request.setAttribute("esAdmin", esAdmin);

            // Redirigir a la JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/articulos/lista.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (Exception e) {
            System.err.println("❌ ERROR EN ArticuloListServlet:");
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los artículos: " + e.getMessage() + "</h2>");
        }
    }
}