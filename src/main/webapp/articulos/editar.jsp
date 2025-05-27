<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Artículo - Doctor Who</title>
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
        <jsp:param name="pageTitle" value="Editar Artículo"/>
    </jsp:include>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">
            <i class="fas fa-edit me-2"></i>Editar Artículo: ${articulo.nombre}
        </h2>
        
        <form method="post" action="${pageContext.request.contextPath}/articulos/actualizar" id="editarForm" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${articulo.id}">

            <!-- Nombre -->
            <div class="mb-3">
                <label for="nombre" class="form-label">
                    <i class="fas fa-tag me-2"></i>Nombre <span class="required">*</span>
                </label>
                <input type="text" id="nombre" name="nombre" class="form-control" 
                       value="${articulo.nombre}" required>
                <div class="form-text">Nombre actual: <span class="current-value">${articulo.nombre}</span></div>
            </div>

            <!-- Descripción -->
            <div class="mb-3">
                <label for="descripcion" class="form-label">
                    <i class="fas fa-align-left me-2"></i>Descripción <span class="required">*</span>
                </label>
                <textarea id="descripcion" name="descripcion" class="form-control" rows="3" required>${articulo.descripcion}</textarea>
                <div class="form-text">Descripción actual: <span class="current-value">${articulo.descripcion}</span></div>
            </div>

            <!-- Precio -->
            <div class="mb-3">
                <label for="precio" class="form-label">
                    <i class="fas fa-euro-sign me-2"></i>Precio <span class="required">*</span>
                </label>
                <input type="number" id="precio" name="precio" class="form-control" 
                       min="0" step="0.01" value="${articulo.precio}" required>
                <div class="form-text">Precio actual: <span class="current-value">${articulo.precio}€</span></div>
            </div>

            <!-- Disponible -->
            <div class="mb-3">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="disponible" 
                           name="disponible" value="true" ${articulo.disponible ? 'checked' : ''}>
                    <label class="form-check-label" for="disponible">
                        <i class="fas fa-check-circle me-2"></i>Artículo disponible
                    </label>
                </div>
                <div class="form-text">
                    Estado actual: 
                    <span class="current-value badge ${articulo.disponible ? 'bg-success' : 'bg-secondary'}">
                        ${articulo.disponible ? 'Disponible' : 'No disponible'}
                    </span>
                </div>
            </div>

            <!-- Fecha de añadido -->
            <div class="mb-3">
                <label for="fechaAnadido" class="form-label">
                    <i class="fas fa-calendar me-2"></i>Fecha de Añadido <span class="required">*</span>
                </label>
                <input type="date" id="fechaAnadido" name="fechaAnadido" 
                       class="form-control" value="<fmt:formatDate value='${articulo.fechaAnadido}' pattern='yyyy-MM-dd'/>" required>
                <div class="form-text">
                    Fecha actual: 
                    <span class="current-value">
                        <fmt:formatDate value="${articulo.fechaAnadido}" pattern="dd/MM/yyyy"/>
                    </span>
                </div>
            </div>

            <!-- Categoría -->
            <div class="mb-3">
                <label for="categoria_id" class="form-label">
                    <i class="fas fa-folder me-2"></i>Categoría <span class="required">*</span>
                </label>
                <select id="categoria_id" name="categoria_id" class="form-control" required>
                    <option value="">Seleccione una categoría</option>
                    <c:forEach var="categoria" items="${categorias}">
                        <option value="${categoria.id}" ${categoria.id == articulo.categoriaId ? 'selected' : ''}>
                            ${categoria.nombre}
                        </option>
                    </c:forEach>
                </select>
                <div class="form-text">Categoría actual: <span class="current-value">${articulo.categoriaNombre}</span></div>
            </div>

            <!-- Imagen -->
            <div class="mb-3">
                <label for="imagen" class="form-label">
                    <i class="fas fa-image me-2"></i>Imagen
                </label>
                
                <!-- Mostrar imagen actual si existe -->
                <c:if test="${articulo.imagen != null && !articulo.imagen.isEmpty() && articulo.imagen != 'default.jpg'}">
                    <div class="mb-2">
                        <img src="${pageContext.request.contextPath}/images/articulos/${articulo.imagen}" 
                             alt="Imagen actual" class="img-thumbnail" style="max-width: 150px; max-height: 150px;">
                        <div class="form-text">Imagen actual: <span class="current-value">${articulo.imagen}</span></div>
                    </div>
                </c:if>
                
                <!-- Campo para subir nueva imagen -->
                <input type="file" id="imagen" name="imagen" class="form-control" 
                       accept="image/*">
                <div class="form-text">
                    Seleccione una nueva imagen para reemplazar la actual (opcional). 
                    Formatos permitidos: JPG, PNG, GIF. Tamaño máximo: 5MB.
                </div>
                
                <!-- Campo oculto para mantener imagen actual si no se sube nueva -->
                <input type="hidden" name="imagen_actual" value="${articulo.imagen}">
            </div>

            <!-- Botones -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-warning">
                    <i class="fas fa-save me-2"></i>Guardar Cambios
                </button>
                <a href="${pageContext.request.contextPath}/articulos/detalle?id=${articulo.id}" 
                   class="btn btn-outline-secondary">
                    <i class="fas fa-times me-2"></i>Cancelar
                </a>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/articulos/lista" class="text-decoration-none">
                <i class="fas fa-list me-2"></i>Volver a la lista de artículos
            </a>
        </div>
    </div>
</div>

<script>
    // Validación del formulario
    document.getElementById('editarForm').addEventListener('submit', function(e) {
        const nombre = document.getElementById('nombre').value.trim();
        const descripcion = document.getElementById('descripcion').value.trim();
        const precio = document.getElementById('precio').value;
        const fecha = document.getElementById('fechaAnadido').value;
        const categoria = document.getElementById('categoria_id').value;

        if (!nombre || !descripcion || !precio || !fecha || !categoria) {
            e.preventDefault();
            alert('Por favor, completa todos los campos obligatorios marcados con *');
            return false;
        }

        if (parseFloat(precio) < 0) {
            e.preventDefault();
            alert('El precio no puede ser negativo');
            return false;
        }

        // Confirmación antes de guardar cambios
        const nombreArticulo = document.getElementById('nombre').value || 'este artículo';
        if (!confirm('¿Confirmas que quieres guardar los cambios en ' + nombreArticulo + '?')) {
            e.preventDefault();
            return false;
        }

        return true;
    });

    // Resaltar campos modificados
    const campos = ['nombre', 'descripcion', 'precio', 'fechaAnadido', 'categoria_id'];
    
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

    // Para el campo de imagen (archivo)
    const imagenInput = document.getElementById('imagen');
    if (imagenInput) {
        imagenInput.addEventListener('change', function() {
            if (this.files.length > 0) {
                this.style.borderLeft = '4px solid #f68712';
                
                // Validar tamaño del archivo
                const file = this.files[0];
                if (file.size > 5 * 1024 * 1024) { // 5MB
                    alert('El archivo es demasiado grande. El tamaño máximo es 5MB.');
                    this.value = '';
                    this.style.borderLeft = '';
                    return;
                }
                
                // Validar tipo de archivo
                if (!file.type.startsWith('image/')) {
                    alert('Solo se permiten archivos de imagen.');
                    this.value = '';
                    this.style.borderLeft = '';
                    return;
                }
            } else {
                this.style.borderLeft = '';
            }
        });
    }

    // Para el checkbox de disponible
    const checkbox = document.getElementById('disponible');
    const checkboxOriginal = checkbox.checked;
    checkbox.addEventListener('change', function() {
        if (this.checked !== checkboxOriginal) {
            this.style.outline = '2px solid #f68712';
        } else {
            this.style.outline = '';
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>