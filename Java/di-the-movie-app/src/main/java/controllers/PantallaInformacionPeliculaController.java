package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import persistence.entities.ActoresPeliculas;
import persistence.entities.DirectoresPeliculas;
import persistence.entities.GeneroPelicula;
import persistence.entities.Pelicula;
import utils.NavegacionPantallas;

public class PantallaInformacionPeliculaController {

    @FXML
    private Label lblFechaEstreno;

    @FXML
    private Label lblFechaVisualizacion;

    @FXML
    private Label lblGeneros;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblValoracion;

    @FXML
    private Label lblValoracionUsuario;

    @FXML
    private ListView<String> lstActores;

    @FXML
    private ListView<String> lstDirectores;

    @FXML
    private Rectangle poster;

    @FXML
    private TextArea txtComentarios;

    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private Label lblVolver;
    
    private Pelicula pelicula = PantallaListaPeliculasController.peliculaActual;
    
    @SuppressWarnings("deprecation")
		@FXML
    void initialize() {
    	poster.setArcWidth(30.0);
    	poster.setArcHeight(30.0);
    	ImagePattern imagen = new ImagePattern(new Image(pelicula.getCartel(), 165, 200, false, false));
    	poster.setFill(imagen);
    	
    	String generos = "";						
    	int generosImportados = 1;
    	for (GeneroPelicula generoPelicula : pelicula.getGeneroPelicula()) {		
  			if (!(generoPelicula.getId().getGenero().getNombre().isBlank())) {
  				String genero = generoPelicula.getId().getGenero().getNombre();
  				if (generosImportados == 1) {
  					generos += genero;
					} else {
						generos = genero + ", " + genero;					
					}
  				generosImportados++;
  			}
  		}
    	lblGeneros.setText(generos);
    	
    	if (pelicula.getIdApi() != null) {
  			lblFechaEstreno.setText((pelicula.getReleaseDate().getDate()) + "-" + (pelicula.getReleaseDate().getMonth() + 1) + "-" + (pelicula.getReleaseDate().getYear() + 1900));
  		} else {
  			lblFechaEstreno.setText(pelicula.getReleaseDate().getDate() + "-" + pelicula.getReleaseDate().getMonth() + "-" + pelicula.getReleaseDate().getYear());			
  		}
    	
    	lblFechaVisualizacion.setText(pelicula.getFechaVisualizacionUsuario().getDate() + "-" + pelicula.getFechaVisualizacionUsuario().getMonth() + "-" + pelicula.getFechaVisualizacionUsuario().getYear());
    	
    	txtDescripcion.setText(pelicula.getOverview());
    	
    	lblValoracion.setText(pelicula.getValoracion() + "/10");
    	
    	lblValoracionUsuario.setText(pelicula.getValoracionUsuario() + "/10");
    	
    	txtComentarios.setText(pelicula.getComentariosUsuario());
    	
    	lblTitulo.setText(pelicula.getTitulo());
    	
    	for (ActoresPeliculas actoresPeliculas : pelicula.getActoresPeliculas()) {
				String actor = actoresPeliculas.getId().getActor().getNombre();
				lstActores.getItems().add(actor);
			}
    	
    	for (DirectoresPeliculas directoresPeliculas : pelicula.getDirectoresPelicula()) {
				String director = directoresPeliculas.getId().getDirector().getNombre();
				lstDirectores.getItems().add(director);
			}
    	
    }
    
    @FXML
    void lblVolverEntered(MouseEvent event) {
    	lblVolver.setUnderline(true);
    }

    @FXML
    void lblVolverExited(MouseEvent event) {
    	lblVolver.setUnderline(false);
    }

    @FXML
    void lblVolverPressed(MouseEvent event) {
    	NavegacionPantallas.cerrarVentanaActual(event);
    }
    
}
