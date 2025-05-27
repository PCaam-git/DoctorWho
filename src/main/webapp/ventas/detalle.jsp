<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Venta - Doctor Who</title>
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
        .venta-icon {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto;
        }
        .related-card {
            background: white;
            border-radius: 10px;
            padding: 1rem;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Detalle de Venta"/>
    </jsp:include>

<div class="container py-5">
    <!-- Botones de navegación -->
    <div class="d-flex justify-content-between mb-4">
        <a href="${pageContext.request.contextPath}/ventas/lista" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left"></i> Volver a la Lista
        </a>
        
        <!-- Solo administradores pueden editar -->
        <c:if test="${esAdmin}">
            <a href="${pageContext.request.contextPath}/ventas/editar?id=${venta.id}" class="btn btn-warning">
                <i class="fas fa-edit"></i> Editar Venta
            </a>
        </c:if>
    </div>

    <div class="row">
        <!-- Icono de venta -->
        <div class="col-md-4 mb-4">
            <div class="text-center">
                <div class="venta-icon shadow">
                    <i class="fas fa-shopping-cart fa-5x text-white"></i>
                </div>
                <h4 class="mt-3">Venta #${venta.id}</h4>
                <p class="text-muted">
                    <fmt:formatDate value="${venta.fechaVenta}" pattern="dd/MM/yyyy"/>
                </p>
            </div>
        </div>

        <!-- Información de la venta -->
        <div class="col-md-8">
            <div class="card detail-card">
                <div class="card-body p-4">
                    <div class="text-center mb-4">
                        <h2 class="card-title display-5">
                            <i class="fas fa-receipt me-3"></i>Venta #${venta.id}
                        </h2>
                    </div>
                    
                    <div class="info-item">
                        <h5><i class="fas fa-user me-2"></i>Usuario:</h5>
                        <p class="ms-4">
                            <c:choose>
                                <c:when test="${not empty usuario}">
                                    ${usuario.nombre} (${usuario.email})
                                    <br><small>ID: ${venta.usuarioId}</small>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-warning">
                                        <i class="fas fa-exclamation-triangle me-1"></i>
                                        Usuario eliminado (ID: ${venta.usuarioId})
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-box me-2"></i>Artículo:</h5>
                        <p class="ms-4">
                            <c:choose>
                                <c:when test="${not empty articulo}">
                                    ${articulo.nombre}
                                    <br><small>ID: ${venta.articuloId}</small>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-warning">
                                        <i class="fas fa-exclamation-triangle me-1"></i>
                                        Artículo no encontrado (ID: ${venta.articuloId})
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-hashtag me-2"></i>Cantidad:</h5>
                        <p class="ms-4">
                            <span class="h4">${venta.cantidad}</span> unidades
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-euro-sign me-2"></i>Total:</h5>
                        <p class="ms-4">
                            <span class="h3">
                                <fmt:formatNumber value="${venta.total}" type="currency" currencySymbol="€"/>
                            </span>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-calendar-alt me-2"></i>Fecha de Venta:</h5>
                        <p class="ms-4">
                            <fmt:formatDate value="${venta.fechaVenta}" pattern="dd/MM/yyyy"/>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-info-circle me-2"></i>Estado:</h5>
                        <p class="ms-4">
                            <c:choose>
                                <c:when test="${venta.estadoVenta == 'Completado'}">
                                    <span class="badge bg-success fs-6">
                                        <i class="fas fa-check me-1"></i>${venta.estadoVenta}
                                    </span>
                                </c:when>
                                <c:when test="${venta.estadoVenta == 'Pendiente'}">
                                    <span class="badge bg-warning fs-6">
                                        <i class="fas fa-clock me-1"></i>${venta.estadoVenta}
                                    </span>
                                </c:when>
                                <c:when test="${venta.estadoVenta == 'Cancelado'}">
                                    <span class="badge bg-danger fs-6">
                                        <i class="fas fa-times me-1"></i>${venta.estadoVenta}
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-info fs-6">
                                        <i class="fas fa-info me-1"></i>${venta.estadoVenta}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-credit-card me-2"></i>Estado del Pago:</h5>
                        <p class="ms-4">
                            <c:choose>
                                <c:when test="${venta.pagado}">
                                    <span class="badge bg-success fs-6">
                                        <i class="fas fa-check me-1"></i>Pagado
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger fs-6">
                                        <i class="fas fa-times me-1"></i>Pendiente de Pago
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <!-- Botones de acción para administradores -->
                    <c:if test="${esAdmin}">
                        <div class="text-center mt-4">
                            <div class="btn-group" role="group">
                                <a href="${pageContext.request.contextPath}/ventas/editar?id=${venta.id}" 
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

    <!-- Información relacionada -->
    <c:if test="${not empty usuario and not empty articulo}">
        <div class="row mt-5">
            <div class="col-md-6 mb-3">
                <div class="related-card">
                    <h5><i class="fas fa-user me-2 text-primary"></i>Información del Usuario</h5>
                    <p><strong>Nombre:</strong> ${usuario.nombre}</p>
                    <p><strong>Email:</strong> ${usuario.email}</p>
                    <p><strong>Crédito:</strong> 
                        <fmt:formatNumber value="${usuario.credito}" type="currency" currencySymbol="€"/>
                    </p>
                    <a href="${pageContext.request.contextPath}/usuarios/detalle?id=${usuario.id}" 
                       class="btn btn-primary btn-sm">
                        <i class="fas fa-eye me-1"></i>Ver Usuario
                    </a>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="related-card">
                    <h5><i class="fas fa-box me-2 text-success"></i>Información del Artículo</h5>
                    <p><strong>Nombre:</strong> ${articulo.nombre}</p>
                    <p><strong>Precio:</strong> 
                        <fmt:formatNumber value="${articulo.precio}" type="currency" currencySymbol="€"/>
                    </p>
                    <p><strong>Disponible:</strong> 
                        <span class="badge ${articulo.disponible ? 'bg-success' : 'bg-secondary'}">
                            ${articulo.disponible ? 'Sí' : 'No'}
                        </span>
                    </p>
                    <a href="${pageContext.request.contextPath}/articulos/detalle?id=${articulo.id}" 
                       class="btn btn-success btn-sm">
                        <i class="fas fa-eye me-1"></i>Ver Artículo
                    </a>
                </div>
            </div>
        </div>
    </c:if>

    <!-- Enlaces relacionados -->
    <div class="text-center mt-5">
        <a href="${pageContext.request.contextPath}/ventas/lista" 
           class="btn btn-outline-primary me-2">
            <i class="fas fa-shopping-cart me-2"></i>Ver Todas las Ventas
        </a>
        <a href="${pageContext.request.contextPath}/" 
           class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Volver al Inicio
        </a>
    </div>
</div>

<!-- JavaScript para confirmación de eliminación -->
<c:if test="${esAdmin}">
<script>
function confirmarEliminacion() {
    if (confirm('¿Estás seguro de que quieres eliminar la venta #${venta.id}?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = '${pageContext.request.contextPath}/ventas/eliminar?id=${venta.id}';
    }
}
</script>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>