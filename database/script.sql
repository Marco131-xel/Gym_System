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


INSERT INTO sucursal (nombre, region, cantidad_maquinas) VALUES
('Central', 'Central', 50),
('Norte', 'Norte',30),
('Sur', 'Sur', 20);

INSERT INTO rol (nombre) VALUES ('Recepcionista'), ('Entrenador'), ('Inventario');

INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol)
VALUES 
(1234567890101, 'Juan', 'Pérez', '123456','55551234', 'Zona 1, Ciudad', 1, 1);

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
  id_cliente INT NOT NULL REFERENCES cliente(id_cliente) ON DELETE CASCADE,
  id_tipo INT NOT NULL REFERENCES membresia_tipo(id_tipo) ON DELETE RESTRICT,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE,
  UNIQUE (id_cliente, fecha_inicio)
);

-- pagos
CREATE TABLE pago (
  id_pago SERIAL PRIMARY KEY,
  id_cliente INT NOT NULL REFERENCES cliente(id_cliente) ON DELETE CASCADE,
  tipo TEXT NOT NULL CHECK (tipo IN ('servicio','adicional')),
  descripcion TEXT,
  monto NUMERIC(12,2) NOT NULL CHECK (monto >= 0),
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE
);

-- Adicionales
CREATE TABLE adicional (
  id_adicional SERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  detalles TEXT,
  precio NUMERIC(12,2) NOT NULL,
  id_entrenador INT REFERENCES empleado(id_empleado)
);

