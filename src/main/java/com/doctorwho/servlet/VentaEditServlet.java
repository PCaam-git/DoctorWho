package com.doctorwho.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.doctorwho.dao.VentaDao;
import com.doctorwho.database.Database;
import com.doctorwho.model.Venta;

@WebServlet("/edit_Venta")
public class VentaEditServlet extends HttpServlet {

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

        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        int articuloId = Integer.parseInt(request.getParameter("articulo_id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        BigDecimal total = new BigDecimal(request.getParameter("total"));
        String estadoVenta = request.getParameter("estado_venta");
        boolean pagado = Boolean.parseBoolean(request.getParameter("pagado"));

        try {
            Database database = new Database();
            database.connect();
            VentaDao ventaDao = new VentaDao(database.getConnection());
            Venta venta = new Venta();

            // Usando los nombres de métodos correctos según la clase Venta actualizada
            venta.setUsuarioId(usuarioId);
            venta.setArticuloId(articuloId);
            venta.setCantidad(cantidad);
            venta.setTotal(total);
            venta.setFechaVenta(new Date(System.currentTimeMillis()));
            venta.setEstadoVenta(estadoVenta);
            venta.setPagado(pagado);

            if (!action.equals("Registrar")) {
                venta.setId(Integer.parseInt(request.getParameter("venta_id")));
            }

            boolean done = false;
            if (action.equals("Registrar")) {
                done = ventaDao.addVenta(venta);
            } else {
                done = ventaDao.updateVenta(venta);
            }

            if (done) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("No se ha podido guardar la venta");
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

        try {
            // Validar que usuario_id sea numérico y exista
            if (request.getParameter("usuario_id").isEmpty() ||
                    !request.getParameter("usuario_id").matches("\\d+")) {
                errors.add("ID de usuario inválido");
            }

            // Validar que articulo_id sea numérico y exista
            if (request.getParameter("articulo_id").isEmpty() ||
                    !request.getParameter("articulo_id").matches("\\d+")) {
                errors.add("ID de artículo inválido");
            }

            // Validar que cantidad sea numérico y positivo
            if (request.getParameter("cantidad").isEmpty() ||
                    !request.getParameter("cantidad").matches("\\d+") ||
                    Integer.parseInt(request.getParameter("cantidad")) <= 0) {
                errors.add("La cantidad debe ser un número positivo");
            }

            // Validar que total sea numérico y positivo
            if (request.getParameter("total").isEmpty() ||
                    !request.getParameter("total").matches("[0-9]*\\.?[0-9]*") ||
                    Double.parseDouble(request.getParameter("total")) <= 0) {
                errors.add("El total debe ser un número positivo");
            }

            // Validar estado de venta
            String estadoVenta = request.getParameter("estado_venta");
            if (estadoVenta == null || estadoVenta.isEmpty()) {
                errors.add("El estado de venta es obligatorio");
            }

        } catch (NumberFormatException e) {
            errors.add("Error en formato de número: " + e.getMessage());
        }

        return errors.isEmpty();
    }
}