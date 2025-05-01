<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!-- Incluimos cabecera -->
<jsp:include page="/includes/header.jsp"/>

<div class="container">
  <h1>Detalles del Artículo</h1>
  
  <div class="card">
    <div class="card-header bg-primary text-white">
      <h2>${articulo.nombre}</h2>
    </div>
    <div class="card-body">
      <p><strong>ID:</strong> ${articulo.id}</p>
      <p><strong>Descripción:</strong> ${articulo.descripcion}</p>
      <p><strong>Precio:</strong> <fmt:formatNumber value="${articulo.precio}" type="currency" currencySymbol="€"/></p>
      <p><strong>Disponible:</strong> 
        <c:choose>
          <c:when test="${articulo.disponible}">Sí</c:when>
          <c:otherwise>No</c:otherwise>
        </c:choose>
      </p>
      <p><strong>Fecha añadido:</strong> ${articulo.fechaAñadido}</p>
    </div>
    <div class="card-footer">
      <a href="${pageContext.request.contextPath}/articulo/editar?id=${articulo.id}" class="btn btn-warning">Editar</a>
      <a href="${pageContext.request.contextPath}/articulo/eliminar?id=${articulo.id}" class="btn btn-danger" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
      <a href="${pageContext.request.contextPath}/articulos" class="btn btn-secondary">Volver</a>
    </div>
  </div>
</div>

<!-- Incluimos pie de página -->
<jsp:include page="/includes/footer.jsp"/>