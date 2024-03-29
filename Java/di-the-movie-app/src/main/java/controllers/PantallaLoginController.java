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
import utils.ControllerUtils;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Clase controller de la pantalla de login
 */
public class PantallaLoginController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/** Boton para iniciar sesión */
	@FXML
	private Button btnAcceder;

	/** Botón para ir a ventana de registro */
	@FXML
	private Button btnRegister;

	/** Imagen de logo */
	@FXML
	private ImageView imgLogo;

	/** Título de la aplicacion */
	@FXML
	private Label lblTitle;

	/** Informacion sobre resultados de login */
	@FXML
	private Label lblInfo;

	@FXML
	private Label lblWelcome;

	/** Text field de correo */
	@FXML
	private TextField txtCorreo;

	/** Text field de contraseña */
	@FXML
	private PasswordField txtPassword;

	/** Usuario actual que usa la aplicacion, cambia al iniciar sesión */
	public static User currentUser;

	/**
	 * Navega a la pantalla de registro
	 * 
	 * @param event evento del mouse
	 */
	@FXML
	void btnRegistroPressed(MouseEvent event) {

		NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla de Registro", Constantes.PANTALLA_REGISTRO,
				Constantes.CSS_REGISTRO);

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
		try {
			// Obtener la sesión de HibernateUtil
			Session session = HibernateUtil.getSession();

			// Recoger datos
			String email = txtCorreo.getText();
			String password = txtPassword.getText();

			// Crear un DAO de usuario utilizando la sesión
			UserDaoImpl userDao = new UserDaoImpl(session);

			// Recoger resultado (buscar al usuario por email)
			User userFound = userDao.getUser(email, password);
			currentUser = userFound;

			// Si el usuario no es nulo (se ha encontrado en la bbdd) y la contraseña del
			// usuario encontrado coincide con la introducida, inicia sesión
			if (currentUser != null) {
				lblInfo.setText("");
				NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal",
						Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
				navegacion.navegaAPantalla();
				// Cerrar pantalla actual
				NavegacionPantallas.cerrarVentanaActual(event);

			} else {
				lblInfo.setText("Error de credenciales, comprueba los datos o regístrate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cerrar la sesión al finalizar
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Inicia los componentes de la pantalla de login
	 */
	@FXML
	void initialize() {

		imgLogo.setImage(new Image("/resources/logo-app.png"));
		// Setting the deep shadow effect to the text
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		// Aplicar efectos de sombra
		ControllerUtils.setShadowLabels(shadow, lblTitle);
		ControllerUtils.setShadowTxtFields(shadow, txtCorreo, txtPassword);
		ControllerUtils.setShadowButtons(shadow, btnAcceder, btnRegister);

		lblInfo.setWrapText(true);
		lblInfo.setCenterShape(true);
		lblWelcome.setWrapText(true);
		lblWelcome.setCenterShape(true);
	}

}
