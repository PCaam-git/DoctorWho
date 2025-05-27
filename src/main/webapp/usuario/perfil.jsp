<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <title>Mi Perfil - Doctor Who</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
                <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

                <style>
                    body {
                        background-color: #f8f9fa;
                        color: #333;
                    }

                    .profile-card {
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

                    .user-avatar {
                        width: 120px;
                        height: 120px;
                        border-radius: 50%;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        margin: 0 auto;
                        border: 4px solid white;
                    }

                    .welcome-card {
                        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                        color: white;
                        border-radius: 15px;
                        padding: 2rem;
                        margin-bottom: 2rem;
                    }
                </style>
            </head>

            <body>
                <!-- navbar -->
                <jsp:include page="/navbar.jsp">
                    <jsp:param name="pageTitle" value="Mi Perfil" />
                </jsp:include>

                <div class="container py-5">
                    <!-- Mensaje de bienvenida -->
                    <div class="welcome-card">
                        <div class="text-center">
                            <h1 class="display-5 mb-3">
                                <i class="fas fa-user-circle me-3"></i>¡Hola, ${usuario.nombre}!
                            </h1>
                            <p class="lead mb-0">Bienvenido a tu zona personal de Doctor Who Store</p>
                        </div>
                    </div>

                    <!-- Mensaje de actualización exitosa -->
                    <c:if test="${param.actualizado == 'true'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle me-2"></i>Tu perfil ha sido actualizado correctamente.
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Botones de navegación -->
                    <div class="d-flex justify-content-between mb-4">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                            <i class="fas fa-home"></i> Volver al Inicio
                        </a>
                    </div>

                    <div class="row">
                        <!-- Avatar del usuario -->
                        <div class="col-md-4 mb-4">
                            <div class="text-center">
                                <div class="user-avatar shadow">
                                    <i class="fas fa-user fa-4x text-white"></i>
                                </div>
                                <h3 class="mt-3">${usuario.nombre}</h3>
                                <p class="text-muted">${usuario.email}</p>
                                <c:choose>
                                    <c:when test="${usuario.esAdmin}">
                                        <span class="badge bg-danger fs-6">
                                            <i class="fas fa-crown me-1"></i>Administrador
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-info fs-6">
                                            <i class="fas fa-user me-1"></i>Usuario
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Información del perfil -->
                        <div class="col-md-8">
                            <div class="card profile-card">
                                <div class="card-body p-4">
                                    <div class="text-center mb-4">
                                        <h2 class="card-title display-6">
                                            <i class="fas fa-id-card me-3"></i>Mi Información Personal
                                        </h2>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-user me-2"></i>Nombre Completo:</h5>
                                        <p class="ms-4 fs-5">${usuario.nombre}</p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-envelope me-2"></i>Correo Electrónico:</h5>
                                        <p class="ms-4">${usuario.email}</p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-coins me-2"></i>Crédito Disponible:</h5>
                                        <p class="ms-4">
                                            <span class="h3">
                                                <fmt:formatNumber value="${usuario.credito}" type="currency"
                                                    currencySymbol="€" />
                                            </span>
                                        </p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-calendar-alt me-2"></i>Miembro Desde:</h5>
                                        <p class="ms-4">
                                            <fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy" />
                                        </p>
                                    </div>

                                    <div class="info-item">
                                        <h5><i class="fas fa-id-badge me-2"></i>ID de Usuario:</h5>
                                        <p class="ms-4">
                                            <code>#${usuario.id}</code>
                                        </p>
                                    </div>

                                    <!-- Botón de edición -->
                                    <div class="text-center mt-4">
                                        <a href="${pageContext.request.contextPath}/usuario/editar"
                                            class="btn btn-warning btn-lg">
                                            <i class="fas fa-edit me-2"></i>Editar Mi Perfil
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>