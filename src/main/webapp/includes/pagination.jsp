<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${totalPages > 1}">
    <nav aria-label="Paginación" class="mt-4">
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:set var="activeClass" value="${i == currentPage ? 'active' : ''}" />
                
                <li class="page-item ${activeClass}">
                    <a class="page-link" href="${pageContext.request.contextPath}/categorias/lista?page=${i}${not empty q ? '&q='.concat(q) : ''}${mostrarSoloConProductos ? '&con_productos=on' : ''}">
                        ${i}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>