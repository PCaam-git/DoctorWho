<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: var(--color-primary, #0072bb);">
    <div class="container">
        <!-- Logo/Brand -->
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
            <i class="fas fa-tardis me-2"></i>Doctor Who Store
        </a>

        <!-- Toggler para móvil -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Menu de navegación -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">
                        <i class="fas fa-home me-1"></i>Inicio
                    </a>
                </li>
                
                <!-- Menús para usuarios logueados -->
                <c:if test="${not empty sessionScope.email}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/categorias/lista">
                            <i class="fas fa-folder me-1"></i>Categorías
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/articulos/lista">
                            <i class="fas fa-box me-1"></i>Artículos
                        </a>
                    </li>
                    
                    <!-- Menús solo para administradores -->
                    <c:if test="${sessionScope.es_admin}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/usuarios/lista">
                                <i class="fas fa-users me-1"></i>Usuarios
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/ventas/lista">
                                <i class="fas fa-shopping-cart me-1"></i>Ventas
                            </a>
                        </li>
                    </c:if>
                </c:if>
            </ul>

            <!-- Usuario y logout -->
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.email}">
                        <!-- Usuario logueado -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user me-1"></i>
                                ${sessionScope.email}
                                <c:if test="${sessionScope.es_admin}">
                                    <span class="badge bg-warning text-dark ms-1">Admin</span>
                                </c:if>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li>
                                    <span class="dropdown-item-text">
                                        <small class="text-muted">
                                            Conectado como: 
                                            <strong>${sessionScope.es_admin ? 'Administrador' : 'Usuario'}</strong>
                                        </small>
                                    </span>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/usuario/perfil">
                                        <i class="fas fa-user-edit me-2"></i>Mi Perfil
                                    </a>
                                </li>

                                
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout"
                                       onclick="return confirm('¿Estás seguro de que quieres cerrar sesión?')">
                                        <i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <!-- Usuario no logueado -->
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                <i class="fas fa-sign-in-alt me-1"></i>Iniciar Sesión
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<!-- Breadcrumb opcional (solo si está logueado) -->
<c:if test="${not empty sessionScope.email && param.showBreadcrumb != 'false'}">
    <nav aria-label="breadcrumb" class="bg-light py-2">
        <div class="container">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/" class="text-decoration-none">
                        <i class="fas fa-home"></i> Inicio
                    </a>
                </li>
                <!-- El breadcrumb específico se agregará en cada página -->
                <c:if test="${not empty pageTitle}">
                    <li class="breadcrumb-item active">${pageTitle}</li>
                </c:if>
            </ol>
        </div>
    </nav>
</c:if>