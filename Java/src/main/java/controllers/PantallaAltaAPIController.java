package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import persistence.dto.PeliculaDTO;
import service.AltaPeliculasService;
import utils.ControllerUtils;
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

	/** Botón de alta para la pelicula */
	@FXML
	private Button btnAlta;

	/** Botón de película anterior */
	@FXML
	private Button btnAnterior;

	/** Botón para buscar una película */
	@FXML
	private Button btnBuscar;

	/** Botón para navegar a la pantalla de alta manual */
	@FXML
	private Button btnConsultaManual;

	/** Botón de película siguiente */
	@FXML
	private Button btnSiguiente;

	/** Cabecera de la pantalla */
	@FXML
	private Pane cabecera;

	/** Imagen representativa de la película */
	@FXML
	private ImageView imgPelicula;

	/** Título de la cabecera */
	@FXML
	private Label lblCabecera;

	/** Label del título de la película encontrada */
	@FXML
	private Label lblResTitulo;

	/** Label de la descripción de la película */
	@FXML
	private Label lblDescripcion;

	/** Label titulo del panel de los resultados de la película */
	@FXML
	private Label lblDatosPelicula;

	/** Label que está al lado del text field para introducir un titulo */
	@FXML
	private Label lblTituloIntroducir;

	/** Text field para introducir titulo */
	@FXML
	private TextField txtTituloPelicula;

	/** Spinner de valoración de usuario (0-10 con incremento de 0,5) */
	@FXML
	private Spinner<Double> spinnerValoracionUsuario;

	/** Panel principal */
	@FXML
	private Pane panelPrincipal;

	/**
	 * Text area donde el usuario introduce los comentarios que tenga sobre la
	 * pelicula
	 */
	@FXML
	private TextArea txtAreaComentarios;

	/** Text area para la localización de la película */
	@FXML
	private TextField txtLocalizacion;

	/** Elemento para elegir fecha de visualizacion del usuario */
	@FXML
	private DatePicker dateFechaVisUsuario;

	/** Botón para volver atrás de ventana */
	@FXML
	private Button btnVolver;

	/** Label de mensajes de error */
	@FXML
	private Label lblError;

	/** Label de mensajes de información */
	@FXML
	private Label lblInfo;

	/** Posición de la película en la búsqueda */
	private int posicionPelicula;

	/** Imagen predeterminada cuando no hay imagen disponible */
	private static final Image EMPTY_IMAGE = new Image("/resources/found-icon-20.jpg");

	/**
	 * Inicializa el controlador después de que se haya cargado la raíz del archivo
	 * FXML. Configura la imagen predeterminada y realiza las asignaciones de los
	 * elementos de la interfaz.
	 */
	@FXML
	void initialize() {

		// Inicializar imagen por defecto
		imgPelicula.setImage(EMPTY_IMAGE);
		lblDescripcion.setWrapText(true);

		// Configurar el SpinnerValueFactory para permitir valores de 0 a 10 con
		// incrementos de 0.5
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 0.5);
		spinnerValoracionUsuario.setValueFactory(valueFactory);

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		ControllerUtils.setShadowLabels(shadow, lblCabecera, lblTituloIntroducir, lblDatosPelicula);
		ControllerUtils.setShadowTxtFields(shadow, txtTituloPelicula);
		ControllerUtils.setShadowButtons(shadow, btnAlta, btnAnterior, btnBuscar, btnSiguiente, btnConsultaManual);
		ControllerUtils.setShadowPanes(shadow, panelPrincipal);

		spinnerValoracionUsuario.getValueFactory().setValue(5.0);

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Alta". Obtiene la película
	 * mediante la API y la inserta en la base de datos.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAltaPressed(MouseEvent event) {

		// Vaciar labels de informacion
		if (!lblInfo.getText().isBlank()) {
			lblInfo.setText("");
		}

		if (!lblError.getText().isBlank()) {
			lblError.setText("");
		}

		// Si el valor de la fecha de visualización (campo obligatorio) no es nulo
		if (dateFechaVisUsuario.getValue() != null) {

			// Recoger datos
			String titulo = txtTituloPelicula.getText();
			LocalDate localDate = dateFechaVisUsuario.getValue();
			String comentariosUsuario = txtAreaComentarios.getText();
			double valoracionUsuario = spinnerValoracionUsuario.getValue();
			String localizacionPelicula = txtLocalizacion.getText();

			// Inserta la pelicula, recoge codigo de salida
			int exitCode = AltaPeliculasService.insertPelicula(titulo, posicionPelicula, localDate, comentariosUsuario,
					valoracionUsuario, localizacionPelicula);

			Alert alert = new Alert(AlertType.NONE, "Alta de película");
			alert.setTitle("Alta de película" + valoracionUsuario);
			alert.setResizable(false);
			alert.setHeaderText("");

			// Mostrar mensaje según el código de salida
			if (exitCode == 0) {
				alert.setAlertType(AlertType.CONFIRMATION);
				alert.setContentText("La película ha sido dada de alta correctamente.");
			} else {
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Ha ocurrido un error al dar de alta la película.");
			}

			alert.showAndWait();

		} else {
			showError("La fecha de visualización es obligatoria");
		}

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Volver". Vuelve al menú
	 * principal
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnVolverPressed(MouseEvent event) {
		NavegacionPantallas pantallaPrincipal = new NavegacionPantallas("Pantalla Principal",
				Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
		pantallaPrincipal.navegaAPantalla();

		NavegacionPantallas.cerrarVentanaActual(event);
	}

	@FXML
	void btnVolverEntered(MouseEvent event) {
		DropShadow shadow = new DropShadow();
		shadow.setColor(new Color(0.0, 0.95, 1.0, 1.0));
		shadow.setSpread(0.18);
		btnVolver.setEffect(shadow);
	}

	@FXML
	void btnVolverExited(MouseEvent event) {
		btnVolver.setEffect(null);

	}

	/**
	 * Habilita elementos de input para insertar películas, antes de buscarla
	 */
	private void habilitaElementosInput() {

		spinnerValoracionUsuario.setDisable(false);
		txtAreaComentarios.setDisable(false);
		txtLocalizacion.setDisable(false);
		dateFechaVisUsuario.setDisable(false);
		btnAlta.setDisable(false);
		btnSiguiente.setDisable(false);
		btnAnterior.setDisable(false);

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

			// Habilitar botón de alta si está deshabilitado, mostrar película
			if (btnAlta.isDisabled()) {
				btnAlta.setDisable(false);
			}

			posicionPelicula--;
			setPelicula();

		} else {

			// Informar si no hay película, deshabilitar botón de alta
			lblResTitulo.setText("No hay película anterior");
			imgPelicula.setImage(EMPTY_IMAGE);
			lblDescripcion.setText("");
			btnAlta.setDisable(true);

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

		// Hablilita elementos para insertar en base de datos
		habilitaElementosInput();

		// Mostrar película
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
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Siguiente". Muestra la película
	 * siguiente en la búsqueda si hay.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnSiguientePressed(MouseEvent event) {

		if (posicionPelicula >= TMDBApi.getResultsLength()) {

			// Informar si no hay película, deshabilitar botón de alta
			lblResTitulo.setText("No hay película siguiente.");
			lblDescripcion.setText("");
			imgPelicula.setImage(EMPTY_IMAGE);
			btnAlta.setDisable(true);

		} else {

			// Habilitar botón de alta si está deshabilitado, mostrar película
			if (btnAlta.isDisabled()) {
				btnAlta.setDisable(false);
			}

			posicionPelicula++;
			setPelicula();
		}
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
			// Si hay error, quitarlo
			if (!lblError.getText().isBlank()) {
				lblError.setText("");
			}
			lblResTitulo.setText(pelicula.getTitulo() + " (" + pelicula.getReleaseDate() + ")");
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
		} else {
			showError("No se pudo encontrar la película");
		}
	}

	/**
	 * Muestra un mensaje de error
	 * 
	 * @param message
	 */
	private void showError(String message) {
		lblError.setText(message);
	}

}
