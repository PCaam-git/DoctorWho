
USE `aa_doctorwho`;

CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `cantidad` int NOT NULL,
  `tiene_productos` BOOLEAN DEFAULT 1,
  `fecha_actualizacion` date NOT NULL,
  `precio_medio` decimal(10,2) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT 'default.jpg',
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `articulos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `disponible` BOOLEAN DEFAULT 1,
  `precio` decimal(10,2) DEFAULT NULL,
  `fecha_anadido` date NOT NULL,
  `categoria_id` int DEFAULT NULL,
  `imagen` varchar(255) DEFAULT 'default.jpg',
  PRIMARY KEY (`id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `fk_articulos_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
);

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `es_admin` BOOLEAN DEFAULT 0,
  `fecha_registro` date NOT NULL,
  `credito` decimal(10,2) DEFAULT 0,
  `imagen` varchar(255) DEFAULT 'default.jpg',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE IF NOT EXISTS `ventas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `articulo_id` int NOT NULL,
  `cantidad` int NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha_venta` date NOT NULL,
  `estado_venta` varchar(50) DEFAULT 'pendiente',
  `pagado` BOOLEAN DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `usuario_id` (`usuario_id`) USING BTREE,
  KEY `articulo_id` (`articulo_id`) USING BTREE,
  CONSTRAINT `fk_ventas_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `fk_ventas_articulo` FOREIGN KEY (`articulo_id`) REFERENCES `articulos` (`id`)
); 