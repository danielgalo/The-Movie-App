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
	
	@SuppressWarnings("deprecation")
	public CeldaPelicula(int posicionX, int posicionY, Pelicula pelicula) {
		this.pelicula = pelicula;
		
		poster = pelicula.getCartel();
		titulo = pelicula.getTitulo();
		for (GeneroPelicula generoId : pelicula.getGeneroPelicula()) {		
			if (!(generoId.getId().getGenero().getNombre().isBlank())) {
				genero = generoId.getId().getGenero().getNombre();
				break;				
			}
		}
		if (pelicula.getFechaVisualizacionUsuario() != null) {
			fechaLanzamiento = (pelicula.getReleaseDate().getDate()) + "/" + (pelicula.getReleaseDate().getMonth() + 1) + "/" + (pelicula.getReleaseDate().getYear() + 1900);
		} else {
			fechaLanzamiento = pelicula.getReleaseDate().getDate() + "/" + pelicula.getReleaseDate().getMonth() + "/" + pelicula.getReleaseDate().getYear();			
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
  	celdaPelicula.setLayoutX(posicionX);
  	celdaPelicula.setLayoutY(posicionY);
  	celdaPelicula.setPrefWidth(1370);
  	celdaPelicula.setPrefHeight(240);
  	celdaPelicula.setId("celdaPelicula");
  	celdaPelicula.setEffect(sombraCelda);
  	
  	/* ADICIÓN DE LOS ELEMENTOS A LA CELDA */
  	//Poster
  	celdaPelicula.getChildren().add(posterElemento);
  	//Título de la película
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
	
	public Pane getCeldaPelicula() {
		return celdaPelicula;
	}
	
	public Pelicula getPelicula() {
		return pelicula;
	}
	
}
