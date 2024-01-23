package persistence.dao;

import persistence.entities.Genero;

/**
 * Interfaz DAO para la entidad Genero
 */
public interface GeneroDaoI {

	/**
	 * Obtener género por nombre
	 * 
	 * @param nombre nombre del género
	 * @return género con el nombre
	 */
	public Genero getGeneroByName(String nombre);

}
