<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.doctorwho.database.Database" %>
        <%@ page import="com.doctorwho.dao.ArticuloDao" %>
            <%@ page import="com.doctorwho.model.Articulo" %>
                <%@ page import="java.util.List" %>
                    <%@ page import="java.util.ArrayList" %>

                        <%@ include file="../../includes/header.jsp" %>
                            <%@ include file="../../includes/navbar.jsp" %>

                                <div class="container mt-4">
                                    <h1>Gestión de Artículos</h1>

                                    <!-- Botón para Añadir Artículo (solo para administradores) -->
                                    <% if (role !=null && role.equalsIgnoreCase("admin")) { %>
                                        <a href="${pageContext.request.contextPath}/create_articulo"
                                            class="btn btn-primary mb-3">
                                            <i class="fas fa-plus"></i> Añadir Artículo
                                        </a>
                                        <% } %>

                                            <!-- Mensajes de notificación -->
                                            <% if (request.getSession().getAttribute("mensaje") !=null) { %>
                                                <div class="alert alert-success alert-dismissible fade show"
                                                    role="alert">
                                                    <%= request.getSession().getAttribute("mensaje") %>
                                                        <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                            aria-label="Close"></button>
                                                </div>
                                                <% request.getSession().removeAttribute("mensaje"); %>
                                                    <% } %>

                                                        <% if (request.getSession().getAttribute("error") !=null) { %>
                                                            <div class="alert alert-danger alert-dismissible fade show"
                                                                role="alert">
                                                                <%= request.getSession().getAttribute("error") %>
                                                                    <button type="button" class="btn-close"
                                                                        data-bs-dismiss="alert"
                                                                        aria-label="Close"></button>
                                                            </div>
                                                            <% request.getSession().removeAttribute("error"); %>
                                                                <% } %>

                                                                    <!-- Tabla de Artículos -->
                                                                    <div class="table-responsive">
                                                                        <table
                                                                            class="table table-striped table-bordered">
                                                                            <thead class="table-dark">
                                                                                <tr>
                                                                                    <th>ID</th>
                                                                                    <th>Nombre</th>
                                                                                    <th>Descripción</th>
                                                                                    <th>Precio</th>
                                                                                    <th>Stock</th>
                                                                                    <th>Categoría</th>
                                                                                    <th>Acciones</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <% try { Database database=new
                                                                                    Database(); database.connect();
                                                                                    ArticuloDao articuloDao=new
                                                                                    ArticuloDao(database.getConnection());
                                                                                    List<Articulo> articulos =
                                                                                    articuloDao.getAll();

                                                                                    for (Articulo articulo : articulos)
                                                                                    {
                                                                                    %>
                                                                                    <tr>
                                                                                        <td>
                                                                                            <%= articulo.getId() %>
                                                                                        </td>
                                                                                        <td>
                                                                                            <%= articulo.getNombre() %>
                                                                                        </td>
                                                                                        <td>
                                                                                            <%= articulo.getDescripcion()
                                                                                                %>
                                                                                        </td>
                                                                                        <td>
                                                                                            <%= articulo.getPrecio() %>
                                                                                                €
                                                                                        </td>
                                                                                        <td>
                                                                                            <%= articulo.getStock() %>
                                                                                        </td>
                                                                                        <td>
                                                                                            <%= articulo.getCategoria()
                                                                                                !=null ?
                                                                                                articulo.getCategoria().getNombre()
                                                                                                : "Sin categoría" %>
                                                                                        </td>
                                                                                        <td>
                                                                                            <!-- Botones de acción -->
                                                                                            <div class="btn-group"
                                                                                                role="group">
                                                                                                <a href="${pageContext.request.contextPath}/articulos/detalle?id=<%= articulo.getId() %>"
                                                                                                    class="btn btn-info btn-sm">
                                                                                                    <i
                                                                                                        class="fas fa-eye"></i>
                                                                                                    Ver
                                                                                                </a>
                                                                                                <% if (role !=null &&
                                                                                                    role.equalsIgnoreCase("admin"))
                                                                                                    { %>
                                                                                                    <a href="${pageContext.request.contextPath}/edit_articulo?articulo_id=<%= articulo.getId() %>"
                                                                                                        class="btn btn-warning btn-sm">
                                                                                                        <i
                                                                                                            class="fas fa-edit"></i>
                                                                                                        Editar
                                                                                                    </a>
                                                                                                    <button
                                                                                                        type="button"
                                                                                                        class="btn btn-danger btn-sm"
                                                                                                        data-bs-toggle="modal"
                                                                                                        data-bs-target="#deleteModal<%= articulo.getId() %>">
                                                                                                        <i
                                                                                                            class="fas fa-trash"></i>
                                                                                                        Eliminar
                                                                                                    </button>
                                                                                                    <% } %>
                                                                                            </div>

                                                                                            <!-- Modal de Confirmación de Eliminación -->
                                                                                            <div class="modal fade"
                                                                                                id="deleteModal<%= articulo.getId() %>"
                                                                                                tabindex="-1"
                                                                                                aria-labelledby="deleteModalLabel<%= articulo.getId() %>"
                                                                                                aria-hidden="true">
                                                                                                <div
                                                                                                    class="modal-dialog">
                                                                                                    <div
                                                                                                        class="modal-content">
                                                                                                        <div
                                                                                                            class="modal-header">
                                                                                                            <h5 class="modal-title"
                                                                                                                id="deleteModalLabel<%= articulo.getId() %>">
                                                                                                                Confirmar
                                                                                                                Eliminación
                                                                                                            </h5>
                                                                                                            <button
                                                                                                                type="button"
                                                                                                                class="btn-close"
                                                                                                                data-bs-dismiss="modal"
                                                                                                                aria-label="Close"></button>
                                                                                                        </div>
                                                                                                        <div
                                                                                                            class="modal-body">
                                                                                                            ¿Está seguro
                                                                                                            de que desea
                                                                                                            eliminar el
                                                                                                            artículo "
                                                                                                            <%= articulo.getNombre()
                                                                                                                %>"?
                                                                                                        </div>
                                                                                                        <div
                                                                                                            class="modal-footer">
                                                                                                            <button
                                                                                                                type="button"
                                                                                                                class="btn btn-secondary"
                                                                                                                data-bs-dismiss="modal">Cancelar</button>
                                                                                                            <a href="${pageContext.request.contextPath}/delete_articulo?articulo_id=<%= articulo.getId() %>"
                                                                                                                class="btn btn-danger">Eliminar</a>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% } database.close(); } catch
                                                                                        (Exception e) {
                                                                                        e.printStackTrace(); %>
                                                                                        <tr>
                                                                                            <td colspan="7"
                                                                                                class="text-center text-danger">
                                                                                                Error al cargar los
                                                                                                artículos: <%=
                                                                                                    e.getMessage() %>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <% } %>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                </div>

                                <%@ include file="../../includes/footer.jsp" %>