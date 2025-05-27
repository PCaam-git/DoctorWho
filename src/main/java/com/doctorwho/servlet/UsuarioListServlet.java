package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet("/usuarios/lista")
public class UsuarioListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            
            UsuarioDao usuarioDao = new UsuarioDao(connection);

            // Obtener parámetros de búsqueda y paginación
            String busqueda = request.getParameter("q");
            
            // Paginación
            int page = 1;
            int limit = 10; // 10 usuarios por página
            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
            }
            int offset = (page - 1) * limit;

            // Contar total de usuarios
            int totalUsuarios = usuarioDao.countUsuarios(busqueda);
            int totalPages = (int) Math.ceil((double) totalUsuarios / limit);

            // Obtener usuarios paginados
            ArrayList<Usuario> usuarios = usuarioDao.getUsuariosPaged(busqueda, offset, limit);

            // Atributos para la vista
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("totalUsuarios", totalUsuarios);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            
            // Mantener filtro actual
            request.setAttribute("busqueda", busqueda);

            // Verificar si hay mensaje de eliminación
            String deletedUsuario = (String) session.getAttribute("deletedUsuario");
            if (deletedUsuario != null) {
                request.setAttribute("mensaje", "Usuario '" + deletedUsuario + "' eliminado correctamente");
                session.removeAttribute("deletedUsuario");
            }

            // Redirigir a la JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios/lista.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (Exception e) {
            System.err.println("❌ ERROR EN UsuarioListServlet:");
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los usuarios: " + e.getMessage() + "</h2>");
        }
    }
}