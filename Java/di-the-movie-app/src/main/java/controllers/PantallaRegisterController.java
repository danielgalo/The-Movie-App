package controllers;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	void btnRegistrarsePressed(MouseEvent e) {
		Session session = HibernateUtil.getSession();
		User user = new User(txtEmail.getText(), txtContrasena.getText());
		session.persist(user);
		
		NavegacionPantallas pantallaLogin = new NavegacionPantallas("Pantalla Login", Constantes.PANTALLA_LOGIN, Constantes.CSS_PANTALLA_PRINCIPAL);
		pantallaLogin.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(e);
	}

}
