package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre actores y peliculas
 */
@Entity
@Table(name = "actores_peliculas")
public class ActoresPeliculas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActoresPeliculasId id;

	/**
	 * @param id
	 */
	public ActoresPeliculas(ActoresPeliculasId id) {
		this.id = id;
	}

	/**
	 * Constructor vacío
	 */
	public ActoresPeliculas() {
		// La librería Jackson necesita constructor vacío
	}

	/**
	 * @return the id
	 */
	public ActoresPeliculasId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ActoresPeliculasId id) {
		this.id = id;
	}

}
