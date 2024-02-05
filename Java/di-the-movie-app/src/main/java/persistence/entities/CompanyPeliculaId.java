package persistence.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa la clave primaria compuesta para la entidad CompanyPelicula. Esta
 * clave primaria compuesta está compuesta por las referencias a las entidades
 * Pelicula y Company.
 * 
 * @see Pelicula
 * @see Company
 * @see CompanyPelicula
 * 
 * @serial 1L
 */
@Embeddable
public class CompanyPeliculaId implements Serializable {

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
	 * Referencia a la entidad Company asociada a esta clave primaria compuesta.
	 */
	@ManyToOne
	@JoinColumn(name = "id_company")
	private Company company;

	/**
	 * Constructor
	 * 
	 * @param pelicula
	 * @param company
	 */
	public CompanyPeliculaId(Pelicula pelicula, Company company) {
		this.pelicula = pelicula;
		this.company = company;
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
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

}
