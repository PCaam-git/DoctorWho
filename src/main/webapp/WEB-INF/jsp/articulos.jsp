<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!-- Incluimos cabecera -->
<jsp:include page="/includes/header.jsp"/>

<h1>Listado de Artículos</h1>

<table class="table table-striped">
  <thead>
    <tr>
      <th>ID</th>
      <th>Nombre</th>
      <th>Descripción</th>
      <th>Precio (€)</th>
      <th>Disponible</th>
      <th>Fecha añadido</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="art" items="${articulos}">
      <tr>
        <td><c:out value="${art.id}"/></td>
        <td><c:out value="${art.nombre}"/></td>
        <td><c:out value="${art.descripcion}"/></td>
        <td><fmt:formatNumber value="${art.precio}" type="currency" currencySymbol="€"/></td>
        <td><c:choose>
              <c:when test="${art.disponible}">Sí</c:when>
              <c:otherwise>No</c:otherwise>
            </c:choose>
        </td>
        <td>${art.fechaAñadido}</td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<!-- Incluimos pie de página -->
<jsp:include page="/includes/footer.jsp"/>