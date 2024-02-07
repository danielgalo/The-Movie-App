package persistence.entities;

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
 * Clase que representa un director en la tabla directores de la base de datos
 */
@Entity
@Table(name = "directores")
public class Director {

	/** Id del actor */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del actor */
	@Column(name = "nombre")
	private String nombre;

	/** Directores de la pelicula */
	@OneToMany(mappedBy = "id.director", cascade = CascadeType.ALL)
	private List<DirectoresPeliculas> directoresPelicula;

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
	 * @return the directoresPelicula
	 */
	public List<DirectoresPeliculas> getDirectoresPelicula() {
		return directoresPelicula;
	}

	/**
	 * @param directoresPelicula the directoresPelicula to set
	 */
	public void setDirectoresPelicula(List<DirectoresPeliculas> directoresPelicula) {
		this.directoresPelicula = directoresPelicula;
	}

}
