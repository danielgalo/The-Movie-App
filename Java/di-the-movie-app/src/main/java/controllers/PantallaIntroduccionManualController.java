package controllers;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
import javafx.scene.paint.Paint;
import persistence.HibernateUtil;
import persistence.dao.GeneroDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.GeneroPelicula;
import persistence.entities.GeneroPeliculaId;
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
    private Label lblPeliculaInsertada;
    
    @FXML
    private Label lblRecordatorioCampoObligatorio = new Label();
    
    @FXML
    void initialize() {
    	String[] generos = {"Acción", "Animación", "Aventura", "Bélica", "Ciencia ficción", "Comedia", "Crimen", "Documental", "Drama", "Familia", "Fantasía", "Historia", "Misterio", "Música", "Película de TV", "Romance", "Suspense", "Terror", "Western"};
    	cmbGenero.getItems().addAll(generos);
    }

    @FXML
    void btnAltaPressed(MouseEvent event) {
    	lblRecordatorioCampoObligatorio.setEffect(null);
    	lblPeliculaInsertada.setVisible(false);
    	
    	
    	if (dateFechaEstreno.getValue() != null) {
    		Image image = null;
				try {
					image = ImageIO.read(new URL(txtUrl.getText()));
				} catch (Exception e) {
					e.printStackTrace();
				}
    		if(image != null){
    			if (txtUrl.getText().endsWith(".png") || txtUrl.getText().contains(".jpg")) {
    				Session session = HibernateUtil.getSession();
    				PeliculaDaoImpl insertadorPelicula = new PeliculaDaoImpl(session);
    				
    				Pelicula pelicula = new Pelicula();
    				pelicula.setUsuario(PantallaLoginController.currentUser);
    				pelicula.setTitulo(txtTitulo.getText());
    				
    				GeneroDaoImpl obtenedorGenero = new GeneroDaoImpl(session);
    				GeneroPelicula generoPelicula = new GeneroPelicula(new GeneroPeliculaId(pelicula, obtenedorGenero.getGeneroByName(cmbGenero.getValue())));
    				List<GeneroPelicula> listaGeneros = new ArrayList<GeneroPelicula>();
    				listaGeneros.add(generoPelicula);
    				
    				pelicula.setGeneroPelicula(listaGeneros);
    				pelicula.setOverview(txtDescripcion.getText());
    				@SuppressWarnings("deprecation")
    				Date fechaEstreno = new Date(dateFechaEstreno.getValue().getYear(), dateFechaEstreno.getValue().getMonthValue(), dateFechaEstreno.getValue().getDayOfMonth()); 
    				pelicula.setReleaseDate(fechaEstreno);
    				pelicula.setCartel(txtUrl.getText());
    				//TODO add id
    				insertadorPelicula.insert(pelicula);							
						
    				lblPeliculaInsertada.setTextFill(Paint.valueOf("Green"));
    				lblPeliculaInsertada.setText("¡Pelicula dada de alta con éxito!");
    				lblPeliculaInsertada.setVisible(true);
    			} else {
    				lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
    				lblPeliculaInsertada.setText("Formato de URL invalido (solo .png y .jpg)");
    				lblPeliculaInsertada.setVisible(true);
    			} 
    		} else {
    			lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
  				lblPeliculaInsertada.setText("URL de imagen inválido.");
  				lblPeliculaInsertada.setVisible(true);
				}
			} else {
				DropShadow shadow = new DropShadow();
	  		shadow.setColor(new Color(1.0, 1.0, 1.0, 1.0));
	  		shadow.setSpread(0.79);
	  		lblRecordatorioCampoObligatorio.setEffect(shadow);
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
