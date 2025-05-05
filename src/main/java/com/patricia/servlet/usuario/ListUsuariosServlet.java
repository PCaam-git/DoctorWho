package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/usuarios")
public class ListUsuariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        String pageParam = request.getParameter("page");
        int page = 0;
        
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException nfe) {
                // Si no es un número, se queda en página 0
            }
        }

        try {
            Database db = new Database();
            db.connect();
            UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
            
            ArrayList<Usuario> usuarios;
            int totalUsuarios;
            
            if (search != null && !search.isEmpty()) {
                usuarios = usuarioDao.getAll(page, search);
                totalUsuarios = usuarioDao.getCount(search);
            } else {
                usuarios = usuarioDao.getAll(page);
                totalUsuarios = usuarioDao.getCount("");
            }
            
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil(totalUsuarios / 10.0)); // Asumiendo 10 elementos por página
            request.setAttribute("search", search);
            
            request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}