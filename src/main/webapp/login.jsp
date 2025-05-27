<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Doctor Who Store</title>
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

        .login-container {
            background-color: white;
            border-radius: 15px;
            padding: 2rem;
            max-width: 400px;
            margin: auto;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
        }

        .login-title {
            color: var(--color-primary, #0072bb);
            text-align: center;
            margin-bottom: 2rem;
        }

        .btn-login {
            background-color: var(--color-primary, #0072bb);
            border: none;
            padding: 12px 30px;
            font-weight: 600;
            width: 100%;
        }

        .btn-login:hover {
            background-color: #005a94;
        }

        .form-control:focus {
            border-color: var(--color-primary, #0072bb);
            box-shadow: 0 0 0 0.2rem rgba(0, 114, 187, 0.25);
        }

        .store-info {
            text-align: center;
            margin-bottom: 2rem;
            color: #6c757d;
        }

        .demo-credentials {
            background-color: #f8f9fa;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

        <div class="login-container">
            <!-- Título y descripción -->
            <h2 class="login-title">
                <i class="fas fa-tardis me-2"></i>Doctor Who Store
            </h2>
            <div class="store-info">
                <p>Tu tienda de productos del Doctor</p>
            </div>

            <!-- Mensajes de estado -->
            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle me-2"></i>
                    ${sessionScope.logoutMessage != null ? sessionScope.logoutMessage : 'Sesión cerrada correctamente'}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="logoutMessage" scope="session"/>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <c:if test="${param.expired == 'true'}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <i class="fas fa-clock me-2"></i>
                    Tu sesión ha expirado. Por favor, inicia sesión nuevamente.
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Formulario de login -->
            <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
                <div class="mb-3">
                    <label for="email" class="form-label">
                        <i class="fas fa-envelope me-2"></i>Email
                    </label>
                    <input type="email" id="email" name="email" class="form-control" 
                           placeholder="tu@email.com" required 
                           value="${param.email != null ? param.email : ''}">
                </div>

                <div class="mb-3">
                    <label for="contrasena" class="form-label">
                        <i class="fas fa-lock me-2"></i>Contraseña
                    </label>
                    <input type="password" id="contrasena" name="contrasena" class="form-control" 
                           placeholder="Tu contraseña" required>
                </div>

                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="recordar">
                    <label class="form-check-label" for="recordar">
                        Recordar mis datos
                    </label>
                </div>

                <button type="submit" class="btn btn-primary btn-login">
                    <i class="fas fa-sign-in-alt me-2"></i>Iniciar Sesión
                </button>
            </form>

            <!-- Enlaces adicionales -->
            <div class="text-center mt-3">
                <p class="mb-0">
                    <a href="${pageContext.request.contextPath}/" class="text-decoration-none">
                        <i class="fas fa-home me-1"></i>Volver al inicio
                    </a>
                </p>
            </div>


        </div>
    </div>

    <script>
        // Auto-rellenar credenciales demo (para desarrollo)
        document.addEventListener('DOMContentLoaded', function() {

            const form = document.getElementById('loginForm');
            form.addEventListener('submit', function(e) {
                const email = document.getElementById('email').value;
                const password = document.getElementById('contrasena').value;
                
                if (!email || !password) {
                    e.preventDefault();
                    alert('Por favor, completa todos los campos');
                    return false;
                }
                
                if (!email.includes('@')) {
                    e.preventDefault();  
                    alert('Por favor, ingresa un email válido');
                    return false;
                }
            });
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>