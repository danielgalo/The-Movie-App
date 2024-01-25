package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.constants.Constantes;

/**
 * Clase principal de la aplicación. Lanza la pantalla de Login
 */
public class TMAMain extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TMAMain.class.getResource(Constantes.PANTALLA_LOGIN));
			Pane ventana = (Pane) loader.load();

			Scene scene = new Scene(ventana);

			String urlCss = getClass().getResource(Constantes.CSS_LOGIN).toExternalForm();

			scene.getStylesheets().add(urlCss);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
