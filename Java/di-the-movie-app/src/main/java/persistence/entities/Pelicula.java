package persistence.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa una película en la tabla peliculas de la base de datos
 */
@Entity
@Table(name = "peliculas")
public class Pelicula {

	/** Id de la pelicula */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Titulo de la pelicula */
	@Column(name = "titulo")
	private String titulo;

	/** Descripcion de la pelicula */
	@Column(name = "descripcion")
	private String overview;

	/** Fecha de salida */
	@Column(name = "release_date")
	private String releaseDate;

	/** Año de la pelicula */
	@Column(name = "year")
	private int year;

	/** Compañia asociadad */
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	/** Imagen del cartel de la pelicula */
	@Column(name = "cartel")
	private String cartel;

	/** Generos de la pelicula */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<GeneroPelicula> generoPelicula;

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

	/** Generos de la pelicula */
	@ManyToMany(mappedBy = "peliculas")
	private List<Director> directores;

	/** Generos de la pelicula */
	@ManyToMany(mappedBy = "peliculas")
	private List<Actor> actores;

	/** Valoracion de la pelicula */
	@Column(name = "valoracion_usuario")
	private String valoracionUsuario;

	/** Fecha de visualizacion del usuario */
	@Column(name = "fecha_visualizacion_usuario")
	private Date fechaVisualizacionUsuario;

	/** Comentarios del usuario */
	@Column(name = "comentarios_usuario")
	private String comentariosUsuario;

	/** Localización de la película */
	@ManyToOne
	@JoinColumn(name = "localizacion_id")
	private Localizacion localizacion;

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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the overview
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * @param overview the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
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

	/**
	 * @return the cartel
	 */
	public String getCartel() {
		return cartel;
	}

	/**
	 * @param cartel the cartel to set
	 */
	public void setCartel(String cartel) {
		this.cartel = cartel;
	}

	/**
	 * @return the directores
	 */
	public List<Director> getDirectores() {
		return directores;
	}

	/**
	 * @param directores the directores to set
	 */
	public void setDirectores(List<Director> directores) {
		this.directores = directores;
	}

	/**
	 * @return the actores
	 */
	public List<Actor> getActores() {
		return actores;
	}

	/**
	 * @param actores the actores to set
	 */
	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	/**
	 * @return the valoracionUsuario
	 */
	public String getValoracionUsuario() {
		return valoracionUsuario;
	}

	/**
	 * @param valoracionUsuario the valoracionUsuario to set
	 */
	public void setValoracionUsuario(String valoracionUsuario) {
		this.valoracionUsuario = valoracionUsuario;
	}

	/**
	 * @return the fechaVisualizacionUsuario
	 */
	public Date getFechaVisualizacionUsuario() {
		return fechaVisualizacionUsuario;
	}

	/**
	 * @param fechaVisualizacionUsuario the fechaVisualizacionUsuario to set
	 */
	public void setFechaVisualizacionUsuario(Date fechaVisualizacionUsuario) {
		this.fechaVisualizacionUsuario = fechaVisualizacionUsuario;
	}

	/**
	 * @return the comentariosUsuario
	 */
	public String getComentariosUsuario() {
		return comentariosUsuario;
	}

	/**
	 * @param comentariosUsuario the comentariosUsuario to set
	 */
	public void setComentariosUsuario(String comentariosUsuario) {
		this.comentariosUsuario = comentariosUsuario;
	}

	/**
	 * @return the localizacion
	 */
	public Localizacion getLocalizacion() {
		return localizacion;
	}

	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

}
