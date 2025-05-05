package com.patricia.servlet.venta;

import com.patricia.dao.VentaDao;
import com.patricia.dao.ArticuloDao;
import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Venta;
import com.patricia.model.Articulo;
import com.patricia.model.Usuario;
import com.patricia.exception.VentaNotFoundException;

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
import java.util.List;
import java.util.UUID;

@WebServlet("/edit_venta")
@MultipartConfig
public class EditVentaServlet extends HttpServlet {

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

        String idComprador = request.getParameter("idComprador");
        String idArticulo = request.getParameter("idArticulo");
        String precio = request.getParameter("precio");
        String estadoVenta = request.getParameter("estadoVenta");
        String pagado = request.getParameter("pagado");
        String activo = request.getParameter("activo");
        Part imagen = request.getPart("imagen");

        try {
            Database database = new Database();
            database.connect();
            VentaDao ventaDao = new VentaDao(database.getConnection());
            Venta venta = new Venta();
            venta.setIdComprador(Integer.parseInt(idComprador));
            venta.setIdArticulo(Integer.parseInt(idArticulo));
            venta.setPrecio(Double.parseDouble(precio));
            venta.setEstadoVenta(estadoVenta);
            venta.setPagado(pagado != null);
            venta.setActivo(activo != null);
            venta.setFechaTransaccion(new Date(System.currentTimeMillis()));

            // Procesa la imagen de la venta
            if (accion.equals("Registrar")) {
                String filename = "default.jpg";
                if (imagen != null && imagen.getSize() > 0) {
                    filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                }

                venta.setImagen(filename);
            } else {
                venta.setIdTransaccion(Integer.parseInt(request.getParameter("ventaId")));
                
                // Si se ha subido una nueva imagen, procesarla
                if (imagen != null && imagen.getSize() > 0) {
                    String filename = UUID.randomUUID() + ".jpg";
                    String imagePath = getServletContext().getInitParameter("imagePath");
                    InputStream inputStream = imagen.getInputStream();
                    Files.copy(inputStream, Path.of(imagePath + File.separator + filename));
                    venta.setImagen(filename);
                } else {
                    // Mantener la imagen actual
                    Venta ventaActual = ventaDao.get(venta.getIdTransaccion());
                    venta.setImagen(ventaActual.getImagen());
                }
            }

            boolean done = false;
            if (accion.equals("Registrar")) {
                done = ventaDao.add(venta);
            } else {
                done = ventaDao.modify(venta);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar la venta");
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

        String ventaId = request.getParameter("venta_id");
        String accion;
        Venta venta = null;

        try {
            Database db = new Database();
            db.connect();
            VentaDao ventaDao = new VentaDao(db.getConnection());
            ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
            UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
            
            List<Articulo> articulos = articuloDao.getAll();
            List<Usuario> usuarios = usuarioDao.getAll();
            
            if (ventaId != null && !ventaId.isEmpty()) {
                accion = "Modificar";
                venta = ventaDao.get(Integer.parseInt(ventaId));
            } else {
                accion = "Registrar";
                venta = new Venta();
            }
            
            request.setAttribute("venta", venta);
            request.setAttribute("accion", accion);
            request.setAttribute("articulos", articulos);
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/WEB-INF/jsp/ventas/editarVenta.jsp").forward(request, response);
            
            db.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (VentaNotFoundException anfe) {
            response.sendRedirect("/doctorwho/ventas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(HttpServletRequest request) {
        errors = new ArrayList<>();
        if (request.getParameter("idComprador").isEmpty()) {
            errors.add("El comprador es un campo obligatorio");
        }
        if (request.getParameter("idArticulo").isEmpty()) {
            errors.add("El artículo es un campo obligatorio");
        }
        if ((request.getParameter("precio").isEmpty()) || (!request.getParameter("precio").matches("[0-9]*\\.?[0-9]*"))) {
            errors.add("El precio es un campo numérico");
        }

        return errors.isEmpty();
    }
}