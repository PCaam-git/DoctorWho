<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Búsqueda Avanzada de Categorías - Doctor Who Merchandise</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/header.jsp"></jsp:include>
    <jsp:include page="../includes/navbar.jsp"></jsp:include>
    
    <div class="container mt-4">
        <h2>Búsqueda Avanzada de Categorías</h2>
        
        <div class="card">
            <div class="card-body">
                <form action="busquedaAvanzadaCategoria" method="post">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre">
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <div class="col-md-6 mb-3">
                                <label for="cantidadMin" class="form-label">Cantidad Mínima:</label>
                                <input type="number" class="form-control" id="cantidadMin" name="cantidadMin">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="cantidadMax" class="form-label">Cantidad Máxima:</label>
                                <input type="number" class="form-control" id="cantidadMax" name="cantidadMax">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="tieneProductos" class="form-label">Tiene Productos:</label>
                            <select class="form-select" id="tieneProductos" name="tieneProductos">
                                <option value="">Todos</option>
                                <option value="true">Sí</option>
                                <option value="false">No</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Buscar</button>
                        <a href="listarCategorias" class="btn btn-secondary">Volver</a>
                    </form>
                </div>
            </div>
        </div>
        
        <jsp:include page="../includes/footer.jsp"></jsp:include>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    </html>