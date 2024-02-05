package controllers;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import persistence.HibernateUtil;
import persistence.entities.User;
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
	private ImageView imgVector;

	@FXML
	void btnRegistrarsePressed(MouseEvent e) {
		// Si los campos no están vacíos
		if (!txtEmail.getText().isBlank() && !txtContrasena.getText().isBlank()
				&& !txtRepetirContrasena.getText().isBlank()) {
			// Y las contraseñas coinciden
			if (txtContrasena.getText().equals(txtRepetirContrasena.getText())) {
				// TODO Ver si el usuario ya existe???
				Session session = HibernateUtil.getSession();
				// Crea un usuario con los datos de los campos
				User user = new User(txtEmail.getText(), txtContrasena.getText());
				// Guardalo en la base de datos
				session.persist(user);
				// Y vuelve a la pantalla de login
				NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN,
						Constantes.CSS_LOGIN);
				pantallaLogin.navegaAPantalla();
				NavegacionPantallas.cerrarVentanaActual(e);
			} else {
				// TODO mensaje error
			}
		} else {
			// TODO mensaje error
		}
	}

}
