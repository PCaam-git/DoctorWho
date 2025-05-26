package com.doctorwho.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import com.doctorwho.dao.UsuarioDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Usuario;

@WebServlet("/edit_Usuario")
@MultipartConfig
public class UsuarioEditServlet extends HttpServlet {

    private ArrayList<String> errors;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if ((currentSession.getAttribute("role") == null) || (!currentSession.getAttribute("role").equals("admin"))) {
            response.sendRedirect("/DoctorWho/login.jsp");
            return;
        }

        if (!validateRequest(request)) {
            response.getWriter().println(errors.toString());
            return;
        }

        String action = request.getParameter("action");

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        boolean esAdmin = Boolean.parseBoolean(request.getParameter("es_admin"));
        String credito = request.getParameter("credito");
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasena);
            usuario.setEsAdmin(esAdmin);
            usuario.setFechaRegistro(new Date(System.currentTimeMillis()));
            usuario.setCredito(new BigDecimal(credito));

            if (action.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen.getSize() != 0) {
                    filename = UUID.randomUUID() + ".jpg";
                    String imagePath = "/home/astable/apache-tomcat-10.1.40/webapp/doctorwho_images";
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                usuario.setImagen(filename);
            } else {
                usuario.setId(Integer.parseInt(request.getParameter("usuario_id")));
                // Si hay una nueva imagen, procesar
                if (imagen.getSize() != 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = "/home/astable/apache-tomcat-10.1.40/webapp/doctorwho_images";
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    usuario.setImagen(filename);
                }
            }

            boolean done = false;
            if (action.equals("Registrar")) {
                done = usuarioDao.add(usuario);
            } else {
                done = usuarioDao.modify(usuario);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar el usuario");
            }
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

    private boolean validateRequest(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("nombre").isEmpty()) {
            errors.add("El nombre es un campo obligatorio");
        }
        if (request.getParameter("email").isEmpty()) {
            errors.add("El email es un campo obligatorio");
        } else if (!request.getParameter("email").matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.add("El formato del email no es válido");
        }
        // Si es acción de registro o se especifica una contraseña para actualizar
        if (request.getParameter("action").equals("Registrar") || !request.getParameter("contrasena").isEmpty()) {
            if (request.getParameter("contrasena").length() < 6) {
                errors.add("La contraseña debe tener al menos 6 caracteres");
            }
        }
        if ((request.getParameter("credito").isEmpty()) ||
                (!request.getParameter("credito").matches("[0-9]*\\.?[0-9]*"))) {
            errors.add("El crédito debe ser un número");
        }

        return errors.isEmpty();
    }
}