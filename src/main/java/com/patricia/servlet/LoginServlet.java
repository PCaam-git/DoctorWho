package com.patricia.servlet;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.exception.UsuarioNotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Database database = new Database();
            database.connect();
            UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());
            String role = usuarioDao.loginUser(username, password);

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            response.getWriter().print("ok");
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