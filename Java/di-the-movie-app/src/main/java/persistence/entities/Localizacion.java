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
