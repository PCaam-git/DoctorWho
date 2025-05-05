<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.UsuarioDao" %>
<%@ page import="com.patricia.model.Usuario" %>
<%@ page import="com.patricia.exception.UsuarioNotFoundException" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    // Verificar permisos de administrador
    if (!role.equals("admin")) {
        response.sendRedirect("/doctorwho/");
        return;
    }

    int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
    Database database = new Database();
    database.connect();
    UsuarioDao usuarioDao = new UsuarioDao(database.getConnection());
    try {
        Usuario usuario = usuarioDao.get(usuarioId);
%>
<div class="container py-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2>Usuario: <%= usuario.getNombre() %></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-12">
                    <p><strong>ID:</strong> <%= usuario.getId() %></p>
                    <p><strong>Nombre:</strong> <%= usuario.getNombre() %></p>
                    <p><strong>Email:</strong> <%= usuario.getEmail() %></p>
                    <p><strong>Rol:</strong> <%= usuario.getRol() %></p>
                    <p><strong>Fecha de Registro:</strong> <%= usuario.getFechaRegistro() %></p>
                    <p><strong>Estado:</strong> <%= usuario.getEstado() %></p>
                    <p><strong>Activo:</strong> <%= usuario.isActivo() ? "Sí" : "No" %></p>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <a href="/doctorwho/jsp/usuarios/usuario-form.jsp?usuario_id=<%= usuario.getId() %>" class="btn btn-warning">Editar</a>
            <a href="/doctorwho/delete_usuario?usuario_id=<%= usuario.getId() %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
            <a href="/doctorwho/jsp/usuarios/usuarios.jsp" class="btn btn-secondary">Volver</a>
        </div>
    </div>
</div>
<%
    } catch (UsuarioNotFoundException unfe) {
%>
<div class="container py-4">
    <div class="alert alert-danger" role="alert">
        No se ha encontrado el usuario solicitado.
    </div>
    <a href="/doctorwho/jsp/usuarios/usuarios.jsp" class="btn btn-primary">Volver a la lista</a>
</div>
<%
    }
%>

<%@include file="../../includes/footer.jsp"%>