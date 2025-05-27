<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Registrar Venta - Doctor Who</title>
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
        <jsp:param name="pageTitle" value="Nueva Venta"/>
    </jsp:include>

    <div class="container">
        <div class="form-container">
            <h2 class="form-title">
                <i class="fas fa-cart-plus me-2"></i>Registrar Nueva Venta
            </h2>
            
            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/ventas/formulario" method="post" 
                  id="ventaForm">
                
                <div class="row">
                    <!-- Usuario -->
                    <div class="col-md-6 mb-3">
                        <label for="usuario_id" class="form-label">
                            <i class="fas fa-user me-2"></i>Usuario <span class="required">*</span>
                        </label>
                        <select id="usuario_id" name="usuario_id" class="form-select" required>
                            <option value="">Selecciona un usuario</option>
                            <c:forEach var="usuario" items="${usuarios}">
                                <option value="${usuario.id}">${usuario.nombre} (${usuario.email})</option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Usuario que realiza la compra</div>
                    </div>

                    <!-- Artículo -->
                    <div class="col-md-6 mb-3">
                        <label for="articulo_id" class="form-label">
                            <i class="fas fa-box me-2"></i>Artículo <span class="required">*</span>
                        </label>
                        <select id="articulo_id" name="articulo_id" class="form-select" required onchange="actualizarPrecio()">
                            <option value="">Selecciona un artículo</option>
                            <c:forEach var="articulo" items="${articulos}">
                                <option value="${articulo.id}" data-precio="${articulo.precio}">
                                    ${articulo.nombre} - ${articulo.precio}€
                                </option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Artículo a vender</div>
                    </div>
                </div>

                <div class="row">
                    <!-- Cantidad -->
                    <div class="col-md-4 mb-3">
                        <label for="cantidad" class="form-label">
                            <i class="fas fa-hashtag me-2"></i>Cantidad <span class="required">*</span>
                        </label>
                        <input type="number" id="cantidad" name="cantidad" class="form-control" 
                               min="1" value="1" required onchange="calcularTotal()">
                        <div class="form-text">Cantidad a vender</div>
                    </div>

                    <!-- Total -->
                    <div class="col-md-4 mb-3">
                        <label for="total" class="form-label">
                            <i class="fas fa-euro-sign me-2"></i>Total <span class="required">*</span>
                        </label>
                        <div class="input-group">
                            <input type="number" id="total" name="total" class="form-control" 
                                   step="0.01" min="0" placeholder="0.00" required readonly>
                            <span class="input-group-text">€</span>
                        </div>
                        <div class="form-text">Total calculado automáticamente</div>
                    </div>

                    <!-- Estado -->
                    <div class="col-md-4 mb-3">
                        <label for="estado_venta" class="form-label">
                            <i class="fas fa-info-circle me-2"></i>Estado <span class="required">*</span>
                        </label>
                        <select id="estado_venta" name="estado_venta" class="form-select" required>
                            <option value="">Selecciona estado</option>
                            <option value="Pendiente" selected>Pendiente</option>
                            <option value="Completado">Completado</option>
                            <option value="Cancelado">Cancelado</option>
                        </select>
                        <div class="form-text">Estado actual de la venta</div>
                    </div>
                </div>

                <!-- Pagado -->
                <div class="mb-3">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="pagado" 
                               name="pagado" value="true">
                        <label class="form-check-label" for="pagado">
                            <i class="fas fa-credit-card me-2"></i>Venta pagada
                        </label>
                    </div>
                    <div class="form-text">Indica si la venta ya ha sido pagada</div>
                </div>

                <!-- Botones -->
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button type="button" class="btn btn-outline-secondary me-md-2" onclick="limpiarFormulario()">
                        <i class="fas fa-eraser me-2"></i>Limpiar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Guardar Venta
                    </button>
                </div>
            </form>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/ventas/lista" class="text-decoration-none">
                    <i class="fas fa-arrow-left me-2"></i>Volver a la lista de ventas
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

        // Función para limpiar el formulario
        function limpiarFormulario() {
            if (confirm('¿Estás seguro de que quieres limpiar todos los campos?')) {
                document.getElementById('ventaForm').reset();
                document.getElementById('cantidad').value = '1';
                document.getElementById('total').value = '';
                document.getElementById('estado_venta').value = 'Pendiente';
            }
        }

        // Validación en tiempo real
        document.getElementById('ventaForm').addEventListener('submit', function(e) {
            const usuario = document.getElementById('usuario_id').value;
            const articulo = document.getElementById('articulo_id').value;
            const cantidad = document.getElementById('cantidad').value;
            const total = document.getElementById('total').value;
            const estado = document.getElementById('estado_venta').value;

            if (!usuario || !articulo || !cantidad || !total || !estado) {
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

            // Confirmación antes de enviar
            if (!confirm('¿Confirmas que quieres crear esta venta?')) {
                e.preventDefault();
                return false;
            }

            return true;
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>