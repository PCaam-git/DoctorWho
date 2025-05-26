package com.doctorwho.servlet;

import com.doctorwho.dao.CategoriaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Categoria;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/categorias/formulario")
public class CategoriaRegisterServlet extends HttpServlet {

    // Muestra el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario es admin
        HttpSession session = request.getSession();
        if (session.getAttribute("es_admin") == null || !(boolean) session.getAttribute("es_admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        request.getRequestDispatcher("/categorias/formulario.jsp").forward(request, response);
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
        String cantidadParam = request.getParameter("cantidad");
        String tieneProductosParam = request.getParameter("tiene_productos");
        String fechaActualizacionParam = request.getParameter("fecha_actualizacion");
        String precioMedioParam = request.getParameter("precio_medio");
        String imagen = request.getParameter("imagen");

        if (nombre == null || descripcion == null || cantidadParam == null || 
            fechaActualizacionParam == null || nombre.isEmpty() || 
            descripcion.isEmpty() || cantidadParam.isEmpty() || 
            fechaActualizacionParam.isEmpty()) {
            
            request.setAttribute("error", "❌ Todos los campos obligatorios deben ser completados.");
            doGet(request, response); // volvemos al formulario
            return;
        }

        try {
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
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setCantidad(cantidad);
            categoria.setTieneProductos(tieneProductos);
            categoria.setFechaActualizacion(fechaActualizacion);
            categoria.setPrecioMedio(precioMedio);
            categoria.setImagen(imagen);

            CategoriaDao categoriaDao = new CategoriaDao(connection);
            boolean exito = categoriaDao.addCategoria(categoria);

            db.close();
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/categorias/lista");
            } else {
                request.setAttribute("error", "❌ Error al guardar la categoría en la base de datos.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "❌ Los campos numéricos deben contener valores válidos.");
            doGet(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "❌ El formato de fecha es incorrecto. Use YYYY-MM-DD.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Error interno al registrar la categoría: " + e.getMessage());
            doGet(request, response);
        }
    }
}