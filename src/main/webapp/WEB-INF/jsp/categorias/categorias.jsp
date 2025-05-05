<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.CategoriaDao" %>
<%@ page import="com.patricia.model.Categoria" %>
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
    CategoriaDao categoriaDao = new CategoriaDao(database.getConnection());
    int categoriaCount = categoriaDao.getCount(search);
    int pageCount = categoriaCount / 10;
    if (categoriaCount % 10 != 0) {
        pageCount += 1;
    }

    int nextPageNumber = pageNumber < pageCount - 1 ? pageNumber + 1 : pageCount - 1;
    int previousPageNumber = pageNumber > 0 ? (pageNumber - 1) : 0;
%>

<div class="container py-4">
    <h1>Listado de Categorías</h1>
    
    <div class="mb-3">
        <form method="get" action="<%= request.getRequestURI() %>">
            <input type="text" name="search" id="search" class="form-control" placeholder="Buscar" value="<%= search != null ? search : "" %>">
        </form>
    </div>

    <% if (role.equals("admin")) { %>
    <div class="mb-3">
        <a href="/doctorwho/jsp/categorias/categoria-form.jsp" class="btn btn-primary">Nueva Categoría</a>
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
                    <th>Cantidad</th>
                    <th>Tiene Productos</th>
                    <th>Fecha Actualización</th>
                    <th>Precio Medio</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Categoria> categoriaList = categoriaDao.getAll(pageNumber, search);
                    for (Categoria categoria : categoriaList) {
                %>
                <tr>
                    <td>
                        <% if (categoria.getImagen() != null && !categoria.getImagen().isEmpty() && !categoria.getImagen().equals("default.jpg")) { %>
                            <img src="${pageContext.request.contextPath}/images/categorias/<%= categoria.getImagen() %>" alt="<%= categoria.getNombre() %>" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } else { %>
                            <img src="${pageContext.request.contextPath}/images/default.jpg" alt="Imagen por defecto" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } %>
                    </td>
                    <td><%= categoria.getId() %></td>
                    <td><a href="/doctorwho/jsp/categorias/categoria-detail.jsp?categoria_id=<%= categoria.getId() %>"><%= categoria.getNombre() %></a></td>
                    <td><%= categoria.getDescripcion() %></td>
                    <td><%= categoria.getCantidad() %></td>
                    <td><%= categoria.isTieneProductos() ? "Sí" : "No" %></td>
                    <td><%= categoria.getFechaActualizacion() %></td>
                    <td><%= categoria.getPrecioMedio() %> €</td>
                    <td>
                        <a href="/doctorwho/jsp/categorias/categoria-detail.jsp?categoria_id=<%= categoria.getId() %>" class="btn btn-info btn-sm">Ver</a>
                        <% if (role.equals("admin")) { %>
                        <a href="/doctorwho/jsp/categorias/categoria-form.jsp?categoria_id=<%= categoria.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="/doctorwho/delete_categoria?categoria_id=<%= categoria.getId() %>"
                            onclick="return confirm('¿Estás seguro de que deseas eliminar esta categoría?')" class="btn btn-danger btn-sm">Eliminar</a>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="row mb-3">
        <div class="col-md-8">
            <form action="/doctorwho/buscarCategorias" method="get" class="d-flex">
                <input type="text" name="searchTerm" class="form-control me-2" placeholder="Buscar categorías...">
                <button type="submit" class="btn btn-outline-primary">Buscar</button>
            </form>
        </div>
        <div class="col-md-4 text-end">
            <a href="/doctorwho/busquedaAvanzadaCategoria" class="btn btn-primary">Búsqueda Avanzada</a>
        </div>
    </div>

    <%@include file="../../includes/pagination.jsp"%>
</div>

<%@include file="../../includes/footer.jsp"%>