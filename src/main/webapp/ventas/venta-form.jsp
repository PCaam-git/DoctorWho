<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.doctorwho.database.Database" %>
        <%@ page import="com.doctorwho.dao.VentaDao" %>
            <%@ page import="com.doctorwho.dao.ArticuloDao" %>
                <%@ page import="com.doctorwho.dao.UsuarioDao" %>
                    <%@ page import="com.doctorwho.model.Venta" %>
                        <%@ page import="com.doctorwho.model.Articulo" %>
                            <%@ page import="com.doctorwho.model.Usuario" %>
                                <%@ page import="java.util.List" %>
                                    <%@ page import="java.util.ArrayList" %>

                                        <%@ include file="../includes/header.jsp" %>
                                            <%@ include file="../includes/navbar.jsp" %>

                                                <% Database database=new Database(); database.connect(); VentaDao
                                                    ventaDao=new VentaDao(database.getConnection()); ArticuloDao
                                                    articuloDao=new ArticuloDao(database.getConnection()); UsuarioDao
                                                    usuarioDao=new UsuarioDao(database.getConnection()); String
                                                    idParam=request.getParameter("venta_id"); Venta venta=null; String
                                                    accion="Registrar" ; if (idParam !=null && !idParam.isEmpty()) {
                                                    venta=ventaDao.get(Integer.parseInt(idParam)); accion="Modificar" ;
                                                    } List<Articulo> articulos = articuloDao.getAll();
                                                    List<Usuario> usuarios = usuarioDao.getAll();

                                                        database.close();
                                                        %>

                                                        <div class="container">
                                                            <h1>
                                                                <%= venta !=null ? "Editar Venta" : "Crear Nueva Venta"
                                                                    %>
                                                            </h1>

                                                            <form
                                                                action="${pageContext.request.contextPath}/<%= venta != null ? "
                                                                edit_venta" : "create_venta" %>" method="post"
                                                                enctype="multipart/form-data">
                                                                <% if (venta !=null) { %>
                                                                    <input type="hidden" name="venta_id"
                                                                        value="<%= venta.getIdTransaccion() %>">
                                                                    <% } %>

                                                                        <div class="mb-3">
                                                                            <label for="idComprador"
                                                                                class="form-label">Usuario:</label>
                                                                            <select class="form-select"
                                                                                name="idComprador" id="idComprador"
                                                                                required>
                                                                                <option value="">Seleccione un usuario
                                                                                </option>
                                                                                <% for (Usuario usuario : usuarios) { %>
                                                                                    <option
                                                                                        value="<%= usuario.getId() %>"
                                                                                        <%=venta !=null &&
                                                                                        venta.getIdComprador()==usuario.getId()
                                                                                        ? "selected" : "" %>>
                                                                                        <%= usuario.getNombre() %>
                                                                                    </option>
                                                                                    <% } %>
                                                                            </select>
                                                                        </div>

                                                                        <div class="mb-3">
                                                                            <label for="idArticulo"
                                                                                class="form-label">Artículo:</label>
                                                                            <select class="form-select"
                                                                                name="idArticulo" id="idArticulo"
                                                                                required>
                                                                                <option value="">Seleccione un artículo
                                                                                </option>
                                                                                <% for (Articulo articulo : articulos) {
                                                                                    %>
                                                                                    <option
                                                                                        value="<%= articulo.getId() %>"
                                                                                        <%=venta !=null &&
                                                                                        venta.getIdArticulo()==articulo.getId()
                                                                                        ? "selected" : "" %>>
                                                                                        <%= articulo.getNombre() %> - €
                                                                                            <%= articulo.getPrecio() %>
                                                                                    </option>
                                                                                    <% } %>
                                                                            </select>
                                                                        </div>

                                                                        <div class="mb-3">
                                                                            <label for="precio"
                                                                                class="form-label">Precio:</label>
                                                                            <input type="number" step="0.01"
                                                                                class="form-control" name="precio"
                                                                                id="precio"
                                                                                value="<%= venta != null ? venta.getPrecio() : "" %>"
                                                                                required>
                                                                        </div>

                                                                        <div class="mb-3">
                                                                            <label for="estadoVenta"
                                                                                class="form-label">Estado:</label>
                                                                            <select class="form-select"
                                                                                name="estadoVenta" id="estadoVenta">
                                                                                <option value="Pendiente" <%=venta
                                                                                    !=null &&
                                                                                    venta.getEstadoVenta().equals("Pendiente")
                                                                                    ? "selected" : "" %>>Pendiente
                                                                                </option>
                                                                                <option value="Completada" <%=venta
                                                                                    !=null &&
                                                                                    venta.getEstadoVenta().equals("Completada")
                                                                                    ? "selected" : "" %>>Completada
                                                                                </option>
                                                                                <option value="Cancelada" <%=venta
                                                                                    !=null &&
                                                                                    venta.getEstadoVenta().equals("Cancelada")
                                                                                    ? "selected" : "" %>>Cancelada
                                                                                </option>
                                                                            </select>
                                                                        </div>

                                                                        <div class="mb-3 form-check">
                                                                            <input type="checkbox"
                                                                                class="form-check-input" name="pagado"
                                                                                id="pagado" value="true" <%=venta !=null
                                                                                && venta.isPagado() ? "checked" : "" %>>
                                                                            <label class="form-check-label"
                                                                                for="pagado">Pagado</label>
                                                                        </div>

                                                                        <div class="mb-3 form-check">
                                                                            <input type="checkbox"
                                                                                class="form-check-input" name="activo"
                                                                                id="activo" value="true" <%=venta !=null
                                                                                && venta.isActivo() ? "checked" : "" %>>
                                                                            <label class="form-check-label"
                                                                                for="activo">Activo</label>
                                                                        </div>

                                                                        <div class="mb-3">
                                                                            <label for="imagen"
                                                                                class="form-label">Comprobante de
                                                                                pago:</label>
                                                                            <input type="file" class="form-control"
                                                                                id="imagen" name="imagen"
                                                                                accept="image/*">
                                                                        </div>

                                                                        <input type="hidden" name="accion"
                                                                            value="<%= accion %>">

                                                                        <div class="mt-4">
                                                                            <button type="submit"
                                                                                class="btn btn-primary">Guardar</button>
                                                                            <a href="${pageContext.request.contextPath}/ventas/ventas.jsp"
                                                                                class="btn btn-secondary">Cancelar</a>
                                                                        </div>
                                                            </form>
                                                        </div>

                                                        <script type="text/javascript">
                                                            document.getElementById("idArticulo").addEventListener("change", function () {
                                                                var selectedOption = this.options[this.selectedIndex];
                                                                if (selectedOption.value) {
                                                                    var precioText = selectedOption.text.split(' - €')[1];
                                                                    if (precioText) {
                                                                        document.getElementById("precio").value = precioText;
                                                                    }
                                                                }
                                                            });
                                                        </script>

                                                        <%@ include file="../includes/footer.jsp" %>