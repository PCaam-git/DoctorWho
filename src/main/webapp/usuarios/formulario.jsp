<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Registrar Usuario - Doctor Who</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }

        .form-container {
            background-color: white;
            border-radius: 15px;
            padding: 2rem;
            max-width: 700px;
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
    </style>
</head>

<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Nuevo Usuario"/>
    </jsp:include>

    <div class="container">
        <div class="form-container">
            <h2 class="form-title">
                <i class="fas fa-user-plus me-2"></i>Registrar Nuevo Usuario
            </h2>
            
            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/usuarios/formulario" method="post" 
                  id="usuarioForm">
                
                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombre" class="form-label">
                        <i class="fas fa-user me-2"></i>Nombre Completo <span class="required">*</span>
                    </label>
                    <input type="text" id="nombre" name="nombre" class="form-control" 
                           placeholder="Ej: Juan Pérez García" required>
                    <div class="form-text">Ingresa el nombre completo del usuario</div>
                </div>

                <!-- Email -->
                <div class="mb-3">
                    <label for="email" class="form-label">
                        <i class="fas fa-envelope me-2"></i>Correo Electrónico <span class="required">*</span>
                    </label>
                    <input type="email" id="email" name="email" class="form-control" 
                           placeholder="ejemplo@correo.com" required>
                    <div class="form-text">Email para iniciar sesión en el sistema</div>
                </div>

                <!-- Contraseña -->
                <div class="mb-3">
                    <label for="contrasena" class="form-label">
                        <i class="fas fa-lock me-2"></i>Contraseña <span class="required">*</span>
                    </label>
                    <input type="password" id="contrasena" name="contrasena" class="form-control" 
                           placeholder="Ingresa una contraseña segura" required>
                    <div class="form-text">Mínimo 6 caracteres recomendado</div>
                </div>

                <div class="row">
                    <!-- Crédito -->
                    <div class="col-md-6 mb-3">
                        <label for="credito" class="form-label">
                            <i class="fas fa-coins me-2"></i>Crédito Inicial <span class="required">*</span>
                        </label>
                        <div class="input-group">
                            <input type="number" id="credito" name="credito" class="form-control" 
                                   step="0.01" min="0" placeholder="0.00" value="0.00" required>
                            <span class="input-group-text">€</span>
                        </div>
                        <div class="form-text">Crédito inicial del usuario</div>
                    </div>

                    <!-- Tipo de usuario -->
                    <div class="col-md-6 mb-3">
                        <label class="form-label">
                            <i class="fas fa-shield-alt me-2"></i>Tipo de Usuario
                        </label>
                        <div class="mt-2">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="es_admin" 
                                       id="usuario_normal" value="false" checked>
                                <label class="form-check-label" for="usuario_normal">
                                    <i class="fas fa-user me-1"></i>Usuario Normal
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="es_admin" 
                                       id="administrador" value="true">
                                <label class="form-check-label" for="administrador">
                                    <i class="fas fa-crown me-1"></i>Administrador
                                </label>
                            </div>
                        </div>
                        <div class="form-text">Selecciona el nivel de acceso</div>
                    </div>
                </div>

                <!-- Botones -->
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button type="button" class="btn btn-outline-secondary me-md-2" onclick="limpiarFormulario()">
                        <i class="fas fa-eraser me-2"></i>Limpiar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Guardar Usuario
                    </button>
                </div>
            </form>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/usuarios/lista" class="text-decoration-none">
                    <i class="fas fa-arrow-left me-2"></i>Volver a la lista de usuarios
                </a>
            </div>
        </div>
    </div>

    <script>
        // Función para limpiar el formulario
        function limpiarFormulario() {
            if (confirm('¿Estás seguro de que quieres limpiar todos los campos?')) {
                document.getElementById('usuarioForm').reset();
                document.getElementById('usuario_normal').checked = true;
                document.getElementById('credito').value = '0.00';
            }
        }

        // Validación en tiempo real
        document.getElementById('usuarioForm').addEventListener('submit', function(e) {
            const nombre = document.getElementById('nombre').value.trim();
            const email = document.getElementById('email').value.trim();
            const contrasena = document.getElementById('contrasena').value;
            const credito = document.getElementById('credito').value;

            if (!nombre || !email || !contrasena || !credito) {
                e.preventDefault();
                alert('Por favor, completa todos los campos obligatorios marcados con *');
                return false;
            }

            if (contrasena.length < 6) {
                e.preventDefault();
                alert('La contraseña debe tener al menos 6 caracteres');
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

            // Confirmación antes de enviar
            if (!confirm('¿Confirmas que quieres crear este usuario?')) {
                e.preventDefault();
                return false;
            }

            return true;
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>