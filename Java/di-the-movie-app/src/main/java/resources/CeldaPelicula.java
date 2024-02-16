package resources;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import persistence.entities.GeneroPelicula;
import persistence.entities.Pelicula;

public class CeldaPelicula {
	
	private Pane celdaPelicula;
	private String titulo;
	private String poster;
	private String genero;
	private String fechaLanzamiento;
	private String descripcion;
	private Pelicula pelicula;
	private Label tituloPulsable;
	
	@SuppressWarnings("deprecation")
	/**
	 * Un panel que actua a modo de "celda", conteniendo datos básicos de una película para su rápido reconocimiento
	 * @param posicionY. La posición vertical de la celda en el panel padre donde será contenida
	 * @param pelicula. La película cuyos datos se quieren contener en la celda
	 */
	public CeldaPelicula(int posicionY, Pelicula pelicula) {
		this.pelicula = pelicula;
		//Obten la URL del cartel de la película
		poster = pelicula.getCartel();
		//Obten el título de la película
		titulo = pelicula.getTitulo();
		/**
		 * Obten UN SOLO GENERO de la película
		 * 
		 * Nota: Un solo género es suficiente para ser básico para mostrar en la celda,
		 * si obtuviese más no cabrían en la celda
		 */
		for (GeneroPelicula generoPelicula : pelicula.getGeneroPelicula()) {		
			if (!(generoPelicula.getId().getGenero().getNombre().isBlank())) {
				genero = generoPelicula.getId().getGenero().getNombre();
				break;				
			}
		}
		/**
		 * Obten la fecha de estreno de la película
		 * 
  	 * Nota: Las películas insertadas desde la API muestran las fechas de manera distinta,
  	 * sospecho que es por la clase Date más que por la API, pero en cualquier caso aquellas películas
  	 * que vengan de la API (que tengan un id que viene de la API). Aquellas que vienen de la inserción manual
  	 * no parecen tener este problema
  	 */
		if (pelicula.getIdApi() != null) {
			fechaLanzamiento = (pelicula.getReleaseDate().getDate()) + "-" + (pelicula.getReleaseDate().getMonth() + 1) + "-" + (pelicula.getReleaseDate().getYear() + 1900);
		} else {
			fechaLanzamiento = pelicula.getReleaseDate().getDate() + "-" + pelicula.getReleaseDate().getMonth() + "-" + pelicula.getReleaseDate().getYear();			
		}
		descripcion = pelicula.getOverview();
		
		/* SOMBRAS DE LOS ELEMENTOS DE LA CELDA */
		//Sombra de la celda
		DropShadow sombraCelda = new DropShadow();
		sombraCelda.setColor(new Color(0.4035, 0.4035, 0.4035, 1.0));
		sombraCelda.setOffsetY(4);
		//Sombra del resto de elementos 
		DropShadow sombraElementos = new DropShadow();
		sombraElementos.setColor(new Color(0.4, 0.4, 0.4, 0.4645));
		sombraElementos.setOffsetY(4);
		
		/* MODELOS DE ELEMENTOS DE LA CELDA (SIN DATOS ASIGNADOS) */
		//Poster
		Rectangle posterElemento = new Rectangle(30, 20, 165, 200);
  	posterElemento.setArcWidth(30.0);
  	posterElemento.setArcHeight(30.0);
  	posterElemento.setEffect(sombraElementos);
		//Título de la película
  	Label tituloPelicula = new Label();
  	tituloPelicula.setLayoutX(220);
  	tituloPelicula.setLayoutY(20);
  	tituloPelicula.setPrefWidth(718);
  	tituloPelicula.setPrefHeight(58);
  	tituloPelicula.setId("tituloPelicula");
  	tituloPelicula.setEffect(sombraElementos);
  	//Etiqueta "GENERO:"
  	Label lblGeneroPelicula = new Label();
  	lblGeneroPelicula.setLayoutX(955);
  	lblGeneroPelicula.setLayoutY(30);
  	lblGeneroPelicula.setPrefWidth(136);
  	lblGeneroPelicula.setPrefHeight(47);
  	lblGeneroPelicula.setId("lblGenero");
  	lblGeneroPelicula.setEffect(sombraElementos);
  	//Genero principal de la película
  	Label generoPelicula = new Label();
  	generoPelicula.setLayoutX(1103);
  	generoPelicula.setLayoutY(30);
  	generoPelicula.setPrefWidth(260);
  	generoPelicula.setPrefHeight(47);
  	generoPelicula.setId("genero");
  	generoPelicula.setEffect(sombraElementos);
  	//Etiqueta "FECHA DE LANZAMIENTO:"
  	Label lblFechaLanzamiento = new Label();
  	lblFechaLanzamiento.setLayoutX(691);
  	lblFechaLanzamiento.setLayoutY(70);
  	lblFechaLanzamiento.setPrefWidth(404);
  	lblFechaLanzamiento.setPrefHeight(47);
  	lblFechaLanzamiento.setId("lblFechaLanzamiento");
  	lblFechaLanzamiento.setEffect(sombraElementos);
  	//Fecha de lanzamiento de la película
  	Label fechaLanzamientoPelicula = new Label();
  	fechaLanzamientoPelicula.setLayoutX(1103);
  	fechaLanzamientoPelicula.setLayoutY(70);
  	fechaLanzamientoPelicula.setPrefWidth(260);
  	fechaLanzamientoPelicula.setPrefHeight(47);
  	fechaLanzamientoPelicula.setId("fechaLanzamiento");
  	fechaLanzamientoPelicula.setEffect(sombraElementos);
  	//Etiqueta "SINOPSIS"
  	Label lblSinopsis = new Label();
  	lblSinopsis.setLayoutX(220);
  	lblSinopsis.setLayoutY(80);
  	lblSinopsis.setPrefWidth(114);
  	lblSinopsis.setPrefHeight(30);
  	lblSinopsis.setId("sinopsis");
  	lblSinopsis.setEffect(sombraElementos);
  	//Descripción de la película (sinopsis)
  	TextArea txtDescripcion = new TextArea();
  	txtDescripcion.setLayoutX(220);
  	txtDescripcion.setLayoutY(120);
  	txtDescripcion.setPrefWidth(1120);
  	txtDescripcion.setPrefHeight(95);
  	txtDescripcion.setId("descripcion");
  	txtDescripcion.setEffect(sombraElementos);
  	txtDescripcion.setWrapText(true);
  	txtDescripcion.setEditable(false);
		
  	/* ASIGNACIÓN DE LOS DATOS A LOS ELEMENTOS */
  	//Poster
  	ImagePattern imagen = new ImagePattern(new Image(poster, 165, 200, false, false));
  	posterElemento.setFill(imagen);
  	//Título de la película
  	tituloPelicula.setText(titulo);
  	//Etiqueta "GENERO:"
  	lblGeneroPelicula.setText("GENERO:");
  	//Genero principal de la película
  	generoPelicula.setText(genero);
  	//Etiqueta "FECHA DE LANZAMIENTO:"
  	lblFechaLanzamiento.setText("FECHA DE LANZAMIENTO:");
  	//Fecha de lanzamiento de la película
  	fechaLanzamientoPelicula.setText(fechaLanzamiento);
  	//Etiqueta "SINOPSIS"
  	lblSinopsis.setText("SINOPSIS");
  	//Descripción de la película (sinopsis)
  	txtDescripcion.setText(descripcion);
  	
  	/* CREACIÓN DE LA CELDA */
		celdaPelicula = new Pane();
  	celdaPelicula.setLayoutX(25);
  	celdaPelicula.setLayoutY(posicionY);
  	celdaPelicula.setPrefWidth(1370);
  	celdaPelicula.setPrefHeight(240);
  	celdaPelicula.setId("celdaPelicula");
  	celdaPelicula.setEffect(sombraCelda);
  	
  	/* ADICIÓN DE LOS ELEMENTOS A LA CELDA */
  	//Poster
  	celdaPelicula.getChildren().add(posterElemento);
  	//Título de la película
  	/**
  	 * Nota: De paso tambien pasa el label a una variable para ser usado
  	 * como botón para abrir más información de la película.
  	 */
  	tituloPulsable = tituloPelicula;
  	celdaPelicula.getChildren().add(tituloPelicula);
  	//Etiqueta "GENERO:"
  	celdaPelicula.getChildren().add(lblGeneroPelicula);
  	//Genero principal de la película
  	celdaPelicula.getChildren().add(generoPelicula);
  	//Etiqueta "FECHA DE LANZAMIENTO:"
  	celdaPelicula.getChildren().add(lblFechaLanzamiento);
  	//Fecha de lanzamiento de la película
  	celdaPelicula.getChildren().add(fechaLanzamientoPelicula);
  	//Etiqueta "SINOPSIS"
  	celdaPelicula.getChildren().add(lblSinopsis);
  	//Descripción de la película (sinopsis)
  	celdaPelicula.getChildren().add(txtDescripcion);
	}
	
	/**
	 * Devuelve el panel (o celda) con los datos de la película
	 * @return
	 */
	public Pane getCeldaPelicula() {
		return celdaPelicula;
	}
	
	/**
	 * Devuelve la película contenida en la celda
	 * @return
	 */
	public Pelicula getPelicula() {
		return pelicula;
	}
	
	/**
	 * Devuelve la etiqueta que contiene el título de la película
	 * @return
	 */
	public Label getLabelTituloPulsable(){
		return tituloPulsable;
	}
	
}
