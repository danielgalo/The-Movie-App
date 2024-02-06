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

		if (!lblError.getText().isBlank()) {
			lblError.setText("");
		}

		Session session = null;

		try {
			// Sesion hibernate
			session = HibernateUtil.getSession();
			PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);

			// Si se ha filtrado por año
			if (rdbtnYear.isSelected()) {

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

				// Si se ha seleccionado genero
			} else if (rdBtnGenero.isSelected()) {

				String generoInput = txtGenero.getText();
				List<Pelicula> peliculasPorGenero = null;

				if (!generoInput.isBlank()) {
					// peliculasPorGenero = peliDao.searchMoviesByGenre(generoInput);
					exportList(peliculasPorGenero);

				} else {
					showError("| El género no puede estar vacío |");
				}

				// Si se ha seleccionado por titulo
			} else if (rdBtnTitulo.isSelected()) {
				String tituloInput = txtTitulo.getText();
				List<Pelicula> peliculasPorTitulo = null;

				if (!tituloInput.isBlank()) {
					peliculasPorTitulo = peliDao.searchMovieByTitle(tituloInput);
					exportList(peliculasPorTitulo);
				} else {
					showError("| El título no puede estar vacío |");
				}

				// Si se ha seleccionado sin filtro
			} else if (rdBtnTodo.isSelected()) {

				List<Pelicula> allPeliculas = null;
				allPeliculas = peliDao.searchAll();
				exportList(allPeliculas);

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

	private void showInfo(String message) {
		lblInfo.setText(lblInfo.getText() + message);
	}

	private void showError(String message) {
		lblError.setText(lblError.getText() + message);
	}

	private void exportJSON(List<Pelicula> peliculas, String directorio) {

		// TODO Auto-generated method stub

	}

	private void exportCSV(List<Pelicula> peliculas, String directorio) {
		// TODO Auto-generated method stub

	}
}
