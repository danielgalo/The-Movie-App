package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

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
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Actor getActorByName(String nombre) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		String hql = "FROM Actor WHERE nombre = :nombre";
		Query<Actor> query = session.createQuery(hql);
		query.setParameter("nombre", nombre);
		
		return query.getSingleResult();
	}

}
