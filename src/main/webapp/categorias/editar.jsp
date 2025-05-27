<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <title>Editar Categoría - Doctor Who</title>
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
                    <jsp:param name="pageTitle" value="Editar Categoría" />
                </jsp:include>
                <div class="container">
                    <div class="form-container">
                        <h2 class="form-title">
                            <i class="fas fa-edit me-2"></i>Editar Categoría: ${categoria.nombre}
                        </h2>

                        <form method="post" action="${pageContext.request.contextPath}/categorias/actualizar"
                            id="editarForm">
                            <input type="hidden" name="id" value="${categoria.id}">

                            <!-- Nombre -->
                            <div class="mb-3">
                                <label for="nombre" class="form-label">
                                    <i class="fas fa-tag me-2"></i>Nombre <span class="required">*</span>
                                </label>
                                <input type="text" id="nombre" name="nombre" class="form-control"
                                    value="${categoria.nombre}" required>
                                <div class="form-text">Nombre actual: <span
                                        class="current-value">${categoria.nombre}</span></div>
                            </div>

                            <!-- Descripción -->
                            <div class="mb-3">
                                <label for="descripcion" class="form-label">
                                    <i class="fas fa-align-left me-2"></i>Descripción <span class="required">*</span>
                                </label>
                                <textarea id="descripcion" name="descripcion" class="form-control" rows="3"
                                    required>${categoria.descripcion}</textarea>
                                <div class="form-text">Descripción actual: <span
                                        class="current-value">${categoria.descripcion}</span></div>
                            </div>

                            <!-- Cantidad -->
                            <div class="mb-3">
                                <label for="cantidad" class="form-label">
                                    <i class="fas fa-hashtag me-2"></i>Cantidad <span class="required">*</span>
                                </label>
                                <input type="number" id="cantidad" name="cantidad" class="form-control" min="0"
                                    value="${categoria.cantidad}" required>
                                <div class="form-text">Cantidad actual: <span
                                        class="current-value">${categoria.cantidad}</span></div>
                            </div>

                            <!-- Tiene productos -->
                            <div class="mb-3">
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" id="tiene_productos"
                                        name="tiene_productos" value="true" ${categoria.tieneProductos ? 'checked' : ''
                                        }>
                                    <label class="form-check-label" for="tiene_productos">
                                        <i class="fas fa-check-circle me-2"></i>Tiene productos disponibles
                                    </label>
                                </div>
                                <div class="form-text">
                                    Estado actual:
                                    <span
                                        class="current-value badge ${categoria.tieneProductos ? 'bg-success' : 'bg-secondary'}">
                                        ${categoria.tieneProductos ? 'Sí' : 'No'}
                                    </span>
                                </div>
                            </div>

                            <!-- Fecha de actualización -->
                            <div class="mb-3">
                                <label for="fecha_actualizacion" class="form-label">
                                    <i class="fas fa-calendar me-2"></i>Fecha de Actualización <span
                                        class="required">*</span>
                                </label>
                                <input type="date" id="fecha_actualizacion" name="fecha_actualizacion"
                                    class="form-control"
                                    value="<fmt:formatDate value='${categoria.fechaActualizacion}' pattern='yyyy-MM-dd'/>"
                                    required>
                                <div class="form-text">
                                    Fecha actual:
                                    <span class="current-value">
                                        <fmt:formatDate value="${categoria.fechaActualizacion}" pattern="dd/MM/yyyy" />
                                    </span>
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-warning">
                                    <i class="fas fa-save me-2"></i>Guardar Cambios
                                </button>
                                <a href="${pageContext.request.contextPath}/categorias/detalle?id=${categoria.id}"
                                    class="btn btn-outline-secondary">
                                    <i class="fas fa-times me-2"></i>Cancelar
                                </a>
                            </div>
                        </form>

                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/categorias/lista" class="text-decoration-none">
                                <i class="fas fa-list me-2"></i>Volver a la lista de categorías
                            </a>
                        </div>
                    </div>
                </div>

                <script>
                    // Validación del formulario
                    document.getElementById('editarForm').addEventListener('submit', function (e) {
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

                        // Confirmación antes de guardar cambios
                        const nombreCategoria = document.getElementById('nombre').value || 'esta categoría';
                        if (!confirm('¿Confirmas que quieres guardar los cambios en ' + nombreCategoria + '?')) {
                            e.preventDefault();
                            return false;
                        }

                        return true;
                    });

                    // Resaltar campos modificados
                    const campos = ['nombre', 'descripcion', 'cantidad'];
                    campos.forEach(campo => {
                        const input = document.getElementById(campo);
                        if (input) {
                            const valorOriginal = input.value;
                            input.addEventListener('input', function () {
                                if (this.value !== valorOriginal) {
                                    this.style.borderLeft = '4px solid #f68712';
                                } else {
                                    this.style.borderLeft = '';
                                }
                            });
                        }
                    });

                    // Para el checkbox de tiene_productos
                    const checkbox = document.getElementById('tiene_productos');
                    const checkboxOriginal = checkbox.checked;
                    checkbox.addEventListener('change', function () {
                        if (this.checked !== checkboxOriginal) {
                            this.style.outline = '2px solid #f68712';
                        } else {
                            this.style.outline = '';
                        }
                    });

                    // Para el campo de fecha
                    const fechaInput = document.getElementById('fecha_actualizacion');
                    const fechaOriginal = fechaInput.value;
                    fechaInput.addEventListener('change', function () {
                        if (this.value !== fechaOriginal) {
                            this.style.borderLeft = '4px solid #f68712';
                        } else {
                            this.style.borderLeft = '';
                        }
                    });
                </script>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>