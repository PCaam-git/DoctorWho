<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Artículos - Doctor Who</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <style>
        body {
            background-color: #f5f5f5;
            color: #333;
        }
        .card {
            text-align: center;
        }
        .form-label {
            color: #333;
        }
        .doctor-who-title {
            color: var(--color-primary);
            font-weight: 700;
        }
        .product-image {
            height: 200px;
            object-fit: cover;
            width: 100%;
        }
    </style>
</head>
<body>
    <!-- navbar -->
    <jsp:include page="/navbar.jsp">
        <jsp:param name="pageTitle" value="Gestión de Artículos"/>
    </jsp:include>

<div class="container py-5 text-center">

    <!-- Mensaje de eliminación exitosa -->
    <%
        String deletedName = (String) session.getAttribute("deletedArticulo");
        if (deletedName != null) {
    %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ✅ El artículo <strong><%= deletedName %></strong> fue eliminado exitosamente.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <%
        session.removeAttribute("deletedArticulo");
        }
    %>

    <h1 class="doctor-who-title display-3 mb-4">📦 Artículos Doctor Who</h1>

    <!-- Botón agregar solo para administradores -->
    <c:if test="${esAdmin}">
        <a href="${pageContext.request.contextPath}/articulos/formulario" class="btn btn-lg btn-primary mb-4">
            ➕ Agregar Artículo
        </a>
    </c:if>

    <!-- Formulario de filtros -->
    <form class="row g-3 justify-content-center mb-5" method="get" action="${pageContext.request.contextPath}/articulos/lista">
        <div class="col-md-3">
            <input type="text" class="form-control" name="q" placeholder="Buscar por nombre o descripción"
                   value="${busqueda != null ? busqueda : ''}">
        </div>
        <div class="col-md-3">
            <select class="form-select" name="categoria">
                <option value="">Todas las categorías</option>
                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat.id}" ${categoriaSeleccionada == cat.id ? 'selected' : ''}>
                        ${cat.nombre}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-select" name="disponibilidad">
                <option value="">Todo</option>
                <option value="true" ${disponibilidadSeleccionada == 'true' ? 'selected' : ''}>Disponible</option>
                <option value="false" ${disponibilidadSeleccionada == 'false' ? 'selected' : ''}>No disponible</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-primary w-100">🔍 Buscar</button>
        </div>
        <c:if test="${busqueda != null || categoriaSeleccionada != null || disponibilidadSeleccionada != null}">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/articulos/lista" class="btn btn-outline-secondary w-100">🔙 Limpiar</a>
            </div>
        </c:if>
    </form>

    <!-- Lista de artículos -->
    <c:choose>
        <c:when test="${empty articulos}">
            <div class="alert alert-warning">⚠️ No se encontraron artículos.</div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                <c:forEach var="articulo" items="${articulos}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <!-- Imagen -->
                            <c:choose>
                                <c:when test="${not empty articulo.imagen and articulo.imagen != 'default.jpg'}">
                                    <img src="${pageContext.request.contextPath}/images/articulos/${articulo.imagen}" 
                                         class="card-img-top product-image" 
                                         alt="${articulo.nombre}"
                                         onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'">
                                </c:when>
                                <c:otherwise>
                                    <div class="product-image bg-light d-flex align-items-center justify-content-center">
                                        <i class="fas fa-image fa-3x text-muted"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="card-body">
                                <h5 class="card-title text-primary">${articulo.nombre}</h5>
                                <p class="card-text">
                                    <strong>Descripción:</strong> ${articulo.descripcion}<br>
                                    <strong>Precio:</strong> 
                                    <fmt:formatNumber value="${articulo.precio}" type="currency" currencySymbol="€"/><br>
                                    <strong>Categoría:</strong> ${articulo.categoriaNombre}<br>
                                    <strong>Fecha:</strong> 
                                    <fmt:formatDate value="${articulo.fechaAnadido}" pattern="dd/MM/yyyy"/><br>
                                    <strong>Estado:</strong> 
                                    <span class="badge ${articulo.disponible ? 'bg-success' : 'bg-secondary'}">
                                        ${articulo.disponible ? 'Disponible' : 'No disponible'}
                                    </span>
                                </p>
                                <div class="d-flex justify-content-center gap-2">
                                    <a href="${pageContext.request.contextPath}/articulos/detalle?id=${articulo.id}" 
                                       class="btn btn-outline-info btn-sm">Ver Detalles</a>
                                    
                                    <!-- Solo administradores pueden eliminar -->
                                    <c:if test="${esAdmin}">
                                        <form method="get" action="${pageContext.request.contextPath}/articulos/eliminar"
                                              onsubmit="return confirm('¿Estás seguro de que quieres eliminar este artículo?');">
                                            <input type="hidden" name="id" value="${articulo.id}">
                                            <button type="submit" class="btn btn-outline-danger btn-sm">Eliminar</button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Paginación -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Paginación de artículos" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <!-- Botón anterior -->
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}&q=${busqueda}&categoria=${categoriaSeleccionada}&disponibilidad=${disponibilidadSeleccionada}">
                                    Anterior
                                </a>
                            </li>
                        </c:if>

                        <!-- Números de página -->
                        <c:forEach begin="1" end="${totalPages}" var="pageNum">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${pageNum}&q=${busqueda}&categoria=${categoriaSeleccionada}&disponibilidad=${disponibilidadSeleccionada}">
                                    ${pageNum}
                                </a>
                            </li>
                        </c:forEach>

                        <!-- Botón siguiente -->
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}&q=${busqueda}&categoria=${categoriaSeleccionada}&disponibilidad=${disponibilidadSeleccionada}">
                                    Siguiente
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </c:otherwise>
    </c:choose>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>