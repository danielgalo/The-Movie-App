package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistence.entities.Director;

/**
 * Clase DAO para los directores
 */
public class DirectorDaoImpl extends CommonDaoImpl<Director> {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session sesion hibernate
	 */
	public DirectorDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Director getDirectorByName(String nombre) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		String hql = "FROM Director WHERE nombre = :nombre";
		Query<Director> query = session.createQuery(hql);
		query.setParameter("nombre", nombre);
		
		return query.getSingleResult();
	}

}
