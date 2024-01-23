package persistence.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * La clase GeneroPeliculaId representa la clave primaria compuesta para la
 * entidad GeneroPelicula. Esta clave primaria compuesta está compuesta por las
 * referencias a las entidades Pelicula y Genero.
 * 
 * @see Pelicula
 * @see Genero
 * @see GeneroPelicula
 * 
 * @serial 1L
 */
@Embeddable
public class GeneroPeliculaId implements Serializable {

	/**
	 * Identificador único para la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Referencia a la entidad Pelicula asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_pelicula")
	private Pelicula pelicula;

	/**
	 * Referencia a la entidad Genero asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_genero")
	private Genero genero;

	/**
	 * Constructor de la clase GeneroPeliculaId.
	 * 
	 * @param pelicula La entidad Pelicula asociada a la clave primaria.
	 * @param genero   La entidad Genero asociada a la clave primaria.
	 */
	public GeneroPeliculaId(Pelicula pelicula, Genero genero) {
		this.pelicula = pelicula;
		this.genero = genero;
	}

	/**
	 * Obtiene la entidad Pelicula asociada a la clave primaria.
	 * 
	 * @return La entidad Pelicula asociada.
	 */
	public Pelicula getPelicula() {
		return pelicula;
	}

	/**
	 * Establece la entidad Pelicula asociada a la clave primaria.
	 * 
	 * @param pelicula La entidad Pelicula a establecer.
	 */
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	/**
	 * Obtiene la entidad Genero asociada a la clave primaria.
	 * 
	 * @return La entidad Genero asociada.
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * Establece la entidad Genero asociada a la clave primaria.
	 * 
	 * @param genero La entidad Genero a establecer.
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

}
