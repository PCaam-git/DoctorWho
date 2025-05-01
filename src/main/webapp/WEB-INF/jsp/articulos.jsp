<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/includes/header.jsp"/>

<h1>Listado de Artículos Doctor Who</h1>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Nombre</th>
    <th>Descripción</th>
    <th>Precio (€)</th>
    <th>Disponible</th>
    <th>Fecha añadido</th>
  </tr>
  <c:forEach var="art" items="${articulos}">
    <tr>
      <td>${art.id}</td>
      <td>${art.nombre}</td>
      <td>${art.descripcion}</td>
      <td>${art.precio}</td>
      <td><c:out value="${art.disponible}"/></td>
      <td><c:out value="${art.fechaAñadido}"/></td>
    </tr>
  </c:forEach>
</table>

<jsp:include page="/includes/footer.jsp"/>
