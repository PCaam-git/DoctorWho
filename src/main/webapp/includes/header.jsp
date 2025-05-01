<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Doctor Who Merchandising</title>
  <!-- Enlaza Bootstrap desde CDN para mayor compatibilidad -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">DoctorWho</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/articulos">Artículos</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/categorias">Categorías</a></li>
          <!-- Añade más enlaces según necesites -->
        </ul>
        <!-- Zona de login/logout -->
        <ul class="navbar-nav">
          <c:choose>
            <c:when test="${not empty sessionScope.usuario}">
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Salir</a></li>
            </c:when>
            <c:otherwise>
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Entrar</a></li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container mt-4">