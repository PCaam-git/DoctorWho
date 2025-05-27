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
import com.doctorwho.model.Usuario;
import com.doctorwho.exception.UsuarioNotFoundException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Si ya está logueado, redirigir al perfil
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null) {
            response.sendRedirect(request.getContextPath() + "/usuario/perfil");
            return;
        }
        
        // Guardar la URL de donde viene (para redirección después del login)
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl != null && !returnUrl.isEmpty()) {
            request.getSession().setAttribute("returnUrl", returnUrl);
        }
        
        // Mostrar formulario de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        // Validación básica
        if (email == null || email.trim().isEmpty() || 
            contrasena == null || contrasena.trim().isEmpty()) {
            
            request.setAttribute("error", "Email y contraseña son obligatorios");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            Database database = new Database();
            database.connect();
            UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());

            // Validar credenciales
            String role = usuarioDao.loginUser(email, contrasena);
            
            // Obtener el usuario completo para tener su ID
            Usuario usuario = usuarioDao.getUserByEmail(email);

            // Crear sesión con TODOS los datos necesarios
            HttpSession session = request.getSession();
            session.setAttribute("user_id", usuario.getId());
            session.setAttribute("email", email);
            session.setAttribute("user_name", usuario.getNombre());
            session.setAttribute("role", role);
            session.setAttribute("es_admin", "admin".equals(role));

            database.close();

            //Redirigir al perfil después del login
            String returnUrl = (String) session.getAttribute("returnUrl");
            if (returnUrl != null && !returnUrl.isEmpty()) {
                session.removeAttribute("returnUrl");
                response.sendRedirect(returnUrl);
            } else {
                // Redirigir al perfil para todos los usuarios
                response.sendRedirect(request.getContextPath() + "/usuario/perfil");
            }

        } catch (SQLException e) {
            request.setAttribute("error", "Error de conexión con la base de datos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            request.setAttribute("error", "Error del sistema");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            e.printStackTrace();
        } catch (UsuarioNotFoundException e) {
            request.setAttribute("error", "Email o contraseña incorrectos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}