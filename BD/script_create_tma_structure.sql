DROP DATABASE IF EXISTS the_movie_app;
CREATE DATABASE the_movie_app;
USE the_movie_app;
-- Eliminar claves foráneas


-- Eliminar tablas existentes si existen
DROP TABLE IF EXISTS actores;
DROP TABLE IF EXISTS actores_peliculas;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS company_pelicula;
DROP TABLE IF EXISTS directores;
DROP TABLE IF EXISTS directores_peliculas;
DROP TABLE IF EXISTS genero_pelicula;
DROP TABLE IF EXISTS generos;
DROP TABLE IF EXISTS localizaciones;
DROP TABLE IF EXISTS peliculas;
DROP TABLE IF EXISTS usuario;

-- Crear nuevas tablas
CREATE TABLE usuario (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    ultima_fecha_modificacion DATETIME(6),
    email VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT UK_5171l57faosmj8myawaucatdw UNIQUE (email)
);

CREATE TABLE localizaciones (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    ultima_fecha_modificacion DATETIME(6),
    localizacion VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE generos (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL,
    ultima_fecha_modificacion DATETIME(6),
    nombre VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE companies (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    ultima_fecha_modificacion DATETIME(6),
    name VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE actores (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    ultima_fecha_modificacion DATETIME(6),
    nombre VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE directores (
    fecha_de_alta DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    ultima_fecha_modificacion DATETIME(6),
    nombre VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;



CREATE TABLE peliculas (
    valoracion_usuario FLOAT(53),
    year INTEGER,
    fecha_de_alta DATETIME(6) NOT NULL,
    fecha_visualizacion_usuario DATETIME(6),
    id BIGINT NOT NULL AUTO_INCREMENT,
    localizacion_id BIGINT,
    release_date DATETIME(6),
    ultima_fecha_modificacion DATETIME(6),
    user_id BIGINT,
    descripcion VARCHAR(10000),
    cartel VARCHAR(255),
    comentarios_usuario VARCHAR(255),
    titulo VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (localizacion_id) REFERENCES localizaciones (id),
    FOREIGN KEY (user_id) REFERENCES usuario (id)
) ENGINE=InnoDB;

CREATE TABLE actores_peliculas (
    fecha_de_alta DATETIME(6) NOT NULL,
    id_actor BIGINT NOT NULL,
    id_pelicula BIGINT NOT NULL,
    ultima_fecha_modificacion DATETIME(6),
    PRIMARY KEY (id_actor, id_pelicula)
) ENGINE=InnoDB;

CREATE TABLE directores_peliculas (
    fecha_de_alta DATETIME(6) NOT NULL,
    id_director BIGINT NOT NULL,
    id_pelicula BIGINT NOT NULL,
    ultima_fecha_modificacion DATETIME(6),
    PRIMARY KEY (id_director, id_pelicula)
) ENGINE=InnoDB;

CREATE TABLE company_pelicula (
    fecha_de_alta DATETIME(6) NOT NULL,
    id_company BIGINT NOT NULL,
    id_pelicula BIGINT NOT NULL,
    ultima_fecha_modificacion DATETIME(6),
    PRIMARY KEY (id_company, id_pelicula)
) ENGINE=InnoDB;


CREATE TABLE genero_pelicula (
    fecha_de_alta DATETIME(6) NOT NULL,
    id_genero BIGINT NOT NULL,
    id_pelicula BIGINT NOT NULL,
    ultima_fecha_modificacion DATETIME(6),
    PRIMARY KEY (id_genero, id_pelicula)
) ENGINE=InnoDB;






-- Agregar claves foráneas a las tablas
ALTER TABLE actores_peliculas ADD CONSTRAINT FK5a8smf0wrroc0mjm7seao6reo FOREIGN KEY (id_pelicula) REFERENCES peliculas (id);
ALTER TABLE actores_peliculas ADD CONSTRAINT FK8cuy9qhj2j6ntjplt78bxg6k2 FOREIGN KEY (id_actor) REFERENCES actores (id);
ALTER TABLE company_pelicula ADD CONSTRAINT FKdoh6bep74t57d9d5f5g8aa1ib FOREIGN KEY (id_pelicula) REFERENCES peliculas (id);
ALTER TABLE company_pelicula ADD CONSTRAINT FKoeuswo0t52n5tuc9kto58gurn FOREIGN KEY (id_company) REFERENCES companies (id);
ALTER TABLE directores_peliculas ADD CONSTRAINT FK45bhn8dkhk83k08t76ds6cvm8 FOREIGN KEY (id_director) REFERENCES directores (id);
ALTER TABLE directores_peliculas ADD CONSTRAINT FKen6uoex8mkvrqacddvdcdnn2m FOREIGN KEY (id_pelicula) REFERENCES peliculas (id);
ALTER TABLE genero_pelicula ADD CONSTRAINT FK90b4wbyoquvm8mfmwbdnkhqqk FOREIGN KEY (id_genero) REFERENCES generos (id);
ALTER TABLE genero_pelicula ADD CONSTRAINT FK9q4duknur3uhb2930vuwrfm3x FOREIGN KEY (id_pelicula) REFERENCES peliculas (id);
ALTER TABLE peliculas ADD CONSTRAINT FKb6mpqsijugqks7i4lbrjma8xn FOREIGN KEY (localizacion_id) REFERENCES localizaciones (id);
ALTER TABLE peliculas ADD CONSTRAINT FKmpp48q489bdpm3fawl18v4mnd FOREIGN KEY (user_id) REFERENCES usuario (id);
