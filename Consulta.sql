CREATE TABLE usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100),
email VARCHAR(100),
contraseña VARCHAR(100),
rol VARCHAR(50),
Fecha_registro DATE,
estado VARCHAR(50),
activo BOOLEAN
);

CREATE TABLE articulos (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
descripcion TEXT,
precio DECIMAL(10,2) NOT NULL,
disponible BOOLEAN DEFAULT TRUE,
fecha_añadido DATE NOT NULL
);

CREATE TABLE categorias (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
descripcion TEXT,
cantidad INT NOT NULL,
tiene_productos BOOLEAN DEFAULT TRUE,
fecha_actualizacion DATE,
precio_medio DECIMAL (10,2)
);

CREATE TABLE ventas (
id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
id_comprador INT NOT NULL,
id_articulo INT NOT NULL,
precio DECIMAL(10,2) NOT NULL,
fecha_transaccion DATE NOT NULL,
estado_venta VARCHAR(50),
pagado BOOLEAN DEFAULT FALSE,

FOREIGN KEY (id_comprador) REFERENCES usuarios(id),
FOREIGN KEY (id_articulo) REFERENCES articulos(id)
);