package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;

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

@WebServlet("/create_articulo")
@MultipartConfig
public class CreateArticuloServlet extends HttpServlet {

    private ArrayList<String> errors;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mostrar el formulario de creación
        request.getRequestDispatcher("/articulo-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        HttpSession currentSession = request.getSession();
        if (currentSession.getAttribute("role") == null) {
            response.sendRedirect("/doctorwho/login.jsp");
            return;
        }

        if (!validate(request)) {
            response.getWriter().println(errors.toString());
            return;
        }

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");
        String disponible = request.getParameter("disponible");
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
            Articulo articulo = new Articulo();
            articulo.setNombre(nombre);
            articulo.setDescripcion(descripcion);
            articulo.setPrecio(Double.parseDouble(precio));
            articulo.setDisponible(disponible != null);
            articulo.setFechaAñadido(new Date(System.currentTimeMillis()));

            // Procesa la imagen del artículo si existe
            String filename = "default.jpg";
            if (imagen != null && imagen.getSize() != 0) {
                filename = UUID.randomUUID() + ".jpg";
                String imagePath = "/home/user/apache-tomcat-10.1.40/webapps/doctorwho_images";
                InputStream inputStream = imagen.getInputStream();
                Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
            }

            articulo.setImagen(filename);

            boolean done = articuloDao.add(articulo);

            if (done) {
                response.sendRedirect("/doctorwho/articulos");
            } else {
                response.getWriter().print("No se ha podido guardar el artículo");
            }
            
            database.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("nombre").isEmpty()) {
            errors.add("El nombre es un campo obligatorio");
        }
        if ((request.getParameter("precio").isEmpty()) || (!request.getParameter("precio").matches("[0-9]*\\.?[0-9]*"))) {
            errors.add("El precio debe ser un valor numérico");
        }
        
        return errors.isEmpty();
    }
}