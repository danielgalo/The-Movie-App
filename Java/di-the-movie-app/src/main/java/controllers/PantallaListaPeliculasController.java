package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaListaPeliculasController {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnVolver;

    @FXML
    private ImageView imgVector;

    @FXML
    void btnBuscarPressed(MouseEvent event) {

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
    void btnVolverPressed(MouseEvent event) {
    	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
    	navegacion.navegaAPantalla();
    	NavegacionPantallas.cerrarVentanaActual(event);
    }

}
