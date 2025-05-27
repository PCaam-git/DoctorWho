<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Who Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    
    <style>
        body {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background: linear-gradient(135deg, #0a0a23 0%, #1a1a3a 50%, #2d1b69 100%);
            position: relative;
        }

        .space-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: 
                radial-gradient(2px 2px at 20px 30px, #eee, transparent),
                radial-gradient(2px 2px at 40px 70px, rgba(255,255,255,0.8), transparent),
                radial-gradient(1px 1px at 90px 40px, #fff, transparent),
                radial-gradient(1px 1px at 130px 80px, rgba(255,255,255,0.6), transparent),
                radial-gradient(2px 2px at 160px 30px, rgba(255,255,255,0.9), transparent),
                linear-gradient(135deg, #0a0a23 0%, #1a1a3a 50%, #2d1b69 100%);
            background-repeat: repeat;
            background-size: 200px 100px;
            animation: twinkle 3s ease-in-out infinite alternate;
            z-index: -2;
        }

        .tardis-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url('${pageContext.request.contextPath}/images/tardis-space.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            opacity: 0.3;
            z-index: -1;
        }

        @keyframes twinkle {
            0% { opacity: 0.3; }
            100% { opacity: 0.8; }
        }

        .hero-banner {
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 100px 0;
            text-align: center;
            backdrop-filter: blur(5px);
            border-bottom: 3px solid #ff6b35;
        }

        .hero-banner h1 {
            font-size: 4.5rem;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.8);
            color: #00d4ff;
            margin-bottom: 1rem;
        }

        .hero-banner .lead {
            font-size: 1.5rem;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
            color: #ffd700;
        }

        .hero-banner p {
            text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
        }

        .main-content {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            margin: 50px auto;
            max-width: 1200px;
            padding: 50px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.3);
        }

        .explore-cards .card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(5px);
            border: 2px solid transparent;
            transition: all 0.3s ease;
            border-radius: 15px;
            overflow: hidden;
            position: relative;
        }

        .explore-cards .card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(45deg, #ff6b35, #00d4ff, #ffd700);
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: -1;
        }

        .explore-cards .card:hover::before {
            opacity: 0.1;
        }

        .explore-cards .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 25px 50px rgba(0,0,0,0.2);
            border-color: #00d4ff;
        }

        .explore-cards .card-body {
            position: relative;
            z-index: 1;
        }

        .card-title {
            color: #2d1b69;
            font-weight: bold;
        }

        .btn-primary {
            background: linear-gradient(45deg, #00d4ff, #0099cc);
            border: none;
            padding: 12px 30px;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background: linear-gradient(45deg, #0099cc, #007aa3);
            transform: scale(1.05);
        }

        .btn-success {
            background: linear-gradient(45deg, #ff6b35, #e55a2b);
            border: none;
            padding: 12px 30px;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .btn-success:hover {
            background: linear-gradient(45deg, #e55a2b, #cc4a1f);
            transform: scale(1.05);
        }

        .welcome-alert {
            background: linear-gradient(45deg, rgba(0, 212, 255, 0.2), rgba(255, 215, 0, 0.2));
            border: 2px solid #00d4ff;
            backdrop-filter: blur(5px);
            border-radius: 15px;
        }

        .features-section {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(5px);
            border-radius: 15px;
            padding: 40px 20px;
            margin-top: 40px;
        }

        .features-section h3 {
            color: #2d1b69;
            font-weight: bold;
        }

        .features-section .text-warning {
            color: #ffd700 !important;
        }

        .features-section .text-info {
            color: #00d4ff !important;
        }

        .features-section .text-success {
            color: #ff6b35 !important;
        }

        /* Navbar transparente */
        .navbar {
            background: rgba(0, 0, 0, 0.8) !important;
            backdrop-filter: blur(10px);
        }

        /* Animaciones de entrada */
        .fade-in {
            animation: fadeIn 1s ease-in;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .slide-in-left {
            animation: slideInLeft 1s ease-out;
        }

        @keyframes slideInLeft {
            from { opacity: 0; transform: translateX(-50px); }
            to { opacity: 1; transform: translateX(0); }
        }

        .slide-in-right {
            animation: slideInRight 1s ease-out;
        }

        @keyframes slideInRight {
            from { opacity: 0; transform: translateX(50px); }
            to { opacity: 1; transform: translateX(0); }
        }
    </style>
</head>
<body>
    <!-- Fondo de espacio animado -->
    <div class="space-background"></div>
    
    <!-- Fondo de TARDIS -->
    <div class="tardis-background"></div>

    <!-- navbar -->
    <jsp:include page="navbar.jsp"/>

    <!-- Hero Section -->
    <div class="hero-banner fade-in">
        <div class="container">
            <h1 class="display-3"><i class="fas fa-tardis me-3"></i>Doctor Who Store</h1>
            <p class="lead">Tu tienda oficial de productos del Doctor</p>
            <p class="fs-5">✨ Explora nuestras categorías y descubre productos únicos del universo de Doctor Who ✨</p>
        </div>
    </div>

    <!-- Contenido principal -->
    <div class="container">
        <div class="main-content fade-in">
            <div class="text-center mb-5">
                <h2 class="display-5 text-primary">🌟 Explora Nuestra Tienda 🌟</h2>
                <p class="lead text-muted">Descubre productos exclusivos del universo de Doctor Who</p>
            </div>

            <div class="row justify-content-center g-4 explore-cards">
                <!-- Categorías -->
                <div class="col-md-6 col-lg-5 slide-in-left">
                    <div class="card h-100 shadow-lg">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-folder fa-5x text-primary"></i>
                            </div>
                            <h3 class="card-title mb-3">📁 Categorías</h3>
                            <p class="card-text mb-4 fs-6">Explora nuestras diferentes categorías de productos organizadas especialmente para ti</p>
                            <a href="${pageContext.request.contextPath}/categorias/lista" class="btn btn-primary btn-lg shadow">
                                <i class="fas fa-folder-open me-2"></i>Explorar Categorías
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Artículos -->
                <div class="col-md-6 col-lg-5 slide-in-right">
                    <div class="card h-100 shadow-lg">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-box fa-5x text-success"></i>
                            </div>
                            <h3 class="card-title mb-3">📦 Artículos</h3>
                            <p class="card-text mb-4 fs-6">Descubre todos nuestros productos exclusivos y coleccionables de Doctor Who</p>
                            <a href="${pageContext.request.contextPath}/articulos/lista" class="btn btn-success btn-lg shadow">
                                <i class="fas fa-shopping-bag me-2"></i>Ver Productos
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sección adicional para usuarios logueados -->
            <c:if test="${not empty sessionScope.email}">
                <div class="row mt-5 fade-in">
                    <div class="col-12">
                        <div class="alert welcome-alert text-center py-4">
                            <h4><i class="fas fa-user-astronaut me-3"></i>¡Hola, ${sessionScope.email}!</h4>
                            <p class="mb-3 fs-5">🎉 Bienvenido de vuelta a Doctor Who Store 🎉</p>
                            <a href="${pageContext.request.contextPath}/usuario/perfil" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-user-circle me-2"></i>Ver Mi Perfil
                            </a>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Características destacadas -->
            <div class="features-section">
                <div class="row">
                    <div class="col-12">
                        <h3 class="text-center mb-5">🌟 ¿Por qué elegir Doctor Who Store? 🌟</h3>
                    </div>
                    <div class="col-md-4 text-center mb-4">
                        <i class="fas fa-star fa-4x text-warning mb-3"></i>
                        <h5 class="fw-bold">Productos Oficiales</h5>
                        <p class="text-muted">Artículos con licencia oficial de la BBC y merchandise auténtico</p>
                    </div>
                    <div class="col-md-4 text-center mb-4">
                        <i class="fas fa-rocket fa-4x text-info mb-3"></i>
                        <h5 class="fw-bold">Envío Temporal</h5>
                        <p class="text-muted">Entrega rápida a través del tiempo y el espacio</p>
                    </div>
                    <div class="col-md-4 text-center mb-4">
                        <i class="fas fa-shield-alt fa-4x text-success mb-3"></i>
                        <h5 class="fw-bold">Garantía Galáctica</h5>
                        <p class="text-muted">Protección garantizada por los Señores del Tiempo</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>