package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import com.alibaba.fastjson.JSON;

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
import utils.ControllerUtils;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaExportacionPeliculasController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnExportar;

	@FXML
	private ChoiceBox<?> cboxFiltroYear;

	@FXML
	private Label lblCabecera;

	@FXML
	private Label lblDirectorioExp;

	@FXML
	private Label lblFormato;

	@FXML
	private Label lblSeleccionaOpc;

	@FXML
	private Label lblError;

	@FXML
	private Label lblInfo;

	@FXML
	private Pane panelCentral;

	@FXML
	private Pane panelSuperior;

	@FXML
	private RadioButton rdBtnGenero;

	@FXML
	private RadioButton rdBtnTitulo;

	@FXML
	private RadioButton rdBtnTodo;

	@FXML
	private RadioButton rdbtnCSV;

	@FXML
	private RadioButton rdbtnJSON;

	@FXML
	private RadioButton rdbtnYear;

	@FXML
	private TextField txtDirectorio;

	@FXML
	private TextField txtGenero;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextField txtYear;

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

				// Habilitar textField, deshabilitar los demás
				txtYear.setDisable(false);
				ControllerUtils.disableTextFields(txtGenero, txtTitulo);

				exportByYear();

				// Si se ha seleccionado genero
			} else if (rdBtnGenero.isSelected()) {
				// Habilitar textField, deshabilitar los demás
				txtGenero.setDisable(false);
				ControllerUtils.disableTextFields(txtYear, txtTitulo);

				exportByGenero();

				// Si se ha seleccionado por titulo
			} else if (rdBtnTitulo.isSelected()) {
				// Habilitar textField, deshabilitar los demás
				txtTitulo.setDisable(false);
				ControllerUtils.disableTextFields(txtYear, txtGenero);

				exportByTitulo(peliDao);

				// Si se ha seleccionado sin filtro
			} else if (rdBtnTodo.isSelected()) {

				ControllerUtils.disableTextFields(txtGenero, txtTitulo, txtYear);
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
	 * @param peliDao
	 */
	private void exportAll(PeliculaDaoImpl peliDao) {
		List<Pelicula> allPeliculas = null;
		allPeliculas = peliDao.searchAll();
		exportList(allPeliculas);
	}

	/**
	 * @param peliDao
	 */
	private void exportByTitulo(PeliculaDaoImpl peliDao) {
		String tituloInput = txtTitulo.getText();
		List<Pelicula> peliculasPorTitulo = null;

		if (!tituloInput.isBlank()) {
			peliculasPorTitulo = peliDao.searchMovieByTitle(tituloInput);
			exportList(peliculasPorTitulo);
		} else {
			showError("| El título no puede estar vacío |");
		}
	}

	/**
	 * 
	 */
	private void exportByGenero() {
		String generoInput = txtGenero.getText();
		List<Pelicula> peliculasPorGenero = null;

		if (!generoInput.isBlank()) {
			// peliculasPorGenero = peliDao.searchMoviesByGenre(generoInput);
			exportList(peliculasPorGenero);

		} else {
			showError("| El género no puede estar vacío |");
		}
	}

	/**
	 * Exporta las películas de la base de datos filtrando por año
	 */
	private void exportByYear() {
		String yearInput = txtYear.getText();

		// Si el input es correcto
		if (yearInput.matches("\\d+")) {

			List<Pelicula> peliculasPorYear = null;
			int year = Integer.parseInt(yearInput);

			/**
			 * Bloques de if, en donde se igualará la lista a los métodos de peliDao
			 * dependiendo si se elige en el cbox mayorque, exacto...
			 */

			// Una vez asignada la lista
			exportList(peliculasPorYear);

		} else {
			showError("| El año no es correcto |");
		}
	}

	/**
	 * @param peliculas
	 * 
	 */
	private void exportList(List<Pelicula> peliculas) {

		String directorio = txtDirectorio.getText();

		// Si está vacío, usar este directorio por defecto
		if (directorio.isBlank()) {
			directorio = "C:\\Users";
		}

		if (rdbtnCSV.isSelected()) {
			// Exportar lista s CSV
			exportCSV(peliculas, directorio);
		} else if (rdbtnJSON.isSelected()) {
			// Exportar lista a JSON
			exportJSON(peliculas, directorio);
		} else {
			showError("| Debe seleccionar un formato de exportación |");
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
	 * Exporta los objetos de una lista a un fichero JSON
	 * 
	 * @param peliculas  Lista de películas a exportar
	 * @param directorio directorio en el que se generará el fichero JSON
	 */
	private void exportJSON(List<Pelicula> peliculas, String directorio) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio))) {
			// Convertir la lista de películas a una cadena JSON
			String jsonString = JSON.toJSONString(peliculas, true);

			// Escribir la cadena JSON en el archivo
			writer.write(jsonString);

			showInfo("JSON creado en: " + directorio);
		} catch (IOException e) {
			showError("Error creando JSON." + e.getLocalizedMessage());
			System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
		}
	}

	// Método para crear un ObjectMapper con límite de anidamiento

	/**
	 * Exporta los objetos de una lista a un fichero CSV
	 * 
	 * @param peliculas  Lista de películas a exportar
	 * @param directorio directorio en el que se generará el fichero CSV
	 */
	private void exportCSV(List<Pelicula> peliculas, String directorio) {
		// TODO Auto-generated method stub

	}
}
