package resources;

import java.util.Date;

import controllers.PantallaListaPeliculasController;
import javafx.scene.layout.Pane;
import persistence.entities.Genero;
import persistence.entities.Pelicula;

public class CeldaPelicula {
	
	private int posicionX;
	private int posicionY;
	private String tituloPelicula;
	private String descripcionPelicula;
	private Date fechaEstrenoPelicula;
	private String posterPelicula;
	private Genero generoPelicula;
	
	public CeldaPelicula(int posicionX, int posicionY) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
//		tituloPelicula = pelicula.getTitulo();
//		descripcionPelicula = pelicula.getOverview();
//		fechaEstrenoPelicula = pelicula.getReleaseDate();
//		posterPelicula = pelicula.getCartel();
	}
	
	public Pane getPane() {
		Pane pane = new Pane();
  	pane.setLayoutX(posicionX);
  	pane.setLayoutY(posicionY);
  	pane.setId("celdaPelicula");
  	return pane;
	}
	
}
