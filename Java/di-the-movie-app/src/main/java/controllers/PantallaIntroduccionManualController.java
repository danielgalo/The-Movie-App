package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaIntroduccionManualController {

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextArea txtFechaEstreno;

    @FXML
    private TextArea txtTitulo;

    @FXML
    private TextArea txtUrl;

    @FXML
    void btnAltaPressed(MouseEvent event) {
    	
    }

    @FXML
    void btnVolverPressed(MouseEvent event) {
    	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
    	navegacion.navegaAPantalla();
    	NavegacionPantallas.cerrarVentanaActual(event);
    }

}
