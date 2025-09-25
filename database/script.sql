-- MARCO POOL CHICHE SAPON 201832053
-- crear base
CREATE DATABASE gym_system;

-- conectarse ala base de datos
/c gym_system;

-- sucursal
CREATE TABLE sucursal (
    id_sucursal SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE,
    region TEXT,
    cantidad_maquinas INT DEFAULT 0 CHECK (cantidad_maquinas >= 0)
);

-- rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE
);

-- empleado
CREATE TABLE empleado (
    id_empleado SERIAL PRIMARY KEY,
    dpi BIGINT UNIQUE NOT NULL,
    nombre TEXT NOT NULL,
    apellido TEXT,
    password TEXT,
    telefono TEXT,
    direccion TEXT,
    id_sucursal INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE RESTRICT,
    id_rol INT NOT NULL REFERENCES rol(id_rol) ON DELETE RESTRICT
);

-- cliente
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    dpi BIGINT UNIQUE NOT NULL,
    nombre TEXT NOT NULL,
    apellido TEXT,
    password TEXT NOT NULL,
    telefono TEXT,
    direccion TEXT,
    fecha_registro TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);

-- tipos de membresia
CREATE TABLE membresia_tipo (
    id_tipo SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE,
    descuento NUMERIC(5,2) NOT NULL DEFAULT 0  
);

-- membresias
CREATE TABLE membresia (
  id_membresia SERIAL PRIMARY KEY,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  id_tipo INT NOT NULL REFERENCES membresia_tipo(id_tipo) ON DELETE RESTRICT,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE,
  UNIQUE (dpi_cliente, fecha_inicio)
);

-- adicionales
CREATE TABLE adicional (
  id_adicional SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  detalles TEXT,
  precio NUMERIC(12,2) NOT NULL,
  dpi_entrenador BIGINT REFERENCES empleado(dpi)
);

-- pagos
CREATE TABLE pago (
  id_pago SERIAL PRIMARY KEY,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  tipo TEXT NOT NULL CHECK (tipo IN ('servicio','adicional')),
  descripcion TEXT,
  monto NUMERIC(12,2) NOT NULL CHECK (monto >= 0),
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE,
  id_adicional INT REFERENCES adicional(id_adicional)
);

-- equipos
CREATE TABLE equipo (
  id_equipo SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  descripcion TEXT,
  tipo TEXT NOT NULL CHECK (tipo IN ('maquina','repuesto','otro'))
);

-- Inventario
CREATE TABLE inventario (
  id_inventario SERIAL PRIMARY KEY,
  id_sucursal INT REFERENCES sucursal(id_sucursal),
  id_equipo INT NOT NULL REFERENCES equipo(id_equipo),
  cantidad INT NOT NULL CHECK (cantidad >= 0)
);

-- entrenador-cliente
CREATE TABLE entrenador_cliente (
  id_asignacion SERIAL PRIMARY KEY,
  dpi_entrenador BIGINT NOT NULL REFERENCES empleado(dpi) ON DELETE RESTRICT,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  fecha_asignacion DATE NOT NULL,
  fecha_fin DATE
);

-- Rutinas
CREATE TABLE rutina (
  id_rutina SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  tipo TEXT,
  fecha_inicio DATE NOT NULL,
  dpi_entrenador BIGINT NOT NULL REFERENCES empleado(dpi) ON DELETE RESTRICT,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE
);

-- ejercicios
CREATE TABLE ejercicio (
  id_ejercicio SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  series INT,
  repeticiones INT,
  duracion_min INT,
  id_equipo INT REFERENCES equipo(id_equipo)
);

-- rutina - ejercicio 
CREATE TABLE rutina_ejercicio (
  id_rut_eje SERIAL PRIMARY KEY,
  id_rutina INT NOT NULL REFERENCES rutina(id_rutina) ON DELETE CASCADE,
  id_ejercicio INT NOT NULL REFERENCES ejercicio(id_ejercicio) ON DELETE RESTRICT,
  orden INT NOT NULL DEFAULT 1,
  CHECK (orden > 0)
);

-- asistencias
CREATE TABLE asistencia (
  id_asistencia SERIAL PRIMARY KEY,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  id_sucursal INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE RESTRICT,
  fecha_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- inventario
CREATE TABLE movimiento_inventario (
  id_movimiento SERIAL PRIMARY KEY,
  id_equipo INT NOT NULL REFERENCES equipo(id_equipo),
  cantidad INT NOT NULL CHECK (cantidad > 0),
  origen_sucursal INT REFERENCES sucursal(id_sucursal),
  destino_sucursal INT REFERENCES sucursal(id_sucursal),
  fecha TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  motivo TEXT
);


-- INSERTS VALUES
INSERT INTO sucursal (nombre, region, cantidad_maquinas) VALUES
('Central', 'Central', 50),
('Norte', 'Norte',30),
('Sur', 'Sur', 20);

INSERT INTO rol (nombre) VALUES ('Recepcionista'), ('Entrenador'), ('Inventario');

-- Empleados para Sucursal Central (id_sucursal = 1)
-- 25 Entrenadores (id_rol = 2)
INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol) VALUES
(1000000100001, 'Carlos', 'Martínez', 'pass123', '1234-5678', 'Zona 1, Ciudad', 1, 2),
(1000000100002, 'Ana', 'López', 'pass123', '1234-5679', 'Zona 2, Ciudad', 1, 2),
(1000000100003, 'Miguel', 'García', 'pass123', '1234-5680', 'Zona 3, Ciudad', 1, 2),
(1000000100004, 'Sofia', 'Rodríguez', 'pass123', '1234-5681', 'Zona 4, Ciudad', 1, 2),
(1000000100005, 'Javier', 'Hernández', 'pass123', '1234-5682', 'Zona 5, Ciudad', 1, 2),
(1000000100006, 'Laura', 'Pérez', 'pass123', '1234-5683', 'Zona 6, Ciudad', 1, 2),
(1000000100007, 'Daniel', 'Gómez', 'pass123', '1234-5684', 'Zona 7, Ciudad', 1, 2),
(1000000100008, 'Elena', 'Díaz', 'pass123', '1234-5685', 'Zona 8, Ciudad', 1, 2),
(1000000100009, 'Roberto', 'Fernández', 'pass123', '1234-5686', 'Zona 9, Ciudad', 1, 2),
(1000000100010, 'Carmen', 'Santos', 'pass123', '1234-5687', 'Zona 10, Ciudad', 1, 2),
(1000000100011, 'Francisco', 'Castillo', 'pass123', '1234-5688', 'Zona 11, Ciudad', 1, 2),
(1000000100012, 'Patricia', 'Ramírez', 'pass123', '1234-5689', 'Zona 12, Ciudad', 1, 2),
(1000000100013, 'Antonio', 'Cruz', 'pass123', '1234-5690', 'Zona 13, Ciudad', 1, 2),
(1000000100014, 'Isabel', 'Morales', 'pass123', '1234-5691', 'Zona 14, Ciudad', 1, 2),
(1000000100015, 'Ricardo', 'Ortiz', 'pass123', '1234-5692', 'Zona 15, Ciudad', 1, 2),
(1000000100016, 'Gabriela', 'Reyes', 'pass123', '1234-5693', 'Zona 16, Ciudad', 1, 2),
(1000000100017, 'Luis', 'Vargas', 'pass123', '1234-5694', 'Zona 17, Ciudad', 1, 2),
(1000000100018, 'María', 'Castro', 'pass123', '1234-5695', 'Zona 18, Ciudad', 1, 2),
(1000000100019, 'Juan', 'Romero', 'pass123', '1234-5696', 'Zona 19, Ciudad', 1, 2),
(1000000100020, 'Karla', 'Mendoza', 'pass123', '1234-5697', 'Zona 20, Ciudad', 1, 2),
(1000000100021, 'Pedro', 'Guerrero', 'pass123', '1234-5698', 'Zona 21, Ciudad', 1, 2),
(1000000100022, 'Andrea', 'Rojas', 'pass123', '1234-5699', 'Zona 22, Ciudad', 1, 2),
(1000000100023, 'José', 'Silva', 'pass123', '1234-5700', 'Zona 23, Ciudad', 1, 2),
(1000000100024, 'Diana', 'Navarro', 'pass123', '1234-5701', 'Zona 24, Ciudad', 1, 2),
(1000000100025, 'Fernando', 'Molina', 'pass123', '1234-5702', 'Zona 25, Ciudad', 1, 2),

-- 5 Encargados de Inventario (id_rol = 3)
(1000000100026, 'Raúl', 'Ortega', 'pass123', '1234-5703', 'Zona 1, Ciudad', 1, 3),
(1000000100027, 'Teresa', 'Delgado', 'pass123', '1234-5704', 'Zona 2, Ciudad', 1, 3),
(1000000100028, 'Oscar', 'Herrera', 'pass123', '1234-5705', 'Zona 3, Ciudad', 1, 3),
(1000000100029, 'Natalia', 'Peña', 'pass123', '1234-5706', 'Zona 4, Ciudad', 1, 3),
(1000000100030, 'Sergio', 'Ríos', 'pass123', '1234-5707', 'Zona 5, Ciudad', 1, 3),

-- 3 Recepcionistas (id_rol = 1)
(1000000100031, 'Verónica', 'Medina', 'pass123', '1234-5708', 'Zona 6, Ciudad', 1, 1),
(1000000100032, 'Héctor', 'Cordero', 'pass123', '1234-5709', 'Zona 7, Ciudad', 1, 1),
(1000000100033, 'Lucía', 'Valdez', 'pass123', '1234-5710', 'Zona 8, Ciudad', 1, 1);

-- Empleados para Sucursal Norte (id_sucursal = 2)
-- 15 Entrenadores (id_rol = 2)
INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol) VALUES
(1000000200001, 'Alejandro', 'Miranda', 'pass123', '2234-5678', 'Zona Norte 1', 2, 2),
(1000000200002, 'Beatriz', 'Soto', 'pass123', '2234-5679', 'Zona Norte 2', 2, 2),
(1000000200003, 'César', 'Paredes', 'pass123', '2234-5680', 'Zona Norte 3', 2, 2),
(1000000200004, 'Daniela', 'Quintana', 'pass123', '2234-5681', 'Zona Norte 4', 2, 2),
(1000000200005, 'Esteban', 'Zamora', 'pass123', '2234-5682', 'Zona Norte 5', 2, 2),
(1000000200006, 'Fabiola', 'Rangel', 'pass123', '2234-5683', 'Zona Norte 6', 2, 2),
(1000000200007, 'Gustavo', 'Acosta', 'pass123', '2234-5684', 'Zona Norte 7', 2, 2),
(1000000200008, 'Irene', 'Bermúdez', 'pass123', '2234-5685', 'Zona Norte 8', 2, 2),
(1000000200009, 'Leonardo', 'Carrillo', 'pass123', '2234-5686', 'Zona Norte 9', 2, 2),
(1000000200010, 'Mónica', 'Duarte', 'pass123', '2234-5687', 'Zona Norte 10', 2, 2),
(1000000200011, 'Nicolás', 'Escobar', 'pass123', '2234-5688', 'Zona Norte 11', 2, 2),
(1000000200012, 'Olga', 'Fuentes', 'pass123', '2234-5689', 'Zona Norte 12', 2, 2),
(1000000200013, 'Pablo', 'Granados', 'pass123', '2234-5690', 'Zona Norte 13', 2, 2),
(1000000200014, 'Queztal', 'Ibarra', 'pass123', '2234-5691', 'Zona Norte 14', 2, 2),
(1000000200015, 'Ramiro', 'Juárez', 'pass123', '2234-5692', 'Zona Norte 15', 2, 2),

-- 3 Encargados de Inventario (id_rol = 3)
(1000000200016, 'Silvia', 'Lara', 'pass123', '2234-5693', 'Zona Norte 16', 2, 3),
(1000000200017, 'Tomás', 'Maldonado', 'pass123', '2234-5694', 'Zona Norte 17', 2, 3),
(1000000200018, 'Úrsula', 'Núñez', 'pass123', '2234-5695', 'Zona Norte 18', 2, 3),

-- 2 Recepcionistas (id_rol = 1)
(1000000200019, 'Víctor', 'Orellana', 'pass123', '2234-5696', 'Zona Norte 19', 2, 1),
(1000000200020, 'Wendy', 'Prieto', 'pass123', '2234-5697', 'Zona Norte 20', 2, 1);

-- Empleados para Sucursal Sur (id_sucursal = 3)
-- 10 Entrenadores (id_rol = 2)
INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol) VALUES
(1000000300001, 'Ximena', 'Quintero', 'pass123', '3234-5678', 'Zona Sur 1', 3, 2),
(1000000300002, 'Yahir', 'Reyes', 'pass123', '3234-5679', 'Zona Sur 2', 3, 2),
(1000000300003, 'Zoe', 'Salazar', 'pass123', '3234-5680', 'Zona Sur 3', 3, 2),
(1000000300004, 'Ángel', 'Torres', 'pass123', '3234-5681', 'Zona Sur 4', 3, 2),
(1000000300005, 'Brenda', 'Urbina', 'pass123', '3234-5682', 'Zona Sur 5', 3, 2),
(1000000300006, 'Cristian', 'Vega', 'pass123', '3234-5683', 'Zona Sur 6', 3, 2),
(1000000300007, 'Dulce', 'Wong', 'pass123', '3234-5684', 'Zona Sur 7', 3, 2),
(1000000300008, 'Emilio', 'Xicará', 'pass123', '3234-5685', 'Zona Sur 8', 3, 2),
(1000000300009, 'Flor', 'Yanes', 'pass123', '3234-5686', 'Zona Sur 9', 3, 2),
(1000000300010, 'Gerardo', 'Zelaya', 'pass123', '3234-5687', 'Zona Sur 10', 3, 2),

-- 2 Encargados de Inventario (id_rol = 3)
(1000000300011, 'Hilda', 'Aguilar', 'pass123', '3234-5688', 'Zona Sur 11', 3, 3),
(1000000300012, 'Ignacio', 'Bolaños', 'pass123', '3234-5689', 'Zona Sur 12', 3, 3),

-- 1 Recepcionista (id_rol = 1)
(1000000300013, 'Jackeline', 'Cabrera', 'pass123', '3234-5690', 'Zona Sur 13', 3, 1);

INSERT INTO cliente (dpi, nombre, apellido, password, telefono, direccion) VALUES
(2000000100001, 'Juan', 'Pérez', 'cliente123', '1234-0001', 'Zona 1, Ciudad'),
(2000000100002, 'María', 'González', 'cliente123', '1234-0002', 'Zona 2, Ciudad'),
(2000000100003, 'Carlos', 'Rodríguez', 'cliente123', '1234-0003', 'Zona 3, Ciudad'),
(2000000100004, 'Ana', 'López', 'cliente123', '1234-0004', 'Zona 4, Ciudad'),
(2000000100005, 'Luis', 'Martínez', 'cliente123', '1234-0005', 'Zona 5, Ciudad'),
(2000000100006, 'Sofia', 'Hernández', 'cliente123', '1234-0006', 'Zona 6, Ciudad'),
(2000000100007, 'Miguel', 'García', 'cliente123', '1234-0007', 'Zona 7, Ciudad'),
(2000000100008, 'Elena', 'Díaz', 'cliente123', '1234-0008', 'Zona 8, Ciudad'),
(2000000100009, 'Javier', 'Fernández', 'cliente123', '1234-0009', 'Zona 9, Ciudad'),
(2000000100010, 'Laura', 'Sánchez', 'cliente123', '1234-0010', 'Zona 10, Ciudad'),

(2000000100011, 'Roberto', 'Ramírez', 'cliente123', '1234-0011', 'Zona 11, Ciudad'),
(2000000100012, 'Carmen', 'Torres', 'cliente123', '1234-0012', 'Zona 12, Ciudad'),
(2000000100013, 'Francisco', 'Flores', 'cliente123', '1234-0013', 'Zona 13, Ciudad'),
(2000000100014, 'Isabel', 'Vargas', 'cliente123', '1234-0014', 'Zona 14, Ciudad'),
(2000000100015, 'Ricardo', 'Castro', 'cliente123', '1234-0015', 'Zona 15, Ciudad'),
(2000000100016, 'Gabriela', 'Romero', 'cliente123', '1234-0016', 'Zona 16, Ciudad'),
(2000000100017, 'Daniel', 'Ortega', 'cliente123', '1234-0017', 'Zona 17, Ciudad'),
(2000000100018, 'Patricia', 'Silva', 'cliente123', '1234-0018', 'Zona 18, Ciudad'),
(2000000100019, 'Alejandro', 'Mendoza', 'cliente123', '1234-0019', 'Zona 19, Ciudad'),
(2000000100020, 'Andrea', 'Rojas', 'cliente123', '1234-0020', 'Zona 20, Ciudad'),

(2000000100021, 'José', 'Guerrero', 'cliente123', '1234-0021', 'Zona 21, Ciudad'),
(2000000100022, 'Diana', 'Navarro', 'cliente123', '1234-0022', 'Zona 22, Ciudad'),
(2000000100023, 'Fernando', 'Molina', 'cliente123', '1234-0023', 'Zona 23, Ciudad'),
(2000000100024, 'Karla', 'Herrera', 'cliente123', '1234-0024', 'Zona 24, Ciudad'),
(2000000100025, 'Pedro', 'Delgado', 'cliente123', '1234-0025', 'Zona 25, Ciudad'),
(2000000100026, 'Natalia', 'Peña', 'cliente123', '1234-0026', 'Zona 26, Ciudad'),
(2000000100027, 'Sergio', 'Ríos', 'cliente123', '1234-0027', 'Zona 27, Ciudad'),
(2000000100028, 'Verónica', 'Medina', 'cliente123', '1234-0028', 'Zona 28, Ciudad'),
(2000000100029, 'Héctor', 'Cordero', 'cliente123', '1234-0029', 'Zona 29, Ciudad'),
(2000000100030, 'Lucía', 'Valdez', 'cliente123', '1234-0030', 'Zona 30, Ciudad'),

(2000000200001, 'Raúl', 'Miranda', 'cliente123', '2234-0001', 'Zona Norte 1'),
(2000000200002, 'Beatriz', 'Soto', 'cliente123', '2234-0002', 'Zona Norte 2'),
(2000000200003, 'César', 'Paredes', 'cliente123', '2234-0003', 'Zona Norte 3'),
(2000000200004, 'Daniela', 'Quintana', 'cliente123', '2234-0004', 'Zona Norte 4'),
(2000000200005, 'Esteban', 'Zamora', 'cliente123', '2234-0005', 'Zona Norte 5'),
(2000000200006, 'Fabiola', 'Rangel', 'cliente123', '2234-0006', 'Zona Norte 6'),
(2000000200007, 'Gustavo', 'Acosta', 'cliente123', '2234-0007', 'Zona Norte 7'),
(2000000200008, 'Irene', 'Bermúdez', 'cliente123', '2234-0008', 'Zona Norte 8'),
(2000000200009, 'Leonardo', 'Carrillo', 'cliente123', '2234-0009', 'Zona Norte 9'),
(2000000200010, 'Mónica', 'Duarte', 'cliente123', '2234-0010', 'Zona Norte 10'),

(2000000200011, 'Nicolás', 'Escobar', 'cliente123', '2234-0011', 'Zona Norte 11'),
(2000000200012, 'Olga', 'Fuentes', 'cliente123', '2234-0012', 'Zona Norte 12'),
(2000000200013, 'Pablo', 'Granados', 'cliente123', '2234-0013', 'Zona Norte 13'),
(2000000200014, 'Queztal', 'Ibarra', 'cliente123', '2234-0014', 'Zona Norte 14'),
(2000000200015, 'Ramiro', 'Juárez', 'cliente123', '2234-0015', 'Zona Norte 15'),
(2000000200016, 'Silvia', 'Lara', 'cliente123', '2234-0016', 'Zona Norte 16'),
(2000000200017, 'Tomás', 'Maldonado', 'cliente123', '2234-0017', 'Zona Norte 17'),
(2000000200018, 'Úrsula', 'Núñez', 'cliente123', '2234-0018', 'Zona Norte 18'),
(2000000200019, 'Víctor', 'Orellana', 'cliente123', '2234-0019', 'Zona Norte 19'),
(2000000200020, 'Wendy', 'Prieto', 'cliente123', '2234-0020', 'Zona Norte 20'),

(2000000300001, 'Ximena', 'Quintero', 'cliente123', '3234-0001', 'Zona Sur 1'),
(2000000300002, 'Yahir', 'Reyes', 'cliente123', '3234-0002', 'Zona Sur 2'),
(2000000300003, 'Zoe', 'Salazar', 'cliente123', '3234-0003', 'Zona Sur 3'),
(2000000300004, 'Ángel', 'Torres', 'cliente123', '3234-0004', 'Zona Sur 4'),
(2000000300005, 'Brenda', 'Urbina', 'cliente123', '3234-0005', 'Zona Sur 5'),
(2000000300006, 'Cristian', 'Vega', 'cliente123', '3234-0006', 'Zona Sur 6'),
(2000000300007, 'Dulce', 'Wong', 'cliente123', '3234-0007', 'Zona Sur 7'),
(2000000300008, 'Emilio', 'Xicará', 'cliente123', '3234-0008', 'Zona Sur 8'),
(2000000300009, 'Flor', 'Yanes', 'cliente123', '3234-0009', 'Zona Sur 9'),
(2000000300010, 'Gerardo', 'Zelaya', 'cliente123', '3234-0010', 'Zona Sur 10');

INSERT INTO membresia_tipo (nombre, descuento) VALUES
('Basica', 0),
('Premiun', 10.00),
('VIP', 20.00);

-- 30 clientes con membresía Básica
INSERT INTO membresia (dpi_cliente, id_tipo, fecha_inicio, fecha_fin) VALUES
(2000000100001, 1, '2025-01-01', '2025-12-31'),
(2000000100002, 1, '2025-01-01', '2025-12-31'),
(2000000100003, 1, '2025-01-01', '2025-12-31'),
(2000000100004, 1, '2025-01-01', '2025-12-31'),
(2000000100005, 1, '2025-01-01', '2025-12-31'),
(2000000100006, 1, '2025-01-01', '2025-12-31'),
(2000000100007, 1, '2025-01-01', '2025-12-31'),
(2000000100008, 1, '2025-01-01', '2025-12-31'),
(2000000100009, 1, '2025-01-01', '2025-12-31'),
(2000000100010, 1, '2025-01-01', '2025-12-31'),

(2000000100011, 1, '2025-01-01', '2025-12-31'),
(2000000100012, 1, '2025-01-01', '2025-12-31'),
(2000000100013, 1, '2025-01-01', '2025-12-31'),
(2000000100014, 1, '2025-01-01', '2025-12-31'),
(2000000100015, 1, '2025-01-01', '2025-12-31'),
(2000000100016, 1, '2025-01-01', '2025-12-31'),
(2000000100017, 1, '2025-01-01', '2025-12-31'),
(2000000100018, 1, '2025-01-01', '2025-12-31'),
(2000000100019, 1, '2025-01-01', '2025-12-31'),
(2000000100020, 1, '2025-01-01', '2025-12-31'),

(2000000100021, 1, '2025-01-01', '2025-12-31'),
(2000000100022, 1, '2025-01-01', '2025-12-31'),
(2000000100023, 1, '2025-01-01', '2025-12-31'),
(2000000100024, 1, '2025-01-01', '2025-12-31'),
(2000000100025, 1, '2025-01-01', '2025-12-31'),
(2000000100026, 1, '2025-01-01', '2025-12-31'),
(2000000100027, 1, '2025-01-01', '2025-12-31'),
(2000000100028, 1, '2025-01-01', '2025-12-31'),
(2000000100029, 1, '2025-01-01', '2025-12-31'),
(2000000100030, 1, '2025-01-01', '2025-12-31');

-- 20 clientes con membresía Premium
INSERT INTO membresia (dpi_cliente, id_tipo, fecha_inicio, fecha_fin) VALUES
(2000000200001, 2, '2025-01-01', '2025-12-31'),
(2000000200002, 2, '2025-01-01', '2025-12-31'),
(2000000200003, 2, '2025-01-01', '2025-12-31'),
(2000000200004, 2, '2025-01-01', '2025-12-31'),
(2000000200005, 2, '2025-01-01', '2025-12-31'),
(2000000200006, 2, '2025-01-01', '2025-12-31'),
(2000000200007, 2, '2025-01-01', '2025-12-31'),
(2000000200008, 2, '2025-01-01', '2025-12-31'),
(2000000200009, 2, '2025-01-01', '2025-12-31'),
(2000000200010, 2, '2025-01-01', '2025-12-31'),

(2000000200011, 2, '2025-01-01', '2025-12-31'),
(2000000200012, 2, '2025-01-01', '2025-12-31'),
(2000000200013, 2, '2025-01-01', '2025-12-31'),
(2000000200014, 2, '2025-01-01', '2025-12-31'),
(2000000200015, 2, '2025-01-01', '2025-12-31'),
(2000000200016, 2, '2025-01-01', '2025-12-31'),
(2000000200017, 2, '2025-01-01', '2025-12-31'),
(2000000200018, 2, '2025-01-01', '2025-12-31'),
(2000000200019, 2, '2025-01-01', '2025-12-31'),
(2000000200020, 2, '2025-01-01', '2025-12-31');

-- 10 clientes con membresía VIP
INSERT INTO membresia (dpi_cliente, id_tipo, fecha_inicio, fecha_fin) VALUES
(2000000300001, 3, '2025-01-01', '2025-12-31'),
(2000000300002, 3, '2025-01-01', '2025-12-31'),
(2000000300003, 3, '2025-01-01', '2025-12-31'),
(2000000300004, 3, '2025-01-01', '2025-12-31'),
(2000000300005, 3, '2025-01-01', '2025-12-31'),
(2000000300006, 3, '2025-01-01', '2025-12-31'),
(2000000300007, 3, '2025-01-01', '2025-12-31'),
(2000000300008, 3, '2025-01-01', '2025-12-31'),
(2000000300009, 3, '2025-01-01', '2025-12-31'),
(2000000300010, 3, '2025-01-01', '2025-12-31');

INSERT INTO adicional (nombre, detalles, precio, dpi_entrenador) VALUES
-- Servicios con entrenador
('Entrenamiento Personalizado', 'Sesión individual con entrenador certificado', 150.00, 1000000100012),
('Entrenamiento Avanzado', 'Rutina intensiva para fuerza y resistencia', 180.00, 1000000100013),
('Clase Funcional', 'Entrenamiento grupal funcional de alta intensidad', 100.00, 1000000100014),
('Boxeo Personalizado', 'Clases de boxeo con entrenador especializado', 160.00, 1000000200010),
('Pilates con Instructor', 'Sesiones personalizadas de pilates', 140.00, 1000000200011),
('Crossfit', 'Sesión de entrenamiento tipo crossfit con entrenador', 170.00, 1000000200012),
('Zumba', 'Clase de baile fitness dirigida por entrenador', 90.00, 1000000300007),
('Spinning', 'Clase de ciclismo indoor con entrenador certificado', 110.00, 1000000300008),

-- Productos y servicios generales
('Suplementos Proteicos', 'Paquete mensual de proteínas y vitaminas', 250.00, NULL),
('Masaje Relajante', 'Sesión de 60 minutos de masaje terapéutico', 200.00, NULL),
('Plan Nutricional', 'Asesoría nutricional personalizada', 120.00, NULL),
('Clases de Yoga', 'Sesión grupal de yoga cada fin de semana', 80.00, NULL),
('Acceso a Sauna', 'Uso ilimitado de sauna durante 1 mes', 60.00, NULL),
('Renta de Locker', 'Locker privado con llave por 1 mes', 40.00, NULL),
('Kit Deportivo', 'Paquete con camiseta, toalla y botella deportiva', 90.00, NULL);

-- PAGOS DE MEMBRESÍA (tipo = servicio)
INSERT INTO pago (dpi_cliente, tipo, descripcion, monto, fecha_inicio, fecha_fin, id_adicional) VALUES
-- 30 clientes Básica
(2000000100001, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100002, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100003, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100004, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100005, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100006, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100007, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100008, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100009, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100010, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100011, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100012, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100013, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100014, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100015, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100016, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100017, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100018, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100019, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100020, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100021, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100022, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100023, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100024, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100025, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100026, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100027, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100028, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100029, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),
(2000000100030, 'servicio', 'Servicio de Membresía: Básica', 250.00, '2025-01-01', '2025-12-31', NULL),

-- 20 clientes Premium
(2000000200001, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200002, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200003, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200004, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200005, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200006, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200007, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200008, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200009, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200010, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200011, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200012, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200013, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200014, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200015, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200016, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200017, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200018, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200019, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),
(2000000200020, 'servicio', 'Servicio de Membresía: Premium', 360.00, '2025-01-01', '2025-12-31', NULL),

-- 10 clientes VIP
(2000000300001, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300002, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300003, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300004, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300005, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300006, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300007, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300008, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300009, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL),
(2000000300010, 'servicio', 'Servicio de Membresía: VIP', 480.00, '2025-01-01', '2025-12-31', NULL);



-- PAGOS ADICIONALES (60 registros, seleccionando clientes al azar)
INSERT INTO pago (dpi_cliente, tipo, descripcion, monto, fecha_inicio, fecha_fin, id_adicional) VALUES
-- Clientes básicos
(2000000100001, 'adicional', 'Entrenamiento Personalizado', 150.00, '2025-02-10', NULL, 1),
(2000000100002, 'adicional', 'Suplementos Proteicos', 250.00, '2025-02-12', NULL, 9),
(2000000100003, 'adicional', 'Clase Funcional', 100.00, '2025-02-15', NULL, 3),
(2000000100004, 'adicional', 'Masaje Relajante', 200.00, '2025-02-18', NULL, 10),
(2000000100005, 'adicional', 'Plan Nutricional', 120.00, '2025-02-20', NULL, 11),
(2000000100006, 'adicional', 'Clase de Yoga', 80.00, '2025-02-22', NULL, 12),
(2000000100007, 'adicional', 'Acceso a Sauna', 60.00, '2025-02-24', NULL, 13),
(2000000100008, 'adicional', 'Renta de Locker', 40.00, '2025-02-26', NULL, 14),
(2000000100009, 'adicional', 'Kit Deportivo', 90.00, '2025-02-28', NULL, 15),
(2000000100010, 'adicional', 'Spinning', 110.00, '2025-03-01', NULL, 8),

-- Clientes premium
(2000000200001, 'adicional', 'Crossfit', 170.00, '2025-03-03', NULL, 6),
(2000000200002, 'adicional', 'Pilates con Instructor', 140.00, '2025-03-05', NULL, 5),
(2000000200003, 'adicional', 'Boxeo Personalizado', 160.00, '2025-03-07', NULL, 4),
(2000000200004, 'adicional', 'Entrenamiento Avanzado', 180.00, '2025-03-09', NULL, 2),
(2000000200005, 'adicional', 'Clase Funcional', 100.00, '2025-03-11', NULL, 3),
(2000000200006, 'adicional', 'Suplementos Proteicos', 250.00, '2025-03-13', NULL, 9),
(2000000200007, 'adicional', 'Plan Nutricional', 120.00, '2025-03-15', NULL, 11),
(2000000200008, 'adicional', 'Masaje Relajante', 200.00, '2025-03-17', NULL, 10),
(2000000200009, 'adicional', 'Clase de Yoga', 80.00, '2025-03-19', NULL, 12),
(2000000200010, 'adicional', 'Acceso a Sauna', 60.00, '2025-03-21', NULL, 13),

-- Clientes VIP
(2000000300001, 'adicional', 'Entrenamiento Personalizado', 150.00, '2025-03-23', NULL, 1),
(2000000300002, 'adicional', 'Crossfit', 170.00, '2025-03-25', NULL, 6),
(2000000300003, 'adicional', 'Spinning', 110.00, '2025-03-27', NULL, 8),
(2000000300004, 'adicional', 'Pilates con Instructor', 140.00, '2025-03-29', NULL, 5),
(2000000300005, 'adicional', 'Boxeo Personalizado', 160.00, '2025-03-31', NULL, 4),
(2000000300006, 'adicional', 'Plan Nutricional', 120.00, '2025-04-02', NULL, 11),
(2000000300007, 'adicional', 'Clase Funcional', 100.00, '2025-04-04', NULL, 3),
(2000000300008, 'adicional', 'Suplementos Proteicos', 250.00, '2025-04-06', NULL, 9),
(2000000300009, 'adicional', 'Masaje Relajante', 200.00, '2025-04-08', NULL, 10),
(2000000300010, 'adicional', 'Clase de Yoga', 80.00, '2025-04-10', NULL, 12),

-- Más clientes variados (aleatorio)
(2000000100011, 'adicional', 'Renta de Locker', 40.00, '2025-04-12', NULL, 14),
(2000000100012, 'adicional', 'Kit Deportivo', 90.00, '2025-04-14', NULL, 15),
(2000000100013, 'adicional', 'Entrenamiento Avanzado', 180.00, '2025-04-16', NULL, 2),
(2000000100014, 'adicional', 'Boxeo Personalizado', 160.00, '2025-04-18', NULL, 4),
(2000000100015, 'adicional', 'Pilates con Instructor', 140.00, '2025-04-20', NULL, 5),
(2000000100016, 'adicional', 'Crossfit', 170.00, '2025-04-22', NULL, 6),
(2000000100017, 'adicional', 'Clase Funcional', 100.00, '2025-04-24', NULL, 3),
(2000000100018, 'adicional', 'Entrenamiento Personalizado', 150.00, '2025-04-26', NULL, 1),
(2000000100019, 'adicional', 'Spinning', 110.00, '2025-04-28', NULL, 8),
(2000000100020, 'adicional', 'Acceso a Sauna', 60.00, '2025-04-30', NULL, 13),

-- Completar hasta 60 pagos con combinaciones similares
(2000000200011, 'adicional', 'Plan Nutricional', 120.00, '2025-05-02', NULL, 11),
(2000000200012, 'adicional', 'Suplementos Proteicos', 250.00, '2025-05-04', NULL, 9),
(2000000200013, 'adicional', 'Masaje Relajante', 200.00, '2025-05-06', NULL, 10),
(2000000200014, 'adicional', 'Clase de Yoga', 80.00, '2025-05-08', NULL, 12),
(2000000200015, 'adicional', 'Renta de Locker', 40.00, '2025-05-10', NULL, 14),
(2000000200016, 'adicional', 'Kit Deportivo', 90.00, '2025-05-12', NULL, 15),
(2000000200017, 'adicional', 'Entrenamiento Avanzado', 180.00, '2025-05-14', NULL, 2),
(2000000200018, 'adicional', 'Boxeo Personalizado', 160.00, '2025-05-16', NULL, 4),
(2000000200019, 'adicional', 'Pilates con Instructor', 140.00, '2025-05-18', NULL, 5),
(2000000200020, 'adicional', 'Crossfit', 170.00, '2025-05-20', NULL, 6),

(2000000300001, 'adicional', 'Clase Funcional', 100.00, '2025-05-22', NULL, 3),
(2000000300002, 'adicional', 'Entrenamiento Personalizado', 150.00, '2025-05-24', NULL, 1),
(2000000300003, 'adicional', 'Spinning', 110.00, '2025-05-26', NULL, 8),
(2000000300004, 'adicional', 'Acceso a Sauna', 60.00, '2025-05-28', NULL, 13),
(2000000300005, 'adicional', 'Plan Nutricional', 120.00, '2025-05-30', NULL, 11),
(2000000300006, 'adicional', 'Suplementos Proteicos', 250.00, '2025-06-01', NULL, 9),
(2000000300007, 'adicional', 'Masaje Relajante', 200.00, '2025-06-03', NULL, 10),
(2000000300008, 'adicional', 'Clase de Yoga', 80.00, '2025-06-05', NULL, 12),
(2000000300009, 'adicional', 'Renta de Locker', 40.00, '2025-06-07', NULL, 14),
(2000000300010, 'adicional', 'Kit Deportivo', 90.00, '2025-06-09', NULL, 15);

INSERT INTO equipo (nombre, descripcion, tipo) VALUES
('Banca Plana', 'Banco para press banca y ejercicios de pecho', 'maquina'),
('Rack de Sentadillas', 'Estructura para sentadillas y peso muerto', 'maquina'),
('Mancuernas', 'Juego de mancuernas de distintos pesos', 'otro'),
('Cinta de Correr', 'Máquina para correr indoor', 'maquina'),
('Bicicleta Estática', 'Cardio de bajo impacto', 'maquina'),
('Cuerda para Saltar', 'Accesorio para cardio', 'otro'),
('Máquina de Remo', 'Cardio y fuerza de espalda', 'maquina'),
('Kettlebells', 'Pesas rusas de distintos tamaños', 'otro'),
('Colchoneta Yoga', 'Accesorio para yoga y estiramientos', 'otro');

INSERT INTO inventario (id_sucursal, id_equipo, cantidad)
VALUES
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Cinta de Correr'), 5),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Máquina de Remo'), 5),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Bicicleta Estática'), 10),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Rack de Sentadillas'), 5),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Banca Plana'), 10),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Colchoneta Yoga'), 5),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Central'), (SELECT id_equipo FROM equipo WHERE nombre='Mancuernas'), 10),

  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Cinta de Correr'), 6),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Bicicleta Estática'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Rack de Sentadillas'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Banca Plana'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Colchoneta Yoga'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Norte'), (SELECT id_equipo FROM equipo WHERE nombre='Mancuernas'), 8),

  ((SELECT id_sucursal FROM sucursal WHERE nombre='Sur'), (SELECT id_equipo FROM equipo WHERE nombre='Cinta de Correr'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Sur'), (SELECT id_equipo FROM equipo WHERE nombre='Bicicleta Estática'), 5),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Sur'), (SELECT id_equipo FROM equipo WHERE nombre='Rack de Sentadillas'), 2),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Sur'), (SELECT id_equipo FROM equipo WHERE nombre='Banca Plana'), 4),
  ((SELECT id_sucursal FROM sucursal WHERE nombre='Sur'), (SELECT id_equipo FROM equipo WHERE nombre='Mancuernas'), 5);

INSERT INTO entrenador_cliente (dpi_entrenador, dpi_cliente, fecha_asignacion, fecha_fin) VALUES
-- Entrenador 1000000100001 (Carlos Martínez) - 2 clientes
(1000000100001, 2000000100001, '2025-01-15', '2025-07-15'),
(1000000100001, 2000000200001, '2025-02-01', '2025-06-30'),

-- Entrenador 1000000100002 (Ana López) - 2 clientes
(1000000100002, 2000000100002, '2025-01-20', '2025-07-20'),
(1000000100002, 2000000200002, '2025-03-10', '2025-09-10'),

-- Entrenador 1000000100003 (Miguel García) - 2 clientes
(1000000100003, 2000000100003, '2025-02-05', '2025-08-15'),
(1000000100003, 2000000300001, '2025-04-01', '2025-10-01'),

-- Entrenador 1000000100004 (Sofia Rodríguez) - 2 clientes
(1000000100004, 2000000100004, '2025-01-10', '2025-07-10'),
(1000000100004, 2000000200003, '2025-03-20', '2025-09-20'),

-- Entrenador 1000000100005 (Javier Hernández) - 2 clientes
(1000000100005, 2000000100005, '2025-02-15', '2025-09-30'),
(1000000100005, 2000000200004, '2025-05-01', '2025-11-01'),

-- Entrenador 1000000100006 (Laura Pérez) - 2 clientes
(1000000100006, 2000000100006, '2025-01-25', '2025-07-25'),
(1000000100006, 2000000300002, '2025-04-10', '2025-10-10'),

-- Entrenador 1000000100007 (Daniel Gómez) - 2 clientes
(1000000100007, 2000000100007, '2025-02-20', '2025-08-20'),
(1000000100007, 2000000200005, '2025-06-01', '2025-11-30'),

-- Entrenador 1000000100008 (Elena Díaz) - 2 clientes
(1000000100008, 2000000100008, '2025-03-01', '2025-09-01'),
(1000000100008, 2000000200006, '2025-05-15', '2025-11-15'),

-- Entrenador 1000000100009 (Roberto Fernández) - 2 clientes
(1000000100009, 2000000100009, '2025-01-30', '2025-10-31'),
(1000000100009, 2000000300003, '2025-07-01', '2025-12-31'),

-- Entrenador 1000000100010 (Carmen Santos) - 2 clientes
(1000000100010, 2000000100010, '2025-02-10', '2025-08-10'),
(1000000100010, 2000000200007, '2025-04-20', '2025-10-20'),

-- Entrenador 1000000100011 (Francisco Castillo) - 2 clientes
(1000000100011, 2000000100011, '2025-03-15', '2025-09-15'),
(1000000100011, 2000000200008, '2025-06-10', '2025-12-15'),

-- Entrenador 1000000100012 (Patricia Ramírez) - 2 clientes
(1000000100012, 2000000100012, '2025-02-25', '2025-08-25'),
(1000000100012, 2000000300004, '2025-08-01', '2025-12-31'),

-- Entrenador 1000000100013 (Antonio Cruz) - 2 clientes
(1000000100013, 2000000100013, '2025-03-20', '2025-09-20'),
(1000000100013, 2000000200009, '2025-07-15', '2025-12-31'),

-- Entrenador 1000000100014 (Isabel Morales) - 2 clientes
(1000000100014, 2000000100014, '2025-01-05', '2025-07-31'),
(1000000100014, 2000000200010, '2025-09-01', '2025-12-31'),

-- Entrenador 1000000100015 (Ricardo Ortiz) - 2 clientes
(1000000100015, 2000000100015, '2025-04-05', '2025-10-05'),
(1000000100015, 2000000300005, '2025-10-01', '2025-12-31'),

-- Entrenador 1000000200001 (Alejandro Miranda) - 2 clientes
(1000000200001, 2000000100016, '2025-02-28', '2025-08-28'),
(1000000200001, 2000000200011, '2025-05-20', '2025-11-20'),

-- Entrenador 1000000200002 (Beatriz Soto) - 2 clientes
(1000000200002, 2000000100017, '2025-03-10', '2025-11-30'),
(1000000200002, 2000000200012, '2025-08-15', '2025-12-31'),

-- Entrenador 1000000200003 (César Paredes) - 2 clientes
(1000000200003, 2000000100018, '2025-04-15', '2025-10-15'),
(1000000200003, 2000000300006, '2025-09-10', '2025-12-31'),

-- Entrenador 1000000200004 (Daniela Quintana) - 2 clientes
(1000000200004, 2000000100019, '2025-01-20', '2025-07-20'),
(1000000200004, 2000000200013, '2025-06-25', '2025-12-25'),

-- Entrenador 1000000200005 (Esteban Zamora) - 2 clientes
(1000000200005, 2000000100020, '2025-03-05', '2025-09-05'),
(1000000200005, 2000000200014, '2025-07-20', '2025-12-20'),

-- Entrenador 1000000200006 (Fabiola Rangel) - 2 clientes
(1000000200006, 2000000100021, '2025-02-15', '2025-08-15'),
(1000000200006, 2000000300007, '2025-10-15', '2025-12-31'),

-- Entrenador 1000000200007 (Gustavo Acosta) - 2 clientes
(1000000200007, 2000000100022, '2025-04-01', '2025-10-01'),
(1000000200007, 2000000200015, '2025-08-30', '2025-12-31'),

-- Entrenador 1000000200008 (Irene Bermúdez) - 2 clientes
(1000000200008, 2000000100023, '2025-01-15', '2025-08-31'),
(1000000200008, 2000000200016, '2025-11-01', '2025-12-31'),

-- Entrenador 1000000200009 (Leonardo Carrillo) - 2 clientes
(1000000200009, 2000000100024, '2025-03-25', '2025-09-25'),
(1000000200009, 2000000300008, '2025-09-05', '2025-12-31'),

-- Entrenador 1000000200010 (Mónica Duarte) - 2 clientes
(1000000200010, 2000000100025, '2025-02-10', '2025-08-10'),
(1000000200010, 2000000200017, '2025-05-30', '2025-11-30'),

-- Entrenador 1000000200011 (Nicolás Escobar) - 2 clientes
(1000000200011, 2000000100026, '2025-04-20', '2025-10-15'),
(1000000200011, 2000000200018, '2025-12-01', '2025-12-31'),

-- Entrenador 1000000200012 (Olga Fuentes) - 2 clientes
(1000000200012, 2000000100027, '2025-01-30', '2025-07-30'),
(1000000200012, 2000000300009, '2025-07-10', '2025-12-31'),

-- Entrenador 1000000200013 (Pablo Granados) - 2 clientes
(1000000200013, 2000000100028, '2025-03-15', '2025-09-15'),
(1000000200013, 2000000200019, '2025-08-01', '2025-12-31'),

-- Entrenador 1000000200014 (Queztal Ibarra) - 2 clientes
(1000000200014, 2000000100029, '2025-02-20', '2025-09-15'),
(1000000200014, 2000000200020, '2025-10-20', '2025-12-31'),

-- Entrenador 1000000200015 (Ramiro Juárez) - 2 clientes
(1000000200015, 2000000100030, '2025-05-05', '2025-11-05'),
(1000000200015, 2000000300010, '2025-11-15', '2025-12-31'),

-- Entrenadores de la zona sur con asignaciones adicionales
(1000000300001, 2000000100001, '2025-07-16', '2025-12-31'),
(1000000300002, 2000000100002, '2025-07-21', '2025-12-31'),
(1000000300003, 2000000200003, '2025-09-21', '2025-12-31'),
(1000000300004, 2000000100004, '2025-07-11', '2025-12-31');

-- Inserts para la tabla rutina
INSERT INTO rutina (nombre, tipo, fecha_inicio, dpi_entrenador, dpi_cliente) VALUES
-- Cliente 2000000100001 con entrenador 1000000100001 (3 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-01-15', 1000000100001, 2000000100001),
('Cardio Básico', 'cardio', '2025-03-01', 1000000100001, 2000000100001),
('Full Body Express', 'fuerza', '2025-05-15', 1000000100001, 2000000100001),

-- Cliente 2000000200001 con entrenador 1000000100001 (3 rutinas)
('Mixto Avanzado', 'mixto', '2025-02-01', 1000000100001, 2000000200001),
('HIIT Quemagrasa', 'cardio', '2025-04-01', 1000000100001, 2000000200001),
('Power Building', 'fuerza', '2025-05-15', 1000000100001, 2000000200001),

-- Cliente 2000000100002 con entrenador 1000000100002 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-01-20', 1000000100002, 2000000100002),
('Cardio Intervalos', 'cardio', '2025-03-10', 1000000100002, 2000000100002),
('Fuerza Superior', 'fuerza', '2025-05-01', 1000000100002, 2000000100002),
('Circuito Completo', 'mixto', '2025-06-15', 1000000100002, 2000000100002),

-- Cliente 2000000200002 con entrenador 1000000100002 (3 rutinas)
('Running Progressivo', 'cardio', '2025-03-10', 1000000100002, 2000000200002),
('Push-Pull-Legs', 'fuerza', '2025-05-01', 1000000100002, 2000000200002),
('Cross Training', 'mixto', '2025-07-01', 1000000100002, 2000000200002),

-- Cliente 2000000100003 con entrenador 1000000100003 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-02-05', 1000000100003, 2000000100003),
('Yoga Flow', 'flexibilidad', '2025-04-01', 1000000100003, 2000000100003),
('Core Intenso', 'fuerza', '2025-06-01', 1000000100003, 2000000100003),
('Spinning Interválico', 'cardio', '2025-07-15', 1000000100003, 2000000100003),

-- Cliente 2000000300001 con entrenador 1000000100003 (3 rutinas)
('Full Body Toning', 'mixto', '2025-04-01', 1000000100003, 2000000300001),
('Powerlifting Basics', 'fuerza', '2025-06-01', 1000000100003, 2000000300001),
('Cardio Steady State', 'cardio', '2025-08-01', 1000000100003, 2000000300001),

-- Cliente 2000000100004 con entrenador 1000000100004 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-01-10', 1000000100004, 2000000100004),
('Functional Training', 'mixto', '2025-03-01', 1000000100004, 2000000100004),
('Flexibilidad Avanzada', 'flexibilidad', '2025-05-01', 1000000100004, 2000000100004),
('HIIT Quemagrasa', 'cardio', '2025-06-15', 1000000100004, 2000000100004),

-- Cliente 2000000200003 con entrenador 1000000100004 (3 rutinas)
('Mixto Avanzado', 'mixto', '2025-03-20', 1000000100004, 2000000200003),
('Cardio Básico', 'cardio', '2025-05-20', 1000000100004, 2000000200003),
('Fuerza Superior', 'fuerza', '2025-07-20', 1000000100004, 2000000200003),

-- Cliente 2000000100005 con entrenador 1000000100005 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-02-15', 1000000100005, 2000000100005),
('Running Progressivo', 'cardio', '2025-04-15', 1000000100005, 2000000100005),
('Push-Pull-Legs', 'fuerza', '2025-06-15', 1000000100005, 2000000100005),
('Yoga Flow', 'flexibilidad', '2025-08-15', 1000000100005, 2000000100005),

-- Cliente 2000000200004 con entrenador 1000000100005 (3 rutinas)
('Full Body Express', 'fuerza', '2025-05-01', 1000000100005, 2000000200004),
('Cardio Intervalos', 'cardio', '2025-07-01', 1000000100005, 2000000200004),
('Cross Training', 'mixto', '2025-09-01', 1000000100005, 2000000200004),

-- Cliente 2000000100006 con entrenador 1000000100006 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-01-25', 1000000100006, 2000000100006),
('Core Intenso', 'fuerza', '2025-03-25', 1000000100006, 2000000100006),
('Spinning Interválico', 'cardio', '2025-05-25', 1000000100006, 2000000100006),
('Functional Training', 'mixto', '2025-07-01', 1000000100006, 2000000100006),

-- Cliente 2000000300002 con entrenador 1000000100006 (3 rutinas)
('Full Body Toning', 'mixto', '2025-04-10', 1000000100006, 2000000300002),
('Powerlifting Basics', 'fuerza', '2025-06-10', 1000000100006, 2000000300002),
('Flexibilidad Avanzada', 'flexibilidad', '2025-08-10', 1000000100006, 2000000300002),

-- Cliente 2000000100007 con entrenador 1000000100007 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-02-20', 1000000100007, 2000000100007),
('HIIT Quemagrasa', 'cardio', '2025-04-20', 1000000100007, 2000000100007),
('Circuito Completo', 'mixto', '2025-06-20', 1000000100007, 2000000100007),
('Cardio Steady State', 'cardio', '2025-07-20', 1000000100007, 2000000100007),

-- Cliente 2000000200005 con entrenador 1000000100007 (3 rutinas)
('Mixto Avanzado', 'mixto', '2025-06-01', 1000000100007, 2000000200005),
('Fuerza Superior', 'fuerza', '2025-07-15', 1000000100007, 2000000200005),
('Running Progressivo', 'cardio', '2025-09-01', 1000000100007, 2000000200005),

-- Cliente 2000000100008 con entrenador 1000000100008 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-03-01', 1000000100008, 2000000100008),
('Yoga Flow', 'flexibilidad', '2025-05-01', 1000000100008, 2000000100008),
('Push-Pull-Legs', 'fuerza', '2025-07-01', 1000000100008, 2000000100008),
('Spinning Interválico', 'cardio', '2025-08-15', 1000000100008, 2000000100008),

-- Cliente 2000000200006 con entrenador 1000000100008 (3 rutinas)
('Full Body Express', 'fuerza', '2025-05-15', 1000000100008, 2000000200006),
('Cardio Básico', 'cardio', '2025-07-15', 1000000100008, 2000000200006),
('Cross Training', 'mixto', '2025-09-15', 1000000100008, 2000000200006),

-- Continuar con el mismo patrón para los demás clientes...
-- Cliente 2000000100009 con entrenador 1000000100009 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-01-30', 1000000100009, 2000000100009),
('Core Intenso', 'fuerza', '2025-04-15', 1000000100009, 2000000100009),
('Functional Training', 'mixto', '2025-07-01', 1000000100009, 2000000100009),
('Cardio Intervalos', 'cardio', '2025-09-01', 1000000100009, 2000000100009),

-- Cliente 2000000300003 con entrenador 1000000100009 (3 rutinas)
('Full Body Toning', 'mixto', '2025-07-01', 1000000100009, 2000000300003),
('Powerlifting Basics', 'fuerza', '2025-08-15', 1000000100009, 2000000300003),
('Flexibilidad Avanzada', 'flexibilidad', '2025-10-01', 1000000100009, 2000000300003),

-- Cliente 2000000100010 con entrenador 1000000100010 (4 rutinas)
('Rutina Fuerza Inicial', 'fuerza', '2025-02-10', 1000000100010, 2000000100010),
('HIIT Quemagrasa', 'cardio', '2025-04-10', 1000000100010, 2000000100010),
('Circuito Completo', 'mixto', '2025-06-10', 1000000100010, 2000000100010),
('Yoga Flow', 'flexibilidad', '2025-07-10', 1000000100010, 2000000100010),

-- Cliente 2000000200007 con entrenador 1000000100010 (3 rutinas)
('Mixto Avanzado', 'mixto', '2025-04-20', 1000000100010, 2000000200007),
('Fuerza Superior', 'fuerza', '2025-06-20', 1000000100010, 2000000200007),
('Running Progressivo', 'cardio', '2025-08-20', 1000000100010, 2000000200007);

-- Fuerza (usa equipos de pesas)
INSERT INTO ejercicio (nombre, series, repeticiones, duracion_min, id_equipo) VALUES
('Press de Banca', 4, 10, 0, 1),
('Sentadillas con Barra', 4, 12, 0, 2),
('Peso Muerto', 3, 8, 0, 2),
('Press Militar', 4, 10, 0, 1),
('Curl de Bíceps', 3, 12, 0, 3),
('Remo con Barra', 4, 10, 0, 2),
('Kettlebell Swings', 4, 15, 0, 8),
('Correr en Cinta', 0, 0, 30, 4),
('Bicicleta Estática', 0, 0, 40, 5),
('Saltar la Cuerda', 0, 0, 15, 6),
('Máquina de Remo', 0, 0, 20, 7),
('HIIT en Cinta', 0, 0, 20, 4),
('Burpees', 5, 15, 0, NULL),
('Plancha Abdominal', 3, 0, 2, NULL),
('Russian Twists', 4, 20, 0, NULL),
('Postura del Perro', 3, 0, 2, 9),
('Postura del Guerrero', 3, 0, 2, 9),
('Estiramiento de Cuádriceps', 3, 0, 1, 9),
('Estiramiento de Isquiotibiales', 3, 0, 1, 9);

-- Inserts para rutina_ejercicio - Asignación de ejercicios a rutinas
INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio, orden) VALUES
-- Rutina 1: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100001
(1, 2, 1),   -- Sentadillas con Barra
(1, 1, 2),   -- Press de Banca
(1, 6, 3),   -- Remo con Barra
(1, 4, 4),   -- Press Militar
(1, 5, 5),   -- Curl de Bíceps

-- Rutina 2: 'Cardio Básico' (cardio) - Cliente 2000000100001
(2, 8, 1),   -- Correr en Cinta
(2, 9, 2),   -- Bicicleta Estática
(2, 10, 3),  -- Saltar la Cuerda

-- Rutina 3: 'Full Body Express' (fuerza) - Cliente 2000000100001
(3, 2, 1),   -- Sentadillas con Barra
(3, 1, 2),   -- Press de Banca
(3, 3, 3),   -- Peso Muerto
(3, 4, 4),   -- Press Militar

-- Rutina 4: 'Mixto Avanzado' (mixto) - Cliente 2000000200001
(4, 2, 1),   -- Sentadillas con Barra
(4, 8, 2),   -- Correr en Cinta
(4, 1, 3),   -- Press de Banca
(4, 11, 4),  -- Máquina de Remo

-- Rutina 5: 'HIIT Quemagrasa' (cardio) - Cliente 2000000200001
(5, 12, 1),  -- HIIT en Cinta
(5, 13, 2),  -- Burpees
(5, 10, 3),  -- Saltar la Cuerda

-- Rutina 6: 'Power Building' (fuerza) - Cliente 2000000200001
(6, 3, 1),   -- Peso Muerto
(6, 1, 2),   -- Press de Banca
(6, 2, 3),   -- Sentadillas con Barra
(6, 7, 4),   -- Kettlebell Swings

-- Rutina 7: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100002
(7, 1, 1),   -- Press de Banca
(7, 2, 2),   -- Sentadillas con Barra
(7, 4, 3),   -- Press Militar
(7, 6, 4),   -- Remo con Barra

-- Rutina 8: 'Cardio Intervalos' (cardio) - Cliente 2000000100002
(8, 12, 1),  -- HIIT en Cinta
(8, 9, 2),   -- Bicicleta Estática
(8, 11, 3),  -- Máquina de Remo

-- Rutina 9: 'Fuerza Superior' (fuerza) - Cliente 2000000100002
(9, 1, 1),   -- Press de Banca
(9, 4, 2),   -- Press Militar
(9, 6, 3),   -- Remo con Barra
(9, 5, 4),   -- Curl de Bíceps

-- Rutina 10: 'Circuito Completo' (mixto) - Cliente 2000000100002
(10, 2, 1),  -- Sentadillas con Barra
(10, 8, 2),  -- Correr en Cinta
(10, 1, 3),  -- Press de Banca
(10, 13, 4), -- Burpees

-- Rutina 11: 'Running Progressivo' (cardio) - Cliente 2000000200002
(11, 8, 1),  -- Correr en Cinta
(11, 10, 2), -- Saltar la Cuerda
(11, 9, 3),  -- Bicicleta Estática

-- Rutina 12: 'Push-Pull-Legs' (fuerza) - Cliente 2000000200002
(12, 1, 1),  -- Press de Banca (Push)
(12, 6, 2),  -- Remo con Barra (Pull)
(12, 2, 3),  -- Sentadillas con Barra (Legs)
(12, 4, 4),  -- Press Militar (Push)

-- Rutina 13: 'Cross Training' (mixto) - Cliente 2000000200002
(13, 7, 1),  -- Kettlebell Swings
(13, 8, 2),  -- Correr en Cinta
(13, 2, 3),  -- Sentadillas con Barra
(13, 11, 4), -- Máquina de Remo

-- Rutina 14: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100003
(14, 2, 1),  -- Sentadillas con Barra
(14, 1, 2),  -- Press de Banca
(14, 6, 3),  -- Remo con Barra
(14, 5, 4),  -- Curl de Bíceps

-- Rutina 15: 'Yoga Flow' (flexibilidad) - Cliente 2000000100003
(15, 16, 1), -- Postura del Perro
(15, 17, 2), -- Postura del Guerrero
(15, 18, 3), -- Estiramiento de Cuádriceps
(15, 19, 4), -- Estiramiento de Isquiotibiales

-- Rutina 16: 'Core Intenso' (fuerza) - Cliente 2000000100003
(16, 14, 1), -- Plancha Abdominal
(16, 15, 2), -- Russian Twists
(16, 13, 3), -- Burpees
(16, 7, 4),  -- Kettlebell Swings

-- Rutina 17: 'Spinning Interválico' (cardio) - Cliente 2000000100003
(17, 9, 1),  -- Bicicleta Estática
(17, 12, 2), -- HIIT en Cinta

-- Rutina 18: 'Full Body Toning' (mixto) - Cliente 2000000300001
(18, 2, 1),  -- Sentadillas con Barra
(18, 1, 2),  -- Press de Banca
(18, 8, 3),  -- Correr en Cinta
(18, 14, 4), -- Plancha Abdominal

-- Rutina 19: 'Powerlifting Basics' (fuerza) - Cliente 2000000300001
(19, 3, 1),  -- Peso Muerto
(19, 1, 2),  -- Press de Banca
(19, 2, 3),  -- Sentadillas con Barra

-- Rutina 20: 'Cardio Steady State' (cardio) - Cliente 2000000300001
(20, 8, 1),  -- Correr en Cinta
(20, 9, 2),  -- Bicicleta Estática
(20, 11, 3), -- Máquina de Remo

-- Rutina 21: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100004
(21, 1, 1),  -- Press de Banca
(21, 2, 2),  -- Sentadillas con Barra
(21, 6, 3),  -- Remo con Barra
(21, 4, 4),  -- Press Militar

-- Rutina 22: 'Functional Training' (mixto) - Cliente 2000000100004
(22, 7, 1),  -- Kettlebell Swings
(22, 13, 2), -- Burpees
(22, 2, 3),  -- Sentadillas con Barra
(22, 14, 4), -- Plancha Abdominal

-- Rutina 23: 'Flexibilidad Avanzada' (flexibilidad) - Cliente 2000000100004
(23, 16, 1), -- Postura del Perro
(23, 17, 2), -- Postura del Guerrero
(23, 18, 3), -- Estiramiento de Cuádriceps
(23, 19, 4), -- Estiramiento de Isquiotibiales

-- Rutina 24: 'HIIT Quemagrasa' (cardio) - Cliente 2000000100004
(24, 12, 1), -- HIIT en Cinta
(24, 13, 2), -- Burpees
(24, 10, 3), -- Saltar la Cuerda

-- Rutina 25: 'Mixto Avanzado' (mixto) - Cliente 2000000200003
(25, 2, 1),  -- Sentadillas con Barra
(25, 8, 2),  -- Correr en Cinta
(25, 1, 3),  -- Press de Banca
(25, 14, 4), -- Plancha Abdominal

-- Rutina 26: 'Cardio Básico' (cardio) - Cliente 2000000200003
(26, 8, 1),  -- Correr en Cinta
(26, 9, 2),  -- Bicicleta Estática
(26, 10, 3), -- Saltar la Cuerda

-- Rutina 27: 'Fuerza Superior' (fuerza) - Cliente 2000000200003
(27, 1, 1),  -- Press de Banca
(27, 4, 2),  -- Press Militar
(27, 6, 3),  -- Remo con Barra
(27, 5, 4),  -- Curl de Bíceps

-- Rutina 28: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100005
(28, 2, 1),  -- Sentadillas con Barra
(28, 1, 2),  -- Press de Banca
(28, 6, 3),  -- Remo con Barra
(28, 4, 4),  -- Press Militar

-- Rutina 29: 'Running Progressivo' (cardio) - Cliente 2000000100005
(29, 8, 1),  -- Correr en Cinta
(29, 10, 2), -- Saltar la Cuerda
(29, 9, 3),  -- Bicicleta Estática

-- Rutina 30: 'Push-Pull-Legs' (fuerza) - Cliente 2000000100005
(30, 1, 1),  -- Press de Banca (Push)
(30, 6, 2),  -- Remo con Barra (Pull)
(30, 2, 3),  -- Sentadillas con Barra (Legs)
(30, 5, 4),  -- Curl de Bíceps (Pull)

-- Rutina 31: 'Yoga Flow' (flexibilidad) - Cliente 2000000100005
(31, 16, 1), -- Postura del Perro
(31, 17, 2), -- Postura del Guerrero
(31, 18, 3), -- Estiramiento de Cuádriceps
(31, 19, 4), -- Estiramiento de Isquiotibiales

-- Rutina 32: 'Full Body Express' (fuerza) - Cliente 2000000200004
(32, 2, 1),  -- Sentadillas con Barra
(32, 1, 2),  -- Press de Banca
(32, 3, 3),  -- Peso Muerto
(32, 4, 4),  -- Press Militar

-- Rutina 33: 'Cardio Intervalos' (cardio) - Cliente 2000000200004
(33, 12, 1), -- HIIT en Cinta
(33, 9, 2),  -- Bicicleta Estática
(33, 11, 3), -- Máquina de Remo

-- Rutina 34: 'Cross Training' (mixto) - Cliente 2000000200004
(34, 7, 1),  -- Kettlebell Swings
(34, 8, 2),  -- Correr en Cinta
(34, 2, 3),  -- Sentadillas con Barra
(34, 13, 4), -- Burpees

-- Continuaría con el mismo patrón para las rutinas restantes...
-- Rutina 35: 'Rutina Fuerza Inicial' (fuerza) - Cliente 2000000100006
(35, 1, 1),  -- Press de Banca
(35, 2, 2),  -- Sentadillas con Barra
(35, 6, 3),  -- Remo con Barra
(35, 4, 4),  -- Press Militar

-- Rutina 36: 'Core Intenso' (fuerza) - Cliente 2000000100006
(36, 14, 1), -- Plancha Abdominal
(36, 15, 2), -- Russian Twists
(36, 13, 3), -- Burpees
(36, 7, 4),  -- Kettlebell Swings

-- Rutina 37: 'Spinning Interválico' (cardio) - Cliente 2000000100006
(37, 9, 1),  -- Bicicleta Estática
(37, 12, 2), -- HIIT en Cinta

-- Rutina 38: 'Functional Training' (mixto) - Cliente 2000000100006
(38, 7, 1),  -- Kettlebell Swings
(38, 13, 2), -- Burpees
(38, 2, 3),  -- Sentadillas con Barra
(38, 14, 4); -- Plancha Abdominal

INSERT INTO asistencia (dpi_cliente, id_sucursal, fecha_hora) VALUES
-- Clientes Básicos
(2000000100001, 1, '2025-01-05 08:15:00'),
(2000000100002, 2, '2025-01-06 09:30:00'),
(2000000100003, 3, '2025-01-07 10:45:00'),
(2000000100001, 1, '2025-01-10 07:50:00'),
(2000000100004, 2, '2025-01-12 18:20:00'),
(2000000100005, 3, '2025-01-15 17:10:00'),
(2000000100006, 1, '2025-01-16 08:05:00'),
(2000000100007, 2, '2025-01-18 19:00:00'),
(2000000100008, 3, '2025-01-20 06:55:00'),
(2000000100009, 1, '2025-01-22 12:15:00'),

(2000000100010, 2, '2025-01-23 14:40:00'),
(2000000100011, 3, '2025-01-25 09:25:00'),
(2000000100012, 1, '2025-01-28 07:30:00'),
(2000000100013, 2, '2025-02-01 16:50:00'),
(2000000100014, 3, '2025-02-03 08:10:00'),
(2000000100015, 1, '2025-02-05 19:20:00'),
(2000000100016, 2, '2025-02-07 07:40:00'),
(2000000100017, 3, '2025-02-10 18:30:00'),
(2000000100018, 1, '2025-02-12 09:15:00'),
(2000000100019, 2, '2025-02-14 10:50:00'),

-- Clientes Premium
(2000000200001, 3, '2025-02-16 08:00:00'),
(2000000200002, 1, '2025-02-18 07:45:00'),
(2000000200003, 2, '2025-02-20 17:35:00'),
(2000000200004, 3, '2025-02-22 08:25:00'),
(2000000200005, 1, '2025-02-24 18:40:00'),
(2000000200006, 2, '2025-02-26 19:15:00'),
(2000000200007, 3, '2025-02-28 07:50:00'),
(2000000200008, 1, '2025-03-01 12:05:00'),
(2000000200009, 2, '2025-03-03 13:30:00'),
(2000000200010, 3, '2025-03-05 08:20:00'),

(2000000200011, 1, '2025-03-07 09:45:00'),
(2000000200012, 2, '2025-03-09 18:00:00'),
(2000000200013, 3, '2025-03-11 06:50:00'),
(2000000200014, 1, '2025-03-13 19:25:00'),
(2000000200015, 2, '2025-03-15 07:35:00'),
(2000000200016, 3, '2025-03-17 08:40:00'),
(2000000200017, 1, '2025-03-19 17:50:00'),
(2000000200018, 2, '2025-03-21 09:10:00'),
(2000000200019, 3, '2025-03-23 10:30:00'),
(2000000200020, 1, '2025-03-25 08:05:00'),

-- Clientes VIP
(2000000300001, 2, '2025-03-27 07:55:00'),
(2000000300002, 3, '2025-03-29 18:15:00'),
(2000000300003, 1, '2025-03-31 09:20:00'),
(2000000300004, 2, '2025-04-02 08:40:00'),
(2000000300005, 3, '2025-04-04 19:05:00'),
(2000000300006, 1, '2025-04-06 07:50:00'),
(2000000300007, 2, '2025-04-08 12:10:00'),
(2000000300008, 3, '2025-04-10 13:25:00'),
(2000000300009, 1, '2025-04-12 08:30:00'),
(2000000300010, 2, '2025-04-14 09:45:00'),

-- Repeticiones y aleatoriedad
(2000000100001, 1, '2025-04-16 07:55:00'),
(2000000100003, 2, '2025-04-18 18:20:00'),
(2000000100005, 3, '2025-04-20 09:10:00'),
(2000000100007, 1, '2025-04-22 08:45:00'),
(2000000100009, 2, '2025-04-24 17:50:00'),
(2000000100011, 3, '2025-04-26 07:30:00'),
(2000000100013, 1, '2025-04-28 18:05:00'),
(2000000100015, 2, '2025-04-30 08:20:00'),
(2000000100017, 3, '2025-05-02 09:35:00'),
(2000000100019, 1, '2025-05-04 07:50:00'),

(2000000200002, 2, '2025-05-06 12:15:00'),
(2000000200004, 3, '2025-05-08 13:40:00'),
(2000000200006, 1, '2025-05-10 08:05:00'),
(2000000200008, 2, '2025-05-12 07:55:00'),
(2000000200010, 3, '2025-05-14 18:20:00'),
(2000000200012, 1, '2025-05-16 09:10:00'),
(2000000200014, 2, '2025-05-18 08:35:00'),
(2000000200016, 3, '2025-05-20 19:00:00'),
(2000000200018, 1, '2025-05-22 07:45:00'),
(2000000200020, 2, '2025-05-24 08:50:00'),

(2000000300001, 3, '2025-05-26 09:15:00'),
(2000000300003, 1, '2025-05-28 07:35:00'),
(2000000300005, 2, '2025-05-30 08:40:00'),
(2000000300007, 3, '2025-06-01 18:10:00'),
(2000000300009, 1, '2025-06-03 07:55:00'),
(2000000300010, 2, '2025-06-05 08:25:00');

-- Movimientos de inventario (origen siempre Central)
INSERT INTO movimiento_inventario (id_equipo, cantidad, origen_sucursal, destino_sucursal, fecha, motivo) VALUES
(1, 5, 1, 1, '2025-09-01 09:30:00', 'Compra inicial de bancas planas para Central'),
(2, 3, 1, 2, '2025-09-01 10:00:00', 'Envio de racks de sentadillas desde Central a Norte'),
(3, 20, 1, 3, '2025-09-02 11:00:00', 'Envio de mancuernas desde Central a Sur'),
(4, 2, 1, 2, '2025-09-03 14:00:00', 'Envio de cintas de correr de Central a Norte'),
(5, 1, 1, 3, '2025-09-03 15:30:00', 'Envio de bicicleta estática de Central a Sur'),
(6, 15, 1, 1, '2025-09-04 09:15:00', 'Compra de cuerdas para saltar en Central'),
(7, 2, 1, 2, '2025-09-04 09:45:00', 'Envio de máquinas de remo desde Central a Norte'),
(8, 10, 1, 3, '2025-09-05 10:10:00', 'Envio de kettlebells desde Central a Sur'),
(9, 8, 1, 1, '2025-09-05 11:00:00', 'Compra de colchonetas yoga para Central'),
(3, 5, 1, 2, '2025-09-06 13:00:00', 'Refuerzo de mancuernas en Norte desde Central'),
(1, 2, 1, 3, '2025-09-06 13:30:00', 'Envio adicional de bancas planas a Sur'),
(4, 1, 1, 1, '2025-09-07 08:45:00', 'Compra extra de cinta de correr para Central'),
(5, 2, 1, 1, '2025-09-07 09:15:00', 'Compra de bicicletas estáticas para Central'),
(7, 1, 1, 2, '2025-09-08 16:20:00', 'Envio adicional de máquina de remo a Norte'),
(6, 10, 1, 3, '2025-09-08 17:00:00', 'Envio masivo de cuerdas para saltar a Sur');