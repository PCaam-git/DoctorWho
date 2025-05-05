<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.UsuarioDao" %>
<%@ page import="com.patricia.model.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    // Verificar permisos de administrador
    if (!role.equals("admin")) {
        response.sendRedirect("/doctorwho/");
        return;
    }

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
    UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());
    int usuarioCount = usuarioDao.getCount(search);
    int pageCount = usuarioCount / 10;
    if (usuarioCount % 10 != 0) {
        pageCount += 1;
    }

    int nextPageNumber = pageNumber < pageCount - 1 ? pageNumber + 1 : pageCount - 1;
    int previousPageNumber = pageNumber > 0 ? (pageNumber - 1) : 0;
%>

<div class="container py-4">
    <h1>Listado de Usuarios</h1>
    
    <div class="mb-3">
        <form method="get" action="<%= request.getRequestURI() %>">
            <input type="text" name="search" id="search" class="form-control" placeholder="Buscar" value="<%= search != null ? search : "" %>">
        </form>
    </div>

    <div class="mb-3">
        <a href="/doctorwho/jsp/usuarios/usuario-form.jsp" class="btn btn-primary">Nuevo Usuario</a>
    </div>

    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Imagen</th>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Fecha Registro</th>
                    <th>Estado</th>
                    <th>Activo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Usuario> usuarioList = usuarioDao.getAll(pageNumber, search);
                    for (Usuario usuario : usuarioList) {
                %>
                <tr>
                    <td>
                        <% if (usuario.getImagen() != null && !usuario.getImagen().isEmpty() && !usuario.getImagen().equals("default.jpg")) { %>
                            <img src="${pageContext.request.contextPath}/images/usuarios/<%= usuario.getImagen() %>" alt="<%= usuario.getNombre() %>" style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%;">
                        <% } else { %>
                            <img src="${pageContext.request.contextPath}/images/default.jpg" alt="Imagen por defecto" style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%;">
                        <% } %>
                    </td>
                    <td><%= usuario.getId() %></td>
                    <td><a href="/doctorwho/jsp/usuarios/usuario-detail.jsp?usuario_id=<%= usuario.getId() %>"><%= usuario.getNombre() %></a></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.getRol() %></td>
                    <td><%= usuario.getFechaRegistro() %></td>
                    <td><%= usuario.getEstado() %></td>
                    <td><%= usuario.isActivo() ? "Sí" : "No" %></td>
                    <td>
                        <a href="/doctorwho/jsp/usuarios/usuario-detail.jsp?usuario_id=<%= usuario.getId() %>" class="btn btn-info btn-sm">Ver</a>
                        <a href="/doctorwho/jsp/usuarios/usuario-form.jsp?usuario_id=<%= usuario.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="/doctorwho/delete_usuario?usuario_id=<%= usuario.getId() %>"
                            onclick="return confirm('¿Estás seguro de que deseas eliminar este usuario?')" class="btn btn-danger btn-sm">Eliminar</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="row mb-3">
        <div class="col-md-8">
            <form action="/doctorwho/buscarUsuarios" method="get" class="d-flex">
                <input type="text" name="searchTerm" class="form-control me-2" placeholder="Buscar usuarios...">
                <button type="submit" class="btn btn-outline-primary">Buscar</button>
            </form>
        </div>
        <div class="col-md-4 text-end">
            <a href="/doctorwho/busquedaAvanzadaUsuario" class="btn btn-primary">Búsqueda Avanzada</a>
        </div>
    </div>

    <%@include file="../../includes/pagination.jsp"%>
</div>

<%@include file="../../includes/footer.jsp"%>