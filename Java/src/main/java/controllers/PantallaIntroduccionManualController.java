package controllers;

import java.awt.Image;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import persistence.HibernateUtil;
import persistence.dao.ActorDaoImpl;
import persistence.dao.DirectorDaoImpl;
import persistence.dao.GeneroDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.ActoresPeliculas;
import persistence.entities.DirectoresPeliculas;
import persistence.entities.GeneroPelicula;
import persistence.entities.GeneroPeliculaId;
import persistence.entities.Localizacion;
import persistence.entities.Pelicula;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaIntroduccionManualController {

	@FXML
  private Button btnAlta;

  @FXML
  private Button btnVolver;

  @FXML
  private ChoiceBox<String> cmbGenero;

  @FXML
  private DatePicker dateFechaEstreno;

  @FXML
  private DatePicker dateFechaVisualizacion;

  @FXML
  private ImageView imgVector;

  @FXML
  private Label lblPeliculaInsertada;

  @FXML
  private Label lblRecordatorioCampoObligatorio;

  @FXML
  private Spinner<Double> spnValoracion;

  @FXML
  private TextField txtActores;

  @FXML
  private TextArea txtComentarios;

  @FXML
  private TextArea txtDescripcion;

  @FXML
  private TextField txtDirectores;

  @FXML
  private TextField txtLocalizacion;

  @FXML
  private TextField txtTitulo;

  @FXML
  private TextField txtUrl;
    
    @FXML
    void initialize() {
    	String[] generos = {"Acción", "Animación", "Aventura", "Bélica", "Ciencia ficción", "Comedia", "Crimen", "Documental", "Drama", "Familia", "Fantasía", "Historia", "Misterio", "Música", "Película de TV", "Romance", "Suspense", "Terror", "Western"};
    	cmbGenero.getItems().addAll(generos);
    	
    	SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 0.5);
  		spnValoracion.setValueFactory(valueFactory);
    }

    @FXML
    void btnAltaPressed(MouseEvent event) {
    	lblRecordatorioCampoObligatorio.setEffect(null);
    	lblPeliculaInsertada.setText("");;
    	
    	
    	if (!camposObligatoriosAreNull()) {
    		Image image = null;
    		//Comprueba que la URL lleva a una imagen usable
				try {
					image = ImageIO.read(new URL(txtUrl.getText()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				//Si la URL es una imagen
    		if(image != null){
    			//Y es una imagen usable
    			/**
    			 * Nota: ImageView solo parecía aceptar formato png y jpg
    			 */
    			if (txtUrl.getText().endsWith(".png") || txtUrl.getText().contains(".jpg")) {
    				if (spnValoracion.getValue() <= 10 && spnValoracion.getValue() >= 0) {
    					//Inicia una nueva sesión con la tabla peliculas
    					Session session = HibernateUtil.getSession();
    					PeliculaDaoImpl gestorPelicula = new PeliculaDaoImpl(session);
    					//Crea una nueva instancia pelicula
    					Pelicula pelicula = new Pelicula();
    					//Asigna a la película el id del usuario que está usando la app
    					pelicula.setUsuario(PantallaLoginController.currentUser);
    					
    					//Asigna a la película un id de película que no esté en uso
    					int idUsuario = Integer.parseInt("" + PantallaLoginController.currentUser.getId());
    					for (long i = 1; i <= 999999999; i++) {
    						Pelicula registroPeliculaVacio = gestorPelicula.searchById(i, idUsuario);
								if (registroPeliculaVacio == null) {
									pelicula.setId(i);
									session.clear();
									break;
								}
							}
    					
    					//Asigna a la película el título
    					pelicula.setTitulo(txtTitulo.getText());
    					
    					//Crea una nueva lista de generos donde se almacenará el genero elegido
    					GeneroDaoImpl obtenedorGenero = new GeneroDaoImpl(session);
    					GeneroPelicula generoPelicula = new GeneroPelicula(new GeneroPeliculaId(pelicula, obtenedorGenero.getGeneroByName(cmbGenero.getValue())));
    					List<GeneroPelicula> listaGeneros = new ArrayList<GeneroPelicula>();
    					listaGeneros.add(generoPelicula);
    					
    					//Asigna a la película el genero elegido
    					pelicula.setGeneroPelicula(listaGeneros);
    					
    					//Asigna a la película la descripción
    					pelicula.setOverview(txtDescripcion.getText());
    					
    					//Asigna a la película los actores separados por una coma
    					if (!txtActores.getText().isBlank()) {
    						String[] nombresActores = txtActores.getText().split(",");
    						ActorDaoImpl gestorActores = new ActorDaoImpl(session);
    						List<ActoresPeliculas> actores = new ArrayList<ActoresPeliculas>();
    						for (String nombreActor : nombresActores) {
    							ActoresPeliculas actor = gestorActores.getActorByName(nombreActor);
    							actores.add(actor);
    						}
    						pelicula.setActoresPeliculas(actores);								
							}
    					
    					//Asigna a la película los directores separados por una coma
    					if (!txtDirectores.getText().isBlank()) {								
    						String[] nombresDirectores = txtDirectores.getText().split(",");
    						DirectorDaoImpl gestorDirectores = new DirectorDaoImpl(session);
    						List<DirectoresPeliculas> directores = new ArrayList<DirectoresPeliculas>();
    						for (String nombreDirector : nombresDirectores) {
    							DirectoresPeliculas director = gestorDirectores.getDirectorByName(nombreDirector);
    							directores.add(director);
    						}
    						pelicula.setDirectoresPelicula(null);
							}
    					
    					//Asigna a la película la fecha de estreno 
    					@SuppressWarnings("deprecation")
    					Date fechaEstreno = new Date(dateFechaEstreno.getValue().getYear(), dateFechaEstreno.getValue().getMonthValue(), dateFechaEstreno.getValue().getDayOfMonth()); 
    					pelicula.setReleaseDate(fechaEstreno);
    					
    					//Asigna a la película la fecha de estreno 
    					@SuppressWarnings("deprecation")
    					Date fechaVisualizacion = new Date(dateFechaVisualizacion.getValue().getYear(), dateFechaVisualizacion.getValue().getMonthValue(), dateFechaVisualizacion.getValue().getDayOfMonth()); 
    					pelicula.setFechaVisualizacionUsuario(fechaVisualizacion);
    					
    					//Asigna a la película la localización
    					if (!txtLocalizacion.getText().isBlank()) {
    						Localizacion localizacion = new Localizacion();
    						localizacion.setNombre(txtLocalizacion.getText());
    						pelicula.setLocalizacion(localizacion);								
							}
    					
    					//Asigna a la película la valoracion
    					pelicula.setValoracion(spnValoracion.getValue());
    					pelicula.setValoracionUsuario(spnValoracion.getValue());
    					
    					//Asigna a la película los comentarios del usuario
    					pelicula.setComentariosUsuario(txtComentarios.getText());
    					
    					//Asigna a la película la URL de la imagen del poster
    					pelicula.setCartel(txtUrl.getText());
    					//TODO add overwrite pop-up
    					gestorPelicula.insert(pelicula);
    					
    					//Si todo va bien, se mostrará un mensaje de confirmación
    					lblPeliculaInsertada.setTextFill(Paint.valueOf("Green"));
    					lblPeliculaInsertada.setText("¡Pelicula dada de alta con éxito!");
    					
						} else {
							lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
		  				lblPeliculaInsertada.setText("La valoración debe ser solo un número entre 0 y 10.");
						}
    			} else {
    				lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
    				lblPeliculaInsertada.setText("Formato de URL invalido (solo .png y .jpg)");
    			} 
    		} else {
    			lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
  				lblPeliculaInsertada.setText("URL de imagen inválido.");
				}
			} else {
				lblPeliculaInsertada.setTextFill(Paint.valueOf("Red"));
				lblPeliculaInsertada.setText("Recuerde rellenar los campo obligatorios (marcados con un * rojo)");
			}
    }

    private boolean camposObligatoriosAreNull() {
			if (txtTitulo.getText().isBlank() || cmbGenero.getValue() == null || dateFechaEstreno.getValue() == null || dateFechaVisualizacion.getValue() == null || txtUrl.getText().isBlank()) {
				return true;
			} else {
				return false;				
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
