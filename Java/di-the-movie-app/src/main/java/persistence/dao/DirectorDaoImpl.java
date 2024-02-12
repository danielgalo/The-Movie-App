package persistence.dao;

import org.hibernate.Session;

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

}
