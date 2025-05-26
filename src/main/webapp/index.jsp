<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Doctor Who</title>
</head>
<body>
    <h1>Doctor Who Test Page</h1>
    <p>If you can see this, static files are working correctly.</p>
    <a href="${pageContext.request.contextPath}/articulos/form.jsp">Try Accessing Form</a>

    <h2>Artículos Destacados</h2>
    <ul>
        <c:forEach var="articulo" items="${articulosDestacados}">
            <li>${articulo.nombre}</li>
        </c:forEach>
    </ul>
</body>
</html>