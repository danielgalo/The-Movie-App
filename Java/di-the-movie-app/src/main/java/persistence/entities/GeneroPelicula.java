package persistence.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
