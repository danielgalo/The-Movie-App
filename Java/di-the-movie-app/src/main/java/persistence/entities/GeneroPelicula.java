package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre generos y peliculas
 */
@Entity
@Table(name = "genero_pelicula")
public class GeneroPelicula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Id de la relacion */
	@EmbeddedId
	private GeneroPeliculaId id;

	public GeneroPelicula(GeneroPeliculaId id) {
		this.id = id;
	}

	/**
	 * Constructor vacío
	 */
	public GeneroPelicula() {
		// La librería Jackson necesita constructor vacío
	}

	/**
	 * @return the id
	 */
	public GeneroPeliculaId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(GeneroPeliculaId id) {
		this.id = id;
	}

}
