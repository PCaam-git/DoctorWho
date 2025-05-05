package com.patricia.servlet.usuario;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;
import com.patricia.exception.UsuarioNotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet("/edit_usuario")
@MultipartConfig
public class EditUsuarioServlet extends HttpServlet {

    private ArrayList<String> errors;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if ((currentSession.getAttribute("rol") == null) || (!currentSession.getAttribute("rol").equals("admin"))) {
            response.sendRedirect("/doctorwho/login.jsp");
            return;
        }

        if (!validate(request)) {
            response.getWriter().println(errors.toString());
            return;
        }

        String accion = request.getParameter("accion");

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contraseña = request.getParameter("contraseña");
        String rol = request.getParameter("rol");
        String estado = request.getParameter("estado");
        String activo = request.getParameter("activo");
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContraseña(contraseña);
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario.setActivo(activo != null);
            usuario.setFechaRegistro(new Date(System.currentTimeMillis()));

            // Procesa la imagen del usuario
            if (accion.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen != null && imagen.getSize() > 0) {
                    filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                usuario.setImagen(filename);
            } else {
                usuario.setId(Integer.parseInt(request.getParameter("usuarioId")));
                
                // Si se ha subido una nueva imagen, procesarla
                if (imagen != null && imagen.getSize() > 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    usuario.setImagen(filename);
                } else {
                    // Mantener la imagen actual
                    Usuario usuarioActual = usuarioDao.get(usuario.getId());
                    usuario.setImagen(usuarioActual.getImagen());
                }
            }

            boolean done = false;
            if (accion.equals("Registrar")) {
                done = usuarioDao.add(usuario);
            } else {
                done = usuarioDao.modify(usuario);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar el usuario");
            }
            
            database.close();
        } catch (SQLException sqle) {
            response.getWriter().println("No se ha podido conectar con la base de datos");
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            response.getWriter().println("No se ha podido cargar el driver de la base de datos");
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            response.getWriter().println("Error no esperado: " + ioe.getMessage());
            ioe.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if ((currentSession.getAttribute("rol") == null) || (!currentSession.getAttribute("rol").equals("admin"))) {
            response.sendRedirect("/doctorwho/login.jsp");
            return;
        }

        String usuarioId = request.getParameter("usuario_id");
        String accion;
        Usuario usuario = null;

        try {
            Database db = new Database();
            db.connect();
            UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
            
            if (usuarioId != null && !usuarioId.isEmpty()) {
                accion = "Modificar";
                usuario = usuarioDao.get(Integer.parseInt(usuarioId));
            } else {
                accion = "Registrar";
                usuario = new Usuario();
            }
            
            request.setAttribute("usuario", usuario);
            request.setAttribute("accion", accion);
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editarUsuario.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (UsuarioNotFoundException anfe) {
            response.sendRedirect("/doctorwho/usuarios");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("nombre").isEmpty()) {
            errors.add("El nombre es un campo obligatorio");
        }
        if (request.getParameter("email").isEmpty()) {
            errors.add("El email es un campo obligatorio");
        }
        if (request.getParameter("contraseña").isEmpty()) {
            errors.add("La contraseña es un campo obligatorio");
        }

        return errors.isEmpty();
    }
}