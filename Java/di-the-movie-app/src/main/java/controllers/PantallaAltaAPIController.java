package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import javafx.fxml.FXML;
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
import models.PeliculaDTO;
import persistence.HibernateUtil;
import persistence.dao.GeneroDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.GeneroPelicula;
import persistence.entities.GeneroPeliculaId;
import persistence.entities.Pelicula;
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
	private Spinner<Double> spinnerValoracionUsuario;

	@FXML
	private Pane panelPrincipal;

	@FXML
	private TextArea txtAreaComentarios;

	@FXML
	private TextField txtLocalizacion;

	@FXML
	private DatePicker dateFechaVisUsuario;

	/** Posición de la película en la búsqueda */
	private int posicionPelicula;

	// Imagen predeterminada cuando no hay imagen disponible
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

		// Aplicar efectos de sombra a componentes
		aplicaEfectos();

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Alta". Obtiene la película
	 * mediante la API y la inserta en la base de datos.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAltaPressed(MouseEvent event) {

		// Inserta la pelicula
		insertPelicula();
	}

	/**
	 * Inserta la película que aparece en la pantalla en la base de datos
	 */
	private void insertPelicula() {
		Session session = null;

		try {
			// Obtener la sesión de HibernateUtil
			session = HibernateUtil.getSession();

			// Obtener película
			String titulo = txtTituloPelicula.getText();
			PeliculaDTO peliDto = TMDBApi.getPeliculaByTitulo(titulo, posicionPelicula);

			// Sesion hibernate
			GeneroDaoImpl generoDao = new GeneroDaoImpl(session);
			PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);

			Pelicula pelicula = new Pelicula();

			// Datos de la entidad pelicula a insertar
			pelicula.setTitulo(peliDto.getTitulo());
			pelicula.setOverview(peliDto.getOverview());
			pelicula.setReleaseDate(peliDto.getReleaseDate());
			pelicula.setCartel(peliDto.getImg());
			pelicula.setComentariosUsuario(txtAreaComentarios.getText());

			System.out.println("LONGITUD DESCRIPCION: " + peliDto.getOverview().length());

			// Conseguir géneros
			List<GeneroPelicula> generosPelicula = new ArrayList<>();

			// Obtener todos los géneros de la película
			for (int generoId : peliDto.getGenreIds()) {

				GeneroPelicula gp = new GeneroPelicula(
						new GeneroPeliculaId(pelicula, generoDao.getGeneroById(Long.valueOf(generoId))));
				generosPelicula.add(gp);
			}

			pelicula.setGeneroPelicula(generosPelicula);

			// Insertar película
			peliDao.insert(pelicula);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cerrar la sesión al finalizar
			if (session != null) {
				HibernateUtil.closeSession();
			}
		}
	}

	/**
	 * Habilita elementos de input para insertar películas, antes de buscarla
	 * 
	 * 
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

			btnAlta.setDisable(false);

			posicionPelicula--;
			setPelicula();

		} else {
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

			lblResTitulo.setText("No hay película siguiente.");
			lblDescripcion.setText("");
			imgPelicula.setImage(EMPTY_IMAGE);

			btnAlta.setDisable(true);

		} else {

			btnAlta.setDisable(false);
			posicionPelicula++;
			setPelicula();
		}
	}

	/**
	 * Aplica efectos a los componentes de la pantalla
	 */
	private void aplicaEfectos() {
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
