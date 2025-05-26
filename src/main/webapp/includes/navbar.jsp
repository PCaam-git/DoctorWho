<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
            <i class="bi bi-house-door"></i> Inicio
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/categorias/lista">
                        <i class="bi bi-tags"></i> Categorías
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/articulos/list.jsp">
                        <i class="bi bi-box-seam"></i> Artículos
                    </a>
                </li>
                <% 
                    Boolean esAdmin = (Boolean) session.getAttribute("es_admin");
                    if (esAdmin != null && esAdmin) { 
                %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/usuarios/list.jsp">
                        <i class="bi bi-people"></i> Usuarios
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/ventas/list.jsp">
                        <i class="bi bi-cart"></i> Ventas
                    </a>
                </li>
                <% } %>
            </ul>
            <ul class="navbar-nav ms-auto">
                <% if (session.getAttribute("usuario_id") != null) { %>
                    <li class="nav-item">
                        <span class="nav-link">
                            <i class="bi bi-person-circle"></i> 
                            <%= session.getAttribute("usuario_nombre") %>
                            <% if (esAdmin != null && esAdmin) { %>
                                <span class="badge bg-danger">Admin</span>
                            <% } %>
                        </span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right"></i> Salir
                        </a>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">
                            <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                        </a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>