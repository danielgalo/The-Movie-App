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
<<<<<<< HEAD
			User userFound = userDao.getUser(email, password);
			currentUser = userFound;
=======
			currentUser = userDao.getUser(email, password);
>>>>>>> 27e8e328f37e79bb02908e5f43c0c1b85de6c0fd

			// Si el usuario no es nulo (se ha encontrado en la bbdd) y la contraseña del
			// usuario encontrado coincide con la introducida, inicia sesión
			if (currentUser != null) {

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

	}

}
