<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.ArticuloDao" %>
<%@ page import="com.patricia.model.Articulo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="includes/header.jsp"%>
<%@include file="includes/navbar.jsp"%>

<%
    String search = request.getParameter("search");
    if (search == null)
        search = "";

    int pageNumber = 0;
    String pageNumberStr = request.getParameter("page");
    if (pageNumberStr != null) {
        pageNumber = Integer.parseInt(pageNumberStr);
    }

    Database database = new Database();
    database.connect();
    ArticuloDao articuloDao = new ArticuloDao(database.getConnection());
    int articuloCount = articuloDao.getCount(search);
    int pageCount = articuloCount / 10;
    if (articuloCount % 10 != 0) {
        pageCount += 1;
    }

    int nextPageNumber = pageNumber < pageCount - 1 ? pageNumber + 1 : pageCount - 1;
    int previousPageNumber = pageNumber > 0 ? (pageNumber - 1) : 0;
%>

<div class="album py-5 bg-body-tertiary">
    <div class="container">
        <form method="get" action="<%= request.getRequestURI() %>">
            <input type="text" name="search" id="search" class="form-control" placeholder="Buscar" value="<%= search != null ? search : "" %>">
        </form>
    </div>
    <div class="container">
        <%@include file="includes/pagination.jsp"%>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
           <%
                ArrayList<Articulo> articuloList = articuloDao.getAll(pageNumber, search);
                for (Articulo articulo : articuloList) {
                    %>
                    <div class="col">
                        <div class="card shadow-sm">
                            <img class="img-thumbnail" src="/doctorwho_images/<%= articulo.getImagen() != null ? articulo.getImagen() : "default.jpg" %>">
                            <div class="card-body">
                                <h4 class="card-text"><%= articulo.getNombre() %></h4>
                                <p class="card-text"><%= articulo.getDescripcion() %></p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a href="/doctorwho/jsp/articulo/articulo-detail.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-sm btn-outline-secondary">Ver más</a>
                                        <%
                                            if (role.equals("user")) {
                                        %>
                                        <a href="/doctorwho/add_to_cart?articulo_id=<%= articulo.getId() %>" class="btn btn-sm btn-outline-secondary">Comprar</a>
                                        <%
                                            } else if (role.equals("admin")) {
                                        %>
                                        <a href="/doctorwho/jsp/articulo/articulo-form.jsp?articulo_id=<%= articulo.getId() %>" class="btn btn-sm btn-outline-warning">Modificar</a>
                                        <a href="/doctorwho/delete_articulo?articulo_id=<%= articulo.getId() %>"
                                           onclick="return confirm('¿Estás seguro de eliminar este artículo?')"
                                           class="btn btn-sm btn-outline-danger">Eliminar</a>
                                        <%
                                            }
                                        %>
                                    </div>
                                    <small class="text-body-secondary"><%= articulo.getPrecio() %> €</small>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                }
            %>
        </div>
        <%@include file="includes/pagination.jsp"%>
    </div>
</div>

<%@include file="includes/footer.jsp"%>