<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.CategoriaDao" %>
<%@ page import="com.patricia.model.Categoria" %>
<%@ page import="com.patricia.exception.CategoriaNotFoundException" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    int categoriaId = Integer.parseInt(request.getParameter("categoria_id"));
    Database database = new Database();
    database.connect();
    CategoriaDao categoriaDao = new CategoriaDao(database.getConnection());
    try {
        Categoria categoria = categoriaDao.get(categoriaId);
%>
<div class="container py-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2><%= categoria.getNombre() %></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-12">
                    <p><strong>ID:</strong> <%= categoria.getId() %></p>
                    <p><strong>Descripción:</strong> <%= categoria.getDescripcion() %></p>
                    <p><strong>Cantidad:</strong> <%= categoria.getCantidad() %></p>
                    <p><strong>Tiene Productos:</strong> <%= categoria.isTieneProductos() ? "Sí" : "No" %></p>
                    <p><strong>Fecha de Actualización:</strong> <%= categoria.getFechaActualizacion() %></p>
                    <p><strong>Precio Medio:</strong> <%= categoria.getPrecioMedio() %> €</p>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <% if (role.equals("admin")) { %>
            <a href="/doctorwho/jsp/categorias/categoria-form.jsp?categoria_id=<%= categoria.getId() %>" class="btn btn-warning">Editar</a>
            <a href="/doctorwho/delete_categoria?categoria_id=<%= categoria.getId() %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
            <% } %>
            <a href="/doctorwho/jsp/categorias/categorias.jsp" class="btn btn-secondary">Volver</a>
        </div>
    </div>
</div>
<%
    } catch (CategoriaNotFoundException cnfe) {
%>
<div class="container py-4">
    <div class="alert alert-danger" role="alert">
        No se ha encontrado la categoría solicitada.
    </div>
    <a href="/doctorwho/jsp/categorias/categorias.jsp" class="btn btn-primary">Volver a la lista</a>
</div>
<%
    }
%>

<%@include file="../../includes/footer.jsp"%>