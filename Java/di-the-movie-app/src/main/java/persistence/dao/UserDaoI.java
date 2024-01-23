package persistence.dao;

import persistence.entities.User;

/**
 * Interfaz DAO del usuario
 */
public interface UserDaoI {

	/**
	 * Busca un usuario en la base de datos por su email
	 * 
	 * @param email email del usuario
	 * @return usuario con el email aportado
	 */
	public User getUserByEmail(final String email);

}
