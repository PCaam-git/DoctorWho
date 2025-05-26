package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/categorias/actualizar")
public class CategoriaUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String cantidadParam = request.getParameter("cantidad");
        String tieneProductosParam = request.getParameter("tiene_productos");
        String fechaActualizacionParam = request.getParameter("fecha_actualizacion");
        String precioMedioParam = request.getParameter("precio_medio");
        String imagen = request.getParameter("imagen");

        if (idParam == null || nombre == null || descripcion == null || cantidadParam == null || 
            fechaActualizacionParam == null || idParam.isEmpty() || nombre.isEmpty() || 
            descripcion.isEmpty() || cantidadParam.isEmpty() || fechaActualizacionParam.isEmpty()) {
            
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Todos los campos obligatorios deben ser completados.</h2>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            int cantidad = Integer.parseInt(cantidadParam);
            boolean tieneProductos = "true".equals(tieneProductosParam);
            Date fechaActualizacion = Date.valueOf(fechaActualizacionParam);
            BigDecimal precioMedio = null;
            
            if (precioMedioParam != null && !precioMedioParam.isEmpty()) {
                precioMedio = new BigDecimal(precioMedioParam);
            }
            
            if (imagen == null || imagen.isEmpty()) {
                imagen = "default.jpg";
            }

            Database db = new Database();
            db.connect();
            Connection connection = db.getConnection();

            Categoria categoria = new Categoria();
            categoria.setId(id);
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setCantidad(cantidad);
            categoria.setTieneProductos(tieneProductos);
            categoria.setFechaActualizacion(fechaActualizacion);
            categoria.setPrecioMedio(precioMedio);
            categoria.setImagen(imagen);

            CategoriaDao categoriaDao = new CategoriaDao(connection);
            boolean exito = categoriaDao.updateCategoria(categoria);

            db.close();

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/categorias/detalle?id=" + id);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar la categoría.</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2 style='color:red'>❌ Error al actualizar la categoría: " + e.getMessage() + "</h2>");
        }
    }
}