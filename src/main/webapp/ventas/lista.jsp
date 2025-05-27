<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Ventas - Doctor Who</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f8f9fa;
        }
        .search-container {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 15px;
            padding: 2rem;
            color: white;
            margin-bottom: 2rem;
        }
        .table-container {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .pagado-badge {
            background: linear-gradient(45deg, #28a745 0%, #20c997 100%);
        }
        .no-pagado-badge {
            background: linear-gradient(45deg, #dc3545 0%, #fd7e14 100%);
        }
        .estado-pendiente {
            background: linear-gradient(45deg, #ffc107 0%, #fd7e14 100%);
        }
        .estado-completado {
            background: linear-gradient(45deg, #28a745 0%, #20c997 100%);
        }
        .estado-cancelado {
            background: linear-gradient(45deg, #dc3545 0%, #e83e8c 100%);
        }
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Gestión de Ventas"/>
    </jsp:include>

<div class="container py-5">
    <!-- Encabezado y búsqueda -->
    <div class="search-container">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2 class="mb-0">
                <i class="fas fa-shopping-cart me-2"></i>Gestión de Ventas
            </h2>
            <a href="${pageContext.request.contextPath}/ventas/formulario" class="btn btn-light btn-lg">
                <i class="fas fa-plus me-2"></i>Nueva Venta
            </a>
        </div>
        
        <!-- Formulario de búsqueda -->
        <form method="get" action="${pageContext.request.contextPath}/ventas/lista" class="row g-3">
            <div class="col-md-3">
                <select name="estado" class="form-select">
                    <option value="">Todos los estados</option>
                    <option value="Pendiente" ${estadoSeleccionado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                    <option value="Completado" ${estadoSeleccionado == 'Completado' ? 'selected' : ''}>Completado</option>
                    <option value="Cancelado" ${estadoSeleccionado == 'Cancelado' ? 'selected' : ''}>Cancelado</option>
                </select>
            </div>
            <div class="col-md-2">
                <select name="pagado" class="form-select">
                    <option value="">Todos los pagos</option>
                    <option value="true" ${pagadoSeleccionado == 'true' ? 'selected' : ''}>Pagado</option>
                    <option value="false" ${pagadoSeleccionado == 'false' ? 'selected' : ''}>Pendiente</option>
                </select>
            </div>
            <div class="col-md-2">
                <input type="date" name="fecha_desde" class="form-control" 
                       value="${fechaDesde}" placeholder="Desde">
            </div>
            <div class="col-md-2">
                <input type="date" name="fecha_hasta" class="form-control" 
                       value="${fechaHasta}" placeholder="Hasta">
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-warning w-100">
                    <i class="fas fa-search me-2"></i>Buscar
                </button>
            </div>
            <c:if test="${not empty estadoSeleccionado || not empty pagadoSeleccionado || not empty fechaDesde || not empty fechaHasta}">
                <div class="col-md-1">
                    <a href="${pageContext.request.contextPath}/ventas/lista" class="btn btn-outline-light w-100">
                        <i class="fas fa-times"></i>
                    </a>
                </div>
            </c:if>
        </form>
        
        <c:if test="${not empty estadoSeleccionado || not empty pagadoSeleccionado || not empty fechaDesde || not empty fechaHasta}">
            <div class="mt-2">
                <small>
                    <i class="fas fa-info-circle me-1"></i>
                    Resultados filtrados
                    <a href="${pageContext.request.contextPath}/ventas/lista" class="text-white ms-2">
                        <i class="fas fa-times"></i> Limpiar filtros
                    </a>
                </small>
            </div>
        </c:if>
    </div>

    <!-- Mensaje de éxito -->
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Tabla de ventas -->
    <div class="table-container">
        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">
                            <i class="fas fa-id-badge me-2"></i>ID
                        </th>
                        <th scope="col">
                            <i class="fas fa-user me-2"></i>Usuario
                        </th>
                        <th scope="col">
                            <i class="fas fa-box me-2"></i>Artículo
                        </th>
                        <th scope="col">
                            <i class="fas fa-hashtag me-2"></i>Cantidad
                        </th>
                        <th scope="col">
                            <i class="fas fa-euro-sign me-2"></i>Total
                        </th>
                        <th scope="col">
                            <i class="fas fa-calendar me-2"></i>Fecha
                        </th>
                        <th scope="col">
                            <i class="fas fa-info-circle me-2"></i>Estado
                        </th>
                        <th scope="col">
                            <i class="fas fa-credit-card me-2"></i>Pago
                        </th>
                        <th scope="col" class="text-center">
                            <i class="fas fa-cogs me-2"></i>Acciones
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty ventas}">
                            <tr>
                                <td colspan="9" class="text-center py-4">
                                    <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                                    <p class="text-muted mb-0">No hay ventas registradas en el sistema.</p>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="venta" items="${ventas}">
                                <tr>
                                    <td>
                                        <strong>#${venta.id}</strong>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-user-circle fa-2x text-secondary me-2"></i>
                                            <div>
                                                <small class="text-muted">ID: ${venta.usuarioId}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <small class="text-muted">ID: ${venta.articuloId}</small>
                                    </td>
                                    <td>
                                        <span class="badge bg-secondary">${venta.cantidad}</span>
                                    </td>
                                    <td>
                                        <strong>
                                            <fmt:formatNumber value="${venta.total}" type="currency" currencySymbol="€"/>
                                        </strong>
                                    </td>
                                    <td>
                                        <small class="text-muted">
                                            <fmt:formatDate value="${venta.fechaVenta}" pattern="dd/MM/yyyy"/>
                                        </small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${venta.estadoVenta == 'Completado'}">
                                                <span class="badge estado-completado">
                                                    <i class="fas fa-check me-1"></i>${venta.estadoVenta}
                                                </span>
                                            </c:when>
                                            <c:when test="${venta.estadoVenta == 'Pendiente'}">
                                                <span class="badge estado-pendiente">
                                                    <i class="fas fa-clock me-1"></i>${venta.estadoVenta}
                                                </span>
                                            </c:when>
                                            <c:when test="${venta.estadoVenta == 'Cancelado'}">
                                                <span class="badge estado-cancelado">
                                                    <i class="fas fa-times me-1"></i>${venta.estadoVenta}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-info">
                                                    <i class="fas fa-info me-1"></i>${venta.estadoVenta}
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${venta.pagado}">
                                                <span class="badge pagado-badge">
                                                    <i class="fas fa-check me-1"></i>Pagado
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge no-pagado-badge">
                                                    <i class="fas fa-times me-1"></i>Pendiente
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/ventas/detalle?id=${venta.id}" 
                                               class="btn btn-outline-primary btn-sm" title="Ver detalles">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/ventas/editar?id=${venta.id}" 
                                               class="btn btn-outline-warning btn-sm" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                                    title="Eliminar" 
                                                    data-venta-id="${venta.id}"
                                                    onclick="confirmarEliminacion(this)">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        
        <c:if test="${not empty ventas}">
            <div class="px-3 py-2 bg-light border-top">
                <small class="text-muted">
                    <i class="fas fa-info-circle me-1"></i>
                    Total de ventas: <strong>${totalVentas}</strong>
                    <c:if test="${totalPages > 1}">
                        | Página ${currentPage} de ${totalPages}
                    </c:if>
                </small>
            </div>
        </c:if>
    </div>

    <!-- Paginación -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Paginación de ventas" class="mt-4">
            <ul class="pagination justify-content-center">
                <!-- Botón anterior -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage - 1}&estado=${estadoSeleccionado}&pagado=${pagadoSeleccionado}&fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}">
                            <i class="fas fa-chevron-left"></i> Anterior
                        </a>
                    </li>
                </c:if>

                <!-- Números de página -->
                <c:forEach begin="1" end="${totalPages}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${pageNum}&estado=${estadoSeleccionado}&pagado=${pagadoSeleccionado}&fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}">
                            ${pageNum}
                        </a>
                    </li>
                </c:forEach>

                <!-- Botón siguiente -->
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage + 1}&estado=${estadoSeleccionado}&pagado=${pagadoSeleccionado}&fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}">
                            Siguiente <i class="fas fa-chevron-right"></i>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </c:if>

    <!-- Enlaces de navegación -->
    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Volver al Inicio
        </a>
    </div>
</div>

<!-- JavaScript para confirmación de eliminación -->
<script>
function confirmarEliminacion(button) {
    const id = button.getAttribute('data-venta-id');
    
    if (confirm('¿Estás seguro de que quieres eliminar la venta #' + id + '?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = '${pageContext.request.contextPath}/ventas/eliminar?id=' + id;
    }
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>