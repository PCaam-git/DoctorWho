package com.doctorwho.servlet;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/add-usuario")
public class UsuarioAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Database database = new Database();
            database.connect();
            Connection connection = database.getConnection();

            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setContrasena(request.getParameter("contrasena"));
            usuario.setEsAdmin(Boolean.parseBoolean(request.getParameter("es_admin")));
            usuario.setFechaRegistro(Date.valueOf(request.getParameter("fecha_registro")));
            usuario.setCredito(new BigDecimal(request.getParameter("credito")));
            usuario.setImagen(request.getParameter("imagen"));

            UsuarioDao dao = new UsuarioDao(connection);
            boolean exito = dao.add(usuario);

            if (exito) {
                response.sendRedirect("usuarios.jsp?mensaje=añadido");
            } else {
                response.sendRedirect("formulario-usuario.jsp?error=true");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("formulario-usuario.jsp?error=true");
        }
    }
}
