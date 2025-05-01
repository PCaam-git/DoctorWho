package com.patricia.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle login logic here (authentication, etc.)
        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        System.out.println("Usuario: " + usuario);
        System.out.println("Contraseña: " + contraseña);

        response.sendRedirect("welcome.jsp");

    }

}
