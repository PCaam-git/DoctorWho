<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Artículo - Doctor Who</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f8f9fa;
            color: #333;
        }
        .detail-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .info-item {
            margin-bottom: 1rem;
            padding: 0.5rem 0;
            border-bottom: 1px solid rgba(255,255,255,0.2);
        }
        .info-item:last-child {
            border-bottom: none;
        }
        .product-image {
            max-width: 100%;
            height: 300px;
            object-fit: cover;
            border-radius: 10px;
        }
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Detalle de Artículo"/>
    </jsp:include>

<div class="container py-5">
    <!-- Botones de navegación -->
    <div class="d-flex justify-content-between mb-4">
        <a href="${pageContext.request.contextPath}/articulos/lista" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left"></i> Volver a la Lista
        </a>
        
        <!-- Solo administradores pueden editar -->
        <c:if test="${esAdmin}">
            <a href="${pageContext.request.contextPath}/articulos/editar?id=${articulo.id}" class="btn btn-warning">
                <i class="fas fa-edit"></i> Editar Artículo
            </a>
        </c:if>
    </div>

    <div class="row">
        <!-- Imagen del artículo -->
        <div class="col-md-6 mb-4">
            <div class="text-center">
                <c:choose>
                    <c:when test="${not empty articulo.imagen and articulo.imagen != 'default.jpg'}">
                        <img src="${pageContext.request.contextPath}/images/articulos/${articulo.imagen}" 
                             class="product-image shadow" 
                             alt="${articulo.nombre}"
                             onerror="this.src='${pageContext.request.contextPath}/images/articulos/default-product.jpg'">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/articulos/default.jpg" 
                             class="product-image shadow" 
                             alt="Imagen por defecto">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Información del artículo -->
        <div class="col-md-6">
            <div class="card detail-card">
                <div class="card-body p-4">
                    <div class="text-center mb-4">
                        <h2 class="card-title display-5">
                            <i class="fas fa-box me-3"></i>${articulo.nombre}
                        </h2>
                    </div>
                    
                    <div class="info-item">
                        <h5><i class="fas fa-info-circle me-2"></i>Descripción:</h5>
                        <p class="ms-4">${articulo.descripcion}</p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-euro-sign me-2"></i>Precio:</h5>
                        <p class="ms-4">
                            <span class="h3">
                                <fmt:formatNumber value="${articulo.precio}" type="currency" currencySymbol="€"/>
                            </span>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-folder me-2"></i>Categoría:</h5>
                        <p class="ms-4">
                            <span class="badge bg-info fs-6">${articulo.categoriaNombre}</span>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-check-circle me-2"></i>Disponibilidad:</h5>
                        <p class="ms-4">
                            <span class="badge ${articulo.disponible ? 'bg-success' : 'bg-secondary'} fs-6">
                                <c:choose>
                                    <c:when test="${articulo.disponible}">
                                        <i class="fas fa-check me-1"></i>Disponible
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-times me-1"></i>No disponible
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-calendar-alt me-2"></i>Fecha de Registro:</h5>
                        <p class="ms-4">
                            <fmt:formatDate value="${articulo.fechaAnadido}" pattern="dd/MM/yyyy"/>
                        </p>
                    </div>

                    <!-- Botones de acción para administradores -->
                    <c:if test="${esAdmin}">
                        <div class="text-center mt-4">
                            <div class="btn-group" role="group">
                                <a href="${pageContext.request.contextPath}/articulos/editar?id=${articulo.id}" 
                                   class="btn btn-warning btn-lg">
                                    <i class="fas fa-edit me-2"></i>Editar
                                </a>
                                <button type="button" class="btn btn-danger btn-lg" 
                                        onclick="confirmarEliminacion()">
                                    <i class="fas fa-trash me-2"></i>Eliminar
                                </button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- Enlaces relacionados -->
    <div class="text-center mt-5">
        <a href="${pageContext.request.contextPath}/articulos/lista?categoria=${articulo.categoriaId}" 
           class="btn btn-outline-primary me-2">
            <i class="fas fa-list me-2"></i>Ver Artículos de esta Categoría
        </a>
        <a href="${pageContext.request.contextPath}/articulos/lista" 
           class="btn btn-outline-secondary">
            <i class="fas fa-box me-2"></i>Ver Todos los Artículos
        </a>
    </div>
</div>

<!-- JavaScript para confirmación de eliminación -->
<c:if test="${esAdmin}">
<script>
function confirmarEliminacion() {
    if (confirm('¿Estás seguro de que quieres eliminar el artículo "${articulo.nombre}"?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = '${pageContext.request.contextPath}/articulos/eliminar?id=${articulo.id}';
    }
}
</script>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>