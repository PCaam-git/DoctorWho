<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.ArticuloDao" %>
<%@ page import="com.patricia.model.Articulo" %>
<%@ page import="com.patricia.exception.ArticuloNotFoundException" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    if ((currentSession.getAttribute("role") == null) || (!currentSession.getAttribute("role").equals("admin"))) {
        response.sendRedirect("/doctorwho/login.jsp");
    }

    String action = null;
    Articulo articulo = null;
    String articuloId = request.getParameter("articulo_id");
    if (articuloId != null) {
        action = "Modificar";
        Database db = new Database();
        db.connect();
        ArticuloDao articuloDao = new ArticuloDao(db.getConnection());
        try {
            articulo = articuloDao.get(Integer.parseInt(articuloId));
        } catch (ArticuloNotFoundException e) {
            response.sendRedirect("/doctorwho/jsp/articulo/articulos.jsp");
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
            const form = $("#articulo-form")[0];
            const formValue = new FormData(form);
            $.ajax({
                url: "<%= action.equals("Registrar") ? "/doctorwho/create_articulo" : "/doctorwho/edit_articulo" %>",
                type: "POST",
                enctype: "multipart/form-data",
                data: formValue,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 10000,
                statusCode: {
                    200: function(response) {
                        console.log(response);
                        if (response === "ok") {
                            window.location.href = "/doctorwho/jsp/articulo/articulos.jsp";
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
    <form class="row g-3" id="articulo-form" method="post" enctype="multipart/form-data">
        <h1 class="h3 mb-3 fw-normal"><%= action %> artículo</h1>
        <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Nombre" 
                   value="<%= articulo != null ? articulo.getNombre() : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="precio" class="form-label">Precio</label>
            <input type="text" name="precio" id="precio" class="form-control" placeholder="Precio" 
                   value="<%= articulo != null ? articulo.getPrecio() : "" %>" required>
        </div>
        <div class="col-md-12">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea name="descripcion" id="descripcion" class="form-control" rows="3"><%= articulo != null ? articulo.getDescripcion() : "" %></textarea>
        </div>
        <div class="col-md-6">
            <label for="imagen" class="form-label">Imagen</label>
            <input type="file" name="imagen" id="imagen" class="form-control">
        </div>
        <div class="col-md-6">
            <div class="form-check mt-4">
                <input type="checkbox" name="disponible" id="disponible" class="form-check-input" 
                       <%= articulo != null && articulo.isDisponible() ? "checked" : "" %>>
                <label for="disponible" class="form-check-label">Disponible</label>
            </div>
        </div>
        
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="/doctorwho/jsp/articulo/articulos.jsp" class="btn btn-secondary">Cancelar</a>
        </div>

        <input type="hidden" name="action" value="<%= action %>">
        <% if (action.equals("Modificar")) { %>
        <input type="hidden" name="id" value="<%= articulo.getId() %>">
        <% } %>

        <div id="result"></div>
    </form>
</div>

<%@include file="../../includes/footer.jsp"%>