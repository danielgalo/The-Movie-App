package persistence.dao;

import persistence.entities.Company;

/**
 * Interfaz DAO de la entidad Company
 */
public interface CompanyDaoI {

	/**
	 * Buscar compañia por nombre
	 * 
	 * @param name nombre de la compañia
	 * @return compañia con el nombre aportado
	 */
	public Company searchCompanyByNombre(final String nombre);

}
