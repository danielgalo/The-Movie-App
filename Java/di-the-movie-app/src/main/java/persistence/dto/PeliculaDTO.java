package persistence.dto;

/**
 * Clase pelicula. Objeto DTO usado para traer datos de películas desde la API y
 * para exportar peliculas a archivos
 */
public class PeliculaDTO {

	/** Titulo de la pelicula */
	private String titulo;

	/** Descripcion de la pelicula */
	private String overview;

	/** Fecha de lanzamiento de la pelicula */
	private String releaseDate;

	/** URL de la imagen de la pelicula */
	private String img;

	/** IDs de los géneros de la película */
	private int[] genreIds;

	/** ID de la pelicula */
	private Long id;

	/** Votacion promedia */
	private double voteAverage;

	private String comentariosUsuario;

	private String fechaVisualizacion;
	private double valoracionUsuario;

	/**
	 * Constructor específico para usar en exportación a ficheros
	 * 
	 * @param titulo
	 * @param overview
	 * @param releaseDate
	 * @param img
	 * @param voteAverage
	 * @param comentariosUsuario
	 * @param fechaVisualizacion
	 * @param valoracionUsuario
	 */
	public PeliculaDTO(String titulo, String overview, String releaseDate, String img, double voteAverage,
			String comentariosUsuario, String fechaVisualizacion, double valoracionUsuario) {
		this.titulo = titulo;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.img = img;
		this.voteAverage = voteAverage;
		this.comentariosUsuario = comentariosUsuario;
		this.fechaVisualizacion = fechaVisualizacion;
		this.valoracionUsuario = valoracionUsuario;
	}

	/**
	 * Constructor con generos e id de la pelicula
	 * 
	 * @param titulo
	 * @param overview
	 * @param releaseDate
	 * @param img
	 * @param genreIds
	 * @param id
	 * @param voteAverage
	 */
	public PeliculaDTO(String titulo, String overview, String releaseDate, String img, int[] genreIds, Long id,
			double voteAverage) {
		this.titulo = titulo;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.img = img;
		this.genreIds = genreIds;
		this.id = id;
		this.voteAverage = voteAverage;
	}

	/**
	 * @return the voteAverage
	 */
	public double getVoteAverage() {
		return voteAverage;
	}

	/**
	 * @param voteAverage the voteAverage to set
	 */
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
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
	 * @return the genreIds
	 */
	public int[] getGenreIds() {
		return genreIds;
	}

	/**
	 * @param genreIds the genreIds to set
	 */
	public void setGenreIds(int[] genreIds) {
		this.genreIds = genreIds;
	}

	/**
	 * Constructor sin generos
	 * 
	 * @param titulo
	 * @param overview
	 * @param releaseDate
	 * @param img
	 */
	public PeliculaDTO(String titulo, String overview, String releaseDate, String img) {
		this.titulo = titulo;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.img = img;

	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
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
	 * @return the fechaVisualizacion
	 */
	public String getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	/**
	 * @param fechaVisualizacion the fechaVisualizacion to set
	 */
	public void setFechaVisualizacion(String fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
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
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

}
