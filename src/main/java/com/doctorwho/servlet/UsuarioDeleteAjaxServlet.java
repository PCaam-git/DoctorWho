package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/usuarios/eliminar-ajax")
public class UsuarioDeleteAjaxServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar respuesta JSON
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            out.print("{\"success\": false, \"message\": \"No tienes permisos para esta acción\"}");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"ID de usuario requerido\"}");
                return;
            }
            
            int id = Integer.parseInt(idParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            UsuarioDao dao = new UsuarioDao(connection);
            
            // Obtener el usuario para validar y obtener su nombre
            Usuario usuario;
            try {
                usuario = dao.get(id);
            } catch (UsuarioNotFoundException e) {
                out.print("{\"success\": false, \"message\": \"Usuario no encontrado\"}");
                db.close();
                return;
            }

            // Intentar eliminar
            boolean eliminado = dao.delete(id);
            
            db.close();

            if (eliminado) {
                out.print("{\"success\": true, \"message\": \"Usuario '" + usuario.getNombre() + "' eliminado correctamente\", \"userId\": " + id + "}");
            } else {
                out.print("{\"success\": false, \"message\": \"Error al eliminar el usuario\"}");
            }

        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"ID de usuario inválido\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error interno del servidor\"}");
        }
    }
}