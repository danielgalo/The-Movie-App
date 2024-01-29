package persistence.dao;

import org.hibernate.Session;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import persistence.entities.User;

/**
 * Implementación DAO para la entidad User
 */
public class UserDaoImpl extends CommonDaoImpl<User> implements UserDaoI {

	/** Sesion Hibernate */
	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session Sesion Hibernate
	 */
	public UserDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public User getUserByEmail(String email) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		TypedQuery<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
