/**
 * Sample Skeleton for 'PantallaPrincipal.fxml' Controller Class
 */

package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaPrincipalController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="lblTitulo"
	private Label lblTitulo; // Value injected by FXMLLoader

	@FXML // fx:id="panelAltaPeliculas"
	private Pane panelAltaPeliculas; // Value injected by FXMLLoader

	@FXML // fx:id="panelConsultaPeliculas"
	private Pane panelConsultaPeliculas; // Value injected by FXMLLoader

	@FXML // fx:id="panelExportarPeliculas"
	private Pane panelExportarPeliculas; // Value injected by FXMLLoader

	@FXML // fx:id="panelSalir"
	private Pane panelSalir; // Value injected by FXMLLoader

	@FXML // fx:id="addBtn"
	private ImageView addBtn; // Value injected by FXMLLoader

	@FXML // fx:id="exitBtn"
	private ImageView exitBtn; // Value injected by FXMLLoader

	@FXML // fx:id="exportBtn"
	private ImageView exportBtn; // Value injected by FXMLLoader

	@FXML // fx:id="searchBtn"
	private ImageView searchBtn; // Value injected by FXMLLoader

	@FXML
	void altaPeliculasPressed(MouseEvent event) {

		NavegacionPantallas navegacion = new NavegacionPantallas("Alta de películas por API",
				Constantes.PANTALLA_ALTA_API, Constantes.CSS_ALTA_API);

		navegacion.navegaAPantalla();
	}

	@FXML
	void consultaPeliculasPressed(MouseEvent event) {

	}

	@FXML
	void exportarPeliculasPressed(MouseEvent event) {

	}

	@FXML
	void panelSalirPressed(MouseEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

		addBtn.setImage(new Image("/resources/btn-add.png"));
		searchBtn.setImage(new Image("/resources/btn-search.png"));
		exportBtn.setImage(new Image("/resources/btn-export.png"));
		exitBtn.setImage(new Image("/resources/btn-exit.png"));

		assert lblTitulo != null
				: "fx:id=\"lblTitulo\" was not injected: check your FXML file 'PantallaPrincipal.fxml'.";
		assert panelAltaPeliculas != null
				: "fx:id=\"panelAltaPeliculas\" was not injected: check your FXML file 'PantallaPrincipal.fxml'.";
		assert panelConsultaPeliculas != null
				: "fx:id=\"panelConsultaPeliculas\" was not injected: check your FXML file 'PantallaPrincipal.fxml'.";
		assert panelExportarPeliculas != null
				: "fx:id=\"panelExportarPeliculas\" was not injected: check your FXML file 'PantallaPrincipal.fxml'.";
		assert panelSalir != null
				: "fx:id=\"panelSalir\" was not injected: check your FXML file 'PantallaPrincipal.fxml'.";

	}

}
