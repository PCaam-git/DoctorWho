<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${accion} Categoría - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>${accion} Categoría</h2>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/edit_categoria" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${categoria.nombre}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción:</label>
                        <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required>${categoria.descripcion}</textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label for="cantidad" class="form-label">Cantidad:</label>
                        <input type="number" class="form-control" id="cantidad" name="cantidad" value="${categoria.cantidad}" min="0" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="precioMedio" class="form-label">Precio Medio (€):</label>
                        <input type="number" step="0.01" class="form-control" id="precioMedio" name="precioMedio" value="${categoria.precioMedio}" required>
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="tieneProductos" name="tieneProductos" value="true" ${categoria.tieneProductos ? 'checked' : ''}>
                        <label class="form-check-label" for="tieneProductos">Tiene productos</label>
                    </div>
                    
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Imagen:</label>
                        <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                        <small class="text-muted">Formatos aceptados: JPG, PNG, GIF</small>
                    </div>
                    
                    <c:if test="${categoria.imagen != null && !categoria.imagen.isEmpty() && categoria.imagen != 'default.jpg'}">
                        <div class="mb-3">
                            <label class="form-label">Imagen actual:</label>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/categorias/${categoria.imagen}" alt="${categoria.nombre}" class="img-thumbnail" style="max-width: 200px;">
                            </div>
                        </div>
                    </c:if>
                    
                    <input type="hidden" name="accion" value="${accion}">
                    <c:if test="${accion == 'Modificar'}">
                        <input type="hidden" name="categoriaId" value="${categoria.id}">
                    </c:if>
                    
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                        <a href="${pageContext.request.contextPath}/categorias" class="btn btn-secondary">Cancelar</a>
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