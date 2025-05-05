<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.VentaDao" %>
<%@ page import="com.patricia.model.Venta" %>
<%@ page import="com.patricia.exception.VentaNotFoundException" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    // Verificar que el usuario esté logueado
    if (!logged) {
        response.sendRedirect("/doctorwho/login.jsp");
        return;
    }

    int ventaId = Integer.parseInt(request.getParameter("venta_id"));
    Database database = new Database();
    database.connect();
    VentaDao ventaDao = new VentaDao(database.getConnection());
    try {
        Venta venta = ventaDao.get(ventaId);
        
        // Si no es admin y no es su compra, redirigir
        if (!role.equals("admin") && !currentSession.getAttribute("username").equals(venta.getNombreComprador())) {
            response.sendRedirect("/doctorwho/jsp/ventas/ventas.jsp");
            return;
        }
%>
<div class="container py-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2>Detalles de la Compra #<%= venta.getIdTransaccion() %></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <p><strong>ID Transacción:</strong> <%= venta.getIdTransaccion() %></p>
                    <p><strong>Comprador:</strong> <%= venta.getNombreComprador() %></p>
                    <p><strong>Artículo:</strong> <%= venta.getNombreArticulo() %></p>
                    <p><strong>Precio:</strong> <%= venta.getPrecio() %> €</p>
                </div>
                <div class="col-md-6">
                    <p><strong>Fecha:</strong> <%= venta.getFechaTransaccion() %></p>
                    <p><strong>Estado:</strong> <%= venta.getEstadoVenta() %></p>
                    <p><strong>Pagado:</strong> <%= venta.isPagado() ? "Sí" : "No" %></p>
                    <p><strong>Activo:</strong> <%= venta.isActivo() ? "Sí" : "No" %></p>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <% if (role.equals("admin")) { %>
            <a href="/doctorwho/jsp/ventas/venta-form.jsp?venta_id=<%= venta.getIdTransaccion() %>" class="btn btn-warning">Editar</a>
            <a href="/doctorwho/delete_venta?venta_id=<%= venta.getIdTransaccion() %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
            <a href="/doctorwho/jsp/ventas/todas-ventas.jsp" class="btn btn-secondary">Volver</a>
            <% } else { %>
            <a href="/doctorwho/jsp/ventas/ventas.jsp" class="btn btn-secondary">Volver</a>
            <% } %>
        </div>
    </div>
</div>
<%
    } catch (VentaNotFoundException vnfe) {
%>
<div class="container py-4">
    <div class="alert alert-danger" role="alert">
        No se ha encontrado la venta solicitada.
    </div>
    <% if (role.equals("admin")) { %>
    <a href="/doctorwho/jsp/ventas/todas-ventas.jsp" class="btn btn-primary">Volver a la lista</a>
    <% } else { %>
    <a href="/doctorwho/jsp/ventas/ventas.jsp" class="btn btn-primary">Volver a la lista</a>
    <% } %>
</div>
<%
    }
%>

<%@include file="../../includes/footer.jsp"%>