<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.patricia.database.Database" %>
<%@ page import="com.patricia.dao.VentaDao" %>
<%@ page import="com.patricia.model.Venta" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

<%
    // Verificar permisos de administrador
    if (!role.equals("admin")) {
        response.sendRedirect("/doctorwho/");
        return;
    }

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
    VentaDao ventaDao = new VentaDao(database.getConnection());
    int ventaCount = ventaDao.getCount(search);
    int pageCount = ventaCount / 10;
    if (ventaCount % 10 != 0) {
        pageCount += 1;
    }

    int nextPageNumber = pageNumber < pageCount - 1 ? pageNumber + 1 : pageCount - 1;
    int previousPageNumber = pageNumber > 0 ? (pageNumber - 1) : 0;
%>

<div class="container py-4">
    <h1>Todas las Ventas</h1>
    
    <div class="mb-3">
        <form method="get" action="<%= request.getRequestURI() %>">
            <input type="text" name="search" id="search" class="form-control" placeholder="Buscar" value="<%= search != null ? search : "" %>">
        </form>
    </div>

    <div class="mb-3">
        <a href="/doctorwho/jsp/ventas/venta-form.jsp" class="btn btn-primary">Nueva Venta</a>
    </div>

    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Comprobante</th>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Artículo</th>
                    <th>Precio (€)</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th>Pagado</th>
                    <th>Activo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Venta> ventaList = ventaDao.getAll(pageNumber, search);
                    for (Venta venta : ventaList) {
                %>
                <tr>
                    <td>
                        <% if (venta.getImagen() != null && !venta.getImagen().isEmpty() && !venta.getImagen().equals("default.jpg")) { %>
                            <img src="${pageContext.request.contextPath}/images/ventas/<%= venta.getImagen() %>" alt="Comprobante" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } else { %>
                            <img src="${pageContext.request.contextPath}/images/default.jpg" alt="Sin comprobante" style="width: 50px; height: 50px; object-fit: cover;">
                        <% } %>
                    </td>
                    <td><%= venta.getIdTransaccion() %></td>
                    <td><%= venta.getNombreComprador() %></td>
                    <td><%= venta.getNombreArticulo() %></td>
                    <td><%= venta.getPrecio() %> €</td>
                    <td><%= venta.getFechaTransaccion() %></td>
                    <td><%= venta.getEstadoVenta() %></td>
                    <td><%= venta.isPagado() ? "Sí" : "No" %></td>
                    <td><%= venta.isActivo() ? "Sí" : "No" %></td>
                    <td>
                        <a href="/doctorwho/jsp/ventas/venta-detail.jsp?venta_id=<%= venta.getIdTransaccion() %>" class="btn btn-info btn-sm">Ver</a>
                        <a href="/doctorwho/jsp/ventas/venta-form.jsp?venta_id=<%= venta.getIdTransaccion() %>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="/doctorwho/delete_venta?venta_id=<%= venta.getIdTransaccion() %>" 
                           onclick="return confirm('¿Estás seguro de que deseas eliminar esta venta?')" class="btn btn-danger btn-sm">Eliminar</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="row mb-3">
        <div class="col-md-8">
            <form action="/doctorwho/buscarVentas" method="get" class="d-flex">
                <input type="text" name="searchTerm" class="form-control me-2" placeholder="Buscar ventas...">
                <button type="submit" class="btn btn-outline-primary">Buscar</button>
            </form>
        </div>
        <div class="col-md-4 text-end">
            <a href="/doctorwho/busquedaAvanzadaVenta" class="btn btn-primary">Búsqueda Avanzada</a>
        </div>
    </div>

    <%@include file="../../includes/pagination.jsp"%>
</div>

<%@include file="../../includes/footer.jsp"%>