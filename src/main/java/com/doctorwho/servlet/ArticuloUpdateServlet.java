package com.doctorwho.servlet;

import com.doctorwho.dao.ArticuloDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Articulo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.UUID;

@WebServlet("/articulos/actualizar")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB máximo
public class ArticuloUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Para formularios multipart, usar este método helper
        String idParam = getParameterValue(request, "id");
        String nombre = getParameterValue(request, "nombre");
        String descripcion = getParameterValue(request, "descripcion");
        String precioParam = getParameterValue(request, "precio");
        String disponibleParam = getParameterValue(request, "disponible");
        String fechaAnadidoParam = getParameterValue(request, "fechaAnadido");
        String categoriaIdParam = getParameterValue(request, "categoria_id");
        String imagenActual = getParameterValue(request, "imagen_actual");
        Part imagenPart = request.getPart("imagen");

        // Validación de campos obligatorios
        if (idParam == null || nombre == null || descripcion == null || precioParam == null || 
            fechaAnadidoParam == null || categoriaIdParam == null || 
            idParam.trim().isEmpty() || nombre.trim().isEmpty() || 
            descripcion.trim().isEmpty() || precioParam.trim().isEmpty() || 
            fechaAnadidoParam.trim().isEmpty() || categoriaIdParam.trim().isEmpty()) {
            
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Todos los campos obligatorios deben ser completados.</h2>");
            response.getWriter().println("<p>Valores recibidos:</p>");
            response.getWriter().println("<p>ID: " + idParam + "</p>");
            response.getWriter().println("<p>Nombre: " + nombre + "</p>");
            response.getWriter().println("<p>Descripción: " + descripcion + "</p>");
            response.getWriter().println("<p>Precio: " + precioParam + "</p>");
            response.getWriter().println("<p>Fecha: " + fechaAnadidoParam + "</p>");
            response.getWriter().println("<p>Categoría: " + categoriaIdParam + "</p>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            BigDecimal precio = new BigDecimal(precioParam);
            boolean disponible = "true".equals(disponibleParam);
            Date fechaAnadido = Date.valueOf(fechaAnadidoParam);
            int categoriaId = Integer.parseInt(categoriaIdParam);

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            // Obtener artículo actual para verificar que existe
            ArticuloDao articuloDao = new ArticuloDao(connection);
            Articulo articulo = articuloDao.getArticuloById(id);
            
            if (articulo == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Artículo no encontrado.</h2>");
                db.close();
                return;
            }

            // Actualizar campos básicos
            articulo.setNombre(nombre);
            articulo.setDescripcion(descripcion);
            articulo.setPrecio(precio);
            articulo.setDisponible(disponible);
            articulo.setFechaAnadido(fechaAnadido);
            articulo.setCategoriaId(categoriaId);
            
            // Procesar imagen
            String imagenFinal = imagenActual; // Por defecto mantener la imagen actual
            
            if (imagenPart != null && imagenPart.getSize() > 0) {
                // Validar tipo de archivo
                String contentType = imagenPart.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().println("<h2 style='color:red'>❌ Solo se permiten archivos de imagen.</h2>");
                    db.close();
                    return;
                }
                
                // Generar nombre único para la nueva imagen
                String extension = getFileExtension(imagenPart.getSubmittedFileName());
                String nuevoNombreImagen = UUID.randomUUID().toString() + "." + extension;
                
                // Guardar imagen en el servidor
                String uploadPath = getServletContext().getRealPath("/images/articulos");
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    Files.copy(inputStream, uploadDir.resolve(nuevoNombreImagen));
                    imagenFinal = nuevoNombreImagen; // Usar la nueva imagen
                } catch (IOException e) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().println("<h2 style='color:red'>❌ Error al guardar la imagen.</h2>");
                    db.close();
                    return;
                }
            }
            
            // Establecer la imagen final (nueva o actual)
            articulo.setImagen(imagenFinal);

            boolean exito = articuloDao.updateArticulo(articulo);
            db.close();

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/articulos/detalle?id=" + id);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el artículo.</h2>");
            }

        } catch (NumberFormatException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Formato de número inválido en precio, categoría o ID.</h2>");
        } catch (IllegalArgumentException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Formato de fecha inválido. Use YYYY-MM-DD.</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar el artículo: " + e.getMessage() + "</h2>");
        }
    }
    
    // Método helper para obtener valores de parámetros en formularios multipart
    private String getParameterValue(HttpServletRequest request, String paramName) {
        try {
            Part part = request.getPart(paramName);
            if (part != null) {
                try (InputStream inputStream = part.getInputStream()) {
                    return new String(inputStream.readAllBytes(), "UTF-8");
                }
            }
        } catch (Exception e) {
            // Si falla, intentar obtener como parámetro normal
        }
        return request.getParameter(paramName);
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "jpg";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}