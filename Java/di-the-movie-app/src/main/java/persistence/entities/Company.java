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
 * Clase que representa una compañía en la tabla companies de la base de datos
 */
@Entity
@Table(name = "companies")
public class Company extends AbstractEntity implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/** Id de la pelicula */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre de la compañía */
	@Column(name = "name")
	private String name;

	/** Generos de la pelicula */
	@OneToMany(mappedBy = "id.company", cascade = CascadeType.ALL)
	private List<CompanyPelicula> compPelicula;

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
	 * @return the compPelicula
	 */
	public List<CompanyPelicula> getCompPelicula() {
		return compPelicula;
	}

	/**
	 * @param compPelicula the compPelicula to set
	 */
	public void setCompPelicula(List<CompanyPelicula> compPelicula) {
		this.compPelicula = compPelicula;
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
