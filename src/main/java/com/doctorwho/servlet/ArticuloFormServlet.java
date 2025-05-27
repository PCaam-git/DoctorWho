package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;
import com.doctorwho.model.Categoria;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/articulos/formulario")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB máximo
public class ArticuloFormServlet extends HttpServlet {

    // Muestra el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            // Guardar la URL actual para redirección después del login
            String currentUrl = request.getRequestURL().toString();
            response.sendRedirect(request.getContextPath() + "/login?returnUrl=" + 
                                java.net.URLEncoder.encode(currentUrl, "UTF-8"));
            return;
        }
        
        try {
            // Cargar categorías para el select
            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();
            
            CategoriaDao categoriaDao = new CategoriaDao(connection);
            List<Categoria> categorias = categoriaDao.getAllCategorias();
            
            request.setAttribute("categorias", categorias);
            
            db.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar las categorías: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/articulos/formulario.jsp").forward(request, response);
    }

    // Procesa el registro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioParam = request.getParameter("precio");
        String disponibleParam = request.getParameter("disponible");
        String categoriaIdParam = request.getParameter("categoria_id");
        Part imagenPart = request.getPart("imagen");

        // Validaciones
        if (nombre == null || descripcion == null || precioParam == null || 
            categoriaIdParam == null || nombre.trim().isEmpty() || 
            descripcion.trim().isEmpty() || precioParam.trim().isEmpty() || 
            categoriaIdParam.trim().isEmpty()) {
            
            request.setAttribute("error", "❌ Todos los campos son obligatorios.");
            doGet(request, response);
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(precioParam);
            boolean disponible = "true".equals(disponibleParam);
            int categoriaId = Integer.parseInt(categoriaIdParam);
            Date fechaAnadido = new Date(System.currentTimeMillis());
            
            // Procesar imagen
            String imagenNombre = "default.jpg";
            if (imagenPart != null && imagenPart.getSize() > 0) {
                // Validar tipo de archivo
                String contentType = imagenPart.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    request.setAttribute("error", "❌ Solo se permiten archivos de imagen.");
                    doGet(request, response);
                    return;
                }
                
                // Generar nombre único para la imagen
                String extension = getFileExtension(imagenPart.getSubmittedFileName());
                imagenNombre = UUID.randomUUID().toString() + "." + extension;
                
                // Guardar imagen en el servidor
                String uploadPath = getServletContext().getRealPath("/images/articulos");
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    Files.copy(inputStream, uploadDir.resolve(imagenNombre));
                }
            }

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            Articulo articulo = new Articulo();
            articulo.setNombre(nombre);
            articulo.setDescripcion(descripcion);
            articulo.setPrecio(precio);
            articulo.setDisponible(disponible);
            articulo.setFechaAnadido(fechaAnadido);
            articulo.setCategoriaId(categoriaId);
            articulo.setImagen(imagenNombre);

            ArticuloDao articuloDao = new ArticuloDao(connection);
            boolean exito = articuloDao.addArticulo(articulo);

            db.close();
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/articulos/lista");
            } else {
                request.setAttribute("error", "❌ Error al guardar el artículo en la base de datos.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "❌ El precio y la categoría deben ser valores numéricos válidos.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Error interno al registrar el artículo: " + e.getMessage());
            doGet(request, response);
        }
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "jpg";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}