<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario - Doctor Who</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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

        .btn-warning {
            background-color: #f68712;
            border: none;
            color: white;
            padding: 12px 30px;
            font-weight: 600;
        }

        .btn-warning:hover {
            background-color: #e5770e;
            color: white;
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
    </style>
</head>
<body>
    <jsp:include page="../navbar.jsp">
        <jsp:param name="pageTitle" value="Editar Usuario"/>
    </jsp:include>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">
            <i class="fas fa-edit me-2"></i>Editar Usuario: ${usuario.nombre}
        </h2>
        
        <form method="post" action="${pageContext.request.contextPath}/usuarios/actualizar" id="editarForm">
            <input type="hidden" name="id" value="${usuario.id}">

            <!-- Nombre -->
            <div class="mb-3">
                <label for="nombre" class="form-label">
                    <i class="fas fa-user me-2"></i>Nombre <span class="required">*</span>
                </label>
                <input type="text" id="nombre" name="nombre" class="form-control" 
                       value="${usuario.nombre}" required>
                <div class="form-text">Nombre actual: <span class="current-value">${usuario.nombre}</span></div>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">
                    <i class="fas fa-envelope me-2"></i>Email <span class="required">*</span>
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
                       placeholder="Dejar vacío para mantener la actual">
                <div class="form-text">Solo completa si quieres cambiar la contraseña actual</div>
            </div>

            <!-- Crédito -->
            <div class="mb-3">
                <label for="credito" class="form-label">
                    <i class="fas fa-coins me-2"></i>Crédito <span class="required">*</span>
                </label>
                <div class="input-group">
                    <input type="number" id="credito" name="credito" class="form-control" 
                           step="0.01" min="0" value="${usuario.credito}" required>
                    <span class="input-group-text">€</span>
                </div>
                <div class="form-text">Crédito actual: <span class="current-value">
                    <fmt:formatNumber value="${usuario.credito}" type="currency" currencySymbol="€"/>
                </span></div>
            </div>

            <!-- Tipo de usuario -->
            <div class="mb-3">
                <label class="form-label">
                    <i class="fas fa-shield-alt me-2"></i>Tipo de Usuario <span class="required">*</span>
                </label>
                <div class="mt-2">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="es_admin" 
                               id="usuario_normal" value="false" ${!usuario.esAdmin ? 'checked' : ''}>
                        <label class="form-check-label" for="usuario_normal">
                            <i class="fas fa-user me-1"></i>Usuario Normal
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="es_admin" 
                               id="administrador" value="true" ${usuario.esAdmin ? 'checked' : ''}>
                        <label class="form-check-label" for="administrador">
                            <i class="fas fa-crown me-1"></i>Administrador
                        </label>
                    </div>
                </div>
                <div class="form-text">
                    Tipo actual: 
                    <span class="current-value badge ${usuario.esAdmin ? 'bg-danger' : 'bg-info'}">
                        ${usuario.esAdmin ? 'Administrador' : 'Usuario Normal'}
                    </span>
                </div>
            </div>

            <!-- Información de solo lectura -->
            <div class="mb-3">
                <label class="form-label">
                    <i class="fas fa-calendar me-2"></i>Fecha de Registro
                </label>
                <input type="text" class="form-control" 
                       value="<fmt:formatDate value='${usuario.fechaRegistro}' pattern='dd/MM/yyyy'/>" 
                       readonly>
                <div class="form-text">Esta fecha no se puede modificar</div>
            </div>

            <!-- Botones -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-warning">
                    <i class="fas fa-save me-2"></i>Guardar Cambios
                </button>
                <a href="${pageContext.request.contextPath}/usuarios/detalle?id=${usuario.id}" 
                   class="btn btn-outline-secondary">
                    <i class="fas fa-times me-2"></i>Cancelar
                </a>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/usuarios/lista" class="text-decoration-none">
                <i class="fas fa-list me-2"></i>Volver a la lista de usuarios
            </a>
        </div>
    </div>
</div>

<script>
    // Validación del formulario
    document.getElementById('editarForm').addEventListener('submit', function(e) {
        const nombre = document.getElementById('nombre').value.trim();
        const email = document.getElementById('email').value.trim();
        const credito = document.getElementById('credito').value;

        if (!nombre || !email || !credito) {
            e.preventDefault();
            alert('Por favor, completa todos los campos obligatorios marcados con *');
            return false;
        }

        if (parseFloat(credito) < 0) {
            e.preventDefault();
            alert('El crédito no puede ser negativo');
            return false;
        }

        // Validar formato de email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            e.preventDefault();
            alert('Por favor, ingresa un email válido');
            return false;
        }

        // Confirmación antes de guardar cambios
        const nombreUsuario = document.getElementById('nombre').value || 'este usuario';
        if (!confirm('¿Confirmas que quieres guardar los cambios en ' + nombreUsuario + '?')) {
            e.preventDefault();
            return false;
        }

        return true;
    });

    // Resaltar campos modificados
    const campos = ['nombre', 'email', 'credito'];
    
    campos.forEach(campo => {
        const input = document.getElementById(campo);
        if (input) {
            const valorOriginal = input.value;
            input.addEventListener('input', function() {
                if (this.value !== valorOriginal) {
                    this.style.borderLeft = '4px solid #f68712';
                } else {
                    this.style.borderLeft = '';
                }
            });
        }
    });

    // Para los radio buttons
    const radioButtons = document.querySelectorAll('input[name="es_admin"]');
    const adminOriginal = document.querySelector('input[name="es_admin"]:checked');
    const valorOriginalAdmin = adminOriginal ? adminOriginal.value === 'true' : false;
    
    radioButtons.forEach(radio => {
        radio.addEventListener('change', function() {
            const valorActual = this.value === 'true';
            if (valorActual !== valorOriginalAdmin) {
                radioButtons.forEach(r => r.style.outline = '2px solid #f68712');
            } else {
                radioButtons.forEach(r => r.style.outline = '');
            }
        });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>