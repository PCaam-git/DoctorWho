<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.CategoriaDao" %>
<%@ page import="com.patricia.model.Categoria" %>
<%@ page import="com.patricia.exception.CategoriaNotFoundException" %>
<%@ page import="java.sql.Date" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    if ((currentSession.getAttribute("role") == null) || (!currentSession.getAttribute("role").equals("admin"))) {
        response.sendRedirect("/doctorwho/login.jsp");
    }

    String action = null;
    Categoria categoria = null;
    String categoriaId = request.getParameter("categoria_id");
    if (categoriaId != null) {
        action = "Modificar";
        Database db = new Database();
        db.connect();
        CategoriaDao categoriaDao = new CategoriaDao(db.getConnection());
        try {
            categoria = categoriaDao.get(Integer.parseInt(categoriaId));
        } catch (CategoriaNotFoundException e) {
            response.sendRedirect("/doctorwho/jsp/categorias/categorias.jsp");
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
            const form = $("#categoria-form")[0];
            const formValue = new FormData(form);
            $.ajax({
                url: "<%= action.equals("Registrar") ? "/doctorwho/create_categoria" : "/doctorwho/edit_categoria" %>",
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
                            window.location.href = "/doctorwho/jsp/categorias/categorias.jsp";
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
    <form class="row g-3" id="categoria-form" method="post">
        <h1 class="h3 mb-3 fw-normal"><%= action %> categoría</h1>
        <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Nombre" 
                   value="<%= categoria != null ? categoria.getNombre() : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="cantidad" class="form-label">Cantidad</label>
            <input type="number" name="cantidad" id="cantidad" class="form-control" placeholder="Cantidad" 
                   value="<%= categoria != null ? categoria.getCantidad() : "0" %>" required>
        </div>
        <div class="col-md-12">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea name="descripcion" id="descripcion" class="form-control" rows="3"><%= categoria != null ? categoria.getDescripcion() : "" %></textarea>
        </div>
        <div class="col-md-6">
            <label for="fechaActualizacion" class="form-label">Fecha de Actualización</label>
            <input type="date" name="fechaActualizacion" id="fechaActualizacion" class="form-control" 
                   value="<%= categoria != null ? categoria.getFechaActualizacion() : new Date(System.currentTimeMillis()) %>">
        </div>
        <div class="col-md-6">
            <label for="precioMedio" class="form-label">Precio Medio</label>
            <input type="text" name="precioMedio" id="precioMedio" class="form-control" placeholder="Precio Medio" 
                   value="<%= categoria != null ? categoria.getPrecioMedio() : "0.0" %>" required>
        </div>
        <div class="col-md-6">
            <div class="form-check mt-4">
                <input type="checkbox" name="tieneProductos" id="tieneProductos" class="form-check-input" 
                       <%= categoria != null && categoria.isTieneProductos() ? "checked" : "" %>>
                <label for="tieneProductos" class="form-check-label">Tiene Productos</label>
            </div>
        </div>
        
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="/doctorwho/jsp/categorias/categorias.jsp" class="btn btn-secondary">Cancelar</a>
        </div>

        <input type="hidden" name="action" value="<%= action %>">
        <% if (action.equals("Modificar")) { %>
        <input type="hidden" name="id" value="<%= categoria.getId() %>">
        <% } %>

        <div id="result"></div>
    </form>
</div>

<%@include file="../../includes/footer.jsp"%>