<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Venta - Doctor Who</title>
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
        <jsp:param name="pageTitle" value="Editar Venta"/>
    </jsp:include>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">
            <i class="fas fa-edit me-2"></i>Editar Venta #${venta.id}
        </h2>
        
        <form method="post" action="${pageContext.request.contextPath}/ventas/actualizar" id="editarForm">
            <input type="hidden" name="id" value="${venta.id}">

            <div class="row">
                <!-- Usuario -->
                <div class="col-md-6 mb-3">
                    <label for="usuario_id" class="form-label">
                        <i class="fas fa-user me-2"></i>Usuario <span class="required">*</span>
                    </label>
                    <select id="usuario_id" name="usuario_id" class="form-select" required>
                        <option value="">Selecciona un usuario</option>
                        <c:forEach var="usuario" items="${usuarios}">
                            <option value="${usuario.id}" ${usuario.id == venta.usuarioId ? 'selected' : ''}>
                                ${usuario.nombre} (${usuario.email})
                            </option>
                        </c:forEach>
                    </select>
                    <div class="form-text">Usuario actual: <span class="current-value">ID: ${venta.usuarioId}</span></div>
                </div>

                <!-- Artículo -->
                <div class="col-md-6 mb-3">
                    <label for="articulo_id" class="form-label">
                        <i class="fas fa-box me-2"></i>Artículo <span class="required">*</span>
                    </label>
                    <select id="articulo_id" name="articulo_id" class="form-select" required onchange="actualizarPrecio()">
                        <option value="">Selecciona un artículo</option>
                        <c:forEach var="articulo" items="${articulos}">
                            <option value="${articulo.id}" data-precio="${articulo.precio}" 
                                    ${articulo.id == venta.articuloId ? 'selected' : ''}>
                                ${articulo.nombre} - ${articulo.precio}€
                            </option>
                        </c:forEach>
                    </select>
                    <div class="form-text">Artículo actual: <span class="current-value">ID: ${venta.articuloId}</span></div>
                </div>
            </div>

            <div class="row">
                <!-- Cantidad -->
                <div class="col-md-4 mb-3">
                    <label for="cantidad" class="form-label">
                        <i class="fas fa-hashtag me-2"></i>Cantidad <span class="required">*</span>
                    </label>
                    <input type="number" id="cantidad" name="cantidad" class="form-control" 
                           min="1" value="${venta.cantidad}" required onchange="calcularTotal()">
                    <div class="form-text">Cantidad actual: <span class="current-value">${venta.cantidad}</span></div>
                </div>

                <!-- Total -->
                <div class="col-md-4 mb-3">
                    <label for="total" class="form-label">
                        <i class="fas fa-euro-sign me-2"></i>Total <span class="required">*</span>
                    </label>
                    <div class="input-group">
                        <input type="number" id="total" name="total" class="form-control" 
                               step="0.01" min="0" value="${venta.total}" required>
                        <span class="input-group-text">€</span>
                    </div>
                    <div class="form-text">Total actual: <span class="current-value">
                        <fmt:formatNumber value="${venta.total}" type="currency" currencySymbol="€"/>
                    </span></div>
                </div>

                <!-- Estado -->
                <div class="col-md-4 mb-3">
                    <label for="estado_venta" class="form-label">
                        <i class="fas fa-info-circle me-2"></i>Estado <span class="required">*</span>
                    </label>
                    <select id="estado_venta" name="estado_venta" class="form-select" required>
                        <option value="">Selecciona estado</option>
                        <option value="Pendiente" ${venta.estadoVenta == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                        <option value="Completado" ${venta.estadoVenta == 'Completado' ? 'selected' : ''}>Completado</option>
                        <option value="Cancelado" ${venta.estadoVenta == 'Cancelado' ? 'selected' : ''}>Cancelado</option>
                    </select>
                    <div class="form-text">Estado actual: <span class="current-value">${venta.estadoVenta}</span></div>
                </div>
            </div>

            <!-- Fecha de venta -->
            <div class="mb-3">
                <label for="fecha_venta" class="form-label">
                    <i class="fas fa-calendar me-2"></i>Fecha de Venta <span class="required">*</span>
                </label>
                <input type="date" id="fecha_venta" name="fecha_venta" 
                       class="form-control" value="<fmt:formatDate value='${venta.fechaVenta}' pattern='yyyy-MM-dd'/>" required>
                <div class="form-text">
                    Fecha actual: 
                    <span class="current-value">
                        <fmt:formatDate value="${venta.fechaVenta}" pattern="dd/MM/yyyy"/>
                    </span>
                </div>
            </div>

            <!-- Pagado -->
            <div class="mb-3">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="pagado" 
                           name="pagado" value="true" ${venta.pagado ? 'checked' : ''}>
                    <label class="form-check-label" for="pagado">
                        <i class="fas fa-credit-card me-2"></i>Venta pagada
                    </label>
                </div>
                <div class="form-text">
                    Estado actual: 
                    <span class="current-value badge ${venta.pagado ? 'bg-success' : 'bg-danger'}">
                        ${venta.pagado ? 'Pagado' : 'Pendiente de Pago'}
                    </span>
                </div>
            </div>

            <!-- Botones -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-warning">
                    <i class="fas fa-save me-2"></i>Guardar Cambios
                </button>
                <a href="${pageContext.request.contextPath}/ventas/detalle?id=${venta.id}" 
                   class="btn btn-outline-secondary">
                    <i class="fas fa-times me-2"></i>Cancelar
                </a>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/ventas/lista" class="text-decoration-none">
                <i class="fas fa-list me-2"></i>Volver a la lista de ventas
            </a>
        </div>
    </div>
</div>

<script>
    // Función para actualizar precio cuando cambia el artículo
    function actualizarPrecio() {
        const articuloSelect = document.getElementById('articulo_id');
        const totalInput = document.getElementById('total');
        const cantidadInput = document.getElementById('cantidad');
        
        const selectedOption = articuloSelect.options[articuloSelect.selectedIndex];
        const precio = selectedOption.dataset.precio || 0;
        const cantidad = cantidadInput.value || 1;
        
        totalInput.value = (precio * cantidad).toFixed(2);
    }

    // Función para calcular total cuando cambia la cantidad
    function calcularTotal() {
        const articuloSelect = document.getElementById('articulo_id');
        const totalInput = document.getElementById('total');
        const cantidadInput = document.getElementById('cantidad');
        
        const selectedOption = articuloSelect.options[articuloSelect.selectedIndex];
        const precio = selectedOption.dataset.precio || 0;
        const cantidad = cantidadInput.value || 1;
        
        totalInput.value = (precio * cantidad).toFixed(2);
    }

    // Validación del formulario
    document.getElementById('editarForm').addEventListener('submit', function(e) {
        const usuario = document.getElementById('usuario_id').value;
        const articulo = document.getElementById('articulo_id').value;
        const cantidad = document.getElementById('cantidad').value;
        const total = document.getElementById('total').value;
        const estado = document.getElementById('estado_venta').value;
        const fecha = document.getElementById('fecha_venta').value;

        if (!usuario || !articulo || !cantidad || !total || !estado || !fecha) {
            e.preventDefault();
            alert('Por favor, completa todos los campos obligatorios marcados con *');
            return false;
        }

        if (parseInt(cantidad) <= 0) {
            e.preventDefault();
            alert('La cantidad debe ser mayor que 0');
            return false;
        }

        if (parseFloat(total) <= 0) {
            e.preventDefault();
            alert('El total debe ser mayor que 0');
            return false;
        }

        // Confirmación antes de guardar cambios
        if (!confirm('¿Confirmas que quieres guardar los cambios en la venta #${venta.id}?')) {
            e.preventDefault();
            return false;
        }

        return true;
    });

    // Resaltar campos modificados
    const campos = ['usuario_id', 'articulo_id', 'cantidad', 'total', 'estado_venta', 'fecha_venta'];
    
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

    // Para el checkbox de pagado
    const checkbox = document.getElementById('pagado');
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