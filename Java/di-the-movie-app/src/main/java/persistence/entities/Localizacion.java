package persistence.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa una película en la tabla peliculas de la base de datos
 */
@Entity
@Table(name = "localizaciones")
public class Localizacion extends AbstractEntity implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/** Id de la localización */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre de la localizacion */
	@Column(name = "localizacion")
	private String nombre;

	/** Películas en la localización */
	@OneToMany(mappedBy = "localizacion")
	private List<Pelicula> peliculas;

	/**
	 * @return the nombreLocalizacion
	 */
	public String getNombreLocalizacion() {
		return nombre;
	}

	/**
	 * @param nombreLocalizacion the nombreLocalizacion to set
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
	 * @return the peliculas
	 */
	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	/**
	 * @param peliculas the peliculas to set
	 */
	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

}
