package persistence.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa un actor en la tabla actores de la base de datos
 */
@Entity
@Table(name = "actores")
public class Actor extends AbstractEntity implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/** Id del actor */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del actor */
	@Column(name = "nombre")
	private String nombre;

	/** Generos de la pelicula */
	@OneToMany(mappedBy = "id.actor", cascade = CascadeType.ALL)
	private List<ActoresPeliculas> actoresPeliculas;

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
	 * @return the actoresPeliculas
	 */
	public List<ActoresPeliculas> getActoresPeliculas() {
		return actoresPeliculas;
	}

	/**
	 * @param actoresPeliculas the actoresPeliculas to set
	 */
	public void setActoresPeliculas(List<ActoresPeliculas> actoresPeliculas) {
		this.actoresPeliculas = actoresPeliculas;
	}

}
