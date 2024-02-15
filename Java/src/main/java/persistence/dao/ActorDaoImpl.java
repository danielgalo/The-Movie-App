package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistence.entities.Actor;
import persistence.entities.ActoresPeliculas;

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
	
	public ActoresPeliculas getActorByName(String nombre) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		String hql = "FROM Actor WHERE nombre = :nombre";
		Query<ActoresPeliculas> query = session.createQuery(hql);
		query.setParameter("nombre", nombre);
		
		return query.getSingleResult();
	}

}
