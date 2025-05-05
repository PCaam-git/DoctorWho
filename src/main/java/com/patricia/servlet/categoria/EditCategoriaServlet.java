package com.patricia.servlet.categoria;

import com.patricia.dao.CategoriaDao;
import com.patricia.database.Database;
import com.patricia.model.Categoria;
import com.patricia.exception.CategoriaNotFoundException;

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

@WebServlet("/edit_categoria")
@MultipartConfig
public class EditCategoriaServlet extends HttpServlet {

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
        String cantidad = request.getParameter("cantidad");
        String tieneProductos = request.getParameter("tieneProductos");
        String precioMedio = request.getParameter("precioMedio");
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            CategoriaDao categoriaDao = new CategoriaDao(database.getConnection());
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setCantidad(Integer.parseInt(cantidad));
            categoria.setTieneProductos(tieneProductos != null);
            categoria.setPrecioMedio(Double.parseDouble(precioMedio));
            categoria.setFechaActualizacion(new Date(System.currentTimeMillis()));

            // Procesa la imagen de la categoría
            if (accion.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen != null && imagen.getSize() > 0) {
                    filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                categoria.setImagen(filename);
            } else {
                categoria.setId(Integer.parseInt(request.getParameter("categoriaId")));
                
                // Si se ha subido una nueva imagen, procesarla
                if (imagen != null && imagen.getSize() > 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    categoria.setImagen(filename);
                } else {
                    // Mantener la imagen actual
                    Categoria categoriaActual = categoriaDao.get(categoria.getId());
                    categoria.setImagen(categoriaActual.getImagen());
                }
            }

            boolean done = false;
            if (accion.equals("Registrar")) {
                done = categoriaDao.add(categoria);
            } else {
                done = categoriaDao.modify(categoria);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar la categoría");
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

        String categoriaId = request.getParameter("categoria_id");
        String accion;
        Categoria categoria = null;

        try {
            Database db = new Database();
            db.connect();
            CategoriaDao categoriaDao = new CategoriaDao(db.getConnection());
            
            if (categoriaId != null && !categoriaId.isEmpty()) {
                accion = "Modificar";
                categoria = categoriaDao.get(Integer.parseInt(categoriaId));
            } else {
                accion = "Registrar";
                categoria = new Categoria();
            }
            
            request.setAttribute("categoria", categoria);
            request.setAttribute("accion", accion);
            request.getRequestDispatcher("/WEB-INF/jsp/categorias/editarCategoria.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (CategoriaNotFoundException anfe) {
            response.sendRedirect("/doctorwho/categorias");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("nombre").isEmpty()) {
            errors.add("El nombre es un campo obligatorio");
        }
        if ((request.getParameter("cantidad").isEmpty()) || (!request.getParameter("cantidad").matches("[0-9]*"))) {
            errors.add("La cantidad es un campo numérico");
        }
        if ((request.getParameter("precioMedio").isEmpty()) || (!request.getParameter("precioMedio").matches("[0-9]*\\.?[0-9]*"))) {
            errors.add("El precio medio es un campo numérico");
        }

        return errors.isEmpty();
    }
}