package persistence.dao;

import java.util.List;

import persistence.entities.Pelicula;
import persistence.entities.User;

/**
 * Interfaz DAO de película
 */
public interface PeliculaDaoI {

	/**
	 * Busca una película por su id
	 * 
	 * @param id id de la pelicula a buscar
	 * @return Pelicula con el id proporcionado
	 */
	public Pelicula searchById(final Long id, final int userId);

	/**
	 * Busca una película por su título
	 * 
	 * @param title  título de la película
	 * @param userId id del usuario que busca las películas
	 * @return Película con el título
	 */
	public List<Pelicula> searchMovieByTitle(final String titulo, final int userId);

	/**
	 * Busca películas por su género
	 * 
	 * @param genre  género de la película
	 * @param userId id del usuario que busca las películas
	 * @return Lista de películas con el género pasado o null si no se encuentra
	 */
	public List<Pelicula> searcyMoviesByGenre(final String genre, final int userId);

	/**
	 * Bussca películas por año exacto
	 * 
	 * @param year   año de la película
	 * @param userId id del usuario que busca las películas
	 * @return Lista con las películas encontradas de ese año, o null si no se
	 *         encuentra nada
	 */
	public List<Pelicula> searchByExactYear(final int year, final int userId);

	/**
	 * Busca películas lanzadas después del año proporcionado
	 * 
	 * @param year   año a filtrar
	 * @param userId id del usuario que busca las películas
	 * @return Lista de películas encontradas o null si no se encuentra
	 */
	public List<Pelicula> searchByGreaterYear(final int year, final int userId);

	/**
	 * Busca películas lanzadas antes del año proporcionado
	 * 
	 * @param year   año a filtrar
	 * @param userId id del usuario que busca las películas
	 * @return Lista de películas encontradas o null si no se encuentra
	 */
	public List<Pelicula> searchByEarlierYear(final int year, final int userId);

	/**
	 * Buscar películas por id de usuario
	 * 
	 * @param user
	 * @return
	 */
	public List<Pelicula> searchByUser(final User user);

}
