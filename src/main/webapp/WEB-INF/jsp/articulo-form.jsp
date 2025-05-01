<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Incluimos cabecera -->
<jsp:include page="/includes/header.jsp"/>

<div class="container">
  <h1>${articulo == null ? 'Nuevo Artículo' : 'Editar Artículo'}</h1>
  
  <c:if test="${error != null}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  
  <form action="${articulo == null ? pageContext.request.contextPath.concat('/articulo/crear') : pageContext.request.contextPath.concat('/articulo/editar')}" method="post">
    <c:if test="${articulo != null}">
      <input type="hidden" name="id" value="${articulo.id}">
    </c:if>
    
    <div class="form-group mb-3">
      <label for="nombre">Nombre:</label>
      <input type="text" class="form-control" id="nombre" name="nombre" value="${articulo != null ? articulo.nombre : ''}" required>
    </div>
    
    <div class="form-group mb-3">
      <label for="descripcion">Descripción:</label>
      <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${articulo != null ? articulo.descripcion : ''}</textarea>
    </div>
    
    <div class="form-group mb-3">
      <label for="precio">Precio (€):</label>
      <input type="number" class="form-control" id="precio" name="precio" step="0.01" value="${articulo != null ? articulo.precio : '0.00'}" required>
    </div>
    
    <div class="form-check mb-3">
      <input type="checkbox" class="form-check-input" id="disponible" name="disponible" ${articulo != null && articulo.disponible ? 'checked' : ''}>
      <label class="form-check-label" for="disponible">Disponible</label>
    </div>
    
    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="${pageContext.request.contextPath}/articulos" class="btn btn-secondary">Cancelar</a>
  </form>
</div>

<!-- Incluimos pie de página -->
<jsp:include page="/includes/footer.jsp"/>