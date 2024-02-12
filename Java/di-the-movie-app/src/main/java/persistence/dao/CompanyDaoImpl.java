package persistence.dao;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;
import persistence.entities.Company;

/**
 * Implementacion DAO de la entidad Company
 */
public class CompanyDaoImpl extends CommonDaoImpl<Company> implements CompanyDaoI {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param session sesion
	 */
	public CompanyDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Company searchCompanyByNombre(String nombre) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		String hql = "FROM Company WHERE name = :nombre";

		TypedQuery<Company> query = session.createQuery(hql, Company.class);
		query.setParameter("nombre", nombre);

		return query.getResultList().get(0);
	}

}
