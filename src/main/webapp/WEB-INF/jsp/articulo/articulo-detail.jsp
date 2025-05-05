<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.ArticuloDao" %>
<%@ page import="com.patricia.model.Articulo" %>
<%@ page import="com.patricia.exception.ArticuloNotFoundException" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    int articuloId = Integer.parseInt(request.getParameter("articulo_id"));
    Database database = new Database();
    database.connect();
    ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
    try {
        Articulo articulo = articuloDao.get(articuloId);
%>
<div class="container py-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2><%= articulo.getNombre() %></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-4">
                    <img class="img-fluid" src="/doctorwho_images/<%= articulo.getImagen() != null ? articulo.getImagen() : "default.jpg" %>" alt="<%= articulo.getNombre() %>">
                </div>
                <div class="col-md-8">
                    <p><strong>ID:</strong> <%= articulo.getId() %></p>
                    <p><strong>Descripción:</strong> <%= articulo.getDescripcion() %></p>
                    <p><strong>Precio:</strong> <%= articulo.getPrecio() %> €</p>
                    <p><strong>Disponible:</strong> <%= articulo.isDisponible() ? "Sí" : "No" %></p>
                    <p><strong>Fecha añadido:</strong> <%= articulo.getFechaAñadido() %></p>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <% if (role.equals("admin")) { %>
            <a href="/doctorwho/jsp/articulo/articulo-form.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-warning">Editar</a>
            <a href="/doctorwho/delete_articulo?articulo_id=<%= articulo.getId() %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
            <% } else if (role.equals("user")) { %>
            <a href="/doctorwho/add_to_cart?articulo_id=<%= articulo.getId() %>" class="btn btn-primary">Comprar</a>
            <% } %>
            <a href="/doctorwho/jsp/articulo/articulos.jsp" class="btn btn-secondary">Volver</a>
        </div>
    </div>
</div>
<%
    } catch (ArticuloNotFoundException anfe) {
%>
<div class="container py-4">
    <div class="alert alert-danger" role="alert">
        No se ha encontrado el artículo solicitado.
    </div>
    <a href="/doctorwho/jsp/articulo/articulos.jsp" class="btn btn-primary">Volver a la lista</a>
</div>
<%
    }
%>

<%@include file="../../includes/footer.jsp"%>