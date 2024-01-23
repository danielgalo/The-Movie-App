package persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa una compañía en la tabla companies de la base de datos
 */
@Entity
@Table(name = "companies")
public class Company {

	/** Id de la pelicula */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** NIF de la compañía */
	@Column(name = "NIF")
	private String nif;

	/** Nombre de la compañía */
	@Column(name = "name")
	private String name;

	/** Peliculas de la compañia */
	@OneToMany(mappedBy = "company")
	private List<Pelicula> movies;

	/**
	 * @return the movies
	 */
	public List<Pelicula> getMovies() {
		return movies;
	}

	/**
	 * @param movies the movies to set
	 */
	public void setMovies(List<Pelicula> movies) {
		this.movies = movies;
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
