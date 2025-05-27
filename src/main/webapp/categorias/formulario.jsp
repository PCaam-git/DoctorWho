<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Registrar Categoría - Doctor Who</title>
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
    <jsp:include page="../navbar.jsp">
        <jsp:param name="pageTitle" value="Nueva Categoría"/>
    </jsp:include>
    <div class="container">
        <div class="form-container">
            <h2 class="form-title">
                <i class="fas fa-plus-circle me-2"></i>Registrar Nueva Categoría
            </h2>
            
            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/categorias/formulario" method="post" id="categoriaForm">
                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombre" class="form-label">
                        <i class="fas fa-tag me-2"></i>Nombre <span class="required">*</span>
                    </label>
                    <input type="text" id="nombre" name="nombre" class="form-control" 
                           placeholder="Ej: Sonic Screwdrivers" required>
                    <div class="form-text">Ingresa el nombre de la categoría</div>
                </div>

                <!-- Descripción -->
                <div class="mb-3">
                    <label for="descripcion" class="form-label">
                        <i class="fas fa-align-left me-2"></i>Descripción <span class="required">*</span>
                    </label>
                    <textarea id="descripcion" name="descripcion" class="form-control" rows="3" 
                              placeholder="Describe la categoría..." required></textarea>
                    <div class="form-text">Proporciona una descripción detallada</div>
                </div>

                <!-- Cantidad -->
                <div class="mb-3">
                    <label for="cantidad" class="form-label">
                        <i class="fas fa-hashtag me-2"></i>Cantidad <span class="required">*</span>
                    </label>
                    <input type="number" id="cantidad" name="cantidad" class="form-control" 
                           min="0" value="0" required>
                    <div class="form-text">Número de productos en esta categoría</div>
                </div>

                <!-- Tiene productos -->
                <div class="mb-3">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="tiene_productos" 
                               name="tiene_productos" value="true" checked>
                        <label class="form-check-label" for="tiene_productos">
                            <i class="fas fa-check-circle me-2"></i>Tiene productos disponibles
                        </label>
                    </div>
                    <div class="form-text">Indica si esta categoría tiene productos disponibles</div>
                </div>

                <!-- Fecha de actualización -->
                <div class="mb-3">
                    <label for="fecha_actualizacion" class="form-label">
                        <i class="fas fa-calendar me-2"></i>Fecha de Actualización <span class="required">*</span>
                    </label>
                    <input type="date" id="fecha_actualizacion" name="fecha_actualizacion" 
                           class="form-control" required>
                    <div class="form-text">Fecha de la última actualización</div>
                </div>

                <!-- Botones -->
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button type="button" class="btn btn-outline-secondary me-md-2" onclick="limpiarFormulario()">
                        <i class="fas fa-eraser me-2"></i>Limpiar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Guardar Categoría
                    </button>
                </div>
            </form>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/categorias/lista" class="text-decoration-none">
                    <i class="fas fa-arrow-left me-2"></i>Volver a la lista de categorías
                </a>
            </div>
        </div>
    </div>

    <script>
        // Establecer fecha actual como valor por defecto
        document.addEventListener('DOMContentLoaded', function() {
            const fechaInput = document.getElementById('fecha_actualizacion');
            const today = new Date().toISOString().split('T')[0];
            fechaInput.value = today;
        });

        // Función para limpiar el formulario
        function limpiarFormulario() {
            if (confirm('¿Estás seguro de que quieres limpiar todos los campos?')) {
                document.getElementById('categoriaForm').reset();
                // Restablecer la fecha actual
                const fechaInput = document.getElementById('fecha_actualizacion');
                const today = new Date().toISOString().split('T')[0];
                fechaInput.value = today;
                // Marcar el checkbox como seleccionado por defecto
                document.getElementById('tiene_productos').checked = true;
            }
        }

        // Validación en tiempo real
        document.getElementById('categoriaForm').addEventListener('submit', function(e) {
            const nombre = document.getElementById('nombre').value.trim();
            const descripcion = document.getElementById('descripcion').value.trim();
            const cantidad = document.getElementById('cantidad').value;
            const fecha = document.getElementById('fecha_actualizacion').value;

            if (!nombre || !descripcion || !cantidad || !fecha) {
                e.preventDefault();
                alert('Por favor, completa todos los campos obligatorios marcados con *');
                return false;
            }

            if (parseInt(cantidad) < 0) {
                e.preventDefault();
                alert('La cantidad no puede ser negativa');
                return false;
            }

            // Confirmación antes de enviar
            if (!confirm('¿Confirmas que quieres crear esta categoría?')) {
                e.preventDefault();
                return false;
            }

            return true;
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>