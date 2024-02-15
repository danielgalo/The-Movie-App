package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa la clave primaria compuesta para la entidad ActoresPeliculas. Esta
 * clave primaria compuesta está compuesta por las referencias a las entidades
 * Pelicula y Actor.
 * 
 * @see Pelicula
 * @see ActoresPeliculas
 * @see Actor
 * 
 * @serial 1L
 */
@Embeddable
public class ActoresPeliculasId implements Serializable {

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
	 * Referencia a la entidad Actor asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_actor")
	private Actor actor;

	/**
	 * Constructor vacío
	 */
	public ActoresPeliculasId() {
		// La librería Jackson necesita constructor vacío
	}

	public ActoresPeliculasId(Pelicula pelicula, Actor actor) {
		this.pelicula = pelicula;
		this.actor = actor;
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
	 * @return the actor
	 */
	public Actor getActor() {
		return actor;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
