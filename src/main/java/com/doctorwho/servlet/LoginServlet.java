package com.doctorwho.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.exception.UsuarioNotFoundException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email"); // Cambiado de username a email
        String password = request.getParameter("contrasena");

        try {
            Database database = new Database();
            database.connect();
            UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());

            // Usando el nuevo método que devuelve "admin" o "user"
            String role = usuarioDao.loginUser(email, password);

            HttpSession session = request.getSession();
            session.setAttribute("email", email); // Guardamos email en lugar de username
            session.setAttribute("role", role); // Guardamos "admin" o "user"

            response.getWriter().print("ok");

            database.close();
        } catch (SQLException sqle) {
            try {
                response.getWriter().println("No se ha podido conectar con la base de datos");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UsuarioNotFoundException unfe) {
            try {
                response.getWriter().println("Usuario/contraseña incorrectos");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}