package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import persistence.entities.ActoresPeliculas;
import persistence.entities.DirectoresPeliculas;
import persistence.entities.GeneroPelicula;
import persistence.entities.Pelicula;
import utils.NavegacionPantallas;

/**
 * Ventana que muestra datos más en detalle de la película escogida comparada con la celda de la película
 */
public class PantallaInformacionPeliculaController {

    @FXML
    private Label lblFechaEstreno;

    @FXML
    private Label lblFechaVisualizacion;

    @FXML
    private TextField txtGeneros;

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
    	//Le añade redondez al rectangulo que contiene el poster
    	poster.setArcWidth(30.0);
    	poster.setArcHeight(30.0);
    	//Añade la imagen a la que lleva el URL del cartel la película
    	ImagePattern imagen = new ImagePattern(new Image(pelicula.getCartel(), 327, 478, false, false));
    	poster.setFill(imagen);
    	
    	
    	String generos = "";						
    	int generosImportados = 1;
    	//Por cada genero que tenga la película
    	for (GeneroPelicula generoPelicula : pelicula.getGeneroPelicula()) {
    		//Si el nombre genero no está vacío
  			if (!(generoPelicula.getId().getGenero().getNombre().isBlank())) {
  				String genero = generoPelicula.getId().getGenero().getNombre();
  				/**
  				 * Si es el nombre del genero es el primero a ser mostrado añadelo sin más,
  				 * el resto ponlos tras una coma y un espacio por motivos de separación
  				 */
  				if (generosImportados == 1) {
  					if (!generos.contains(genero)) {
  						generos += genero;							
  						generosImportados++;
						}
					} else {
						if (!generos.contains(genero)) {
							generos = generos + ", " + genero;
						}
					}
  			}
  		}
    	//Muestra los generos de la película
    	txtGeneros.setText(generos);
    	
    	/**
    	 * Nota: Las películas insertadas desde la API muestran las fechas de manera distinta,
    	 * sospecho que es por la clase Date más que por la API, pero en cualquier caso aquellas películas
    	 * que vengan de la API (que tengan un id que viene de la API). Aquellas que vienen de la inserción manual
    	 * no parecen tener este problema
    	 */
    	if (pelicula.getIdApi() != null) {
  			lblFechaEstreno.setText((pelicula.getReleaseDate().getDate()) + "-" + (pelicula.getReleaseDate().getMonth() + 1) + "-" + (pelicula.getReleaseDate().getYear() + 1900));
  			lblFechaVisualizacion.setText(pelicula.getFechaVisualizacionUsuario().getDate() + "-" + (pelicula.getFechaVisualizacionUsuario().getMonth() + 1) + "-" + (pelicula.getFechaVisualizacionUsuario().getYear() + 1900));
  		} else {
  			lblFechaEstreno.setText(pelicula.getReleaseDate().getDate() + "-" + pelicula.getReleaseDate().getMonth() + "-" + pelicula.getReleaseDate().getYear());			
  			lblFechaVisualizacion.setText(pelicula.getFechaVisualizacionUsuario().getDate() + "-" + pelicula.getFechaVisualizacionUsuario().getMonth() + "-" + pelicula.getFechaVisualizacionUsuario().getYear());
  		}
    	
    	//Muestra la descripción de la película
    	txtDescripcion.setText(pelicula.getOverview());
    	//Muestra la valoración media de la película
    	lblValoracion.setText(pelicula.getValoracion() + "/10");
    	//Muestra la valoración del usuario de la película
    	lblValoracionUsuario.setText(pelicula.getValoracionUsuario() + "/10");
    	//Muestra los comentarios del usuario de la película
    	txtComentarios.setText(pelicula.getComentariosUsuario());
    	//Muestra el título de la película
    	lblTitulo.setText(pelicula.getTitulo());
    	
    	//Muestra en una lista los actores de la película
    	for (ActoresPeliculas actoresPeliculas : pelicula.getActoresPeliculas()) {
				String actor = actoresPeliculas.getId().getActor().getNombre();
				lstActores.getItems().add(actor);
			}

    	//Muestra en una lista los directores de la película
    	for (DirectoresPeliculas directoresPeliculas : pelicula.getDirectoresPelicula()) {
				String director = directoresPeliculas.getId().getDirector().getNombre();
				lstDirectores.getItems().add(director);
			}
    	
    }
    
    @FXML
    /**
     * Subraya la etiqueta (para hacer ver que es interactuable)
     * @param event
     */
    void lblVolverEntered(MouseEvent event) {
    	lblVolver.setUnderline(true);
    }

    @FXML
    /**
     * Desubraya la etiqueta 
     * @param event
     */
    void lblVolverExited(MouseEvent event) {
    	lblVolver.setUnderline(false);
    }

    @FXML
    /**
     * Cierra la ventana de información de la película
     * @param event
     */
    void lblVolverPressed(MouseEvent event) {
    	NavegacionPantallas.cerrarVentanaActual(event);
    }
    
}
