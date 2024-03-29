package utils;

import java.io.IOException;

import application.TMAMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Clase para navegar entre pantallas
 */
public class NavegacionPantallas {

	/** Titulo de la pantalla */
	private String tituloVentana;

	/** Ruta al archivo FXML */
	private String rutaArchivoFxml;

	/** Ruta al archivo CSS */
	private String styleSheet;

	/**
	 * Consturctor sobrecargado
	 * 
	 * @param tituloVentana   título de la ventana
	 * @param rutaArchivoFxml ruta al archivo FXML de la pantalla
	 * @param styleSheet      ruta a la hoja de estilos de la pantalla
	 */
	public NavegacionPantallas(String tituloVentana, String rutaArchivoFxml, String styleSheet) {
		this.tituloVentana = tituloVentana;
		this.rutaArchivoFxml = rutaArchivoFxml;
		this.styleSheet = styleSheet;
	}

	/**
	 * Navega a la pantalla
	 */
	public void navegaAPantalla() {
		try {
			//Crear Stage
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			// Cargar la clase
			loader.setLocation(TMAMain.class.getResource(rutaArchivoFxml));
			// Crear la ventana
			Pane ventana = (Pane) loader.load();
			Scene scene = new Scene(ventana);
			// Añadirle los estilos
			String urlCss = getClass().getResource(styleSheet).toExternalForm();
			scene.getStylesheets().add(urlCss);
			// Mostrar la pantalla
			stage.setTitle(tituloVentana);
			stage.setScene(scene);

			// Hacer que la ventana no se pueda cambiar de tamaño
			stage.setResizable(false);

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la ventana actual
	 * 
	 * @param event evento de ratón
	 */
	public static void cerrarVentanaActual(MouseEvent event) {
		// Obtener la escena y la ventana actual
		Scene scene = ((Node) event.getSource()).getScene();
		Stage stage = (Stage) scene.getWindow();

		// Cerrar la ventana actual
		stage.close();
	}
	
}
