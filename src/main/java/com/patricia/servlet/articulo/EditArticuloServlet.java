package com.patricia.servlet.articulo;

import com.patricia.dao.ArticuloDao;
import com.patricia.database.Database;
import com.patricia.model.Articulo;
import com.patricia.exception.ArticuloNotFoundException;

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

@WebServlet("/edit_articulo")
@MultipartConfig
public class EditArticuloServlet extends HttpServlet {

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
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");
        Part imagen = request.getPart("imagen");
        String disponible = request.getParameter("disponible");

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

            // Procesa la imagen del artículo
            if (accion.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen != null && imagen.getSize() > 0) {
                    filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                articulo.setImagen(filename);
            } else {
                articulo.setId(Integer.parseInt(request.getParameter("articuloId")));
                
                // Si se ha subido una nueva imagen, procesarla
                if (imagen != null && imagen.getSize() > 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    articulo.setImagen(filename);
                } else {
                    // Mantener la imagen actual
                    Articulo articuloActual = articuloDao.get(articulo.getId());
                    articulo.setImagen(articuloActual.getImagen());
                }
            }

            boolean done = false;
            if (accion.equals("Registrar")) {
                done = articuloDao.add(articulo);
            } else {
                done = articuloDao.modify(articulo);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar el artículo");
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

        String articuloId = request.getParameter("articulo_id");
        String accion;
        Articulo articulo = null;

        try {
            Database db = new Database();
            db.connect();
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            
            if (articuloId != null && !articuloId.isEmpty()) {
                accion = "Modificar";
                articulo = articuloDao.get(Integer.parseInt(articuloId));
            } else {
                accion = "Registrar";
                articulo = new Articulo();
            }
            
            request.setAttribute("articulo", articulo);
            request.setAttribute("accion", accion);
            request.getRequestDispatcher("/WEB-INF/jsp/articulo/editarArticulo.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (ArticuloNotFoundException anfe) {
            response.sendRedirect("/doctorwho/articulos");
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
            errors.add("El precio es un campo numérico");
        }

        return errors.isEmpty();
    }
}