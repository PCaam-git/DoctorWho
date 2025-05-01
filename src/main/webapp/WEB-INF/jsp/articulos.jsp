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
      <th>Acciones</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="art" items="${articulos}">
      <tr>
        <td><c:out value="${art.id}"/></td>
        <td><a href="${pageContext.request.contextPath}/articulo/detalle?id=${art.id}"><c:out value="${art.nombre}"/></a></td>
        <td><c:out value="${art.descripcion}"/></td>
        <td><fmt:formatNumber value="${art.precio}" type="currency" currencySymbol="€"/></td>
        <td><c:choose>
              <c:when test="${art.disponible}">Sí</c:when>
              <c:otherwise>No</c:otherwise>
            </c:choose>
        </td>
        <td>${art.fechaAñadido}</td>
        <td>
          <a href="${pageContext.request.contextPath}/articulo/detalle?id=${art.id}" class="btn btn-info btn-sm">Ver</a>
          <a href="${pageContext.request.contextPath}/articulo/editar?id=${art.id}" class="btn btn-warning btn-sm">Editar</a>
          <a href="${pageContext.request.contextPath}/articulo/eliminar?id=${art.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<a href="${pageContext.request.contextPath}/articulo/crear" class="btn btn-primary">Nuevo Artículo</a>

<!-- Incluimos pie de página -->
<jsp:include page="/includes/footer.jsp"/>