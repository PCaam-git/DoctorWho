<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <title>Detalle de Categoría - Doctor Who</title>
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
                        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
                    }

                    .info-item {
                        margin-bottom: 1rem;
                        padding: 0.5rem 0;
                        border-bottom: 1px solid rgba(255, 255, 255, 0.2);
                    }

                    .info-item:last-child {
                        border-bottom: none;
                    }
                </style>
            </head>

            <body>
                <jsp:include page="../navbar.jsp">
                    <jsp:param name="pageTitle" value="Detalle de Categorías" />
                </jsp:include>
                <div class="container py-5">
                    <!-- Botones de navegación -->
                    <div class="d-flex justify-content-between mb-4">
                        <a href="${pageContext.request.contextPath}/categorias/lista" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left"></i> Volver a la Lista
                        </a>

                        <!-- Solo administradores pueden editar -->
                        <c:if test="${esAdmin}">
                            <a href="${pageContext.request.contextPath}/categorias/editar?id=${categoria.id}"
                                class="btn btn-warning">
                                <i class="fas fa-edit"></i> Editar Categoría
                            </a>
                        </c:if>
                    </div>

                    <!-- Tarjeta de detalle -->
                    <div class="card detail-card">
                        <div class="card-body p-5">
                            <div class="text-center mb-4">
                                <h2 class="card-title display-4">
                                    <i class="fas fa-folder-open me-3"></i>${categoria.nombre}
                                </h2>
                            </div>

                            <div class="row">
                                <div class="col-md-8 mx-auto">
                                    <!-- Información básica -->
                                    <div class="info-item">
                                        <h5><i class="fas fa-info-circle me-2"></i>Descripción:</h5>
                                        <p class="ms-4">${categoria.descripcion}</p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-boxes me-2"></i>Cantidad:</h5>
                                        <p class="ms-4">${categoria.cantidad} productos</p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-euro-sign me-2"></i>Precio Medio:</h5>
                                        <p class="ms-4">
                                            <c:choose>
                                                <c:when test="${categoria.precioMedio != null}">
                                                    <span class="h4">
                                                        <fmt:formatNumber value="${categoria.precioMedio}"
                                                            type="currency" currencySymbol="€" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">No disponible</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-check-circle me-2"></i>Estado de Productos:</h5>
                                        <p class="ms-4">
                                            <span
                                                class="badge ${categoria.tieneProductos ? 'bg-success' : 'bg-secondary'} fs-6">
                                                <c:choose>
                                                    <c:when test="${categoria.tieneProductos}">
                                                        <i class="fas fa-check me-1"></i>Tiene productos disponibles
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="fas fa-times me-1"></i>Sin productos disponibles
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-calendar-alt me-2"></i>Última Actualización:</h5>
                                        <p class="ms-4">
                                            <fmt:formatDate value="${categoria.fechaActualizacion}"
                                                pattern="dd/MM/yyyy" />
                                        </p>
                                    </div>

                                    <!-- Sin mostrar información de imagen -->
                                </div>
                            </div>

                            <!-- Botones de acción para administradores -->
                            <c:if test="${esAdmin}">
                                <div class="text-center mt-4">
                                    <div class="btn-group" role="group">
                                        <a href="${pageContext.request.contextPath}/categorias/editar?id=${categoria.id}"
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

                    <!-- Enlaces relacionados -->
                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/articulos/lista?categoria=${categoria.id}"
                            class="btn btn-outline-primary me-2">
                            <i class="fas fa-list me-2"></i>Ver Artículos de esta Categoría
                        </a>
                        <a href="${pageContext.request.contextPath}/categorias/lista" class="btn btn-outline-secondary">
                            <i class="fas fa-th-large me-2"></i>Ver Todas las Categorías
                        </a>
                    </div>
                </div>

                <!-- JavaScript para confirmación de eliminación -->
                <c:if test="${esAdmin}">
                    <script>
                        function confirmarEliminacion() {
                            if (confirm('¿Estás seguro de que quieres eliminar la categoría "${categoria.nombre}"?\n\nEsta acción no se puede deshacer.')) {
                                window.location.href = '${pageContext.request.contextPath}/categorias/eliminar?id=${categoria.id}';
                            }
                        }
                    </script>
                </c:if>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>