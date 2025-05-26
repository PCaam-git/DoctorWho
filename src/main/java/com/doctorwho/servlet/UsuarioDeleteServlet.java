package com.doctorwho.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;

@WebServlet("/delete_Usuario")
public class UsuarioDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if (currentSession.getAttribute("es_admin") == null) {
            response.sendRedirect("/DoctorWho/login.jsp");
            return;
        }

        String usuario_id = request.getParameter("usuario_id");
        // TODO añadir validación
        try {
            Database db = new Database();
            db.connect();
            UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
            usuarioDao.delete(Integer.parseInt(usuario_id));
            // TODO borrar imagen
            response.sendRedirect("/DoctorWho");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}