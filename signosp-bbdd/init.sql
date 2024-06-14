CREATE TABLE public.cliente (
    id_cliente BIGSERIAL PRIMARY KEY,
    nomyape varchar(255),
    correo varchar(255),
    telefono varchar(15)
);

CREATE TABLE public.empleado (
    id_empleado BIGSERIAL PRIMARY KEY,
    nomyape varchar(255),
    correo varchar(255),
    telefono varchar(15) NOT NULL
);

CREATE TABLE public.especialidad (
    id_especialidad BIGSERIAL PRIMARY KEY,
    nombre varchar(100)
);

CREATE TABLE public.empleado_especialidad (
    id_empleado BIGINT REFERENCES empleado(id_empleado),
    id_especialidad BIGINT REFERENCES especialidad(id_especialidad)
);

CREATE TABLE public.disponibilidad (
    id_disponibilidad BIGSERIAL PRIMARY KEY,
    id_empleado BIGINT REFERENCES cliente(id_cliente),
    dia varchar(10),
    horario varchar(4)
);

CREATE TABLE public.usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    username varchar(255),
    nomyape varchar(255),
    password varchar(255),
    ROLE varchar(255)
);

CREATE TABLE public.categoria (
    id_categoria BIGSERIAL PRIMARY KEY,
    nombre varchar(100)
);

CREATE TABLE public.paquete(
    id_paquete BIGSERIAL PRIMARY KEY,
    nombre varchar(255),
    precio float,
    detalles varchar(500)
);

CREATE TABLE public.servicio(
    id_servicio BIGSERIAL PRIMARY KEY,
    nombre varchar(255)
);

CREATE TABLE public.paquete_servicio(
    id_paquete BIGINT NOT NULL REFERENCES paquete(id_paquete),
    id_servicio BIGINT NOT NULL REFERENCES servicio(id_servicio)
);

CREATE TABLE public.material_de_entrega(
    id_material_de_entrega BIGSERIAL PRIMARY KEY,
    nombre varchar(255)
);
CREATE TABLE public.paquete_material_de_entrega(
	id_paquete_material_de_entrega BIGSERIAL PRIMARY KEY,
	id_paquete BIGINT NOT NULL REFERENCES paquete(id_paquete),
	id_material_de_entrega BIGINT NOT NULL REFERENCES material_de_entrega(id_material_de_entrega),
	cantidad integer
);

CREATE TABLE public.evento(
    id_evento BIGSERIAL PRIMARY KEY,
    estado boolean,
    fecha date,
    id_cliente BIGINT REFERENCES cliente(id_cliente),
    id_paquete BIGINT REFERENCES paquete(id_paquete),
    id_categoria BIGINT REFERENCES categoria(id_categoria)
);

CREATE TABLE public.evento_empleado(
	id_evento_empleado BIGSERIAL PRIMARY KEY,
    id_evento BIGINT REFERENCES evento(id_evento),
    id_empleado BIGINT REFERENCES empleado(id_empleado),
    fecha_trabajada date
);

CREATE TABLE public.comentario (
    id_comentario BIGSERIAL PRIMARY KEY,
    id_evento BIGINT REFERENCES evento(id_evento),
    id_empleado BIGINT REFERENCES empleado(id_empleado),
    contenido varchar(500)
);

CREATE TABLE public.subtarea(
    id_subtarea BIGSERIAL PRIMARY KEY,
    id_evento BIGINT NOT NULL REFERENCES evento(id_evento),
    nombre varchar(255),
    estado boolean
);

CREATE TABLE public.pago (
    id_pago BIGSERIAL PRIMARY KEY,
    id_evento BIGINT NOT NULL REFERENCES evento(id_evento),
    forma_de_pago varchar(10),
    cant_cuotas integer
);

CREATE TABLE public.cuota (
    id_pago BIGINT REFERENCES pago(id_pago),
    nro_cuota BIGINT,
    monto float,
    fecha_de_pago date,
    PRIMARY KEY (nro_cuota, id_pago)
);

CREATE TABLE public.SECURITY (
	json TEXT,
	TYPE VARCHAR(255)
);
INSERT INTO security (json, type) VALUES ('
{
"roles": [
	{
		"name": "ROLE_ADMIN", 
		"permissions": [
			"/api/ganancias"
		]
	},
	{
		"name": "ROLE_USER", 
		"permissions": [
			"/api/users/me", 
			"/api/posts"
		]
	}
]}', 
'application/json');
