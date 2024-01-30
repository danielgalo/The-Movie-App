package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaRegisterController {

	@FXML
	private Pane mainPane;

	@FXML
	private Pane subPane;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtContrasena;

	@FXML
	private TextField txtRepetirContrasena;

	@FXML
	private Label lblRegistro;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblContrasena;

	@FXML
	private Label lblRepetirContrasena;

	@FXML
	private Button btnRegistrarse;
	
	@FXML
	void btnRegistrarsePressed(MouseEvent e) {
		NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN, Constantes.CSS_PANTALLA_PRINCIPAL);
		pantallaLogin.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(e);
	}

}
