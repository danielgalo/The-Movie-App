package persistence.dao;

import java.util.List;

import persistence.entities.Pelicula;

/**
 * Interfaz DAO de película
 */
public interface PeliculaDaoI {

	/**
	 * Busca una película por su título
	 * 
	 * @param title título de la película
	 * @return Película con el título
	 */
	public List<Pelicula> searchMovieByTitle(final String titulo);

}
