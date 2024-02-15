package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa la clave primaria compuesta para la entidad DirectoresPeliculas.
 * Esta clave primaria compuesta está compuesta por las referencias a las
 * entidades Pelicula y Director.
 * 
 * @see Pelicula
 * @see DirectoresPeliculas
 * @see Director
 * 
 * @serial 1L
 */
@Embeddable
public class DirectoresPeliculasId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Referencia a la entidad Pelicula asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_pelicula")
	private Pelicula pelicula;

	/**
	 * Referencia a la entidad Director asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_director")
	private Director director;

	/**
	 * @param pelicula
	 * @param director
	 */
	public DirectoresPeliculasId(Pelicula pelicula, Director director) {
		this.pelicula = pelicula;
		this.director = director;
	}

	/**
	 * Constructor vacío
	 */
	public DirectoresPeliculasId() {
		// La librería Jackson necesita constructor vacío
	}

	/**
	 * @return the pelicula
	 */
	public Pelicula getPelicula() {
		return pelicula;
	}

	/**
	 * @param pelicula the pelicula to set
	 */
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	/**
	 * @return the director
	 */
	public Director getDirector() {
		return director;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(Director director) {
		this.director = director;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
