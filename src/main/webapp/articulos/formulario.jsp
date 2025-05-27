<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Registrar Artículo - Doctor Who</title>
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
        <jsp:param name="pageTitle" value="Nuevo Artículo"/>
    </jsp:include>

    <div class="container">
        <div class="form-container">
            <h2 class="form-title">
                <i class="fas fa-plus-circle me-2"></i>Registrar Nuevo Artículo
            </h2>
            
            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/articulos/formulario" method="post" 
                  id="articuloForm" enctype="multipart/form-data">
                
                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombre" class="form-label">
                        <i class="fas fa-tag me-2"></i>Nombre del Artículo <span class="required">*</span>
                    </label>
                    <input type="text" id="nombre" name="nombre" class="form-control" 
                           placeholder="Ej: Destornillador Sónico 10º Doctor" required>
                    <div class="form-text">Ingresa el nombre del producto</div>
                </div>

                <!-- Descripción -->
                <div class="mb-3">
                    <label for="descripcion" class="form-label">
                        <i class="fas fa-align-left me-2"></i>Descripción <span class="required">*</span>
                    </label>
                    <textarea id="descripcion" name="descripcion" class="form-control" rows="3" 
                              placeholder="Describe el artículo..." required></textarea>
                    <div class="form-text">Proporciona una descripción detallada del producto</div>
                </div>

                <div class="row">
                    <!-- Precio -->
                    <div class="col-md-6 mb-3">
                        <label for="precio" class="form-label">
                            <i class="fas fa-euro-sign me-2"></i>Precio <span class="required">*</span>
                        </label>
                        <div class="input-group">
                            <input type="number" id="precio" name="precio" class="form-control" 
                                   step="0.01" min="0" placeholder="0.00" required>
                            <span class="input-group-text">€</span>
                        </div>
                        <div class="form-text">Precio del artículo en euros</div>
                    </div>

                    <!-- Categoría -->
                    <div class="col-md-6 mb-3">
                        <label for="categoria_id" class="form-label">
                            <i class="fas fa-folder me-2"></i>Categoría <span class="required">*</span>
                        </label>
                        <select id="categoria_id" name="categoria_id" class="form-select" required>
                            <option value="">Selecciona una categoría</option>
                            <c:forEach var="categoria" items="${categorias}">
                                <option value="${categoria.id}">${categoria.nombre}</option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Categoría a la que pertenece el artículo</div>
                    </div>
                </div>

                <!-- Disponibilidad -->
                <div class="mb-3">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="disponible" 
                               name="disponible" value="true" checked>
                        <label class="form-check-label" for="disponible">
                            <i class="fas fa-check-circle me-2"></i>Artículo disponible para la venta
                        </label>
                    </div>
                    <div class="form-text">Indica si el artículo está disponible actualmente</div>
                </div>

                <!-- Imagen -->
                <div class="mb-4">
                    <label for="imagen" class="form-label">
                        <i class="fas fa-image me-2"></i>Imagen del Artículo
                    </label>
                    <input type="file" id="imagen" name="imagen" class="form-control" 
                           accept="image/*">
                    <div class="form-text">
                        Selecciona una imagen para el artículo (JPG, PNG, GIF)<br>
                        <small class="text-muted">Tamaño máximo: 5MB. Si no seleccionas imagen, se usará una por defecto.</small>
                    </div>
                </div>

                <!-- Botones -->
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button type="button" class="btn btn-outline-secondary me-md-2" onclick="limpiarFormulario()">
                        <i class="fas fa-eraser me-2"></i>Limpiar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Guardar Artículo
                    </button>
                </div>
            </form>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/articulos/lista" class="text-decoration-none">
                    <i class="fas fa-arrow-left me-2"></i>Volver a la lista de artículos
                </a>
            </div>
        </div>
    </div>

    <script>
        // Función para limpiar el formulario
        function limpiarFormulario() {
            if (confirm('¿Estás seguro de que quieres limpiar todos los campos?')) {
                document.getElementById('articuloForm').reset();
                document.getElementById('disponible').checked = true;
            }
        }

        // Validación en tiempo real
        document.getElementById('articuloForm').addEventListener('submit', function(e) {
            const nombre = document.getElementById('nombre').value.trim();
            const descripcion = document.getElementById('descripcion').value.trim();
            const precio = document.getElementById('precio').value;
            const categoria = document.getElementById('categoria_id').value;

            if (!nombre || !descripcion || !precio || !categoria) {
                e.preventDefault();
                alert('Por favor, completa todos los campos obligatorios marcados con *');
                return false;
            }

            if (parseFloat(precio) <= 0) {
                e.preventDefault();
                alert('El precio debe ser mayor que 0');
                return false;
            }

            // Confirmación antes de enviar
            if (!confirm('¿Confirmas que quieres crear este artículo?')) {
                e.preventDefault();
                return false;
            }

            return true;
        });

        // Preview de imagen
        document.getElementById('imagen').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    // Aquí podrías mostrar una preview de la imagen
                    console.log('Imagen seleccionada:', file.name);
                };
                reader.readAsDataURL(file);
            }
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>