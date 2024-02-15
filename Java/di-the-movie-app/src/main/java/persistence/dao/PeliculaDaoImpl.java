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
 * Implementación del DAO de películas.
 */
public class PeliculaDaoImpl extends CommonDaoImpl<Pelicula> implements PeliculaDaoI {

	/** Sesion hibernate */
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
	public List<Pelicula> searchMovieByTitle(String title, int userId) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		String hql = "FROM Pelicula WHERE titulo LIKE :title AND usuario.id = :id";

		TypedQuery<Pelicula> query = session.createQuery(hql, Pelicula.class);
		query.setParameter("title", "%" + title + "%");
		query.setParameter("id", userId);

		return query.getResultList();

	}

	@Override
	public List<Pelicula> searcyMoviesByGenre(String nombre, int userId) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

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
				Long idPeli = genPeli.getId().getPelicula().getIdApi();

				// Buscar películas por ese id, añadirlas a la lista de pelis encontradas
				TypedQuery<Pelicula> queryPelicula = session
						.createQuery("FROM Pelicula WHERE idApi = :id AND usuario.id = :userId", Pelicula.class);
				queryPelicula.setParameter("id", idPeli);
				queryPelicula.setParameter("userId", userId);
				Pelicula peliEncontrada = queryPelicula.getSingleResult();
				peliculasEncontradas.add(peliEncontrada);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return peliculasEncontradas;
	}

	@Override
	public List<Pelicula> searchByExactYear(int year, int userId) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year = :year AND usuario.id = :id",
					Pelicula.class);
			query.setParameter("year", year);
			query.setParameter("id", userId);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pelicula> searchByGreaterYear(int year, int userId) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year > :year AND usuario.id = :id",
					Pelicula.class);
			query.setParameter("year", year);
			query.setParameter("id", userId);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pelicula> searchByEarlierYear(int year, int userId) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE year < :year AND usuario.id = :id",
					Pelicula.class);
			query.setParameter("year", year);
			query.setParameter("id", userId);

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

	@Override
	public Pelicula searchById(Long id, int userId) {
		try {
			TypedQuery<Pelicula> query = session.createQuery("FROM Pelicula WHERE idApi = :id AND usuario.id = :userId",
					Pelicula.class);
			query.setParameter("id", id);
			query.setParameter("userId", userId);

			return query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
