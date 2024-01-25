package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.PeliculaDTO;
import utils.NavegacionPantallas;
import utils.TMDBApi;
import utils.constants.Constantes;

/**
 * Controlador para la pantalla de alta de películas mediante la API.
 */
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
	private Label lblDescripcion;

	@FXML
	private TextField txtTituloPelicula;

	@FXML
	private Pane panelPrincipal;

	/** Posición de la película en la búsqueda */
	private int posicionPelicula;

	// Imagen predeterminada cuando no hay imagen disponible
	private static final Image EMPTY_IMAGE = new Image("/resources/found-icon-20.jpg");

	/**
	 * Maneja el evento cuando se presiona el botón "Alta". Obtiene la película
	 * mediante la API y la inserta en la base de datos (TODO).
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAltaPressed(MouseEvent event) {
		// Obtener película
		String titulo = txtTituloPelicula.getText();
		PeliculaDTO peli = TMDBApi.getPeliculaByTitulo(titulo, posicionPelicula);

		// TODO Insertarla en la base de datos
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Anterior". Muestra la película
	 * anterior en la búsqueda.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAnteriorPressed(MouseEvent event) {
		if (posicionPelicula > 0) {
			posicionPelicula--;
			setPelicula();
		} else {
			lblResTitulo.setText("No hay película anterior");
			imgPelicula.setImage(EMPTY_IMAGE);
			lblDescripcion.setText("");
		}
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Buscar". Inicia una nueva
	 * búsqueda de películas.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnBuscarPressed(MouseEvent event) {
		posicionPelicula = 0;
		setPelicula();
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Consulta Manual". Navega a la
	 * pantalla de consulta manual.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnConsultaManualPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Consulta Manual", Constantes.PANTALLA_ALTA_MANUAL,
				Constantes.CSS_ALTA_MANUAL);
		navegacion.navegaAPantalla();
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Siguiente". Muestra la película
	 * siguiente en la búsqueda.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnSiguientePressed(MouseEvent event) {
		if (posicionPelicula >= TMDBApi.getResultsLength()) {
			lblResTitulo.setText("No hay película siguiente.");
			lblDescripcion.setText("");
			imgPelicula.setImage(EMPTY_IMAGE);
		} else {
			posicionPelicula++;
			setPelicula();
		}
	}

	/**
	 * Inicializa el controlador después de que se haya cargado la raíz del archivo
	 * FXML. Configura la imagen predeterminada y realiza las asignaciones de los
	 * elementos de la interfaz.
	 */
	@FXML
	void initialize() {
		imgPelicula.setImage(EMPTY_IMAGE);
		lblDescripcion.setWrapText(true);

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		panelPrincipal.setEffect(shadow);
		btnAlta.setEffect(shadow);
		btnAnterior.setEffect(shadow);
		btnBuscar.setEffect(shadow);
		btnSiguiente.setEffect(shadow);
		btnConsultaManual.setEffect(shadow);

	}

	/**
	 * Obtiene una película de la API con el título introducido y la muestra en la
	 * interfaz.
	 */
	private void setPelicula() {
		// Consigo el título introducido
		String tituloPelicula = txtTituloPelicula.getText();
		// Obtengo película
		PeliculaDTO pelicula = TMDBApi.getPeliculaByTitulo(tituloPelicula, posicionPelicula);

		if (pelicula != null) {
			lblResTitulo.setText(pelicula.getTitulo() + "(" + pelicula.getReleaseDate() + ")");
			lblDescripcion.setText(pelicula.getOverview());
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
