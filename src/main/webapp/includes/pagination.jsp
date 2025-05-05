<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav aria-label="Paginación">
    <ul class="pagination justify-content-center">
        <li class="page-item <%= pageNumber == 0 ? "disabled" : "" %>">
            <a class="page-link" href="?page=<%= previousPageNumber %>&search=<%= search %>">Anterior</a>
        </li>
        <% for (int i = 0; i < pageCount; i++) { %>
        <li class="page-item <%= pageNumber == i ? "active" : "" %>">
            <a class="page-link" href="?page=<%= i %>&search=<%= search %>"><%= i + 1 %></a>
        </li>
        <% } %>
        <li class="page-item <%= pageNumber == pageCount - 1 ? "disabled" : "" %>">
            <a class="page-link" href="?page=<%= nextPageNumber %>&search=<%= search %>">Siguiente</a>
        </li>
    </ul>
</nav>