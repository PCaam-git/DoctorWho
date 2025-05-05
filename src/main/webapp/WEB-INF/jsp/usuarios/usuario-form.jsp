<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.UsuarioDao" %>
<%@ page import="com.patricia.model.Usuario" %>
<%@ page import="com.patricia.exception.UsuarioNotFoundException" %>
<%@ page import="java.sql.Date" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    if ((currentSession.getAttribute("role") == null) || (!currentSession.getAttribute("role").equals("admin"))) {
        response.sendRedirect("/doctorwho/login.jsp");
    }

    String action = null;
    Usuario usuario = null;
    String usuarioId = request.getParameter("usuario_id");
    if (usuarioId != null) {
        action = "Modificar";
        Database db = new Database();
        db.connect();
        UsuarioDao usuarioDao = new UsuarioDao(db.getConnection());
        try {
            usuario = usuarioDao.get(Integer.parseInt(usuarioId));
        } catch (UsuarioNotFoundException e) {
            response.sendRedirect("/doctorwho/jsp/usuarios/usuarios.jsp");
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
            const form = $("#usuario-form")[0];
            const formValue = new FormData(form);
            $.ajax({
                url: "<%= action.equals("Registrar") ? "/doctorwho/create_usuario" : "/doctorwho/edit_usuario" %>",
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
                            window.location.href = "/doctorwho/jsp/usuarios/usuarios.jsp";
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
    <form class="row g-3" id="usuario-form" method="post">
        <h1 class="h3 mb-3 fw-normal"><%= action %> usuario</h1>
        <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Nombre" 
                   value="<%= usuario != null ? usuario.getNombre() : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="email" class="form-label">Email</label>
            <input type="email" name="email" id="email" class="form-control" placeholder="Email" 
                   value="<%= usuario != null ? usuario.getEmail() : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="contraseña" class="form-label">Contraseña</label>
            <input type="password" name="contraseña" id="contraseña" class="form-control" placeholder="Contraseña" 
                   <%= usuario != null ? "" : "required" %>>
            <% if (usuario != null) { %>
            <small class="text-muted">Dejar en blanco para mantener la contraseña actual</small>
            <% } %>
        </div>
        <div class="col-md-6">
            <label for="rol" class="form-label">Rol</label>
            <select name="rol" id="rol" class="form-select" required>
                <option value="">Seleccione un rol</option>
                <option value="admin" <%= usuario != null && usuario.getRol().equals("admin") ? "selected" : "" %>>Administrador</option>
                <option value="user" <%= usuario != null && usuario.getRol().equals("user") ? "selected" : "" %>>Usuario</option>
            </select>
        </div>
        <div class="col-md-6">
            <label for="fechaRegistro" class="form-label">Fecha de Registro</label>
            <input type="date" name="fechaRegistro" id="fechaRegistro" class="form-control" 
                   value="<%= usuario != null ? usuario.getFechaRegistro() : new Date(System.currentTimeMillis()) %>">
        </div>
        <div class="col-md-6">
            <label for="estado" class="form-label">Estado</label>
            <input type="text" name="estado" id="estado" class="form-control" placeholder="Estado" 
                   value="<%= usuario != null ? usuario.getEstado() : "Activo" %>">
        </div>
        <div class="col-md-6">
            <div class="form-check mt-4">
                <input type="checkbox" name="activo" id="activo" class="form-check-input" 
                       <%= usuario != null && usuario.isActivo() ? "checked" : "" %>>
                <label for="activo" class="form-check-label">Activo</label>
            </div>
        </div>
        
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="/doctorwho/jsp/usuarios/usuarios.jsp" class="btn btn-secondary">Cancelar</a>
        </div>

        <input type="hidden" name="action" value="<%= action %>">
        <% if (action.equals("Modificar")) { %>
        <input type="hidden" name="id" value="<%= usuario.getId() %>">
        <% } %>

        <div id="result"></div>
    </form>
</div>

<%@include file="../../includes/footer.jsp"%>