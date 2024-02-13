package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import persistence.HibernateUtil;
import persistence.dao.UserDaoImpl;
import persistence.entities.User;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Unit test for simple App.
 */
@ExtendWith(ApplicationExtension.class)
class LoginTest {

	private static final String LBL_INFO = "#lblInfo";
	private static final String BTN_ACCEDER = "#btnAcceder";
	private static final String TXTFIELD_PASSWORD = "#txtPassword";
	private static final String TXTFIELD_CORREO = "#txtCorreo";
	private static final String PASSWORD_TEST = "passwordTest.123";
	private static final String EMAIL_TEST = "emailtest@email.com";
	private static final String ERROR_LOGIN_TEXT = "Error de credenciales, comprueba los datos o regístrate";

	@Start
	private void start(Stage stage) {

		try {

			// Insertar un usuario de prueba
			Session session = HibernateUtil.getSession();
			UserDaoImpl userDao = new UserDaoImpl(session);

			// buscar el usuario para no insertarlo mas de una vez
			User userFound = userDao.getUser(EMAIL_TEST, PASSWORD_TEST);
			if (userFound == null) {
				User userTest = new User();
				userTest.setEmail(EMAIL_TEST);
				userTest.setPassword(PASSWORD_TEST);
				userDao.insert(userTest);
			}

			// Navegar a pantalla de login
			NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla de Login", Constantes.PANTALLA_LOGIN,
					Constantes.CSS_LOGIN);
			pantallaLogin.navegaAPantalla();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cerrar la sesión al finalizar
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Intenta acceder a la aplicación rellenando solo el campo de email o username.
	 * Debe mostrar un mensaje de error
	 */
	@Test
	void accederSoloUsuarioTest() {
		FxRobot fxRobot = new FxRobot();

		fxRobot.clickOn(TXTFIELD_CORREO);
		fxRobot.write("usuario123");
		fxRobot.clickOn(BTN_ACCEDER);
		FxAssert.verifyThat(LBL_INFO, LabeledMatchers.hasText(ERROR_LOGIN_TEXT));
	}

	/**
	 * Intenta acceder a la aplicación rellenando solo el campo de contraseña. Debe
	 * mostrar un mensaje de error
	 */
	@Test
	void accederSoloPasswordTest() {
		FxRobot fxRobot = new FxRobot();

		fxRobot.clickOn(TXTFIELD_PASSWORD);
		fxRobot.write("12345678");
		fxRobot.clickOn(BTN_ACCEDER);
		FxAssert.verifyThat(LBL_INFO, LabeledMatchers.hasText(ERROR_LOGIN_TEXT));
	}

	/**
	 * Intenta acceder a la aplicación con el usuario de prueba insertado, debe
	 * dejar de iniciar sesion.
	 */
	@Order(1)
	@Test
	void accederCredencialesCorrectas() {
		FxRobot fxRobot = new FxRobot();

		fxRobot.clickOn(TXTFIELD_CORREO);
		fxRobot.write(EMAIL_TEST);
		fxRobot.clickOn(TXTFIELD_PASSWORD);
		fxRobot.write(PASSWORD_TEST);
		fxRobot.clickOn(BTN_ACCEDER);

		// Verificar que el usuario encontrado tiene las credenciales
		assertEquals(true, PantallaLoginController.currentUser != null);
	}

	/**
	 * Intenta acceder a la aplicación con el usuario de prueba insertado, debe
	 * dejar de iniciar sesion.
	 */
	@Order(2)
	@Test
	void accederSinCredenciales() {
		FxRobot fxRobot = new FxRobot();

		fxRobot.clickOn(BTN_ACCEDER);

		// Verificar que el usuario encontrado tiene las credenciales
		FxAssert.verifyThat(LBL_INFO, LabeledMatchers.hasText(ERROR_LOGIN_TEXT));
	}
}
