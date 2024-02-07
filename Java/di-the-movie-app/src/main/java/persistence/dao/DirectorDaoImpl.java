package persistence.dao;

import org.hibernate.Session;

import persistence.entities.Director;

public class DirectorDaoImpl extends CommonDaoImpl<Director> {

	private Session session;

	public DirectorDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

}
