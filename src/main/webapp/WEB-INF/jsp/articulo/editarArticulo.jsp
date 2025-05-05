<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${accion} Artículo - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>${accion} Artículo</h2>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/edit_articulo" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${articulo.nombre}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción:</label>
                        <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required>${articulo.descripcion}</textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label for="precio" class="form-label">Precio (€):</label>
                        <input type="number" step="0.01" class="form-control" id="precio" name="precio" value="${articulo.precio}" required>
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="disponible" name="disponible" value="true" ${articulo.disponible ? 'checked' : ''}>
                        <label class="form-check-label" for="disponible">Disponible</label>
                    </div>
                    
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Imagen:</label>
                        <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                        <small class="text-muted">Formatos aceptados: JPG, PNG, GIF</small>
                    </div>
                    
                    <c:if test="${articulo.imagen != null && !articulo.imagen.isEmpty() && articulo.imagen != 'default.jpg'}">
                        <div class="mb-3">
                            <label class="form-label">Imagen actual:</label>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/articulos/${articulo.imagen}" alt="${articulo.nombre}" class="img-thumbnail" style="max-width: 200px;">
                            </div>
                        </div>
                    </c:if>
                    
                    <input type="hidden" name="accion" value="${accion}">
                    <c:if test="${accion == 'Modificar'}">
                        <input type="hidden" name="articuloId" value="${articulo.id}">
                    </c:if>
                    
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                        <a href="${pageContext.request.contextPath}/articulos" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Confirmación antes de enviar el formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            if (!confirm('¿Está seguro de guardar los cambios?')) {
                e.preventDefault();
            }
        });
    </script>
</body>
</html>