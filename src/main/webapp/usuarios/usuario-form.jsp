<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.doctorwho.database.Database" %>
        <%@ page import="com.doctorwho.dao.UsuarioDao" %>
            <%@ page import="com.doctorwho.model.Usuario" %>

                <%@ include file="../includes/header.jsp" %>
                    <%@ include file="../includes/navbar.jsp" %>

                        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
                            <%@ page import="com.doctorwho.database.Database" %>
                                <%@ page import="com.doctorwho.dao.UsuarioDao" %>
                                    <%@ page import="com.doctorwho.model.Usuario" %>

                                        <%@ include file="../includes/header.jsp" %>
                                            <%@ include file="../includes/navbar.jsp" %>

                                                <% Database database=new Database(); database.connect(); UsuarioDao
                                                    usuarioDao=new UsuarioDao(database.getConnection()); String
                                                    idParam=request.getParameter("usuario_id"); Usuario usuario=null;
                                                    String accion="Registrar" ; if (idParam !=null &&
                                                    !idParam.isEmpty()) {
                                                    usuario=usuarioDao.get(Integer.parseInt(idParam));
                                                    accion="Modificar" ; } database.close(); %>

                                                    <div class="container">
                                                        <h1>
                                                            <%= usuario !=null ? "Editar Usuario"
                                                                : "Crear Nuevo Usuario" %>
                                                        </h1>
                                                        <form
                                                            action="${pageContext.request.contextPath}/<%= usuario != null ? "
                                                            edit_usuario" : "create_usuario" %>" method="post"
                                                            enctype="multipart/form-data">
                                                            <% if (usuario !=null) { %>
                                                                <input type="hidden" name="usuario_id"
                                                                    value="<%= usuario.getId() %>">
                                                                <% } %>

                                                                    <div class="mb-3">
                                                                        <label for="nombre"
                                                                            class="form-label">Nombre:</label>
                                                                        <input type="text" class="form-control"
                                                                            name="nombre" id="nombre"
                                                                            value="<%= usuario != null ? usuario.getNombre() : "" %>"
                                                                            required>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="email"
                                                                            class="form-label">Email:</label>
                                                                        <input type="email" class="form-control"
                                                                            name="email" id="email"
                                                                            value="<%= usuario != null ? usuario.getEmail() : "" %>"
                                                                            required>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="contraseña"
                                                                            class="form-label">Contraseña:</label>
                                                                        <input type="password" class="form-control"
                                                                            name="contraseña" id="contraseña" value=""
                                                                            <%=usuario==null ? "required" : "" %>>
                                                                        <% if (usuario !=null) { %>
                                                                            <small class="text-muted">Dejar en blanco
                                                                                para mantener la actual</small>
                                                                            <% } %>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="rol" class="form-label">Rol:</label>
                                                                        <select class="form-select" name="rol" id="rol">
                                                                            <option value="user" <%=usuario !=null &&
                                                                                usuario.getRol().equals("user")
                                                                                ? "selected" : "" %>>Usuario</option>
                                                                            <option value="admin" <%=usuario !=null &&
                                                                                usuario.getRol().equals("admin")
                                                                                ? "selected" : "" %>>Administrador
                                                                            </option>
                                                                        </select>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="imagen"
                                                                            class="form-label">Imagen:</label>
                                                                        <input type="file" class="form-control"
                                                                            id="imagen" name="imagen" accept="image/*">
                                                                    </div>

                                                                    <input type="hidden" name="accion"
                                                                        value="<%= accion %>">

                                                                    <div class="mt-4">
                                                                        <button type="submit"
                                                                            class="btn btn-primary">Guardar</button>
                                                                        <a href="${pageContext.request.contextPath}/usuarios/usuarios.jsp"
                                                                            class="btn btn-secondary">Cancelar</a>
                                                                    </div>
                                                        </form>
                                                    </div>

                                                    <%@ include file="../includes/footer.jsp" %>