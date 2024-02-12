package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import persistence.HibernateUtil;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.Pelicula;
import service.ExportacionFicherosService;
import utils.ControllerUtils;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Controlador de la pantalla de exportación de películas
 */
public class PantallaExportacionPeliculasController {

	/** Texto para el choice box, año mayor */
	private static final String YEAR_MAYOR_QUE = "Mayor que";

	/** Texto para el choice box, año menor */
	private static final String YEAR_MENOR_QUE = "Menor que";

	/** Texto para el choice box, año exacto */
	private static final String YEAR_EXACTO = "Año exacto";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/** Botón para exportar */
	@FXML
	private Button btnExportar;

	/** Choice box para filtrar por años */
	@FXML
	private ChoiceBox<String> cboxFiltroYear;

	/** Label de la cabecera */
	@FXML
	private Label lblCabecera;

	/** Label de la cabecera */
	@FXML
	private Label lblDirectorioExp;

	/** Label formato */
	@FXML
	private Label lblFormato;

	/** Label seleccionar opción */
	@FXML
	private Label lblSeleccionaOpc;

	/** Label de error */
	@FXML
	private Label lblError;

	/** Label de informacion */
	@FXML
	private Label lblInfo;

	/** Panel central con el contenido principal */
	@FXML
	private Pane panelCentral;

	/** Cabecera */
	@FXML
	private Pane panelSuperior;

	/** Opcion filtro por genero */
	@FXML
	private RadioButton rdBtnGenero;

	/** Opcion filtro por titulo */
	@FXML
	private RadioButton rdBtnTitulo;

	/** Opcion filtro por todo */
	@FXML
	private RadioButton rdBtnTodo;

	/** Opcion exportar CSV */
	@FXML
	private RadioButton rdbtnCSV;

	/** Opcion exportar JSON */
	@FXML
	private RadioButton rdbtnJSON;

	/** Opcion filtro por año */
	@FXML
	private RadioButton rdbtnYear;

	/** Text field para introducir el directorio */
	@FXML
	private TextField txtDirectorio;

	/** Text field para introducir el genero */
	@FXML
	private TextField txtGenero;

	/** Text field para introducir el titulo */
	@FXML
	private TextField txtTitulo;

	/** Text field para introducir el año */
	@FXML
	private TextField txtYear;

	/**
	 * Inicia los componentes de la aplicación
	 */
	@FXML
	void initialize() {

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		// Aplicar efectos de sombra
		ControllerUtils.setShadowLabels(shadow, lblCabecera, lblDirectorioExp, lblFormato, lblSeleccionaOpc);
		ControllerUtils.setShadowRdbtn(shadow, rdbtnCSV, rdBtnGenero, rdbtnJSON, rdBtnTitulo, rdBtnTodo, rdbtnYear);
		ControllerUtils.setShadowTxtFields(shadow, txtDirectorio, txtGenero, txtTitulo, txtYear);
		ControllerUtils.setShadowButtons(shadow, btnExportar);
		ControllerUtils.setShadowPanes(shadow, panelCentral);

		// Grupos de radio button
		ToggleGroup grupoFiltro = new ToggleGroup();
		rdBtnTitulo.setToggleGroup(grupoFiltro);
		rdbtnYear.setToggleGroup(grupoFiltro);
		rdBtnGenero.setToggleGroup(grupoFiltro);
		rdBtnTodo.setToggleGroup(grupoFiltro);

		ToggleGroup grupoFormatoExp = new ToggleGroup();
		rdbtnJSON.setToggleGroup(grupoFormatoExp);
		rdbtnCSV.setToggleGroup(grupoFormatoExp);

		// Añadir elementos al choice box
		cboxFiltroYear.getItems().addAll(YEAR_EXACTO, YEAR_MENOR_QUE, YEAR_MAYOR_QUE);

		// Valor por defecto de la choice box
		cboxFiltroYear.setValue(YEAR_EXACTO);

		lblInfo.setWrapText(true);

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

	/**
	 * Acciones a realizar cuando se pulsa el botón de exportar (comprueba las
	 * opciones elegidas y exporta en base a ellas)
	 * 
	 * @param event
	 */
	@FXML
	void btnExportarPressed(MouseEvent event) {

		// Resetear mensaje de error
		if (!lblError.getText().isBlank()) {
			lblError.setText("");
		}
		// Resetear mensaje de información
		if (!lblInfo.getText().isBlank()) {
			lblInfo.setText("");
		}

		Session session = null;

		try {
			// Sesion hibernate
			session = HibernateUtil.getSession();
			PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);

			// Si se ha filtrado por año
			if (rdbtnYear.isSelected()) {

				exportByYear(peliDao);

				// Si se ha seleccionado genero
			} else if (rdBtnGenero.isSelected()) {

				exportByGenero(peliDao);

				// Si se ha seleccionado por titulo
			} else if (rdBtnTitulo.isSelected()) {

				exportByTitulo(peliDao);

				// Si se ha seleccionado sin filtro
			} else if (rdBtnTodo.isSelected()) {

				exportAll(peliDao);

				// Si no se ha seleccionado nada
			} else {
				showError("| Debe seleccionar filtro |");
			}

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
	 * Exporta todas las películas
	 * 
	 * @param peliDao
	 */
	private void exportAll(PeliculaDaoImpl peliDao) {
		List<Pelicula> allPeliculas = null;
		allPeliculas = peliDao.searchByUser(PantallaLoginController.currentUser);
		exportList(allPeliculas);
	}

	/**
	 * Exporta películas por su título
	 * 
	 * @param peliDao
	 */
	private void exportByTitulo(PeliculaDaoImpl peliDao) {

		int userId = Integer.parseInt(PantallaLoginController.currentUser.getId().toString());

		String tituloInput = txtTitulo.getText();
		List<Pelicula> peliculasPorTitulo = null;

		if (!tituloInput.isBlank()) {
			peliculasPorTitulo = peliDao.searchMovieByTitle(tituloInput, userId);
			exportList(peliculasPorTitulo);
		} else {
			showError("| El título no puede estar vacío |");
		}
	}

	/**
	 * Exporta películas por su genero
	 * 
	 * @param peliDao
	 * 
	 */
	private void exportByGenero(PeliculaDaoImpl peliDao) {
		int userId = Integer.parseInt(PantallaLoginController.currentUser.getId().toString());

		String generoInput = txtGenero.getText();
		List<Pelicula> peliculasPorGenero = null;

		if (!generoInput.isBlank()) {
			peliculasPorGenero = peliDao.searcyMoviesByGenre(generoInput, userId);

			exportList(peliculasPorGenero);

		} else {
			showError("| El género no puede estar vacío |");
		}
	}

	/**
	 * Exporta las películas de la base de datos filtrando por año
	 * 
	 * @param peliDao
	 */
	private void exportByYear(PeliculaDaoImpl peliDao) {

		int userId = Integer.parseInt(PantallaLoginController.currentUser.getId().toString());

		String yearInput = txtYear.getText();

		// Si el input es correcto
		if (yearInput.matches("\\d+")) {

			List<Pelicula> peliculasPorYear = null;
			int year = Integer.parseInt(yearInput);

			String filtro = cboxFiltroYear.getValue();

			// Dependiendo de la opción elegida en el choice box
			if (filtro.equals(YEAR_EXACTO)) {

				peliculasPorYear = peliDao.searchByExactYear(year, userId);

			} else if (filtro.equals(YEAR_MAYOR_QUE)) {

				peliculasPorYear = peliDao.searchByGreaterYear(year, userId);

			} else if (filtro.equals(YEAR_MENOR_QUE)) {

				peliculasPorYear = peliDao.searchByEarlierYear(year, userId);

			}

			// Una vez asignada la lista
			exportList(peliculasPorYear);

		} else {
			showError("| El año no es correcto |");
		}
	}

	/**
	 * Exporta una lista a CSV o JSON según sea elegido
	 * 
	 * @param peliculas
	 * 
	 */
	private void exportList(List<Pelicula> peliculas) {

		String directorio = txtDirectorio.getText();

		if (directorio != null) {
			ExportacionFicherosService ficheroService = new ExportacionFicherosService(directorio);
			if (rdbtnCSV.isSelected()) {
				// Exportar lista s CSV
				int exitCode = ficheroService.exportCSV(peliculas);
				verificaExitCode(exitCode, "CSV");

			} else if (rdbtnJSON.isSelected()) {
				// Exportar lista a JSON
				int exitCode = ficheroService.exportJSON(peliculas);
				verificaExitCode(exitCode, "JSON");

			} else {
				showError("| Debe seleccionar un formato de exportación |");
			}
		} else {
			showError("| Indique un directorio de exportación |");
		}
	}

	/**
	 * Muestra en el label de informacion un mensaje
	 * 
	 * @param message mensaje a mostrar
	 */
	private void showInfo(String message) {
		lblInfo.setText(lblInfo.getText() + message);
	}

	/**
	 * Muestra un mensaje en el label de error
	 * 
	 * @param message
	 */
	private void showError(String message) {
		lblError.setText(lblError.getText() + message);
	}

	/**
	 * Verifica un código de salida
	 * 
	 * @param exitCode
	 */
	private void verificaExitCode(int exitCode, String tipoExportacion) {
		if (exitCode == 0) {
			showInfo("Fichero " + tipoExportacion + " creado correctamente.");
		} else {
			showError("Hubo un error creando el fichero " + tipoExportacion + ".");
		}
	}
}
