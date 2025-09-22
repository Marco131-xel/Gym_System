CREATE DATABASE gym_system;

CREATE USER admin WITH PASSWORD '1234';

GRANT ALL PRIVILEGES ON DATABASE gym_system to admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

/c gym_system;

CREATE TABLE sucursal (
    id_sucursal SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE,
    region TEXT,
    cantidad_maquinas INT DEFAULT 0 CHECK (cantidad_maquinas >= 0)
);

CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE
);

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

-- historial de membresias
CREATE TABLE membresia (
  id_membresia SERIAL PRIMARY KEY,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  id_tipo INT NOT NULL REFERENCES membresia_tipo(id_tipo) ON DELETE RESTRICT,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE,
  UNIQUE (dpi_cliente, fecha_inicio)
);

-- Adicionales
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

-- Equipos (maquinas y repuestos)
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

-- Asignacion entrenador-cliente (historial)
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
  tipo TEXT, -- fuerza, cardio, mixto, etc.
  fecha_inicio DATE NOT NULL,
  dpi_entrenador BIGINT NOT NULL REFERENCES empleado(dpi) ON DELETE RESTRICT,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE
);

-- Ejercicios (plantilla)
CREATE TABLE ejercicio (
  id_ejercicio SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  series INT,
  repeticiones INT,
  duracion_min INT, -- minutos estimados (si aplica)
  id_equipo INT REFERENCES equipo(id_equipo)
);

-- Relacion rutina - ejercicio (muchos a muchos)
CREATE TABLE rutina_ejercicio (
  id_rut_eje SERIAL PRIMARY KEY,
  id_rutina INT NOT NULL REFERENCES rutina(id_rutina) ON DELETE CASCADE,
  id_ejercicio INT NOT NULL REFERENCES ejercicio(id_ejercicio) ON DELETE RESTRICT,
  orden INT NOT NULL DEFAULT 1,
  CHECK (orden > 0)
);

-- Asistencias
CREATE TABLE asistencia (
  id_asistencia SERIAL PRIMARY KEY,
  dpi_cliente BIGINT NOT NULL REFERENCES cliente(dpi) ON DELETE CASCADE,
  id_sucursal INT NOT NULL REFERENCES sucursal(id_sucursal) ON DELETE RESTRICT,
  fecha_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- INSERTS VALUES

INSERT INTO sucursal (nombre, region, cantidad_maquinas) VALUES
('Central', 'Central', 50),
('Norte', 'Norte',30),
('Sur', 'Sur', 20);

INSERT INTO rol (nombre) VALUES ('Recepcionista'), ('Entrenador'), ('Inventario');

INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol)
VALUES 
(1234567890101, 'Juan', 'Pérez', '123456','55551234', 'Zona 1, Ciudad', 1, 1);

INSERT INTO cliente (dpi, nombre, apellido, password, telefono, direccion) VALUES
(3214567890101, 'Pedro', 'Hernández', 'cli123', '44441234', 'Zona 2, Ciudad'),
(3214567890102, 'Lucía', 'Mendoza', 'cli456', '44442345', 'Zona 12, Ciudad'),
(3214567890103, 'José', 'Cruz', 'cli789', '44443456', 'Zona 8, Ciudad'),
(3214567890104, 'Elena', 'Torres', 'cli101', '44444567', 'Zona 4, Ciudad');


INSERT INTO membresia_tipo (nombre, descuento) VALUES
('Basica', 0),
('Premiun', 10.00),
('VIP', 20.00);

INSERT INTO membresia (dpi_cliente, id_tipo, fecha_inicio, fecha_fin) VALUES
(3214567890101, 1, '2025-03-01', '2025-03-31'); -- victor basica

INSERT INTO adicional (nombre, detalles, precio, dpi_entrenador) VALUES
('Entrenamiento Personalizado', 'Sesión individual con entrenador certificado', 150.00, 3357975630901),
('Suplementos Proteicos', 'Paquete mensual de proteínas y vitaminas', 250.00, NULL),
('Clase de Yoga', 'Clase grupal de yoga todos los sábados', 75.00, 3357975630901),
('Masaje Relajante', 'Sesión de 60 minutos de masaje terapéutico', 200.00, NULL),
('Plan Nutricional', 'Asesoría nutricional personalizada', 120.00, NULL);


-- Pagos de membresía (tipo servicio)
INSERT INTO pago (dpi_cliente, tipo, descripcion, monto, fecha_inicio, fecha_fin, id_adicional)
VALUES
(3214567890101, 'servicio', 'Membresía mensual básica', 250.00, '2025-09-01', '2025-09-30', NULL);

-- Pagos de adicionales (tipo adicional)
INSERT INTO pago (dpi_cliente, tipo, descripcion, monto, fecha_inicio, fecha_fin, id_adicional)
VALUES
(3214567890101, 'adicional', 'Clase de Spinning', 50.00, '2025-09-05', NULL, 1),
(3214567890101, 'adicional', 'Entrenamiento Personal', 120.00, '2025-09-10', NULL, 3);



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

