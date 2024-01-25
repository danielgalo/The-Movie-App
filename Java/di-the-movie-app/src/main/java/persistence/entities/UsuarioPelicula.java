package persistence.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre usuarios y peliculas
 */
@Entity
@Table(name = "usuario_pelicula")
public class UsuarioPelicula {

	/** Id de la relación */
	@EmbeddedId
	private UsuarioPeliculaId id;

	/**
	 * @param id
	 */
	public UsuarioPelicula(UsuarioPeliculaId id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public UsuarioPeliculaId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UsuarioPeliculaId id) {
		this.id = id;
	}

}
