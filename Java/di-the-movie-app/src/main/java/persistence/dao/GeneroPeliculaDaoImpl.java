package persistence.dao;

import org.hibernate.Session;

import persistence.entities.GeneroPelicula;

/**
 * Implementación DAO de la entidad GeneroPelicula
 */
public class GeneroPeliculaDaoImpl extends CommonDaoImpl<GeneroPelicula> {

	/** Sesion hibernate */
	private Session session;

	/**
	 * Consturctor sobrecargado
	 * 
	 * @param session sesion
	 */
	public GeneroPeliculaDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

}
