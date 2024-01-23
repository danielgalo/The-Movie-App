package persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa un genero en la tabla generos de la base de datos
 */
@Entity
@Table(name = "generos")
public class Genero {

	/** Id deld genero */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre de la pelicula */
	@Column(name = "nombre")
	private String nombre;

	/** Peliculas del genero */
	@OneToMany(mappedBy = "id.genero", cascade = CascadeType.ALL)
	private List<GeneroPelicula> generoPelicula;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the generoPelicula
	 */
	public List<GeneroPelicula> getGeneroPelicula() {
		return generoPelicula;
	}

	/**
	 * @param generoPelicula the generoPelicula to set
	 */
	public void setGeneroPelicula(List<GeneroPelicula> generoPelicula) {
		this.generoPelicula = generoPelicula;
	}

}
