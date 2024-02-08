package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import persistence.HibernateUtil;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.GeneroPelicula;
import persistence.entities.Pelicula;
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
    private DatePicker dateFechaEstreno;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtUrl;
    
    @FXML
    private ChoiceBox<String> cmbGenero;
    
    @FXML
    private Label lblExito;
    
    @FXML
    private Label lblCampoObligatorio;
    
    @FXML
    void initialize() {
    	String[] generos = {"Acción", "Animación", "Aventura", "Bélica", "Ciencia ficción", "Comedia", "Crimen", "Documental", "Drama", "Familia", "Fantasía", "Historia", "Misterio", "Música", "Película de TV", "Romance", "Suspense", "Terror", "Western"};
    	cmbGenero.getItems().addAll(generos);
    }

    @FXML
    void btnAltaPressed(MouseEvent event) {
    	lblCampoObligatorio.setEffect(null);
    	lblExito.setVisible(false);
    	if (dateFechaEstreno.getValue() != null) {				
    		Session session = HibernateUtil.getSession();
    		PeliculaDaoImpl insertadorPeli = new PeliculaDaoImpl(session);
    		//TODO NO DEJAR DAR DE ALTA SIN UNA URL VALIDA
    		Pelicula peli = new Pelicula();
    		peli.setUsuario(PantallaLoginController.currentUser);
    		peli.setTitulo(txtTitulo.getText());
    		List<GeneroPelicula> genero = new ArrayList<GeneroPelicula>();
    		peli.setGeneroPelicula(genero);
    		peli.setOverview(txtDescripcion.getText());
    		Date fechaEstreno = new Date(dateFechaEstreno.getValue().getYear(), dateFechaEstreno.getValue().getMonthValue(), dateFechaEstreno.getValue().getDayOfMonth()); 
    		peli.setReleaseDate(fechaEstreno);
    		peli.setCartel(txtUrl.getText());
    		insertadorPeli.insert(peli);
    		lblExito.setVisible(true);
			} else {
				DropShadow shadow = new DropShadow();
	  		shadow.setColor(new Color(1.0, 1.0, 1.0, 1.0));
	  		shadow.setSpread(0.79);
	  		lblCampoObligatorio.setEffect(shadow);
			}
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
