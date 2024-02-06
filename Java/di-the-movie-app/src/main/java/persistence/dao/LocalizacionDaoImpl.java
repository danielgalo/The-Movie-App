package persistence.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import jakarta.persistence.NoResultException;
import persistence.entities.Localizacion;

public class LocalizacionDaoImpl extends CommonDaoImpl<Localizacion> implements LocalizacionDaoI {

	private Session session;

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
		Query<Localizacion> query = session.createQuery(hql);
		query.setParameter("nombre", nombre);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
