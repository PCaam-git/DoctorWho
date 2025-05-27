<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios - Doctor Who</title>
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
        .admin-badge {
            background: linear-gradient(45deg, #f093fb 0%, #f5576c 100%);
        }
        .user-badge {
            background: linear-gradient(45deg, #4facfe 0%, #00f2fe 100%);
        }
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Gestión de Usuarios"/>
    </jsp:include>

<div class="container py-5">
    <!-- Encabezado y búsqueda -->
    <div class="search-container">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2 class="mb-0">
                <i class="fas fa-users me-2"></i>Gestión de Usuarios
            </h2>
            <a href="${pageContext.request.contextPath}/usuarios/formulario" class="btn btn-light btn-lg">
                <i class="fas fa-plus me-2"></i>Nuevo Usuario
            </a>
        </div>
        
        <!-- Búsqueda -->
        <form method="get" action="${pageContext.request.contextPath}/usuarios/lista" class="row g-3">
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-text bg-white border-0">
                        <i class="fas fa-search"></i>
                    </span>
                    <input type="text" name="q" class="form-control border-0" 
                           placeholder="Buscar por nombre o email..." 
                           value="${busqueda}">
                </div>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-warning w-100">
                    <i class="fas fa-search me-2"></i>Buscar
                </button>
            </div>
        </form>
        
        <c:if test="${not empty busqueda}">
            <div class="mt-2">
                <small>
                    <i class="fas fa-info-circle me-1"></i>
                    Mostrando resultados para: "<strong>${busqueda}</strong>"
                    <a href="${pageContext.request.contextPath}/usuarios/lista" class="text-white ms-2">
                        <i class="fas fa-times"></i> Limpiar búsqueda
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

    <!-- Tabla de usuarios -->
    <div class="table-container">
        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">
                            <i class="fas fa-id-badge me-2"></i>ID
                        </th>
                        <th scope="col">
                            <i class="fas fa-user me-2"></i>Nombre
                        </th>
                        <th scope="col">
                            <i class="fas fa-envelope me-2"></i>Email
                        </th>
                        <th scope="col">
                            <i class="fas fa-shield-alt me-2"></i>Rol
                        </th>
                        <th scope="col">
                            <i class="fas fa-coins me-2"></i>Crédito
                        </th>
                        <th scope="col">
                            <i class="fas fa-calendar me-2"></i>Registro
                        </th>
                        <th scope="col" class="text-center">
                            <i class="fas fa-cogs me-2"></i>Acciones
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty usuarios}">
                            <tr>
                                <td colspan="7" class="text-center py-4">
                                    <i class="fas fa-users fa-3x text-muted mb-3"></i>
                                    <p class="text-muted mb-0">
                                        <c:choose>
                                            <c:when test="${not empty busqueda}">
                                                No se encontraron usuarios que coincidan con tu búsqueda.
                                            </c:when>
                                            <c:otherwise>
                                                No hay usuarios registrados.
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr>
                                    <td>
                                        <strong>${usuario.id}</strong>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-user-circle fa-2x text-secondary me-2"></i>
                                            <div>
                                                <strong>${usuario.nombre}</strong>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <small class="text-muted">${usuario.email}</small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${usuario.esAdmin}">
                                                <span class="badge admin-badge">
                                                    <i class="fas fa-crown me-1"></i>Administrador
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge user-badge">
                                                    <i class="fas fa-user me-1"></i>Usuario
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <strong>
                                            <fmt:formatNumber value="${usuario.credito}" type="currency" currencySymbol="€"/>
                                        </strong>
                                    </td>
                                    <td>
                                        <small class="text-muted">
                                            <fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy"/>
                                        </small>
                                    </td>
                                    <td class="text-center">
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/usuarios/detalle?id=${usuario.id}" 
                                               class="btn btn-outline-primary btn-sm" title="Ver detalles">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/usuarios/editar?id=${usuario.id}" 
                                               class="btn btn-outline-warning btn-sm" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                                    title="Eliminar" 
                                                    data-usuario-id="${usuario.id}" 
                                                    data-usuario-nombre="${usuario.nombre}"
                                                    onclick="eliminarUsuarioAjax(this)">
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
        
        <c:if test="${not empty usuarios}">
            <div class="px-3 py-2 bg-light border-top">
                <small class="text-muted">
                    <i class="fas fa-info-circle me-1"></i>
                    Total de usuarios: <strong>${totalUsuarios}</strong>
                    <c:if test="${totalPages > 1}">
                        | Página ${currentPage} de ${totalPages}
                    </c:if>
                </small>
            </div>
        </c:if>
    </div>

    <!-- Paginación -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Paginación de usuarios" class="mt-4">
            <ul class="pagination justify-content-center">
                <!-- Botón anterior -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage - 1}&q=${busqueda}">
                            <i class="fas fa-chevron-left"></i> Anterior
                        </a>
                    </li>
                </c:if>

                <!-- Números de página -->
                <c:forEach begin="1" end="${totalPages}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${pageNum}&q=${busqueda}">
                            ${pageNum}
                        </a>
                    </li>
                </c:forEach>

                <!-- Botón siguiente -->
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage + 1}&q=${busqueda}">
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

<!-- Modal de confirmación -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">
                    <i class="fas fa-exclamation-triangle text-warning me-2"></i>Confirmar eliminación
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que quieres eliminar al usuario <strong id="userName"></strong>?</p>
                <p class="text-muted small">Esta acción no se puede deshacer.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="fas fa-times me-2"></i>Cancelar
                </button>
                <button type="button" class="btn btn-danger" id="confirmDeleteBtn">
                    <i class="fas fa-trash me-2"></i>Eliminar
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Toast para notificaciones -->
<div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
    <div id="successToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
                <i class="fas fa-check-circle me-2"></i>
                <span id="successMessage"></span>
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
    <div id="errorToast" class="toast align-items-center text-white bg-danger border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
                <i class="fas fa-exclamation-triangle me-2"></i>
                <span id="errorMessage"></span>
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</div>

<!-- JavaScript para eliminación Ajax -->
<script>
let userIdToDelete = null;

function eliminarUsuarioAjax(button) {
    const userId = button.getAttribute('data-usuario-id');
    const userName = button.getAttribute('data-usuario-nombre');
    
    // Configurar modal
    document.getElementById('userName').textContent = userName;
    userIdToDelete = userId;
    
    // Mostrar modal
    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();
}

// Confirmar eliminación
document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
    if (!userIdToDelete) return;
    
    const button = this;
    const originalText = button.innerHTML;
    
    // Deshabilitar botón y mostrar loading
    button.disabled = true;
    button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Eliminando...';
    
    // Realizar petición Ajax
    fetch('${pageContext.request.contextPath}/usuarios/eliminar-ajax', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'id=' + encodeURIComponent(userIdToDelete)
    })
    .then(response => response.json())
    .then(data => {
        // Cerrar modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmDeleteModal'));
        modal.hide();
        
        if (data.success) {
            // Mostrar toast de éxito
            document.getElementById('successMessage').textContent = data.message;
            const successToast = new bootstrap.Toast(document.getElementById('successToast'));
            successToast.show();
            
            // Eliminar fila de la tabla con animación
            const userRow = document.querySelector(`button[data-usuario-id="${data.userId}"]`).closest('tr');
            userRow.style.transition = 'opacity 0.5s ease';
            userRow.style.opacity = '0';
            
            setTimeout(() => {
                userRow.remove();
                
                // Actualizar contador si existe
                const totalElement = document.querySelector('.table-container .bg-light strong');
                if (totalElement) {
                    const currentTotal = parseInt(totalElement.textContent);
                    totalElement.textContent = currentTotal - 1;
                }
                
                // Si no quedan usuarios en la página, recargar
                const remainingRows = document.querySelectorAll('tbody tr').length;
                if (remainingRows === 0) {
                    window.location.reload();
                }
            }, 500);
            
        } else {
            // Mostrar toast de error
            document.getElementById('errorMessage').textContent = data.message;
            const errorToast = new bootstrap.Toast(document.getElementById('errorToast'));
            errorToast.show();
        }
    })
    .catch(error => {
        console.error('Error:', error);
        
        // Cerrar modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmDeleteModal'));
        modal.hide();
        
        // Mostrar toast de error
        document.getElementById('errorMessage').textContent = 'Error de conexión. Inténtalo de nuevo.';
        const errorToast = new bootstrap.Toast(document.getElementById('errorToast'));
        errorToast.show();
    })
    .finally(() => {
        // Restaurar botón
        button.disabled = false;
        button.innerHTML = originalText;
        userIdToDelete = null;
    });
});

// Función original para compatibilidad (si se usa en algún otro lugar)
function confirmarEliminacion(button) {
    const id = button.getAttribute('data-usuario-id');
    const nombre = button.getAttribute('data-usuario-nombre');
    
    if (confirm('¿Estás seguro de que quieres eliminar al usuario "' + nombre + '"?\n\nEsta acción no se puede deshacer.')) {
        window.location.href = '${pageContext.request.contextPath}/usuarios/eliminar?id=' + id;
    }
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>