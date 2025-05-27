package com.doctorwho.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener sesión actual
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Guardar información para mensaje de despedida
            String email = (String) session.getAttribute("email");
            
            // Invalidar sesión
            session.invalidate();
            
            // Crear nueva sesión para el mensaje
            HttpSession newSession = request.getSession();
            if (email != null) {
                newSession.setAttribute("logoutMessage", "Sesión cerrada correctamente para " + email);
            }
        }
        
        // Redirigir al login con mensaje
        response.sendRedirect(request.getContextPath() + "/login.jsp?logout=true");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // También permitir logout por POST
        doGet(request, response);
    }
}