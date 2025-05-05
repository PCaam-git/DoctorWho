<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.ArticuloDao" %>
<%@ page import="com.patricia.dao.CategoriaDao" %>
<%@ page import="com.patricia.model.Articulo" %>
<%@ page import="com.patricia.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="includes/header.jsp"%>
<%@include file="includes/navbar.jsp"%>

<%
    // Obtener artículos destacados para mostrar en la página principal
    Database database = new Database();
    database.connect();
    
    ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
    CategoriaDao categoriaDao = new CategoriaDao(database.getConnection());
    
    // Obtener algunos artículos para mostrar (limitamos a 4)
    ArrayList<Articulo> articulosDestacados = articuloDao.getAll(0);
    if (articulosDestacados.size() > 4) {
        articulosDestacados = new ArrayList<>(articulosDestacados.subList(0, 4));
    }
    
    // Obtener categorías
    List<Categoria> categorias = categoriaDao.getAll();
    
    database.close();
%>

<!-- Banner Hero -->
<div class="hero-banner">
    <div class="container">
        <h1>Doctor Who Merchandising</h1>
        <p>Explora nuestra colección de productos oficiales de Doctor Who</p>
        <a href="/doctorwho/jsp/articulo/articulos.jsp" class="btn btn-light btn-lg">Ver Productos</a>
    </div>
</div>

<!-- Productos Destacados -->
<div class="container">
    <h2 class="section-title">Productos Destacados</h2>
    
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-5">
        <% for (Articulo articulo : articulosDestacados) { %>
        <div class="col">
            <div class="card product-card h-100">
                <div class="product-image-container">
                    <% if (articulo.getImagen() != null && !articulo.getImagen().isEmpty() && !articulo.getImagen().equals("default.jpg")) { %>
                        <img src="${pageContext.request.contextPath}/images/articulos/<%= articulo.getImagen() %>" class="product-image" alt="<%= articulo.getNombre() %>">
                    <% } else { %>
                        <img src="${pageContext.request.contextPath}/images/default.jpg" class="product-image" alt="Imagen por defecto">
                    <% } %>
                    
                    <% if (!articulo.isDisponible()) { %>
                        <div class="unavailable-badge">Agotado</div>
                    <% } %>
                </div>
                <div class="card-body">
                    <h5 class="card-title"><%= articulo.getNombre() %></h5>
                    <p class="description"><%= articulo.getDescripcion() %></p>
                    <p class="price"><%= articulo.getPrecio() %> €</p>
                    <a href="/doctorwho/jsp/articulo/articulo-detail.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-primary">Ver detalles</a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    
    <!-- Categorías -->
    <h2 class="section-title">Explora por Categorías</h2>
    
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-5">
        <% for (Categoria categoria : categorias) { %>
        <div class="col">
            <a href="/doctorwho/jsp/categorias/categoria-detail.jsp?categoria_id=<%= categoria.getId() %>" class="text-decoration-none">
                <div class="category-card">
                    <div class="category-icon">
                        <% if (categoria.getNombre().equalsIgnoreCase("Figuras")) { %>
                            <i class="bi bi-person-standing"></i>
                        <% } else if (categoria.getNombre().equalsIgnoreCase("Ropa")) { %>
                            <i class="bi bi-bag"></i>
                        <% } else if (categoria.getNombre().equalsIgnoreCase("Accesorios")) { %>
                            <i class="bi bi-watch"></i>
                        <% } else if (categoria.getNombre().equalsIgnoreCase("Libros")) { %>
                            <i class="bi bi-book"></i>
                        <% } else if (categoria.getNombre().equalsIgnoreCase("Gadgets")) { %>
                            <i class="bi bi-tools"></i>
                        <% } else { %>
                            <i class="bi bi-box"></i>
                        <% } %>
                    </div>
                    <h3 class="category-title"><%= categoria.getNombre() %></h3>
                    <p><%= categoria.getDescripcion() %></p>
                </div>
            </a>
        </div>
        <% } %>
    </div>
    
    <!-- Acerca de Doctor Who -->
    <div class="about-section">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <h2>Acerca de Doctor Who</h2>
                    <p>Doctor Who es una serie de televisión británica de ciencia ficción producida por la BBC desde 1963. La serie narra las aventuras de un Señor del Tiempo llamado simplemente "El Doctor", que explora el universo en su TARDIS, una nave espacial con conciencia propia capaz de viajar a través del tiempo y el espacio.</p>
                    <p>Nuestra tienda ofrece productos oficiales para que los fans puedan llevar consigo un poco de las aventuras del Doctor.</p>
                    <a href="/doctorwho/jsp/articulo/articulos.jsp" class="btn btn-outline-light mt-3">Explorar Productos</a>
                </div>
                <div class="col-md-4">
                    <div class="tardis-animation">
                        <div class="tardis">
                            <div class="tardis-light"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp"%>