DROP DATABASE IF EXISTS bd_the_movie_app;
CREATE DATABASE IF NOT EXISTS bd_the_movie_app;
USE bd_the_movie_app;


CREATE TABLE IF NOT EXISTS usuario (

	email varchar(200),
    user_password varchar(50),
    
    primary key(email)

);

CREATE TABLE IF NOT EXISTS localizaciones(
	localizacion varchar(250),
    
    primary key(localizacion)
);

CREATE TABLE IF NOT EXISTS companies (
    NIF varchar(15) unique key,
    nombre varchar(200),
    primary key(NIF)
);

CREATE TABLE IF NOT EXISTS pelicula (
    id_pelicula int,
    titulo varchar(200),
    anyo_pelicula year,
    company varchar(15), 
    descripcion varchar(300),
    cartel varchar(250),
    generos varchar(300),
    directores varchar(300),
    actores varchar(300),
    valoracion varchar(250),
    valoracion_usuario varchar(250),
    fecha_visualizacion_usuario DATE,
    comentarios_usuario varchar(250),
    localizacion_pelicula varchar(250),
    primary key(id_pelicula),
    foreign key(company) references companies(NIF),
    foreign key (localizacion_pelicula) references localizaciones(localizacion)
);

CREATE TABLE IF NOT EXISTS actor (

	id_actor int,
    nombre varchar(200),
    apellidos varchar(200),
    fecha_nacimiento date,
    
    primary key (id_actor)

);

-- Relacion N:M entre actores y peliculas
CREATE TABLE IF NOT EXISTS pelicula_actor (

	id_relacion int auto_increment,
    id_pelicula int,
    id_actor int,
    
    primary key (id_relacion),
    foreign key (id_pelicula) references pelicula(id_pelicula),
    foreign key (id_actor) references actor(id_actor)
);

CREATE TABLE IF NOT EXISTS director (

	id_director int,
    nombre varchar(200),
    apellidos varchar(200),
    fecha_nacimiento date,
    
    primary key (id_director)

);

-- Relacion N:M entre directores y peliculas
CREATE TABLE IF NOT EXISTS pelicula_director (

	id_relacion int auto_increment,
	id_pelicula int,
    id_director int,
    
    primary key (id_relacion),
    foreign key (id_pelicula) references pelicula(id_pelicula),
    foreign key (id_director) references director(id_director)
 
);

CREATE TABLE IF NOT EXISTS genero (

    nombre varchar(200),
    primary key(nombre)

);

-- Relacion N:M entre peliculas y géneros
CREATE TABLE IF NOT EXISTS peliculas_genero (

	id_relacion int auto_increment,
    id_pelicula int,
    genero varchar(200),
    
    primary key (id_relacion),
	foreign key (id_pelicula) references pelicula(id_pelicula),
    foreign key (genero) references genero(nombre)
);




