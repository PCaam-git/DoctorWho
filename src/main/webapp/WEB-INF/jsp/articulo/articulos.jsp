<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.ArticuloDao" %>
<%@ page import="com.patricia.model.Articulo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    String search = request.getParameter("search");
    if (search == null)
        search = "";

    int pageNumber = 0;
    String pageNumberStr = request.getParameter("page");
    if (pageNumberStr != null) {
        pageNumber = Integer.parseInt(pageNumberStr);
    }

    Database database = new Database();
    database.connect();
    ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
    int articuloCount = articuloDao.getCount(search);
    int pageCount = articuloCount / 10;
    if (articuloCount % 10 != 0) {
        pageCount += 1;
    }

    int nextPageNumber = pageNumber < pageCount - 1 ? pageNumber + 1 : pageCount - 1;
    int previousPageNumber = pageNumber > 0 ? (pageNumber - 1) : 0;
%>

<div class="container py-4">
    <h1>Listado de Artículos</h1>
    
    <div class="mb-3">
        <form method="get" action="<%= request.getRequestURI() %>">
            <input type="text" name="search" id="search" class="form-control" placeholder="Buscar" value="<%= search != null ? search : "" %>">
        </form>
    </div>

    <% if (role.equals("admin")) { %>
    <div class="mb-3">
        <a href="/doctorwho/jsp/articulo/articulo-form.jsp" class="btn btn-primary">Nuevo Artículo</a>
    </div>
    <% } %>

    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Imagen</th>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Precio (€)</th>
                    <th>Disponible</th>
                    <th>Fecha añadido</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Articulo> articuloList = articuloDao.getAll(pageNumber, search);
                    for (Articulo articulo : articuloList) {
                %>
                <tr>
                    <td>
                        <% if (articulo.getImagen() != null && !articulo.getImagen().isEmpty() && !articulo.getImagen().equals("default.jpg")) { %>
                            <img src="${pageContext.request.contextPath}/images/articulos/<%= articulo.getImagen() %>" alt="<%= articulo.getNombre() %>" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } else { %>
                            <img src="${pageContext.request.contextPath}/images/default.jpg" alt="Imagen por defecto" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } %>
                    </td>
                    <td><%= articulo.getId() %></td>
                    <td><a href="/doctorwho/jsp/articulo/articulo-detail.jsp?articulo_id=<%= articulo.getId() %>"><%= articulo.getNombre() %></a></td>
                    <td><%= articulo.getDescripcion() %></td>
                    <td><%= articulo.getPrecio() %> €</td>
                    <td><%= articulo.isDisponible() ? "Sí" : "No" %></td>
                    <td><%= articulo.getFechaAñadido() %></td>
                    <td>
                        <a href="/doctorwho/jsp/articulo/articulo-detail.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-info btn-sm">Ver</a>
                        <% if (role.equals("admin")) { %>
                        <a href="/doctorwho/jsp/articulo/articulo-form.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="/doctorwho/delete_articulo?articulo_id=<%= articulo.getId() %>"
                            onclick="return confirm('¿Estás seguro de que deseas eliminar este artículo?')" class="btn btn-danger btn-sm">Eliminar</a>
                        <% } else if (role.equals("user")) { %>
                        <a href="/doctorwho/add_to_cart?articulo_id=<%= articulo.getId() %>" class="btn btn-success btn-sm">Comprar</a>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <div class="row mb-3">
        <div class="col-md-8">
            <form action="/doctorwho/buscarArticulos" method="get" class="d-flex">
                <input type="text" name="searchTerm" class="form-control me-2" placeholder="Buscar artículos...">
                <button type="submit" class="btn btn-outline-primary">Buscar</button>
            </form>
        </div>
        <div class="col-md-4 text-end">
            <a href="/doctorwho/busquedaAvanzadaArticulo" class="btn btn-primary">Búsqueda Avanzada</a>
        </div>
    </div>

    <%@include file="../../includes/pagination.jsp"%>
</div>

<%@include file="../../includes/footer.jsp"%>