package persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;
import persistence.entities.Genero;
import persistence.entities.GeneroPelicula;
import persistence.entities.Pelicula;
import persistence.entities.User;

/**
 * Implementación del DAO de películas
 */
public class PeliculaDaoImpl extends CommonDaoImpl<Pelicula> implements PeliculaDaoI {

	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session
	 */
	public PeliculaDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public List<Pelicula> searchMovieByTitle(String title) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		String hql = "FROM Pelicula WHERE titulo = :title";

		return session.createQuery(hql).setParameter("title", title).list();

	}

	@Override
	public List<Pelicula> searcyMoviesByGenre(String nombre) {

		List<Pelicula> peliculasEncontradas = null;

		try {
			// Buscar el género en la BBDD
			TypedQuery<Genero> queryGenero = session.createQuery("FROM Genero WHERE nombre = :nombre", Genero.class);
			queryGenero.setParameter("nombre", nombre);

			Long id = queryGenero.getSingleResult().getId();

			// Una vez obtenido el id del género, buscar las películas que tengan ese
			// id_genero
			TypedQuery<GeneroPelicula> queryGP = session.createQuery("FROM GeneroPelicula WHERE id.genero.id = :id",
					GeneroPelicula.class);
			queryGP.setParameter("id", id);

			// Lista de GeneroPelicula con el id del género obtenido anteriormente y los
			// ids de las películas de ese género
			List<GeneroPelicula> queryGPResults = queryGP.getResultList();

			peliculasEncontradas = new ArrayList<>();

			// Por cada relación de género y película
			for (GeneroPelicula genPeli : queryGPResults) {

				// Obtener el id de las películas
				Long idPeli = genPeli.getId().getPelicula().getId();

				// Buscar películas por ese id, añadirlas a la lista de pelis encontradas
				TypedQuery<Pelicula> queryPelicula = session.createQuery("FROM Pelicula WHERE id = :id",
						Pelicula.class);
				queryPelicula.setParameter("id", idPeli);
				Pelicula peliEncontrada = queryPelicula.getSingleResult();
				peliculasEncontradas.add(peliEncontrada);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return peliculasEncontradas;
	}

	@Override
	public List<Pelicula> searchByExactYear(int year) {

		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year = :year", Pelicula.class);
			query.setParameter("year", year);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pelicula> searchByGreaterYear(int year) {
		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year > :year", Pelicula.class);
			query.setParameter("year", year);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pelicula> searchByEarlierYear(int year) {
		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year < :year", Pelicula.class);
			query.setParameter("year", year);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pelicula> searchByUser(User user) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		String hql = "FROM Pelicula WHERE usuario.id = :id";

		return session.createQuery(hql).setParameter("id", user.getId()).list();
	}

}
