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

	/** Expresión regular para validar la contraseña */
	private static final String REGEX_PASSWORD = "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	/** Expresion regular para validar email */
	private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

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

	/**
	 * Inicializa el controlador después de que se haya cargado la raíz del archivo
	 * FXML.
	 */
	@FXML
	void initialize() {
		txtContrasena.setManaged(true);
		txtContrasena.setVisible(true);
	}

	@FXML
	void btnRegistrarsePressed(MouseEvent e) {
		// Si los campos no están vacíos
		if (validateFieldsNotEmpty() && validateRegisterFields()) {
			// Si la contraseña y email cumplen con el formato

			// Y las contraseñas coinciden
			if (txtContrasena.getText().equals(txtRepetirContrasena.getText())) {
				Session session = HibernateUtil.getSession();
				UserDaoImpl buscadorUsuario = new UserDaoImpl(session);
				User usuarioExistente = buscadorUsuario.getUser(txtEmail.getText(), txtContrasena.getText());
				// Y no existe un usuario con ese email
				if (usuarioExistente == null) {
					registraUsuario(e, session);
				} else { // Usuario ya existe
					lblErrores.setText("Ese correo electrónico ya está en uso.");
				}
			} else { // Contraseñas no coinciden
				lblErrores.setText("Las contraseñas no coinciden, compruebe e intente de nuevo.");
			}

		} else { // Campos vacíos o formato no válido

			// Campos vacíos
			if (!validateFieldsNotEmpty()) {
				lblErrores.setText("Todos los campos deben rellenarse, compruebe e intente de nuevo.");
			}

			// Formatos incorrectos
			if (!validateRegisterFields()) {
				showAlertMessages();
			}
		}
	}

	/**
	 * Comprueba que el usuario no ha introducido valores en blanco
	 * 
	 * @return true si el usuario no ha introducido ningún valor en blanco, false en
	 *         caso contrario
	 */
	private boolean validateFieldsNotEmpty() {
		return !txtEmail.getText().isBlank() && !txtContrasena.getText().isBlank()
				&& !txtRepetirContrasena.getText().isBlank();
	}

	/**
	 * Valida que los campos de registros tengan un formato correcto
	 * 
	 * @return true si los campos coinciden con el formato esperado, false en caso
	 *         contrario
	 */
	private boolean validateRegisterFields() {
		return Pattern.matches(REGEX_PASSWORD, txtContrasena.getText())
				&& Pattern.matches(REGEX_EMAIL, txtEmail.getText());
	}

	/**
	 * Registra un usuario en la base de datos
	 * 
	 * @param e
	 * @param session
	 */
	private void registraUsuario(MouseEvent e, Session session) {
		try {
			// Crea un usuario con los datos de los campos
			User user = new User(txtEmail.getText(), txtContrasena.getText());
			// Guardalo en la base de datos
			session.persist(user);
			// Y vuelve a la pantalla de login
			NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN,
					Constantes.CSS_LOGIN);
			pantallaLogin.navegaAPantalla();
			NavegacionPantallas.cerrarVentanaActual(e);
		} catch (Exception e2) {
			lblErrores.setText("Ese correo electrónico ya está en uso.");
			e2.printStackTrace();
			session.clear();
		}
	}

	/**
	 * Muestra ventana emergente con información del registro, al introducir
	 * credenciales no válidas
	 */
	private void showAlertMessages() {

		// Alerta contraseña
		if (!Pattern.matches(REGEX_PASSWORD, txtContrasena.getText())) {
			Alert alert = new Alert(AlertType.WARNING, "Contraseña no válida");
			alert.setTitle("Error de formato de credenciales.");
			alert.setResizable(false);
			alert.setHeaderText("Comprueba que la contraseña tenga al menos:" + "\n- Entre 8 y 20 caracteres."
					+ "\n- Una mayúscula." + "\n- Una minúscula." + "\n- Un dígito." + "\n- Un caracter especial.\n");
			alert.show();
		}

		// Alerta email
		if (!Pattern.matches(REGEX_EMAIL, txtEmail.getText())) {
			Alert alert = new Alert(AlertType.WARNING, "Email no válido");
			alert.setResizable(false);
			alert.setHeaderText(
					"Comprueba que el correo electrónico tenga un formato correcto, por ejemplo: usuario@dominio.com");
			alert.show();
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
