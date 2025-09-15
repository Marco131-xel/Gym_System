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

INSERT INTO empleado (dpi, nombre, apellido, telefono, direccion, id_sucursal, id_rol)
VALUES 
(1234567890101, 'Juan', 'Pérez', '5555-1234', 'Zona 1, Ciudad', 1, 1);
