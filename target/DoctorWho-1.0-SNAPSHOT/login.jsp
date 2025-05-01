<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <h2>Iniciar Sesión</h2>
        <form action="login" method="post">
            <label for="usuario">Usuario:</label>
            <input type="text" id="usuario" name="usuario"><br>

            <label for="contraseña">Contraseña</label>
            <input type="password" name="contraseña" id="contraseña" required><br>

            <button type="submit">Entrar</button>
        </form>
    </body>
</html>