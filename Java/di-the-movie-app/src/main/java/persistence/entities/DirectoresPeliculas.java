package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre actores y peliculas
 */
@Entity
@Table(name = "directores_peliculas")
public class DirectoresPeliculas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DirectoresPeliculasId id;

	/**
	 * @param id
	 */
	public DirectoresPeliculas(DirectoresPeliculasId id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public DirectoresPeliculasId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(DirectoresPeliculasId id) {
		this.id = id;
	}

}
