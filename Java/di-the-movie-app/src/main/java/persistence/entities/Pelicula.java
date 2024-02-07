package persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa una película en la tabla peliculas de la base de datos
 */
@Entity
@Table(name = "peliculas")
public class Pelicula implements Serializable {

	/** Id serial */
	private static final long serialVersionUID = 1L;

	/** Id de la pelicula */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Titulo de la pelicula */
	@Column(name = "titulo")
	private String titulo;

	/** Descripcion de la pelicula */
	@Column(name = "descripcion", length = 1000)
	private String overview;

	/** Fecha de salida */
	@Column(name = "release_date")
	private Date releaseDate;

	/** Año de la pelicula */
	@Column(name = "year")
	private int year;

	/** Compañias de la pelicula */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<CompanyPelicula> compPelicula;

	/** Imagen del cartel de la pelicula */
	@Column(name = "cartel")
	private String cartel;

	/** Generos de la pelicula */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<GeneroPelicula> generoPelicula;

	/** Usuario asociadad */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User usuario;

	/** Directores de la pelicula */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<DirectoresPeliculas> directoresPelicula;

	/** Generos de la pelicula */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<ActoresPeliculas> actoresPeliculas;

	/** Valoracion de la pelicula por el usuario */
	@Column(name = "valoracion_usuario")
	private double valoracionUsuario;

	/** Valoracion de la pelicula por el usuario */
	@Column(name = "valoracion")
	private double valoracion;

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
	 * @return the generoPelicula
	 */
	public List<GeneroPelicula> getGeneroPelicula() {
		return generoPelicula;
	}

	/**
	 * @return the valoracion
	 */
	public double getValoracion() {
		return valoracion;
	}

	/**
	 * @param valoracion the valoracion to set
	 */
	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}

	/**
	 * @param generoPelicula the generoPelicula to set
	 */
	public void setGeneroPelicula(List<GeneroPelicula> generoPelicula) {
		this.generoPelicula = generoPelicula;
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
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
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

	/**
	 * @return the valoracionUsuario
	 */
	public double getValoracionUsuario() {
		return valoracionUsuario;
	}

	/**
	 * @param valoracionUsuario the valoracionUsuario to set
	 */
	public void setValoracionUsuario(double valoracionUsuario) {
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

	/**
	 * @return the usuario
	 */
	public User getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}
