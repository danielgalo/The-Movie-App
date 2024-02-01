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
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	@FXML
	void consultaPeliculasPressed(MouseEvent event) {

	}

	@FXML
	void exportarPeliculasPressed(MouseEvent event) {

	}

	@FXML
	void panelSalirPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN, Constantes.CSS_LOGIN);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
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
