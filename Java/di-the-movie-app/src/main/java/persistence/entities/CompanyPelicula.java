package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa la relacion entre generos y compañias
 */
@Entity
@Table(name = "company_pelicula")
public class CompanyPelicula extends AbstractEntity implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/** Id de la relacion */
	@EmbeddedId
	private CompanyPeliculaId id;

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public CompanyPelicula(CompanyPeliculaId id) {
		this.id = id;
	}

	/**
	 * Constructor vacío
	 */
	public CompanyPelicula() {
		// La librería Jackson necesita constructor vacío
	}

	/**
	 * @return the id
	 */
	public CompanyPeliculaId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(CompanyPeliculaId id) {
		this.id = id;
	}

}
