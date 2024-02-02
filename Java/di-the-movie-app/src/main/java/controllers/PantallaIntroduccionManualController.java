package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaIntroduccionManualController {

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFechaEstreno;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtUrl;

    @FXML
    void btnAltaPressed(MouseEvent event) {
    	
    }

    @FXML
    void btnVolverPressed(MouseEvent event) {
    	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
    	navegacion.navegaAPantalla();
    	NavegacionPantallas.cerrarVentanaActual(event);
    }
    
    @FXML
    void btnVolverEntered(MouseEvent event) {
    	DropShadow shadow = new DropShadow();
  		shadow.setColor(new Color(0.0, 0.95, 1.0, 1.0));
  		shadow.setSpread(0.18);
  		btnVolver.setEffect(shadow);
    }
    
    @FXML
    void btnVolverExited(MouseEvent event) {
    	btnVolver.setEffect(null);
    }
    
    @FXML
    void btnAltaEntered(MouseEvent event) {
    	DropShadow shadow = new DropShadow();
  		shadow.setColor(new Color(0.3421, 0.3421, 0.3421, 1.0));
  		shadow.setSpread(0.18);
  		btnAlta.setEffect(shadow);
    }
    
    @FXML
    void btnAltaExited(MouseEvent event) {
    	btnAlta.setEffect(null);
    }

}
