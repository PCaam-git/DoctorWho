<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Usuario - Doctor Who</title>
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
        .user-avatar {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto;
        }
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Detalle de Usuario"/>
    </jsp:include>

<div class="container py-5">
    <!-- Botones de navegación -->
    <div class="d-flex justify-content-between mb-4">
        <a href="${pageContext.request.contextPath}/usuarios/lista" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left"></i> Volver a la Lista
        </a>
        
        <!-- Solo administradores pueden editar -->
        <c:if test="${esAdmin}">
            <a href="${pageContext.request.contextPath}/usuarios/editar?id=${usuario.id}" class="btn btn-warning">
                <i class="fas fa-edit"></i> Editar Usuario
            </a>
        </c:if>
    </div>

    <div class="row">
        <!-- Avatar del usuario -->
        <div class="col-md-4 mb-4">
            <div class="text-center">
                <div class="user-avatar shadow">
                    <i class="fas fa-user fa-5x text-white"></i>
                </div>
                <h4 class="mt-3">${usuario.nombre}</h4>
                <p class="text-muted">${usuario.email}</p>
            </div>
        </div>

        <!-- Información del usuario -->
        <div class="col-md-8">
            <div class="card detail-card">
                <div class="card-body p-4">
                    <div class="text-center mb-4">
                        <h2 class="card-title display-5">
                            <i class="fas fa-user me-3"></i>${usuario.nombre}
                        </h2>
                    </div>
                    
                    <div class="info-item">
                        <h5><i class="fas fa-envelope me-2"></i>Email:</h5>
                        <p class="ms-4">${usuario.email}</p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-shield-alt me-2"></i>Tipo de Usuario:</h5>
                        <p class="ms-4">
                            <c:choose>
                                <c:when test="${usuario.esAdmin}">
                                    <span class="badge bg-danger fs-6">
                                        <i class="fas fa-crown me-1"></i>Administrador
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-info fs-6">
                                        <i class="fas fa-user me-1"></i>Usuario Normal
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-coins me-2"></i>Crédito Disponible:</h5>
                        <p class="ms-4">
                            <span class="h3">
                                <fmt:formatNumber value="${usuario.credito}" type="currency" currencySymbol="€"/>
                            </span>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-calendar-alt me-2"></i>Fecha de Registro:</h5>
                        <p class="ms-4">
                            <fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy"/>
                        </p>
                    </div>

                    <div class="info-item">
                        <h5><i class="fas fa-id-badge me-2"></i>ID de Usuario:</h5>
                        <p class="ms-4">
                            <code>#${usuario.id}</code>
                        </p>
                    </div>

                    <!-- Botones de acción para administradores -->
                    <c:if test="${esAdmin}">
                        <div class="text-center mt-4">
                            <div class="btn-group" role="group">
                                <a href="${pageContext.request.contextPath}/usuarios/editar?id=${usuario.id}" 
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
        <a href="${pageContext.request.contextPath}/usuarios/lista" 
           class="btn btn-outline-primary me-2">
            <i class="fas fa-users me-2"></i>Ver Todos los Usuarios
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
    if (confirm('¿Estás seguro de que quieres eliminar al usuario "${usuario.nombre}"?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = '${pageContext.request.contextPath}/usuarios/eliminar?id=${usuario.id}';
    }
}
</script>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>