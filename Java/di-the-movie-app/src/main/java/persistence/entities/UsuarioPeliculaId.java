package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa la clave primaria compuesta para la entidad UsuarioPelicula. Esta
 * clave primaria compuesta está compuesta por las referencias a las entidades
 * Pelicula y Genero.
 * 
 * @see Pelicula
 * @see User
 * @see UserPelicula
 * 
 * @serial 1L
 */
@Embeddable
public class UsuarioPeliculaId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Pelicula */
	@ManyToOne
	@JoinColumn(name = "id_pelicula")
	private Pelicula pelicula;

	/** Usuario */
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User usuario;

	/**
	 * Constructor con todos los parametros
	 * 
	 * @param pelicula
	 * @param usuario
	 */
	public UsuarioPeliculaId(Pelicula pelicula, User usuario) {
		super();
		this.pelicula = pelicula;
		this.usuario = usuario;
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
	 * @return the usuario
	 */
	public User getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
