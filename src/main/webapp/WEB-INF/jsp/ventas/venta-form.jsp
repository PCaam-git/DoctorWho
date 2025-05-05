<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.*" %>
<%@ page import="com.patricia.model.*" %>
<%@ page import="com.patricia.exception.VentaNotFoundException" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.List" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    if ((currentSession.getAttribute("role") == null) || (!currentSession.getAttribute("role").equals("admin"))) {
        response.sendRedirect("/doctorwho/login.jsp");
    }

    String action = null;
    Venta venta = null;
    String ventaId = request.getParameter("venta_id");
    
    Database db = new Database();
    db.connect();
    
    // Obtener listas de usuarios y artículos para los selectores
    UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
    ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
    List<Usuario> usuarioList = usuarioDao.getAll();
    List<Articulo> articuloList = articuloDao.getAll();
    
    if (ventaId != null) {
        action = "Modificar";
        VentaDao ventaDao = new VentaDao(db.getConnection());
        try {
            venta = ventaDao.get(Integer.parseInt(ventaId));
        } catch (VentaNotFoundException e) {
            response.sendRedirect("/doctorwho/jsp/ventas/todas-ventas.jsp");
            return;
        }
    } else {
        action = "Registrar";
    }
%>

<script type="text/javascript">
    $(document).ready(function() {
        $("form").on("submit", function(event) {
            event.preventDefault();
            const form = $("#venta-form")[0];
            const formValue = new FormData(form);
            $.ajax({
                url: "<%= action.equals("Registrar") ? "/doctorwho/create_venta" : "/doctorwho/edit_venta" %>",
                type: "POST",
                data: formValue,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 10000,
                statusCode: {
                    200: function(response) {
                        console.log(response);
                        if (response === "ok") {
                            window.location.href = "/doctorwho/jsp/ventas/todas-ventas.jsp";
                        } else {
                            $("#result").html("<div class='alert alert-danger' role='alert'>" + response + "</div>");
                        }
                    },
                    404: function(response) {
                        $("#result").html("<div class='alert alert-danger' role='alert'>Error al enviar los datos</div>");
                    },
                    500: function(response) {
                        console.log(response);
                        $("#result").html("<div class='alert alert-danger' role='alert'>" + response.toString() + "</div>");
                    }
                }
            });
        });
    });
</script>

<div class="container py-4">
    <form class="row g-3" id="venta-form" method="post">
        <h1 class="h3 mb-3 fw-normal"><%= action %> venta</h1>
        <div class="col-md-6">
            <label for="idComprador" class="form-label">Cliente</label>
            <select name="idComprador" id="idComprador" class="form-select" required>
                <option value="">Seleccione un cliente</option>
                <% for (Usuario usuario : usuarioList) { %>
                <option value="<%= usuario.getId() %>"
                    <%= (venta != null && venta.getIdComprador() == usuario.getId()) ? "selected" : "" %>>
                    <%= usuario.getNombre() %> - <%= usuario.getEmail() %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="col-md-6">
            <label for="idArticulo" class="form-label">Artículo</label>
            <select name="idArticulo" id="idArticulo" class="form-select" required>
                <option value="">Seleccione un artículo</option>
                <% for (Articulo articulo : articuloList) { %>
                <option value="<%= articulo.getId() %>"
                    <%= (venta != null && venta.getIdArticulo() == articulo.getId()) ? "selected" : "" %>>
                    <%= articulo.getNombre() %> - <%= articulo.getPrecio() %> €
                </option>
                <% } %>
            </select>
        </div>
        <div class="col-md-6">
            <label for="precio" class="form-label">Precio</label>
            <input type="text" name="precio" id="precio" class="form-control" placeholder="Precio" 
                   value="<%= venta != null ? venta.getPrecio() : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="fechaTransaccion" class="form-label">Fecha de Transacción</label>
            <input type="date" name="fechaTransaccion" id="fechaTransaccion" class="form-control" 
                   value="<%= venta != null ? venta.getFechaTransaccion() : new Date(System.currentTimeMillis()) %>">
        </div>
        <div class="col-md-6">
            <label for="estadoVenta" class="form-label">Estado</label>
            <select name="estadoVenta" id="estadoVenta" class="form-select" required>
                <option value="">Seleccione un estado</option>
                <option value="Pendiente" <%= venta != null && venta.getEstadoVenta().equals("Pendiente") ? "selected" : "" %>>Pendiente</option>
                <option value="Procesando" <%= venta != null && venta.getEstadoVenta().equals("Procesando") ? "selected" : "" %>>Procesando</option>
                <option value="Enviado" <%= venta != null && venta.getEstadoVenta().equals("Enviado") ? "selected" : "" %>>Enviado</option>
                <option value="Entregado" <%= venta != null && venta.getEstadoVenta().equals("Entregado") ? "selected" : "" %>>Entregado</option>
                <option value="Cancelado" <%= venta != null && venta.getEstadoVenta().equals("Cancelado") ? "selected" : "" %>>Cancelado</option>
            </select>
        </div>
        <div class="col-md-6">
            <div class="form-check mt-4">
                <input type="checkbox" name="pagado" id="pagado" class="form-check-input" 
                       <%= venta != null && venta.isPagado() ? "checked" : "" %>>
                <label for="pagado" class="form-check-label">Pagado</label>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-check mt-4">
                <input type="checkbox" name="activo" id="activo" class="form-check-input" 
                       <%= venta != null && venta.isActivo() ? "checked" : "" %>>
                <label for="activo" class="form-check-label">Activo</label>
            </div>
        </div>
        
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="/doctorwho/jsp/ventas/todas-ventas.jsp" class="btn btn-secondary">Cancelar</a>
        </div>

        <input type="hidden" name="action" value="<%= action %>">
        <% if (action.equals("Modificar")) { %>
        <input type="hidden" name="idTransaccion" value="<%= venta.getIdTransaccion() %>">
        <% } %>

        <div id="result"></div>
    </form>
</div>

<%@include file="../../includes/footer.jsp"%>