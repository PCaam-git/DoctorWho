<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Búsqueda Avanzada de Artículos - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <h2>Búsqueda Avanzada de Artículos</h2>
        
        <div class="card">
            <div class="card-body">
                <form action="busquedaAvanzadaArticulo" method="post">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre">
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="precioMin" class="form-label">Precio Mínimo (€):</label>
                            <input type="number" step="0.01" class="form-control" id="precioMin" name="precioMin">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="precioMax" class="form-label">Precio Máximo (€):</label>
                            <input type="number" step="0.01" class="form-control" id="precioMax" name="precioMax">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="disponible" class="form-label">Disponibilidad:</label>
                        <select class="form-select" id="disponible" name="disponible">
                            <option value="">Todos</option>
                            <option value="true">Disponible</option>
                            <option value="false">No disponible</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Buscar</button>
                    <a href="listarArticulos" class="btn btn-secondary">Volver</a>
                </form>
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>