package persistence.dao;

import persistence.entities.Localizacion;

public interface LocalizacionDaoI {

	/**
	 * Obtiene una localizacion de la base de datos dado su nombre
	 * 
	 * @param nombre nombre de la localizacion
	 * @return Localizacion con el nombre o null si no hay ninguna
	 */
	public Localizacion getLocalizacionByNombre(String nombre);

}
