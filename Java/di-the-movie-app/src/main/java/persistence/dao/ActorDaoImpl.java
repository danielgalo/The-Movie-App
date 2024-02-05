package persistence.dao;

import org.hibernate.Session;

import persistence.entities.Actor;

public class ActorDaoImpl extends CommonDaoImpl<Actor> {

	private Session session;

	public ActorDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

}
