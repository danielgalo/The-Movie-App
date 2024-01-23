package persistence.dao;

import org.hibernate.Session;

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

		String hql = "FROM Genero WHERE titulo = :nombre";
		return (Genero) session.createQuery(hql).setParameter("nombre", nombre);

	}

}
