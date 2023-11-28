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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TMAMain.class.getResource("/views/PantallaRegister.fxml"));
			Pane ventana;
			ventana = (Pane) loader.load();
			Scene scene = new Scene(ventana);
			String urlCss = getClass().getResource("/styles/register-style.css").toExternalForm();
			scene.getStylesheets().add(urlCss);

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

	}

}
