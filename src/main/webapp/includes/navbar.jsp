<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/doctorwho">Doctor Who Merchandising</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/doctorwho/jsp/articulo/articulos.jsp">Artículos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/doctorwho/jsp/categorias/categorias.jsp">Categorías</a>
                </li>
                <% if (logged) { %>
                <li class="nav-item">
                    <a class="nav-link" href="/doctorwho/jsp/ventas/ventas.jsp">Mis Compras</a>
                </li>
                <% } %>
                <% if (role.equals("admin")) { %>
                <li class="nav-item">
                    <a class="nav-link" href="/doctorwho/jsp/usuarios/usuarios.jsp">Usuarios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/doctorwho/jsp/ventas/todas-ventas.jsp">Todas las Ventas</a>
                </li>
                <% } %>
            </ul>
            <div class="d-flex">
                <% if (!logged) { %>
                <a class="btn btn-outline-light me-2" href="/doctorwho/login.jsp">Iniciar Sesión</a>
                <% } else { %>
                <span class="navbar-text me-3">
                    Hola, <%= currentSession.getAttribute("username") %>
                </span>
                <a class="btn btn-outline-light" href="/doctorwho/logout">Cerrar Sesión</a>
                <% } %>
            </div>
        </div>
    </div>
</nav>