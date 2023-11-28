package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.TMAMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

	@FXML
	void btnRegistroPressed(MouseEvent event) {

		// Navegar a pantalla de registro
		try {
			// Crear stage
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			// Cargar la clase
			loader.setLocation(TMAMain.class.getResource("/views/PantallaRegister.fxml"));
			// Crear la ventana
			Pane ventana;
			ventana = (Pane) loader.load();
			Scene scene = new Scene(ventana);
			// Añadirle los estilos
			String urlCss = getClass().getResource("/styles/register-style.css").toExternalForm();
			scene.getStylesheets().add(urlCss);
			// Mostrar la pantalla
			stage.setTitle("Pantalla de Registro");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
