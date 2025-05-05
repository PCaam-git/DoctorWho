<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${accion} Usuario - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>${accion} Usuario</h2>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/edit_usuario" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="email" class="form-label">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" value="${usuario.email}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="contraseña" class="form-label">Contraseña:</label>
                        <input type="password" class="form-control" id="contraseña" name="contraseña" value="${usuario.contraseña}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="rol" class="form-label">Rol:</label>
                        <select class="form-select" id="rol" name="rol" required>
                            <option value="admin" ${usuario.rol == 'admin' ? 'selected' : ''}>Administrador</option>
                            <option value="user" ${usuario.rol == 'user' ? 'selected' : ''}>Usuario</option>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="estado" class="form-label">Estado:</label>
                        <input type="text" class="form-control" id="estado" name="estado" value="${usuario.estado}">
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="activo" name="activo" value="true" ${usuario.activo ? 'checked' : ''}>
                        <label class="form-check-label" for="activo">Activo</label>
                    </div>
                    
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Imagen:</label>
                        <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                        <small class="text-muted">Formatos aceptados: JPG, PNG, GIF</small>
                    </div>
                    
                    <c:if test="${usuario.imagen != null && !usuario.imagen.isEmpty() && usuario.imagen != 'default.jpg'}">
                        <div class="mb-3">
                            <label class="form-label">Imagen actual:</label>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/usuarios/${usuario.imagen}" alt="${usuario.nombre}" class="img-thumbnail" style="max-width: 200px;">
                            </div>
                        </div>
                    </c:if>
                    
                    <input type="hidden" name="accion" value="${accion}">
                    <c:if test="${accion == 'Modificar'}">
                        <input type="hidden" name="usuarioId" value="${usuario.id}">
                    </c:if>
                    
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                        <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary">Cancelar</a>
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