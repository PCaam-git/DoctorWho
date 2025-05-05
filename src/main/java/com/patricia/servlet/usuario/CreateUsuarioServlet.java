package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/create_usuario")
public class CreateUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setContraseña(request.getParameter("contraseña"));
            usuario.setRol(request.getParameter("rol"));
            usuario.setEstado(request.getParameter("estado"));
            usuario.setActivo(true);

            Database db = new Database();
            db.connect();
            UsuarioDao dao = new UsuarioDao(db.getConnection());
            dao.add(usuario);
            db.close();

            response.sendRedirect("/doctorwho/usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
