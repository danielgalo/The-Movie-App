package controllers;

import java.util.regex.Pattern;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import persistence.HibernateUtil;
import persistence.dao.UserDaoImpl;
import persistence.entities.User;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaRegisterController {

	private static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

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
	private Label lblErrores;

	@FXML
	private Button btnVolver;

	@FXML
	void btnRegistrarsePressed(MouseEvent e) {
		// Si los campos no están vacíos
		if (!txtEmail.getText().isBlank() && !txtContrasena.getText().isBlank()
				&& !txtRepetirContrasena.getText().isBlank()) {
			// Si la contraseña cumple con los requisitos
			if (Pattern.matches(REGEX_PASSWORD, txtContrasena.getText())) {
				// Y las contraseñas coinciden
				if (txtContrasena.getText().equals(txtRepetirContrasena.getText())) {
					Session session = HibernateUtil.getSession();
					UserDaoImpl buscadorUsuario = new UserDaoImpl(session);
					User usuarioExistente = buscadorUsuario.getUser(txtEmail.getText(), txtContrasena.getText());
					// Y no existe un usuario con ese email
					if (usuarioExistente == null) {
						try {
							// Crea un usuario con los datos de los campos
							User user = new User(txtEmail.getText(), txtContrasena.getText());
							// Guardalo en la base de datos
							session.persist(user);
							// Y vuelve a la pantalla de login
							NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login",
									Constantes.PANTALLA_LOGIN, Constantes.CSS_LOGIN);
							pantallaLogin.navegaAPantalla();
							NavegacionPantallas.cerrarVentanaActual(e);
						} catch (Exception e2) {
							lblErrores.setText("Ese correo electrónico ya está en uso.");
							e2.printStackTrace();
							session.clear();
						}
					} else { // Usuario ya existe
						lblErrores.setText("Ese correo electrónico ya está en uso.");
					}
				} else { // Contraseñas no coinciden
					lblErrores.setText("Las contraseñas no coinciden, compruebe e intente de nuevo.");
				}
			} else { // Contraseña o correo incumple formato
				Alert alert = new Alert(AlertType.WARNING,
						"Error de formato de credenciales. Comprueba que la contraseña o el correo electrónico sean válidos.");
				alert.setTitle("Error de formato de credenciales.");
				alert.setResizable(false);
				alert.setHeaderText(
						"Comprueba que el correo electrónico tenga un formato correcto y que la contraseña tenga al menos:"
								+ "\n- Entre 8 y 20 caracteres." + "\n- Una mayúscula." + "\n- Una minúscula."
								+ "\n- Un dígito." + "\n- Un caracter especial.\n");
				alert.show();
			}
		} else { // Campos vacíos
			lblErrores.setText("Todos los campos deben rellenarse, compruebe e intente de nuevo.");
		}
	}

	@FXML
	void btnRegistrarseEntered(MouseEvent event) {
		DropShadow shadow = new DropShadow();
		shadow.setColor(new Color(0.0, 0.0, 0.0, 1.0));
		shadow.setSpread(0.18);
		btnRegistrarse.setEffect(shadow);
	}

	@FXML
	void btnRegistrarseExited(MouseEvent event) {
		btnRegistrarse.setEffect(null);
	}

	@FXML
	void btnVolverPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Logín", Constantes.PANTALLA_LOGIN,
				Constantes.CSS_LOGIN);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	@FXML
	void btnVolverEntered(MouseEvent event) {
		DropShadow shadow = new DropShadow();
		shadow.setColor(new Color(0.0, 0.0, 0.0, 1.0));
		shadow.setSpread(0.18);
		btnVolver.setEffect(shadow);
	}

	@FXML
	void btnVolverExited(MouseEvent event) {
		btnVolver.setEffect(null);
	}

}
