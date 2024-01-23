package persistence.dao;

import org.hibernate.Session;

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
		return null;
	}

}
