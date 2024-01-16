package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utils.NavegacionPantallas;

/**
 * Clase controller de la pantalla de login
 */
public class PantallaLoginController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnAcceder;

	@FXML
	private Button btnRegister;

	@FXML
	private ImageView imgLogo;

	@FXML
	private Label lblTitle;

	@FXML
	private TextField txtCorreo;

	@FXML
	private PasswordField txtPassword;

	/**
	 * Navega a la pantalla de registro
	 * 
	 * @param event
	 */
	@FXML
	void btnRegistroPressed(MouseEvent event) {

		NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla de Registro", "/views/PantallaRegister.fxml",
				"/styles/register-style.css");

		navegacion.navegaAPantalla();

	}

	@FXML
	void initialize() {

		imgLogo.setImage(new Image("/resources/logo-app.png"));
		// Setting the deep shadow effect to the text
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		lblTitle.setEffect(shadow);
		txtCorreo.setEffect(shadow);
		txtPassword.setEffect(shadow);
		btnAcceder.setEffect(shadow);
		btnRegister.setEffect(shadow);

	}

}
