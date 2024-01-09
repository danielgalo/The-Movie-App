package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación. Lanza la pantalla de Login
 */
public class TMAMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TMAMain.class.getResource("/views/PantallaLogin.fxml"));
			Pane ventana = (Pane) loader.load();

			Scene scene = new Scene(ventana);

			String urlCss = getClass().getResource("/styles/login-style.css").toExternalForm();

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
