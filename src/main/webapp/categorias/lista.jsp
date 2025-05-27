<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Categorías - Doctor Who</title>
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
    </style>
</head>
<body>
    <jsp:include page="../navbar.jsp">
        <jsp:param name="pageTitle" value="Gestión de Categorías"/>
    </jsp:include>

<div class="container py-5 text-center">

    <!-- Mensaje de eliminación exitosa -->
    <%
        String deletedName = (String) session.getAttribute("deletedCategoria");
        if (deletedName != null) {
    %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ✅ La categoría <strong><%= deletedName %></strong> fue eliminada exitosamente.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <%
        session.removeAttribute("deletedCategoria");
        }
    %>

    <h1 class="doctor-who-title display-3 mb-4">📂 Categorías Doctor Who</h1>

    <!-- Botón agregar solo para administradores -->
    <c:if test="${esAdmin}">
        <a href="${pageContext.request.contextPath}/categorias/formulario" class="btn btn-lg btn-primary mb-4">
            ➕ Agregar Categoría
        </a>
    </c:if>

    <!-- Formulario de filtros -->
    <form class="row g-3 justify-content-center mb-5" method="get" action="${pageContext.request.contextPath}/categorias/lista">
        <div class="col-md-4">
            <input type="text" class="form-control" name="q" placeholder="Buscar por nombre o descripción"
                   value="${q != null ? q : ''}">
        </div>
        <div class="col-md-3">
            <div class="form-check form-switch d-flex justify-content-center align-items-center h-100">
                <input class="form-check-input me-2" type="checkbox" name="con_productos" id="conProductos"
                       ${mostrarSoloConProductos ? 'checked' : ''}>
                <label class="form-check-label" for="conProductos">
                    Solo con productos
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-primary w-100">🔍 Buscar</button>
        </div>
        <c:if test="${q != null || mostrarSoloConProductos}">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/categorias/lista" class="btn btn-outline-secondary w-100">🔙 Limpiar</a>
            </div>
        </c:if>
    </form>

    <!-- Lista de categorías -->
    <c:choose>
        <c:when test="${empty categorias}">
            <div class="alert alert-warning">⚠️ No se encontraron categorías.</div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                <c:forEach var="categoria" items="${categorias}">
                    <div class="col">
                        <div class="card h-100 shadow-sm category-card">
                            <div class="card-body">
                                <h5 class="card-title text-primary">${categoria.nombre}</h5>
                                <p class="card-text">
                                    <strong>Descripción:</strong> ${categoria.descripcion}<br>
                                    <strong>Cantidad:</strong> ${categoria.cantidad}<br>
                                    <strong>Precio medio:</strong> 
                                    <c:choose>
                                        <c:when test="${categoria.precioMedio != null}">
                                            <fmt:formatNumber value="${categoria.precioMedio}" type="currency" currencySymbol="€"/>
                                        </c:when>
                                        <c:otherwise>
                                            No disponible
                                        </c:otherwise>
                                    </c:choose><br>
                                    <strong>Tiene productos:</strong> 
                                    <span class="badge ${categoria.tieneProductos ? 'bg-success' : 'bg-secondary'}">
                                        ${categoria.tieneProductos ? 'Sí' : 'No'}
                                    </span>
                                </p>
                                <div class="d-flex justify-content-center gap-2">
                                    <a href="${pageContext.request.contextPath}/categorias/detalle?id=${categoria.id}" 
                                       class="btn btn-outline-info btn-sm">Ver Detalles</a>
                                    
                                    <!-- Solo administradores pueden eliminar -->
                                    <c:if test="${esAdmin}">
                                        <form method="get" action="${pageContext.request.contextPath}/categorias/eliminar"
                                              onsubmit="return confirm('¿Estás seguro de que quieres eliminar la categoría ${categoria.nombre}?');">
                                            <input type="hidden" name="id" value="${categoria.id}">
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
                <nav aria-label="Paginación de categorías" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <!-- Botón anterior -->
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}&q=${q}&con_productos=${mostrarSoloConProductos ? 'on' : ''}">
                                    Anterior
                                </a>
                            </li>
                        </c:if>

                        <!-- Números de página -->
                        <c:forEach begin="1" end="${totalPages}" var="pageNum">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${pageNum}&q=${q}&con_productos=${mostrarSoloConProductos ? 'on' : ''}">
                                    ${pageNum}
                                </a>
                            </li>
                        </c:forEach>

                        <!-- Botón siguiente -->
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}&q=${q}&con_productos=${mostrarSoloConProductos ? 'on' : ''}">
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