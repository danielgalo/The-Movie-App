package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import models.Pelicula;
import utils.TMDBApi;

public class PantallaAltaAPIController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnAlta;

	@FXML
	private Button btnAnterior;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnConsultaManual;

	@FXML
	private Button btnSiguiente;

	@FXML
	private Pane cabecera;

	@FXML
	private ImageView imgPelicula;

	@FXML
	private Label lblCabecera;

	@FXML
	private Label lblResTitulo;

	@FXML
	private Label lblResYear;

	@FXML
	private TextField txtTituloPelicula;

	@FXML
	private Pane panelPrincipal;

	/** Posición de la película en la búsqueda */
	private int posicionPelicula;

	@FXML
	void btnAltaPressed(MouseEvent event) {
		// Obtener pelicula
		String titulo = txtTituloPelicula.getText();
		Pelicula peli = TMDBApi.getPelicula(titulo, posicionPelicula);

		// TODO Insertarla en la base de datos

	}

	@FXML
	void btnAnteriorPressed(MouseEvent event) {
		if (posicionPelicula > 0) {
			posicionPelicula--;
			setPelicula();
		} else {
			lblResTitulo.setText("No hay pelicula anterior");
		}
	}

	@FXML
	void btnBuscarPressed(MouseEvent event) {

		posicionPelicula = 0;

		setPelicula();

	}

	@FXML
	void btnConsultaManualPressed(MouseEvent event) {

	}

	@FXML
	void btnSiguientePressed(MouseEvent event) {
		if (posicionPelicula >= TMDBApi.getResultsLength()) {
			lblResTitulo.setText("No hay pelicula siguiente.");
		} else {
			posicionPelicula++;
			setPelicula();
		}
	}

	@FXML
	void initialize() {
		assert btnAlta != null : "fx:id=\"btnAlta\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert btnAnterior != null
				: "fx:id=\"btnAnterior\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert btnBuscar != null : "fx:id=\"btnBuscar\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert btnConsultaManual != null
				: "fx:id=\"btnConsultaManual\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert btnSiguiente != null
				: "fx:id=\"btnSiguiente\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert cabecera != null : "fx:id=\"cabecera\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert lblResTitulo != null
				: "fx:id=\"lblResTitulo\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert lblResYear != null
				: "fx:id=\"lblResYear\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";
		assert txtTituloPelicula != null
				: "fx:id=\"txtTituloPelicula\" was not injected: check your FXML file 'PantallaAltaAPI.fxml'.";

	}

	/**
	 * Obtiene una pelicula de la API con el titulo introducido
	 */
	private void setPelicula() {
		// Consigo el titulo introducido
		String tituloPelicula = txtTituloPelicula.getText();
		// Obtengo pelicula
		Pelicula pelicula = TMDBApi.getPelicula(tituloPelicula, posicionPelicula);

		if (pelicula != null) {

			lblResYear.setText(pelicula.getReleaseDate());
			lblResTitulo.setText(pelicula.getTitulo());
			InputStream stream = null;
			try {
				stream = new URL(pelicula.getImg()).openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image image = new Image(stream);

			// Mostrar la imagen en un ImageView
			imgPelicula.setImage(image);
		}

	}

}
