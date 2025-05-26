<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="javax.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Nuevo Artículo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body class="container mt-5">

            <h2 class="mb-4">Añadir nuevo artículo</h2>

            <form method="post" action="${pageContext.request.contextPath}/articulos/add-articulo">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>

                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" required></textarea>
                </div>

                <div class="mb-3">
                    <label for="disponible" class="form-label">¿Disponible?</label>
                    <select class="form-select" id="disponible" name="disponible">
                        <option value="true" selected>Sí</option>
                        <option value="false">No</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="precio" class="form-label">Precio (€)</label>
                    <input type="number" class="form-control" id="precio" name="precio" step="0.01" required>
                </div>

                <div class="mb-3">
                    <label for="fecha_anadido" class="form-label">Fecha añadido</label>
                    <input type="date" class="form-control" id="fecha_anadido" name="fecha_anadido" required>
                </div>

                <div class="mb-3">
                    <label for="categoria_id" class="form-label">ID de Categoría</label>
                    <input type="number" class="form-control" id="categoria_id" name="categoria_id" required>
                </div>

                <div class="mb-3">
                    <label for="imagen" class="form-label">Imagen (URL o nombre de archivo)</label>
                    <input type="text" class="form-control" id="imagen" name="imagen" value="default.jpg">
                </div>

                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="${pageContext.request.contextPath}/articulos/list.jsp" class="btn btn-secondary">Cancelar</a>
            </form>

            <% if (request.getParameter("error") !=null) { %>
                <div class="alert alert-danger mt-3">❌ Error al guardar el artículo. Intenta de nuevo.</div>
                <% } %>

        </body>

        </html>