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

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;

@WebServlet("/edit_Articulo")
@MultipartConfig
public class ArticuloEditServlet extends HttpServlet {

    private ArrayList<String> errors;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if ((currentSession.getAttribute("role") == null)
                || (!currentSession.getAttribute("role").equals("admin"))) {
            response.sendRedirect("/DoctorWho/login.jsp");
            return;
        }

        if (!validate(request)) {
            response.getWriter().println(errors.toString());
            return;
        }

        String action = request.getParameter("action");

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");
        boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
            Articulo articulo = new Articulo();
            articulo.setNombre(nombre);
            articulo.setDescripcion(descripcion);
            articulo.setPrecio(new BigDecimal(precio));
            articulo.setDisponible(disponible);
            articulo.setFechaAnadido(new Date(System.currentTimeMillis()));

            if (action.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen.getSize() != 0) {
                    filename = UUID.randomUUID() + ".jpg";

                    String imagePath = "/home/astable/apache-tomcat-10.1.40/webapp/doctorwho_images";
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                articulo.setImagen(filename);
            } else {
                articulo.setId(Integer.parseInt(request.getParameter("articulo_id")));
                // Si hay una nueva imagen, procesarla
                if (imagen != null && imagen.getSize() > 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = "/home/astable/apache-tomcat-10.1.40/webapp/doctorwho_images";
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    articulo.setImagen(filename);
                }
            }

            boolean done = false;
            if (action.equals("Registrar")) {
                done = articuloDao.addArticulo(articulo);
            } else {
                done = articuloDao.updateArticulo(articulo);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar el artículo");
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

    // Renombrado a validate para coincidir con la llamada en doPost
    private boolean validate(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("nombre").isEmpty()) {
            errors.add("El nombre es un campo obligatorio");
        }
        if ((request.getParameter("precio").isEmpty())
                || (!request.getParameter("precio").matches("[0-9]*\\.?[0-9]*"))) {
            errors.add("El precio es un campo numérico");
        }
        if (request.getParameter("descripcion").isEmpty()) {
            errors.add("La descripción es obligatoria");
        }
        // Validación de disponible (si es necesario)
        String disponible = request.getParameter("disponible");
        if (disponible == null || (!disponible.equals("true") && !disponible.equals("false"))) {
            errors.add("El valor de disponibilidad es inválido");
        }

        return errors.isEmpty();
    }
}