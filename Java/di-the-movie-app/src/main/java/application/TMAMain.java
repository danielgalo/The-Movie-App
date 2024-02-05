package application;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Clase principal de la aplicación. Lanza la pantalla de Login
 */
public class TMAMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN,
				Constantes.CSS_LOGIN);
		pantallaLogin.navegaAPantalla();
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
