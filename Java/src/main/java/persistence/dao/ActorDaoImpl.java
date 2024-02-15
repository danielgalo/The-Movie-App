package persistence.dao;

import org.hibernate.Session;

import persistence.entities.Actor;

/**
 * Implementación DAO para actores
 */
public class ActorDaoImpl extends CommonDaoImpl<Actor> {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session sesion hibernate
	 */
	public ActorDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

}
