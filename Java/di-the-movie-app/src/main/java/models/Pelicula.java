package models;

/**
 * Clase pelicula
 */
public class Pelicula {

	private String titulo;
	private String overview;
	private String releaseDate;
	private String img;

	/**
	 * @param titulo
	 * @param overview
	 * @param releaseDate
	 * @param img
	 */
	public Pelicula(String titulo, String overview, String releaseDate, String img) {
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
