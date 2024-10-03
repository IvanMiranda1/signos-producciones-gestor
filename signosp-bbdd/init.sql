--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.3

-- Started on 2024-09-25 18:21:01 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

DO $$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'public') THEN
    CREATE SCHEMA public;
  END IF;
END $$;



ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16385)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id_categoria bigint NOT NULL,
    nombre character varying(100)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16388)
-- Name: categoria_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_id_categoria_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_id_categoria_seq OWNER TO postgres;

--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 216
-- Name: categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;


--
-- TOC entry 217 (class 1259 OID 16389)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id_cliente bigint NOT NULL,
    nomyape character varying(255),
    correo character varying(255),
    telefono character varying(15)
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16394)
-- Name: cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 218
-- Name: cliente_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_cliente_seq OWNED BY public.cliente.id_cliente;


--
-- TOC entry 219 (class 1259 OID 16395)
-- Name: comentario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comentario (
    id_comentario bigint NOT NULL,
    id_evento bigint,
    id_usuario bigint,
    contenido character varying(500)
);


ALTER TABLE public.comentario OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16400)
-- Name: comentario_id_comentario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comentario_id_comentario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comentario_id_comentario_seq OWNER TO postgres;

--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 220
-- Name: comentario_id_comentario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comentario_id_comentario_seq OWNED BY public.comentario.id_comentario;


--
-- TOC entry 221 (class 1259 OID 16401)
-- Name: cuota; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuota (
    id_pago bigint NOT NULL,
    nro_cuota bigint NOT NULL,
    monto double precision,
    fecha_de_pago date
);


ALTER TABLE public.cuota OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16404)
-- Name: disponibilidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.disponibilidad (
    id_disponibilidad bigint NOT NULL,
    id_empleado bigint,
    dia character varying(10),
    desde character varying(4),
    hasta character varying(4)
);


ALTER TABLE public.disponibilidad OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16407)
-- Name: disponibilidad_id_disponibilidad_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.disponibilidad_id_disponibilidad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.disponibilidad_id_disponibilidad_seq OWNER TO postgres;

--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 223
-- Name: disponibilidad_id_disponibilidad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.disponibilidad_id_disponibilidad_seq OWNED BY public.disponibilidad.id_disponibilidad;


--
-- TOC entry 224 (class 1259 OID 16408)
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empleado (
    id_empleado bigint NOT NULL,
    nomyape character varying(255),
    correo character varying(255),
    telefono character varying(15) NOT NULL
);


ALTER TABLE public.empleado OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16413)
-- Name: empleado_especialidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empleado_especialidad (
    id_empleado bigint,
    id_especialidad bigint
);


ALTER TABLE public.empleado_especialidad OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16416)
-- Name: empleado_id_empleado_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empleado_id_empleado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empleado_id_empleado_seq OWNER TO postgres;

--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 226
-- Name: empleado_id_empleado_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empleado_id_empleado_seq OWNED BY public.empleado.id_empleado;


--
-- TOC entry 227 (class 1259 OID 16417)
-- Name: especialidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.especialidad (
    id_especialidad bigint NOT NULL,
    nombre character varying(100)
);


ALTER TABLE public.especialidad OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16420)
-- Name: especialidad_id_especialidad_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.especialidad_id_especialidad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.especialidad_id_especialidad_seq OWNER TO postgres;

--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 228
-- Name: especialidad_id_especialidad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.especialidad_id_especialidad_seq OWNED BY public.especialidad.id_especialidad;


--
-- TOC entry 229 (class 1259 OID 16421)
-- Name: evento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.evento (
    id_evento bigint NOT NULL,
    titulo character varying(255),
    estado boolean,
    fecha date,
    id_cliente bigint,
    id_paquete bigint,
    id_categoria bigint
);


ALTER TABLE public.evento OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16424)
-- Name: evento_empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.evento_empleado (
    id_evento_empleado bigint NOT NULL,
    id_evento bigint,
    id_empleado bigint
);


ALTER TABLE public.evento_empleado OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16427)
-- Name: evento_empleado_id_evento_empleado_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.evento_empleado_id_evento_empleado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.evento_empleado_id_evento_empleado_seq OWNER TO postgres;

--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 231
-- Name: evento_empleado_id_evento_empleado_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.evento_empleado_id_evento_empleado_seq OWNED BY public.evento_empleado.id_evento_empleado;


--
-- TOC entry 232 (class 1259 OID 16428)
-- Name: evento_id_evento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.evento_id_evento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.evento_id_evento_seq OWNER TO postgres;

--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 232
-- Name: evento_id_evento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.evento_id_evento_seq OWNED BY public.evento.id_evento;


--
-- TOC entry 233 (class 1259 OID 16433)
-- Name: material_de_entrega; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.material_de_entrega (
    id_material_de_entrega bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.material_de_entrega OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16436)
-- Name: material_de_entrega_id_material_de_entrega_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.material_de_entrega_id_material_de_entrega_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.material_de_entrega_id_material_de_entrega_seq OWNER TO postgres;

--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 234
-- Name: material_de_entrega_id_material_de_entrega_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.material_de_entrega_id_material_de_entrega_seq OWNED BY public.material_de_entrega.id_material_de_entrega;


--
-- TOC entry 235 (class 1259 OID 16437)
-- Name: pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pago (
    id_pago bigint NOT NULL,
    id_evento bigint NOT NULL,
    forma_de_pago character varying(10),
    cant_cuotas integer
);


ALTER TABLE public.pago OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 16440)
-- Name: pago_id_pago_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pago_id_pago_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pago_id_pago_seq OWNER TO postgres;

--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 236
-- Name: pago_id_pago_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pago_id_pago_seq OWNED BY public.pago.id_pago;


--
-- TOC entry 237 (class 1259 OID 16441)
-- Name: paquete; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paquete (
    id_paquete bigint NOT NULL,
    nombre character varying(255),
    precio double precision,
    detalles character varying(500)
);


ALTER TABLE public.paquete OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16446)
-- Name: paquete_id_paquete_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paquete_id_paquete_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paquete_id_paquete_seq OWNER TO postgres;

--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 238
-- Name: paquete_id_paquete_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paquete_id_paquete_seq OWNED BY public.paquete.id_paquete;


--
-- TOC entry 239 (class 1259 OID 16447)
-- Name: paquete_material_de_entrega; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paquete_material_de_entrega (
    id_paquete_material_de_entrega bigint NOT NULL,
    id_paquete bigint NOT NULL,
    id_material_de_entrega bigint NOT NULL,
    cantidad integer
);


ALTER TABLE public.paquete_material_de_entrega OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 16450)
-- Name: paquete_material_de_entrega_id_paquete_material_de_entrega_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paquete_material_de_entrega_id_paquete_material_de_entrega_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paquete_material_de_entrega_id_paquete_material_de_entrega_seq OWNER TO postgres;

--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 240
-- Name: paquete_material_de_entrega_id_paquete_material_de_entrega_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paquete_material_de_entrega_id_paquete_material_de_entrega_seq OWNED BY public.paquete_material_de_entrega.id_paquete_material_de_entrega;


--
-- TOC entry 241 (class 1259 OID 16451)
-- Name: paquete_servicio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paquete_servicio (
    id_paquete bigint NOT NULL,
    id_servicio bigint NOT NULL
);


ALTER TABLE public.paquete_servicio OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16454)
-- Name: security; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.security (
    json text,
    type character varying(255)
);


ALTER TABLE public.security OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 16459)
-- Name: servicio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servicio (
    id_servicio bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.servicio OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 16462)
-- Name: servicio_id_servicio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servicio_id_servicio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.servicio_id_servicio_seq OWNER TO postgres;

--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 244
-- Name: servicio_id_servicio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.servicio_id_servicio_seq OWNED BY public.servicio.id_servicio;


--
-- TOC entry 245 (class 1259 OID 16463)
-- Name: subtarea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subtarea (
    id_subtarea bigint NOT NULL,
    id_evento bigint NOT NULL,
    nombre character varying(255),
    estado boolean
);


ALTER TABLE public.subtarea OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 16466)
-- Name: subtarea_id_subtarea_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subtarea_id_subtarea_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.subtarea_id_subtarea_seq OWNER TO postgres;

--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 246
-- Name: subtarea_id_subtarea_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subtarea_id_subtarea_seq OWNED BY public.subtarea.id_subtarea;


--
-- TOC entry 247 (class 1259 OID 16467)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id_usuario bigint NOT NULL,
    username character varying(255),
    nomyape character varying(255),
    password character varying(255),
    role character varying(255)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 16472)
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 248
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- TOC entry 3289 (class 2604 OID 16473)
-- Name: categoria id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);


--
-- TOC entry 3290 (class 2604 OID 16474)
-- Name: cliente id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_cliente_seq'::regclass);


--
-- TOC entry 3291 (class 2604 OID 16475)
-- Name: comentario id_comentario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentario ALTER COLUMN id_comentario SET DEFAULT nextval('public.comentario_id_comentario_seq'::regclass);


--
-- TOC entry 3292 (class 2604 OID 16476)
-- Name: disponibilidad id_disponibilidad; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disponibilidad ALTER COLUMN id_disponibilidad SET DEFAULT nextval('public.disponibilidad_id_disponibilidad_seq'::regclass);


--
-- TOC entry 3293 (class 2604 OID 16477)
-- Name: empleado id_empleado; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado ALTER COLUMN id_empleado SET DEFAULT nextval('public.empleado_id_empleado_seq'::regclass);


--
-- TOC entry 3294 (class 2604 OID 16478)
-- Name: especialidad id_especialidad; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.especialidad ALTER COLUMN id_especialidad SET DEFAULT nextval('public.especialidad_id_especialidad_seq'::regclass);


--
-- TOC entry 3295 (class 2604 OID 16479)
-- Name: evento id_evento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento ALTER COLUMN id_evento SET DEFAULT nextval('public.evento_id_evento_seq'::regclass);


--
-- TOC entry 3296 (class 2604 OID 16480)
-- Name: evento_empleado id_evento_empleado; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento_empleado ALTER COLUMN id_evento_empleado SET DEFAULT nextval('public.evento_empleado_id_evento_empleado_seq'::regclass);


--
-- TOC entry 3297 (class 2604 OID 16482)
-- Name: material_de_entrega id_material_de_entrega; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.material_de_entrega ALTER COLUMN id_material_de_entrega SET DEFAULT nextval('public.material_de_entrega_id_material_de_entrega_seq'::regclass);


--
-- TOC entry 3298 (class 2604 OID 16483)
-- Name: pago id_pago; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago ALTER COLUMN id_pago SET DEFAULT nextval('public.pago_id_pago_seq'::regclass);


--
-- TOC entry 3299 (class 2604 OID 16484)
-- Name: paquete id_paquete; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete ALTER COLUMN id_paquete SET DEFAULT nextval('public.paquete_id_paquete_seq'::regclass);


--
-- TOC entry 3300 (class 2604 OID 16485)
-- Name: paquete_material_de_entrega id_paquete_material_de_entrega; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_material_de_entrega ALTER COLUMN id_paquete_material_de_entrega SET DEFAULT nextval('public.paquete_material_de_entrega_id_paquete_material_de_entrega_seq'::regclass);


--
-- TOC entry 3301 (class 2604 OID 16486)
-- Name: servicio id_servicio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicio ALTER COLUMN id_servicio SET DEFAULT nextval('public.servicio_id_servicio_seq'::regclass);


--
-- TOC entry 3302 (class 2604 OID 16487)
-- Name: subtarea id_subtarea; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subtarea ALTER COLUMN id_subtarea SET DEFAULT nextval('public.subtarea_id_subtarea_seq'::regclass);


--
-- TOC entry 3303 (class 2604 OID 16488)
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- TOC entry 3494 (class 0 OID 16385)
-- Dependencies: 215
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (1, 'Bautismo');
INSERT INTO public.categoria VALUES (2, 'Casamiento');
INSERT INTO public.categoria VALUES (3, 'Cena de egresados');
INSERT INTO public.categoria VALUES (4, 'Cumpleaños de 15');
INSERT INTO public.categoria VALUES (5, 'Celebraciones escolares');
INSERT INTO public.categoria VALUES (6, 'Eventos deportivos');
INSERT INTO public.categoria VALUES (7, 'Conferencias y seminarios');
INSERT INTO public.categoria VALUES (8, 'Fiestas');


--
-- TOC entry 3496 (class 0 OID 16389)
-- Dependencies: 217
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cliente VALUES (1, 'Tamara Mansilla', 'tamaraMansilla@gmail.com', '2964589888');
INSERT INTO public.cliente VALUES (2, 'Mercedes Farias', 'mercedesFarias@gmail.com', '2964577665');
INSERT INTO public.cliente VALUES (3, 'Jorge Gonzales', 'jorgeGonzales@gmail.com', '2964556674');
INSERT INTO public.cliente VALUES (4, 'Ivan Miranda', 'ivanmiranda@gmail.com', '2964578776');
INSERT INTO public.cliente VALUES (6, 'Toti Miranda', 'totiMiranda@gmail.com', '2964589886');
INSERT INTO public.cliente VALUES (7, 'Josefina Rodriguez', 'josefinarod@gmail.com', '2964765876');
INSERT INTO public.cliente VALUES (8, 'Sandra sene', 'senesandra@gmail.com', '2964589877');
INSERT INTO public.cliente VALUES (9, 'Gerardo Cuevas', 'cuevasgr@gmail.com', '2964557788');
INSERT INTO public.cliente VALUES (10, 'Jose Morinigo', 'joseMorinigo@gmail.com', '2964277434');
INSERT INTO public.cliente VALUES (11, 'Mariana Ramirez', 'mariaanaRamirez@gmail.com', '2964556677');
INSERT INTO public.cliente VALUES (5, 'Martin Monzon', 'monzonMartin@gmail.com', '2964578909');
INSERT INTO public.cliente VALUES (12, 'Ana Portillo', 'anaPortillo@gmail.com', '2964589899');


--
-- TOC entry 3498 (class 0 OID 16395)
-- Dependencies: 219
-- Data for Name: comentario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3500 (class 0 OID 16401)
-- Dependencies: 221
-- Data for Name: cuota; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuota VALUES (1, 1, 0, '2024-08-01');
INSERT INTO public.cuota VALUES (8, 1, 0, '2024-06-05');
INSERT INTO public.cuota VALUES (5, 1, 0, '2024-09-01');
INSERT INTO public.cuota VALUES (3, 1, 0, '2024-09-25');
INSERT INTO public.cuota VALUES (3, 2, 0, '2024-09-25');
INSERT INTO public.cuota VALUES (7, 1, 0, '2024-09-25');


--
-- TOC entry 3501 (class 0 OID 16404)
-- Dependencies: 222
-- Data for Name: disponibilidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.disponibilidad VALUES (1, 1, 'Viernes', '1800', '0');
INSERT INTO public.disponibilidad VALUES (2, 1, 'Sábado', '0', '0');
INSERT INTO public.disponibilidad VALUES (3, 1, 'Domingo', '0', '1300');
INSERT INTO public.disponibilidad VALUES (4, 1, 'Domingo', '2000', '0');
INSERT INTO public.disponibilidad VALUES (5, 2, 'Sábado', '0', '0');
INSERT INTO public.disponibilidad VALUES (6, 2, 'Domingo', '0', '1300');
INSERT INTO public.disponibilidad VALUES (7, 2, '', '2000', '0');
INSERT INTO public.disponibilidad VALUES (8, 3, 'Viernes', '0', '0');
INSERT INTO public.disponibilidad VALUES (9, 3, 'Sábado', '0', '0');
INSERT INTO public.disponibilidad VALUES (10, 3, 'Domingo', '0', '0');
INSERT INTO public.disponibilidad VALUES (11, 4, 'Viernes', '1300', '0');
INSERT INTO public.disponibilidad VALUES (12, 4, 'Sábado', '0', '0');
INSERT INTO public.disponibilidad VALUES (13, 4, 'Domingo', '0', '0');
INSERT INTO public.disponibilidad VALUES (14, 5, 'Viernes', '0', '0');
INSERT INTO public.disponibilidad VALUES (15, 5, 'Sábado', '0', '0');
INSERT INTO public.disponibilidad VALUES (16, 5, 'Domingo', '0', '0');
INSERT INTO public.disponibilidad VALUES (17, 6, 'Sábado', '1300', '0');
INSERT INTO public.disponibilidad VALUES (18, 6, 'Domingo', '0', '0');
INSERT INTO public.disponibilidad VALUES (19, 7, 'Viernes', '0', '0');
INSERT INTO public.disponibilidad VALUES (20, 7, 'Sábado', '0', '0');


--
-- TOC entry 3503 (class 0 OID 16408)
-- Dependencies: 224
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.empleado VALUES (1, 'Sergio Magallanes', 'msergio@gmail.com', '2964786543');
INSERT INTO public.empleado VALUES (2, 'Sofia Farias', 'fsofia1@gmail.com', '2965465467');
INSERT INTO public.empleado VALUES (3, 'Pablo Rodriguez', 'pabrodriguez@gmail.com', '2965778656');
INSERT INTO public.empleado VALUES (4, 'Personal prueba', 'pruebaperso@gmail.com', '2964777777');
INSERT INTO public.empleado VALUES (5, 'Rodrigo Miranda', 'rodrimiranda@gmail.com', '2964587656');
INSERT INTO public.empleado VALUES (6, 'Florencia Ferreri', 'fferreri@gmail.com', '2964589886');
INSERT INTO public.empleado VALUES (7, 'Pedro Rosso', 'pedroRosso@gmail.com', '2964332211');


--
-- TOC entry 3504 (class 0 OID 16413)
-- Dependencies: 225
-- Data for Name: empleado_especialidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.empleado_especialidad VALUES (1, 1);
INSERT INTO public.empleado_especialidad VALUES (1, 3);
INSERT INTO public.empleado_especialidad VALUES (2, 1);
INSERT INTO public.empleado_especialidad VALUES (2, 2);
INSERT INTO public.empleado_especialidad VALUES (3, 4);
INSERT INTO public.empleado_especialidad VALUES (4, 6);
INSERT INTO public.empleado_especialidad VALUES (4, 7);
INSERT INTO public.empleado_especialidad VALUES (5, 1);
INSERT INTO public.empleado_especialidad VALUES (5, 3);
INSERT INTO public.empleado_especialidad VALUES (6, 5);
INSERT INTO public.empleado_especialidad VALUES (7, 1);


--
-- TOC entry 3506 (class 0 OID 16417)
-- Dependencies: 227
-- Data for Name: especialidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.especialidad VALUES (1, 'Camarografo');
INSERT INTO public.especialidad VALUES (2, 'Sonido');
INSERT INTO public.especialidad VALUES (3, 'Editor');
INSERT INTO public.especialidad VALUES (4, 'Animador');
INSERT INTO public.especialidad VALUES (5, 'Mozo');
INSERT INTO public.especialidad VALUES (6, 'Cocinero');
INSERT INTO public.especialidad VALUES (7, 'Bartender');


--
-- TOC entry 3508 (class 0 OID 16421)
-- Dependencies: 229
-- Data for Name: evento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.evento VALUES (2, 'Cumpleaños de Julieta', false, '2024-11-23', 1, 4, 4);
INSERT INTO public.evento VALUES (3, 'Bautismo de Mateo', false, '2024-09-28', 2, 6, 1);
INSERT INTO public.evento VALUES (4, 'Conferencia de Tecnología Innovate 2024', false, '2024-10-05', 10, 10, 7);
INSERT INTO public.evento VALUES (1, 'Boda de Ana y Carlos', true, '2024-08-10', 12, 9, 2);
INSERT INTO public.evento VALUES (6, 'Fiesta de Fin de Año de la Empresa Tech Solutions', false, '2024-12-20', 5, 7, 8);
INSERT INTO public.evento VALUES (5, 'Taller de Fotografía Creativa', true, '2024-09-19', 10, 10, 7);
INSERT INTO public.evento VALUES (8, 'Entrega de diplomas, Polivalente de arte Daiana Cotorruelo', true, '2024-06-20', 9, 12, 5);
INSERT INTO public.evento VALUES (7, 'Apertura del torneo Interfábrica', true, '2024-09-21', 10, 11, 6);


--
-- TOC entry 3509 (class 0 OID 16424)
-- Dependencies: 230
-- Data for Name: evento_empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.evento_empleado VALUES (1, 1, 1);
INSERT INTO public.evento_empleado VALUES (2, 1, 2);
INSERT INTO public.evento_empleado VALUES (3, 1, 3);
INSERT INTO public.evento_empleado VALUES (4, 2, 1);
INSERT INTO public.evento_empleado VALUES (5, 2, 3);
INSERT INTO public.evento_empleado VALUES (6, 2, 7);
INSERT INTO public.evento_empleado VALUES (7, 3, 1);
INSERT INTO public.evento_empleado VALUES (8, 3, 3);
INSERT INTO public.evento_empleado VALUES (9, 4, 1);
INSERT INTO public.evento_empleado VALUES (10, 4, 3);
INSERT INTO public.evento_empleado VALUES (11, 4, 7);
INSERT INTO public.evento_empleado VALUES (12, 5, 1);
INSERT INTO public.evento_empleado VALUES (13, 5, 3);
INSERT INTO public.evento_empleado VALUES (14, 5, 7);
INSERT INTO public.evento_empleado VALUES (15, 6, 1);
INSERT INTO public.evento_empleado VALUES (16, 6, 3);
INSERT INTO public.evento_empleado VALUES (17, 6, 7);
INSERT INTO public.evento_empleado VALUES (18, 7, 1);
INSERT INTO public.evento_empleado VALUES (19, 7, 3);
INSERT INTO public.evento_empleado VALUES (20, 7, 5);
INSERT INTO public.evento_empleado VALUES (21, 8, 1);
INSERT INTO public.evento_empleado VALUES (22, 8, 3);
INSERT INTO public.evento_empleado VALUES (23, 8, 5);


--
-- TOC entry 3512 (class 0 OID 16433)
-- Dependencies: 233
-- Data for Name: material_de_entrega; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.material_de_entrega VALUES (1, 'Album de Fotos');
INSERT INTO public.material_de_entrega VALUES (2, 'CDs/DVDs');
INSERT INTO public.material_de_entrega VALUES (3, 'Centro de mesas personalizados');
INSERT INTO public.material_de_entrega VALUES (4, 'Gigantografias');
INSERT INTO public.material_de_entrega VALUES (5, 'Pendrive/USB');


--
-- TOC entry 3514 (class 0 OID 16437)
-- Dependencies: 235
-- Data for Name: pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pago VALUES (1, 1, 'Tarjeta', 1);
INSERT INTO public.pago VALUES (2, 2, 'Tarjeta', 3);
INSERT INTO public.pago VALUES (3, 3, 'Tarjeta', 3);
INSERT INTO public.pago VALUES (4, 4, 'Tarjeta', 1);
INSERT INTO public.pago VALUES (5, 5, 'Tarjeta', 1);
INSERT INTO public.pago VALUES (6, 6, 'Tarjeta', 3);
INSERT INTO public.pago VALUES (7, 7, 'Tarjeta', 1);
INSERT INTO public.pago VALUES (8, 8, 'Tarjeta', 1);


--
-- TOC entry 3516 (class 0 OID 16441)
-- Dependencies: 237
-- Data for Name: paquete; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.paquete VALUES (1, 'Cena de egresados 2023', 150000, 'Incluye servicio de cabina de fotos, camara 360, animador, barra de cocteles, food trucks y como material de entrega pendrive/usb con fotos tomadas en el evento para los estudiantes.');
INSERT INTO public.paquete VALUES (2, 'Cena de egresados 2024', 250000, 'Incluye servicio de cabina de fotos, drones, camara 360, animador, barra de cocteles, food trucks y como material de entrega pendrive/usb con fotos tomadas en el evento para los estudiantes.');
INSERT INTO public.paquete VALUES (3, 'Cumpleaños de 15, año 2023', 180000, 'Incluye servicio de cabina de fotos, mesa dulce y postres, decoracion tematica y como material de entrega gigantografia, pendrive/usb o CD/DVD con fotos del evento');
INSERT INTO public.paquete VALUES (4, 'Cumpleaños de 15, año 2024', 280000, 'Incluye servicio de cabina de fotos, mesa dulce y postres, decoracion tematica, drones, camara 360  y como material de entrega gigantografia, pendrive/usb o CD/DVD con fotos del evento');
INSERT INTO public.paquete VALUES (5, 'Evento especial, 2024', 420000, 'El evento especial contiene servicios de cocteleria y varios servicios mas, vienen incluido grabaciones de todo el evento y materiales de entrega como fotos enmarcadas y pendrive/USB con fotos y videos editados');
INSERT INTO public.paquete VALUES (6, 'Bautismo 2024', 120000, 'Menú personalizado.
Arreglos florales y decoración temática.
Fotografía del evento.
Música en vivo para la recepción.
Diseño y entrega de invitaciones personalizadas.
Souvenirs para todos los invitados.');
INSERT INTO public.paquete VALUES (7, 'Evento especial 2024', 240000, 'El evento especial cuenta con catering completo, artistas, animadores, decoracion tematica, barras de cocteles, pudiendo elegir la cantidad de usb con los videos y demas del evento y un maximo de 2 album de fotos.');
INSERT INTO public.paquete VALUES (8, 'Casamiento 2023', 200000, 'Incluye servicio de catering, cabina de fotos, food truck, y como material de entrega un pendrive/usb con todas las fotos tomadas en el evento ');
INSERT INTO public.paquete VALUES (9, 'Casamiento 2024', 210000, 'El paquete de Casamiento 2024 cuenta con grabación en pre-producción: (Books) en exterior.
Acompañado de servicios durante el evento (catering completo) y animadores.
Album de fotos con todos los recuerdo como material de entrega.');
INSERT INTO public.paquete VALUES (10, 'Conferencias 2024', 260000, 'Este paquete incluye todos los servicios necesarios para la organización y ejecución de una conferencia de alto impacto. Ideal para empresas, instituciones y organizaciones que buscan un evento profesional y dinámico en el ámbito de la innovación, tecnología, y emprendimiento.');
INSERT INTO public.paquete VALUES (11, 'Evento Deportivos 2024', 300000, 'Este paquete está diseñado para organizar eventos deportivos de cualquier escala, desde torneos locales hasta competiciones regionales. Ideal para clubes deportivos, instituciones educativas, empresas y asociaciones que desean ofrecer una experiencia deportiva de primer nivel.');
INSERT INTO public.paquete VALUES (12, 'Eventos Escolares', 200000, 'Este paquete está especialmente diseñado para instituciones educativas que desean celebrar momentos clave en la vida académica de sus estudiantes, como ceremonias de graduación, entrega de diplomas y actos conmemorativos. Proporciona una organización impecable y una experiencia memorable tanto para los estudiantes como para sus familias y el personal académico.');


--
-- TOC entry 3518 (class 0 OID 16447)
-- Dependencies: 239
-- Data for Name: paquete_material_de_entrega; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.paquete_material_de_entrega VALUES (1, 1, 5, 31);
INSERT INTO public.paquete_material_de_entrega VALUES (3, 2, 5, 31);
INSERT INTO public.paquete_material_de_entrega VALUES (4, 3, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (5, 3, 2, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (6, 4, 2, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (7, 4, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (8, 5, 2, 3);
INSERT INTO public.paquete_material_de_entrega VALUES (9, 5, 1, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (12, 7, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (13, 6, 1, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (14, 6, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (15, 8, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (16, 9, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (17, 10, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (18, 11, 5, 1);
INSERT INTO public.paquete_material_de_entrega VALUES (19, 12, 5, 31);


--
-- TOC entry 3520 (class 0 OID 16451)
-- Dependencies: 241
-- Data for Name: paquete_servicio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.paquete_servicio VALUES (1, 4);
INSERT INTO public.paquete_servicio VALUES (1, 6);
INSERT INTO public.paquete_servicio VALUES (1, 7);
INSERT INTO public.paquete_servicio VALUES (2, 1);
INSERT INTO public.paquete_servicio VALUES (2, 4);
INSERT INTO public.paquete_servicio VALUES (2, 6);
INSERT INTO public.paquete_servicio VALUES (2, 7);
INSERT INTO public.paquete_servicio VALUES (2, 10);
INSERT INTO public.paquete_servicio VALUES (3, 4);
INSERT INTO public.paquete_servicio VALUES (3, 10);
INSERT INTO public.paquete_servicio VALUES (3, 11);
INSERT INTO public.paquete_servicio VALUES (4, 1);
INSERT INTO public.paquete_servicio VALUES (4, 4);
INSERT INTO public.paquete_servicio VALUES (4, 10);
INSERT INTO public.paquete_servicio VALUES (4, 11);
INSERT INTO public.paquete_servicio VALUES (4, 13);
INSERT INTO public.paquete_servicio VALUES (5, 1);
INSERT INTO public.paquete_servicio VALUES (5, 5);
INSERT INTO public.paquete_servicio VALUES (5, 6);
INSERT INTO public.paquete_servicio VALUES (5, 10);
INSERT INTO public.paquete_servicio VALUES (5, 13);
INSERT INTO public.paquete_servicio VALUES (7, 5);
INSERT INTO public.paquete_servicio VALUES (7, 6);
INSERT INTO public.paquete_servicio VALUES (7, 10);
INSERT INTO public.paquete_servicio VALUES (7, 12);
INSERT INTO public.paquete_servicio VALUES (6, 5);
INSERT INTO public.paquete_servicio VALUES (6, 7);
INSERT INTO public.paquete_servicio VALUES (8, 4);
INSERT INTO public.paquete_servicio VALUES (8, 5);
INSERT INTO public.paquete_servicio VALUES (8, 7);
INSERT INTO public.paquete_servicio VALUES (9, 2);
INSERT INTO public.paquete_servicio VALUES (9, 3);
INSERT INTO public.paquete_servicio VALUES (9, 4);
INSERT INTO public.paquete_servicio VALUES (9, 5);
INSERT INTO public.paquete_servicio VALUES (9, 7);
INSERT INTO public.paquete_servicio VALUES (9, 11);
INSERT INTO public.paquete_servicio VALUES (10, 2);
INSERT INTO public.paquete_servicio VALUES (10, 3);
INSERT INTO public.paquete_servicio VALUES (10, 5);
INSERT INTO public.paquete_servicio VALUES (10, 13);
INSERT INTO public.paquete_servicio VALUES (11, 2);
INSERT INTO public.paquete_servicio VALUES (11, 3);
INSERT INTO public.paquete_servicio VALUES (11, 7);
INSERT INTO public.paquete_servicio VALUES (11, 13);
INSERT INTO public.paquete_servicio VALUES (12, 2);
INSERT INTO public.paquete_servicio VALUES (12, 3);


--
-- TOC entry 3521 (class 0 OID 16454)
-- Dependencies: 242
-- Data for Name: security; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3522 (class 0 OID 16459)
-- Dependencies: 243
-- Data for Name: servicio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.servicio VALUES (1, 'Camara 360');
INSERT INTO public.servicio VALUES (2, 'Fotografia profesional');
INSERT INTO public.servicio VALUES (3, 'Videografia');
INSERT INTO public.servicio VALUES (4, 'Cabina de fotos');
INSERT INTO public.servicio VALUES (5, 'Catering completo');
INSERT INTO public.servicio VALUES (6, 'Barras de Cocteles');
INSERT INTO public.servicio VALUES (7, 'Food Trucks');
INSERT INTO public.servicio VALUES (8, 'Estaciones de Comida Tematica');
INSERT INTO public.servicio VALUES (9, 'Animadores y Presentadores');
INSERT INTO public.servicio VALUES (10, 'Decoracion Tematica');
INSERT INTO public.servicio VALUES (11, 'Mesa dulce y postres');
INSERT INTO public.servicio VALUES (12, 'Artistas');
INSERT INTO public.servicio VALUES (13, 'Drones');


--
-- TOC entry 3524 (class 0 OID 16463)
-- Dependencies: 245
-- Data for Name: subtarea; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.subtarea VALUES (1, 1, 'Decoracion romantica', true);


--
-- TOC entry 3526 (class 0 OID 16467)
-- Dependencies: 247
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (1, 'admin', 'admin', '$2a$10$aLWU.VHcTJAyFslZBcntHundhobpQzmgRPYYQ.RzHPNcWS3ZXn8P6', 'ADMIN');


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 216
-- Name: categoria_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_id_categoria_seq', 8, true);


--
-- TOC entry 3550 (class 0 OID 0)
-- Dependencies: 218
-- Name: cliente_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_cliente_seq', 12, true);


--
-- TOC entry 3551 (class 0 OID 0)
-- Dependencies: 220
-- Name: comentario_id_comentario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comentario_id_comentario_seq', 1, false);


--
-- TOC entry 3552 (class 0 OID 0)
-- Dependencies: 223
-- Name: disponibilidad_id_disponibilidad_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.disponibilidad_id_disponibilidad_seq', 20, true);


--
-- TOC entry 3553 (class 0 OID 0)
-- Dependencies: 226
-- Name: empleado_id_empleado_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empleado_id_empleado_seq', 7, true);


--
-- TOC entry 3554 (class 0 OID 0)
-- Dependencies: 228
-- Name: especialidad_id_especialidad_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.especialidad_id_especialidad_seq', 7, true);


--
-- TOC entry 3555 (class 0 OID 0)
-- Dependencies: 231
-- Name: evento_empleado_id_evento_empleado_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.evento_empleado_id_evento_empleado_seq', 23, true);


--
-- TOC entry 3556 (class 0 OID 0)
-- Dependencies: 232
-- Name: evento_id_evento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.evento_id_evento_seq', 8, true);


--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 234
-- Name: material_de_entrega_id_material_de_entrega_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.material_de_entrega_id_material_de_entrega_seq', 5, true);


--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 236
-- Name: pago_id_pago_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pago_id_pago_seq', 8, true);


--
-- TOC entry 3559 (class 0 OID 0)
-- Dependencies: 238
-- Name: paquete_id_paquete_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paquete_id_paquete_seq', 12, true);


--
-- TOC entry 3560 (class 0 OID 0)
-- Dependencies: 240
-- Name: paquete_material_de_entrega_id_paquete_material_de_entrega_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paquete_material_de_entrega_id_paquete_material_de_entrega_seq', 19, true);


--
-- TOC entry 3561 (class 0 OID 0)
-- Dependencies: 244
-- Name: servicio_id_servicio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servicio_id_servicio_seq', 13, true);


--
-- TOC entry 3562 (class 0 OID 0)
-- Dependencies: 246
-- Name: subtarea_id_subtarea_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subtarea_id_subtarea_seq', 1, true);


--
-- TOC entry 3563 (class 0 OID 0)
-- Dependencies: 248
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 1, true);


--
-- TOC entry 3305 (class 2606 OID 16490)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria);


--
-- TOC entry 3307 (class 2606 OID 16492)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 3309 (class 2606 OID 16494)
-- Name: comentario comentario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_pkey PRIMARY KEY (id_comentario);


--
-- TOC entry 3311 (class 2606 OID 16496)
-- Name: cuota cuota_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuota
    ADD CONSTRAINT cuota_pkey PRIMARY KEY (nro_cuota, id_pago);


--
-- TOC entry 3313 (class 2606 OID 16498)
-- Name: empleado empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (id_empleado);


--
-- TOC entry 3315 (class 2606 OID 16500)
-- Name: especialidad especialidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.especialidad
    ADD CONSTRAINT especialidad_pkey PRIMARY KEY (id_especialidad);


--
-- TOC entry 3319 (class 2606 OID 16502)
-- Name: evento_empleado evento_empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento_empleado
    ADD CONSTRAINT evento_empleado_pkey PRIMARY KEY (id_evento_empleado);


--
-- TOC entry 3317 (class 2606 OID 16504)
-- Name: evento evento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id_evento);


--
-- TOC entry 3321 (class 2606 OID 16508)
-- Name: material_de_entrega material_de_entrega_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.material_de_entrega
    ADD CONSTRAINT material_de_entrega_pkey PRIMARY KEY (id_material_de_entrega);


--
-- TOC entry 3323 (class 2606 OID 16510)
-- Name: pago pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_pkey PRIMARY KEY (id_pago);


--
-- TOC entry 3327 (class 2606 OID 16512)
-- Name: paquete_material_de_entrega paquete_material_de_entrega_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_material_de_entrega
    ADD CONSTRAINT paquete_material_de_entrega_pkey PRIMARY KEY (id_paquete_material_de_entrega);


--
-- TOC entry 3325 (class 2606 OID 16514)
-- Name: paquete paquete_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete
    ADD CONSTRAINT paquete_pkey PRIMARY KEY (id_paquete);


--
-- TOC entry 3329 (class 2606 OID 16516)
-- Name: servicio servicio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (id_servicio);


--
-- TOC entry 3331 (class 2606 OID 16518)
-- Name: subtarea subtarea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subtarea
    ADD CONSTRAINT subtarea_pkey PRIMARY KEY (id_subtarea);


--
-- TOC entry 3333 (class 2606 OID 16520)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 3334 (class 2606 OID 16521)
-- Name: comentario comentario_id_evento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_id_evento_fkey FOREIGN KEY (id_evento) REFERENCES public.evento(id_evento);


--
-- TOC entry 3335 (class 2606 OID 16526)
-- Name: comentario comentario_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 3336 (class 2606 OID 16531)
-- Name: cuota cuota_id_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuota
    ADD CONSTRAINT cuota_id_pago_fkey FOREIGN KEY (id_pago) REFERENCES public.pago(id_pago);


--
-- TOC entry 3337 (class 2606 OID 16536)
-- Name: disponibilidad disponibilidad_id_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disponibilidad
    ADD CONSTRAINT disponibilidad_id_empleado_fkey FOREIGN KEY (id_empleado) REFERENCES public.empleado(id_empleado);


--
-- TOC entry 3338 (class 2606 OID 16541)
-- Name: empleado_especialidad empleado_especialidad_id_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado_especialidad
    ADD CONSTRAINT empleado_especialidad_id_empleado_fkey FOREIGN KEY (id_empleado) REFERENCES public.empleado(id_empleado);


--
-- TOC entry 3339 (class 2606 OID 16546)
-- Name: empleado_especialidad empleado_especialidad_id_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado_especialidad
    ADD CONSTRAINT empleado_especialidad_id_especialidad_fkey FOREIGN KEY (id_especialidad) REFERENCES public.especialidad(id_especialidad);


--
-- TOC entry 3343 (class 2606 OID 16551)
-- Name: evento_empleado evento_empleado_id_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento_empleado
    ADD CONSTRAINT evento_empleado_id_empleado_fkey FOREIGN KEY (id_empleado) REFERENCES public.empleado(id_empleado);


--
-- TOC entry 3344 (class 2606 OID 16556)
-- Name: evento_empleado evento_empleado_id_evento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento_empleado
    ADD CONSTRAINT evento_empleado_id_evento_fkey FOREIGN KEY (id_evento) REFERENCES public.evento(id_evento);


--
-- TOC entry 3340 (class 2606 OID 16561)
-- Name: evento evento_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id_categoria);


--
-- TOC entry 3341 (class 2606 OID 16566)
-- Name: evento evento_id_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente);


--
-- TOC entry 3342 (class 2606 OID 16571)
-- Name: evento evento_id_paquete_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_id_paquete_fkey FOREIGN KEY (id_paquete) REFERENCES public.paquete(id_paquete);


--
-- TOC entry 3345 (class 2606 OID 16581)
-- Name: pago pago_id_evento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_id_evento_fkey FOREIGN KEY (id_evento) REFERENCES public.evento(id_evento);


--
-- TOC entry 3346 (class 2606 OID 16586)
-- Name: paquete_material_de_entrega paquete_material_de_entrega_id_material_de_entrega_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_material_de_entrega
    ADD CONSTRAINT paquete_material_de_entrega_id_material_de_entrega_fkey FOREIGN KEY (id_material_de_entrega) REFERENCES public.material_de_entrega(id_material_de_entrega);


--
-- TOC entry 3347 (class 2606 OID 16591)
-- Name: paquete_material_de_entrega paquete_material_de_entrega_id_paquete_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_material_de_entrega
    ADD CONSTRAINT paquete_material_de_entrega_id_paquete_fkey FOREIGN KEY (id_paquete) REFERENCES public.paquete(id_paquete);


--
-- TOC entry 3348 (class 2606 OID 16596)
-- Name: paquete_servicio paquete_servicio_id_paquete_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_servicio
    ADD CONSTRAINT paquete_servicio_id_paquete_fkey FOREIGN KEY (id_paquete) REFERENCES public.paquete(id_paquete);


--
-- TOC entry 3349 (class 2606 OID 16601)
-- Name: paquete_servicio paquete_servicio_id_servicio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paquete_servicio
    ADD CONSTRAINT paquete_servicio_id_servicio_fkey FOREIGN KEY (id_servicio) REFERENCES public.servicio(id_servicio);


--
-- TOC entry 3350 (class 2606 OID 16606)
-- Name: subtarea subtarea_id_evento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subtarea
    ADD CONSTRAINT subtarea_id_evento_fkey FOREIGN KEY (id_evento) REFERENCES public.evento(id_evento);


-- Completed on 2024-09-25 18:21:01 -03

--
-- PostgreSQL database dump complete
--

