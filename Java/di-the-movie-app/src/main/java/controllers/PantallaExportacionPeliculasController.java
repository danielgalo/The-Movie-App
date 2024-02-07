package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import com.alibaba.fastjson.JSON;
import com.opencsv.CSVWriter;

import dto.PeliculaDTO;
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

	private static final String YEAR_MAYOR_QUE = "Mayor que";

	private static final String YEAR_MENOR_QUE = "Menor que";

	private static final String YEAR_EXACTO = "Año exacto";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnExportar;

	@FXML
	private ChoiceBox<String> cboxFiltroYear;

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
		allPeliculas = peliDao.searchAll();
		exportList(allPeliculas);
	}

	/**
	 * Exporta películas por su título
	 * 
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
	 * Exporta películas por su genero
	 * 
	 * @param peliDao
	 * 
	 */
	private void exportByGenero(PeliculaDaoImpl peliDao) {
		String generoInput = txtGenero.getText();
		List<Pelicula> peliculasPorGenero = null;

		if (!generoInput.isBlank()) {
			peliculasPorGenero = peliDao.searcyMoviesByGenre(generoInput);

			for (Pelicula pelicula : peliculasPorGenero) {
				System.out.println("------------------" + pelicula.getTitulo());
			}
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
		String yearInput = txtYear.getText();

		// Si el input es correcto
		if (yearInput.matches("\\d+")) {

			List<Pelicula> peliculasPorYear = null;
			int year = Integer.parseInt(yearInput);

			String filtro = cboxFiltroYear.getValue();

			// Dependiendo de la opción elegida en el choice box
			if (filtro.equals(YEAR_EXACTO)) {

				peliculasPorYear = peliDao.searchByExactYear(year);

			} else if (filtro.equals(YEAR_MAYOR_QUE)) {

				peliculasPorYear = peliDao.searchByGreaterYear(year);

			} else if (filtro.equals(YEAR_MENOR_QUE)) {

				peliculasPorYear = peliDao.searchByEarlierYear(year);

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

			List<PeliculaDTO> pelisDTO = new ArrayList<>();

			// Convertir las entidades a DTO, para evitar anidaciones al escribir en JSON
			peliculaEntityToDTO(peliculas, pelisDTO);

			// Convertir la lista de películas a una cadena JSON
			String jsonString = JSON.toJSONString(pelisDTO, true);

			// Escribir la cadena JSON en el archivo
			writer.write(jsonString);

			showInfo("JSON creado en: " + directorio);
		} catch (IOException e) {
			showError("Error creando JSON.");
			System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
		}
	}

	/**
	 * Convierte de una lista de Entidades Pelicula a Objetos DTO, y los añade a la
	 * lista de objetos DTO.
	 * 
	 * @param peliculas Lista de Entidades Películas
	 * @param pelisDTO  Lista de DTOs Películas
	 */
	private void peliculaEntityToDTO(List<Pelicula> peliculas, List<PeliculaDTO> pelisDTO) {
		for (Pelicula pelicula : peliculas) {

			String titulo = pelicula.getTitulo();
			String overview = pelicula.getOverview();
			String releaseDate = pelicula.getReleaseDate().toString();
			String img = pelicula.getCartel();
			double voteAverage = pelicula.getValoracion();
			String comentariosUsuario = pelicula.getComentariosUsuario();
			String fechaVisualizacion = pelicula.getFechaVisualizacionUsuario().toString();
			double valoracionUsuario = pelicula.getValoracionUsuario();

			PeliculaDTO peliDto = new PeliculaDTO(titulo, overview, releaseDate, img, voteAverage, comentariosUsuario,
					fechaVisualizacion, valoracionUsuario);
			pelisDTO.add(peliDto);

		}
	}

	/**
	 * Exporta los objetos de una lista a un fichero CSV
	 * 
	 * @param peliculas  Lista de películas a exportar
	 * @param directorio directorio en el que se generará el fichero CSV
	 */
	private void exportCSV(List<Pelicula> peliculas, String directorio) {

		// Convertir la lista de entidades en DTO
		List<PeliculaDTO> pelisDTO = new ArrayList<>();
		peliculaEntityToDTO(peliculas, pelisDTO);

		for (PeliculaDTO peliDTO : pelisDTO) {
			// Crear objetos para escribir en el archivo CSV
			try (CSVWriter writer = new CSVWriter(new FileWriter(directorio))) {
				// Crear lista de objetos para escribir en el archivo CSV
				List<String[]> data = new ArrayList<>();
				data.add(new String[] { "titulo", "overview", "releaseDate", "img", "voteAverage", "comentariosUsuario",
						"fechaVisualizacion", "valoracionUsuario" });
				data.add(new String[] { peliDTO.getTitulo(), peliDTO.getOverview(), peliDTO.getReleaseDate(),
						peliDTO.getImg(), String.valueOf(peliDTO.getVoteAverage()), peliDTO.getComentariosUsuario(),
						peliDTO.getFechaVisualizacion(), String.valueOf(peliDTO.getValoracionUsuario()) });

				// Escribir datos en el archivo CSV
				writer.writeAll(data);
				showInfo("CSV escrito en: " + directorio);
			} catch (IOException e) {
				showError("Error escribiendo el archivo CSV");
				e.printStackTrace();
			}
		}

	}
}
