<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Mi Perfil - Doctor Who</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }

        .form-container {
            background-color: white;
            border-radius: 15px;
            padding: 2rem;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
        }

        .form-title {
            color: var(--color-primary);
            text-align: center;
            margin-bottom: 2rem;
        }

        .btn-primary {
            background-color: var(--color-primary);
            border: none;
            color: white;
            padding: 12px 30px;
            font-weight: 600;
        }

        .btn-primary:hover {
            background-color: #005a94;
        }

        .form-control:focus {
            border-color: var(--color-primary);
            box-shadow: 0 0 0 0.2rem rgba(0, 114, 187, 0.25);
        }

        .required {
            color: #dc3545;
        }

        .current-value {
            background-color: #f8f9fa;
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            font-size: 0.875rem;
            color: #6c757d;
        }

        .info-readonly {
            background-color: #e9ecef;
            border: 1px solid #ced4da;
            padding: 0.375rem 0.75rem;
            border-radius: 0.375rem;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <jsp:include page="../navbar.jsp">
        <jsp:param name="pageTitle" value="Editar Mi Perfil"/>
    </jsp:include>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">
            <i class="fas fa-user-edit me-2"></i>Editar Mi Perfil
        </h2>
        
        <form method="post" action="${pageContext.request.contextPath}/usuario/actualizar" id="editarPerfilForm">

            <!-- Nombre -->
            <div class="mb-3">
                <label for="nombre" class="form-label">
                    <i class="fas fa-user me-2"></i>Nombre Completo <span class="required">*</span>
                </label>
                <input type="text" id="nombre" name="nombre" class="form-control" 
                       value="${usuario.nombre}" required>
                <div class="form-text">Nombre actual: <span class="current-value">${usuario.nombre}</span></div>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">
                    <i class="fas fa-envelope me-2"></i>Correo Electrónico <span class="required">*</span>
                </label>
                <input type="email" id="email" name="email" class="form-control" 
                       value="${usuario.email}" required>
                <div class="form-text">Email actual: <span class="current-value">${usuario.email}</span></div>
            </div>

            <!-- Contraseña -->
            <div class="mb-3">
                <label for="contrasena" class="form-label">
                    <i class="fas fa-lock me-2"></i>Nueva Contraseña
                </label>
                <input type="password" id="contrasena" name="contrasena" class="form-control" 
                       placeholder="Dejar vacío para mantener la contraseña actual">
                <div class="form-text">
                    <i class="fas fa-info-circle me-1"></i>
                    Solo completa este campo si quieres cambiar tu contraseña
                </div>
            </div>

            <!-- Información de solo lectura -->
            <div class="mb-3">
                <label class="form-label">
                    <i class="fas fa-coins me-2"></i>Crédito Disponible
                </label>
                <div class="info-readonly">
                    <fmt:formatNumber value="${usuario.credito}" type="currency" currencySymbol="€"/>
                </div>
                <div class="form-text">
                    <i class="fas fa-info-circle me-1"></i>
                    El crédito solo puede ser modificado por un administrador
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">
                    <i class="fas fa-shield-alt me-2"></i>Tipo de Usuario
                </label>
                <div class="info-readonly">
                    <c:choose>
                        <c:when test="${usuario.esAdmin}">
                            <i class="fas fa-crown me-1"></i>Administrador
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-user me-1"></i>Usuario Normal
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-text">
                    <i class="fas fa-info-circle me-1"></i>
                    El tipo de usuario no se puede modificar
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">
                    <i class="fas fa-calendar me-2"></i>Fecha de Registro
                </label>
                <div class="info-readonly">
                    <fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy"/>
                </div>
                <div class="form-text">
                    <i class="fas fa-info-circle me-1"></i>
                    Esta fecha no se puede modificar
                </div>
            </div>

            <!-- Botones -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save me-2"></i>Guardar Cambios
                </button>
                <a href="${pageContext.request.contextPath}/usuario/perfil" 
                   class="btn btn-outline-secondary">
                    <i class="fas fa-times me-2"></i>Cancelar
                </a>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/usuario/perfil" class="text-decoration-none">
                <i class="fas fa-user me-2"></i>Volver a mi perfil
            </a>
        </div>
    </div>
</div>

<script>
    // Validación del formulario
    document.getElementById('editarPerfilForm').addEventListener('submit', function(e) {
        const nombre = document.getElementById('nombre').value.trim();
        const email = document.getElementById('email').value.trim();
        const contrasena = document.getElementById('contrasena').value;

        if (!nombre || !email) {
            e.preventDefault();
            alert('Por favor, completa todos los campos obligatorios marcados con *');
            return false;
        }

        // Validar formato de email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            e.preventDefault();
            alert('Por favor, ingresa un email válido');
            return false;
        }

        // Validar contraseña si se proporciona
        if (contrasena && contrasena.length < 6) {
            e.preventDefault();
            alert('La nueva contraseña debe tener al menos 6 caracteres');
            return false;
        }

        // Confirmación antes de guardar cambios
        if (!confirm('¿Confirmas que quieres guardar los cambios en tu perfil?')) {
            e.preventDefault();
            return false;
        }

        return true;
    });

    // Resaltar campos modificados
    const campos = ['nombre', 'email'];
    
    campos.forEach(campo => {
        const input = document.getElementById(campo);
        if (input) {
            const valorOriginal = input.value;
            input.addEventListener('input', function() {
                if (this.value !== valorOriginal) {
                    this.style.borderLeft = '4px solid #0072bb';
                } else {
                    this.style.borderLeft = '';
                }
            });
        }
    });

    // Para el campo de contraseña
    const contrasenaInput = document.getElementById('contrasena');
    contrasenaInput.addEventListener('input', function() {
        if (this.value.length > 0) {
            this.style.borderLeft = '4px solid #0072bb';
        } else {
            this.style.borderLeft = '';
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>