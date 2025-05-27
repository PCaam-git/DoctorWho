package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/usuarios/editar")
public class UsuarioEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || !idParam.matches("\\d+")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ ID de usuario inválido.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            UsuarioDao usuarioDao = new UsuarioDao(connection);
            Usuario usuario = usuarioDao.get(id);

            request.setAttribute("usuario", usuario);

            db.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios/editar.jsp");
            dispatcher.forward(request, response);

        } catch (UsuarioNotFoundException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Usuario no encontrado.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al cargar el usuario para edición.</h2>");
        }
    }
}