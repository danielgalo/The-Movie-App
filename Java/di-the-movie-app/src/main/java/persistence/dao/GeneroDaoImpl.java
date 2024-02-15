package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistence.entities.Genero;

/**
 * Implementacion DAO para la entidad Genero
 */
public class GeneroDaoImpl extends CommonDaoImpl<Genero> implements GeneroDaoI {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session sesión hibernate
	 */
	public GeneroDaoImpl(Session session) {
		super(session);
		this.session = session;

	}

	@Override
	public Genero getGeneroByName(String nombre) {

		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		String hql = "FROM Genero WHERE nombre = :nombre";
		Query<Genero> query = session.createQuery(hql);
		query.setParameter("nombre", nombre);
		
		return query.getSingleResult();
	}

	@Override
	public Genero getGeneroById(Long id) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		String hql = "FROM Genero WHERE id = :id";
		Query<Genero> query = session.createQuery(hql);
		query.setParameter("id", id);

		return query.getSingleResult();
	}

}
