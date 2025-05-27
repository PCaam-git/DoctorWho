package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.model.Usuario;
import com.doctorwho.database.Database;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuarios/detalle")
public class UsuarioDetailServlet extends HttpServlet {

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
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario requerido");
                return;
            }

            int id = Integer.parseInt(idParam);
            
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            UsuarioDao dao = new UsuarioDao(connection);
            Usuario usuario = dao.get(id);

            request.setAttribute("usuario", usuario);
            
            // Verificar si el usuario es admin
            request.setAttribute("esAdmin", true); // Ya verificado arriba
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios/detalle.jsp");
            dispatcher.forward(request, response);

            db.close();

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario inválido");
        } catch (UsuarioNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar los detalles del usuario</h2>");
        }
    }
}