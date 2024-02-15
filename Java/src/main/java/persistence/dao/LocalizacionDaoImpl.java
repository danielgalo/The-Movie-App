package persistence.dao;

import org.hibernate.Session;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import persistence.entities.Localizacion;

/**
 * Implementación DAO de la entidad Localizacion
 */
public class LocalizacionDaoImpl extends CommonDaoImpl<Localizacion> implements LocalizacionDaoI {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Constructor
	 * 
	 * @param session sesion hibernate
	 */
	public LocalizacionDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Localizacion getLocalizacionByNombre(String nombre) {

		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		String hql = "FROM Localizacion WHERE nombre = :nombre";

		TypedQuery<Localizacion> query = session.createQuery(hql, Localizacion.class);
		query.setParameter("nombre", nombre);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
