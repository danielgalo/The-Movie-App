/**
 * Sample Skeleton for 'PantallaPrincipal.fxml' Controller Class
 */

package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import persistence.HibernateUtil;
import persistence.dao.GeneroDaoImpl;
import persistence.entities.Genero;
import utils.NavegacionPantallas;
import utils.TMDBApi;
import utils.constants.Constantes;

/**
 * Controlador de la pantalla o menú principal
 */
public class PantallaPrincipalController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/** Titulo de la pantalla */
	@FXML
	private Label lblTitulo;

	/** Panel de alta de peliculas */
	@FXML
	private Pane panelAltaPeliculas;

	/** Panel de consulta de películas */
	@FXML
	private Pane panelConsultaPeliculas;

	/** Panel de exportar películas */
	@FXML
	private Pane panelExportarPeliculas;

	/** Panel de salir al login */
	@FXML
	private Pane panelSalir;

	/** Imagen de añadir */
	@FXML
	private ImageView addBtn;

	/** Imagen de salir */
	@FXML
	private ImageView exitBtn;

	/** Imagen de exportar */
	@FXML
	private ImageView exportBtn;

	/** Imagen de buscar */
	@FXML
	private ImageView searchBtn;

	/**
	 * Navega a la pantalla de alta de películas
	 * 
	 * @param event
	 */
	@FXML
	void altaPeliculasPressed(MouseEvent event) {

		NavegacionPantallas navegacion = new NavegacionPantallas("Alta de películas por API",
				Constantes.PANTALLA_ALTA_API, Constantes.CSS_ALTA_API);

		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Navega a pantalla de consulta de películas
	 * 
	 * @param event
	 */
	@FXML
	void consultaPeliculasPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Listado de películas",
				Constantes.PANTALLA_LISTA_PELICULAS, Constantes.CSS_LISTA_PELICULAS);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Navega a pantalla de exportacion de peliculas
	 * 
	 * @param event
	 */
	@FXML
	void exportarPeliculasPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Exportación de Películas",
				Constantes.PANTALLA_EXPORTACION_PELICULAS, Constantes.CSS_EXPORTACION_PELICULAS);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Navega a la pantalla de login
	 * 
	 * @param event
	 */
	@FXML
	void panelSalirPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN,
				Constantes.CSS_LOGIN);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Inicia componentes de la pantalla
	 */
	@FXML
	void initialize() {

		addBtn.setImage(new Image("/resources/btn-add.png"));
		searchBtn.setImage(new Image("/resources/btn-search.png"));
		exportBtn.setImage(new Image("/resources/btn-export.png"));
		exitBtn.setImage(new Image("/resources/btn-exit.png"));

		insertGenresIfAbsent();

	}

	/**
	 * Inserta los géneros en la base de datos si hay
	 */
	private void insertGenresIfAbsent() {
		Session session = null;

		try {
			// Obtener la sesión de HibernateUtil
			session = HibernateUtil.getSession();

			// Buscar si hay géneros
			GeneroDaoImpl generoDao = new GeneroDaoImpl(session);
			List<Genero> generosEncontrados = generoDao.searchAll();

			// Si no hay, insertarlos
			if (generosEncontrados.isEmpty() || generosEncontrados == null) {
				// Obtener todos los géneros
				List<Genero> generos = TMDBApi.getAllGenres();

				// Insertarlos
				for (Genero gen : generos) {
					generoDao.insert(gen);
				}
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

}
