package dto;

/**
 * Clase pelicula. Objeto DTO usado para traer datos de películas desde la API
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

	/**
	 * Constructor con todos los argumentos
	 * 
	 * @param titulo
	 * @param overview
	 * @param releaseDate
	 * @param img
	 * @param genreIds
	 */
	public PeliculaDTO(String titulo, String overview, String releaseDate, String img, int[] genreIds) {
		this.titulo = titulo;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.img = img;
		this.genreIds = genreIds;
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
	 * @param genres
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
