package persistence.dao;

import java.util.List;

import org.hibernate.Session;

import persistence.entities.User;

public class UserDaoImpl extends CommonDaoImpl<User> implements UserDaoI {

	private Session session;

	public UserDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public User getUserByEmail(String email) {

		String hql = "FROM User WHERE email = :email";

		List<User> results = session.createQuery(hql).setParameter("email", email).list();

		// Si está vacío devuelve nulo, si no el primer resultado
		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

}
