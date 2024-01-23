package persistence.dao;

import java.util.List;

import org.hibernate.Session;

import persistence.entities.Pelicula;

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

		String hql = "FROM Pelicula WHERE titulo = :title";

		return session.createQuery(hql).setParameter("title", title).list();

	}

}
