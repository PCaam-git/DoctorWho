<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.doctorwho.database.Database" %>
        <%@ page import="com.doctorwho.dao.VentaDao" %>
            <%@ page import="com.doctorwho.model.Venta" %>
                <%@ page import="com.doctorwho.exception.VentaNotFoundException" %>

                    <%@include file="../../includes/header.jsp" %>
                        <%@include file="../../includes/navbar.jsp" %>

                            <% // Verificar que el usuario esté logueado boolean
                                logged=(session.getAttribute("username") !=null); if (!logged) {
                                response.sendRedirect(request.getContextPath() + "/login.jsp" ); return; } int
                                venta_id=Integer.parseInt(request.getParameter("venta_id")); Database database=new
                                Database(); database.connect(); VentaDao ventaDao=new
                                VentaDao(database.getConnection()); try { Venta venta=ventaDao.get(venta_id); // Si no
                                es admin y no es su compra, redirigir if (!role.equals("admin") &&
                                !session.getAttribute("username").equals(venta.getNombreComprador())) {
                                response.sendRedirect(request.getContextPath() + "/ventas/ventas.jsp" ); return; } %>
                                <div class="container py-4">
                                    <div class="card">
                                        <div class="card-header bg-primary text-white">
                                            <h2>Detalles de la Compra #<%= venta.getIdTransaccion() %>
                                            </h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <p><strong>ID Transacción:</strong>
                                                        <%= venta.getIdTransaccion() %>
                                                    </p>
                                                    <p><strong>Comprador:</strong>
                                                        <%= venta.getNombreComprador() %>
                                                    </p>
                                                    <p><strong>Artículo:</strong>
                                                        <%= venta.getNombreArticulo() %>
                                                    </p>
                                                    <p><strong>Precio:</strong>
                                                        <%= venta.getPrecio() %> €
                                                    </p>
                                                </div>
                                                <div class="col-md-6">
                                                    <p><strong>Fecha:</strong>
                                                        <%= venta.getFechaTransaccion() %>
                                                    </p>
                                                    <p><strong>Estado:</strong>
                                                        <%= venta.getEstadoVenta() %>
                                                    </p>
                                                    <p><strong>Pagado:</strong>
                                                        <%= venta.isPagado() ? "Sí" : "No" %>
                                                    </p>
                                                    <p><strong>Activo:</strong>
                                                        <%= venta.isActivo() ? "Sí" : "No" %>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card-footer">
                                            <% if (role.equals("admin")) { %>
                                                <a href="${pageContext.request.contextPath}/ventas/venta-form.jsp?venta_id=<%= venta.getIdTransaccion() %>"
                                                    class="btn btn-warning">Editar</a>
                                                <a href="${pageContext.request.contextPath}/delete_venta?venta_id=<%= venta.getIdTransaccion() %>"
                                                    class="btn btn-danger"
                                                    onclick="return confirm('¿Estás seguro?')">Eliminar</a>
                                                <% } else { %>
                                                    <a href="${pageContext.request.contextPath}/ventas/ventas.jsp"
                                                        class="btn btn-secondary">Volver</a>
                                                    <% } %>
                                        </div>
                                    </div>
                                </div>
                                <% } catch (VentaNotFoundException vnfe) { %>
                                    <div class="container py-4">
                                        <div class="alert alert-danger" role="alert">
                                            No se ha encontrado la venta solicitada.
                                        </div>
                                        <% if (role.equals("admin")) { %>
                                            <% } else { %>
                                                <a href="${pageContext.request.contextPath}/ventas/ventas.jsp"
                                                    class="btn btn-primary">Volver a la lista</a>
                                                <% } %>
                                    </div>
                                    <% } %>

                                        <%@include file="../../includes/footer.jsp" %>