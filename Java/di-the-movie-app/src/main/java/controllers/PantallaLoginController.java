package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;

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
import persistence.HibernateUtil;
import persistence.dao.UserDaoImpl;
import persistence.entities.User;
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
	private Label lblInfo;

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

		// Cerrar pantalla actual
		NavegacionPantallas.cerrarVentanaActual(event);

	}

	/**
	 * Acciones realizadas al pulsar el botón de Acceder. Dependiendo de los datos
	 * introducidos, se busca al usuario en la base de datos. Si está accederá a la
	 * pantalla principal, si no muestra un mensaje informativo
	 * 
	 * @param event
	 */
	@FXML
	void btnAccederPressed(MouseEvent event) {

		// TODO verificacion bbdd

		// Recoger datos
		String email = txtCorreo.getText();
		String password = txtPassword.getText();

		// Crear una sesion y DAO de usuario
		Session session = HibernateUtil.getSession();
		UserDaoImpl userDao = new UserDaoImpl(session);

		// Recoger resultado (buscar al usuario por email)
		User userFound = userDao.getUserByEmail(email);

		// Si el usuario no es nulo (Se ha encontrado en la bbdd) y la contraseña del
		// usuario encontrado coincide con la introducida, inicia sesion
		if (userFound != null && userFound.getPassword().equals(password)) {

			NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal",
					"/views/PantallaPrincipal.fxml", "/styles/pantalla-principal-style.css");
			navegacion.navegaAPantalla();

			// Cerrar pantalla actual
			NavegacionPantallas.cerrarVentanaActual(event);

		} else {
			lblInfo.setText("Error de credenciales, comprueba los datos o registrate");
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

		lblInfo.setWrapText(true);
		lblInfo.setCenterShape(true);

	}

}
