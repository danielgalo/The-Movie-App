package persistence.entities;

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
public class Localizacion {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre de la localizacion */
	@Column(name = "localizacion")
	private String nombreLocalizacion;

	@OneToMany(mappedBy = "localizacion")
	private List<Pelicula> peliculas;

	/**
	 * @return the nombreLocalizacion
	 */
	public String getNombreLocalizacion() {
		return nombreLocalizacion;
	}

	/**
	 * @param nombreLocalizacion the nombreLocalizacion to set
	 */
	public void setNombreLocalizacion(String nombreLocalizacion) {
		this.nombreLocalizacion = nombreLocalizacion;
	}

}
