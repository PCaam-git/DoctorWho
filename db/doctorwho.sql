-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS doctorwho;
USE doctorwho;

-- Crear tablas
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100),
  email VARCHAR(100),
  contraseña VARCHAR(100),
  rol VARCHAR(50),
  fecha_registro DATE,
  estado VARCHAR(50),
  activo BOOLEAN,
  imagen VARCHAR(255) DEFAULT 'default.jpg'
);

CREATE TABLE categorias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  cantidad INT NOT NULL,
  tiene_productos BOOLEAN DEFAULT TRUE,
  fecha_actualizacion DATE,
  precio_medio DECIMAL (10,2),
  imagen VARCHAR(255) DEFAULT 'default.jpg'
);

CREATE TABLE articulos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2) NOT NULL,
  disponible BOOLEAN DEFAULT TRUE,
  fecha_añadido DATE NOT NULL,
  imagen VARCHAR(255) DEFAULT 'default.jpg',
  categoria_id INT,
  FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE ventas (
  id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
  id_comprador INT NOT NULL,
  id_articulo INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  fecha_transaccion DATE NOT NULL,
  estado_venta VARCHAR(50),
  pagado BOOLEAN DEFAULT FALSE,
  activo BOOLEAN DEFAULT TRUE,
  imagen VARCHAR(255) DEFAULT 'default.jpg',
  FOREIGN KEY (id_comprador) REFERENCES usuarios(id),
  FOREIGN KEY (id_articulo) REFERENCES articulos(id)
);

-- Insertar datos de ejemplo
-- Usuarios
INSERT INTO usuarios (nombre, email, contraseña, rol, fecha_registro, estado, activo, imagen) VALUES
('admin', 'admin@doctorwho.com', 'admin123', 'admin', '2023-01-01', 'Activo', TRUE, 'default.jpg'),
('usuario', 'usuario@example.com', 'user123', 'user', '2023-01-02', 'Activo', TRUE, 'default.jpg'),
('John Smith', 'john@example.com', 'john123', 'user', '2023-01-03', 'Activo', TRUE, 'default.jpg'),
('River Song', 'river@example.com', 'river123', 'user', '2023-01-04', 'Activo', TRUE, 'default.jpg'),
('Clara Oswald', 'clara@example.com', 'clara123', 'user', '2023-01-05', 'Activo', TRUE, 'default.jpg');

-- Categorías
INSERT INTO categorias (nombre, descripcion, cantidad, tiene_productos, fecha_actualizacion, precio_medio, imagen) VALUES
('Figuras', 'Figuras coleccionables de Doctor Who', 10, TRUE, '2023-01-10', 25.99, 'default.jpg'),
('Ropa', 'Camisetas, sudaderas y más', 20, TRUE, '2023-01-11', 19.99, 'default.jpg'),
('Accesorios', 'Llaveros, tazas y otros accesorios', 15, TRUE, '2023-01-12', 12.50, 'default.jpg'),
('Libros', 'Novelas y cómics de Doctor Who', 30, TRUE, '2023-01-13', 15.75, 'default.jpg'),
('Gadgets', 'Réplicas de gadgets de la serie', 5, TRUE, '2023-01-14', 35.50, 'default.jpg');

-- Artículos
INSERT INTO articulos (nombre, descripcion, precio, disponible, fecha_añadido, imagen, categoria_id) VALUES
('Figura TARDIS', 'Réplica a escala de la TARDIS', 29.99, TRUE, '2023-02-01', 'default.jpg', 1),
('Destornillador sónico', 'Réplica del destornillador sónico', 39.99, TRUE, '2023-02-02', 'default.jpg', 5),
('Camiseta TARDIS', 'Camiseta azul con diseño de la TARDIS', 19.99, TRUE, '2023-02-03', 'default.jpg', 2),
('Taza Dalek', 'Taza en forma de Dalek', 14.99, TRUE, '2023-02-04', 'default.jpg', 3),
('Figura 11º Doctor', 'Figura del 11º Doctor', 24.99, TRUE, '2023-02-05', 'default.jpg', 1),
('Novela The Lonely God', 'Aventura del 10º Doctor', 12.99, TRUE, '2023-02-06', 'default.jpg', 4),
('Sudadera Doctor Who', 'Sudadera negra con logo de Doctor Who', 29.99, TRUE, '2023-02-07', 'default.jpg', 2),
('Llavero TARDIS', 'Llavero metálico con forma de TARDIS', 9.99, TRUE, '2023-02-08', 'default.jpg', 3),
('Modelo K-9', 'Réplica del perro robot K-9', 49.99, TRUE, '2023-02-09', 'default.jpg', 5),
('Figura Weeping Angel', 'Figura de un Ángel Lloroso', 27.99, TRUE, '2023-02-10', 'default.jpg', 1);

-- Ventas
INSERT INTO ventas (id_comprador, id_articulo, precio, fecha_transaccion, estado_venta, pagado, activo, imagen) VALUES
(2, 1, 29.99, '2023-03-01', 'Completada', TRUE, TRUE, 'default.jpg'),
(3, 3, 19.99, '2023-03-02', 'Completada', TRUE, TRUE, 'default.jpg'),
(4, 5, 24.99, '2023-03-03', 'Pendiente', FALSE, TRUE, 'default.jpg'),
(5, 7, 29.99, '2023-03-04', 'Completada', TRUE, TRUE, 'default.jpg'),
(2, 9, 49.99, '2023-03-05', 'Cancelada', FALSE, FALSE, 'default.jpg');