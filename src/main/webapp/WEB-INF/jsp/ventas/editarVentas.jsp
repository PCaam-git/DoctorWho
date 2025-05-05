<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${accion} Venta - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>${accion} Venta</h2>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/edit_venta" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="idComprador" class="form-label">Comprador:</label>
                        <select class="form-select" id="idComprador" name="idComprador" required>
                            <option value="">Seleccione un comprador</option>
                            <c:forEach items="${usuarios}" var="usuario">
                                <option value="${usuario.id}" ${venta.idComprador == usuario.id ? 'selected' : ''}>${usuario.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="idArticulo" class="form-label">Artículo:</label>
                        <select class="form-select" id="idArticulo" name="idArticulo" required>
                            <option value="">Seleccione un artículo</option>
                            <c:forEach items="${articulos}" var="articulo">
                                <option value="${articulo.id}" ${venta.idArticulo == articulo.id ? 'selected' : ''}>${articulo.nombre} - €${articulo.precio}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="precio" class="form-label">Precio (€):</label>
                        <input type="number" step="0.01" class="form-control" id="precio" name="precio" value="${venta.precio}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="estadoVenta" class="form-label">Estado Venta:</label>
                        <select class="form-select" id="estadoVenta" name="estadoVenta" required>
                            <option value="Pendiente" ${venta.estadoVenta == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                            <option value="Completada" ${venta.estadoVenta == 'Completada' ? 'selected' : ''}>Completada</option>
                            <option value="Cancelada" ${venta.estadoVenta == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                        </select>
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="pagado" name="pagado" value="true" ${venta.pagado ? 'checked' : ''}>
                        <label class="form-check-label" for="pagado">Pagado</label>
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="activo" name="activo" value="true" ${venta.activo ? 'checked' : ''}>
                        <label class="form-check-label" for="activo">Activo</label>
                    </div>
                    
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Comprobante de pago:</label>
                        <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                        <small class="text-muted">Formatos aceptados: JPG, PNG, GIF</small>
                    </div>
                    
                    <c:if test="${venta.imagen != null && !venta.imagen.isEmpty() && venta.imagen != 'default.jpg'}">
                        <div class="mb-3">
                            <label class="form-label">Comprobante actual:</label>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/ventas/${venta.imagen}" alt="Comprobante" class="img-thumbnail" style="max-width: 200px;">
                            </div>
                        </div>
                    </c:if>
                    
                    <input type="hidden" name="accion" value="${accion}">
                    <c:if test="${accion == 'Modificar'}">
                        <input type="hidden" name="ventaId" value="${venta.idTransaccion}">
                    </c:if>
                    
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                        <a href="${pageContext.request.contextPath}/ventas" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Confirmación antes de enviar el formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            if (!confirm('¿Está seguro de guardar los cambios?')) {
                e.preventDefault();
            }
        });
        
        // Actualizar precio basado en artículo seleccionado
        document.getElementById('idArticulo').addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            if (selectedOption.value) {
                const precioText = selectedOption.text.split(' - €')[1];
                if (precioText) {
                    document.getElementById('precio').value = precioText;
                }
            }
        });
    </script>
</body>
</html>