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
import persistence.dao.LocalizacionDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.Actor;
import persistence.entities.ActoresPeliculas;
import persistence.entities.ActoresPeliculasId;
import persistence.entities.Director;
import persistence.entities.DirectoresPeliculas;
import persistence.entities.DirectoresPeliculasId;
import persistence.entities.GeneroPelicula;
import persistence.entities.GeneroPeliculaId;
import persistence.entities.Localizacion;
import persistence.entities.Pelicula;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Pantalla para la introducción de una película y sus datos en la base de datos con un formulario.
 */
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
  private Label lblResultadoAlta;

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
    
  protected static String[] generos = {"Acción", "Animación", "Aventura", "Bélica", "Ciencia ficción", "Comedia", "Crimen", "Documental", "Drama", "Familia", "Fantasía", "Historia", "Misterio", "Música", "Película de TV", "Romance", "Suspense", "Terror", "Western"};
  
    @FXML
    void initialize() {
    	//Añade todos los generos seleccionables al comboBox de generos 
    	cmbGenero.getItems().addAll(generos);
    	//Asigna los valores y límites del campo de valoración
    	SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 0.5);
  		spnValoracion.setValueFactory(valueFactory);
    }

    @FXML
    /**
     * Crea una película nueva en la base de datos con los datos provistos en el formulario
     * @param event
     */
    void btnAltaPressed(MouseEvent event) {
    	//Limpia la etiqueta del resultado del alta
    	lblResultadoAlta.setText("");
    	
    	//Si los campos obligatorios están rellenos
    	if (!camposObligatoriosAreNull()) {
    		//Comprueba que la URL lleva a una imagen usable
    		Image image = null;
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
    				//Y si la valoración es un número entre 0 y 10
    				if (spnValoracion.getValue() <= 10 && spnValoracion.getValue() >= 0) {
    					//Inicia una nueva sesión con la tabla peliculas
    					Session session = HibernateUtil.getSession();
    					PeliculaDaoImpl gestorPelicula = new PeliculaDaoImpl(session);
    					//Crea una nueva instancia pelicula
    					Pelicula pelicula = new Pelicula();
    					//Asigna a la película el usuario_id del usuario que está usando la app
    					pelicula.setUsuario(PantallaLoginController.currentUser);
    					
    					//Asigna a la película el título
    					pelicula.setTitulo(txtTitulo.getText());
    					
    					//Obten el genero de la base de datos según el nombre del comboBox
    					GeneroDaoImpl obtenedorGenero = new GeneroDaoImpl(session);
    					GeneroPelicula generoPelicula = new GeneroPelicula(new GeneroPeliculaId(pelicula, obtenedorGenero.getGeneroByName(cmbGenero.getValue())));
    					//Crea una nueva lista para el genero
    					List<GeneroPelicula> listaGeneros = new ArrayList<GeneroPelicula>();
    					//Añade el genero a la lista
    					listaGeneros.add(generoPelicula);
    					
    					//Asigna a la película el genero elegido
    					pelicula.setGeneroPelicula(listaGeneros);
    					
    					//Asigna a la película la descripción
    					pelicula.setOverview(txtDescripcion.getText());
    					
    					//Si el campo de actores no está vacío
    					if (!txtActores.getText().isBlank()) {
    						//Divide los nombres y guardalos en un array
    						String[] nombresActores = txtActores.getText().split(",");
    						//Crea la lista que se asignará a la película
    						ActorDaoImpl gestorActores = new ActorDaoImpl(session);
    						List<ActoresPeliculas> actores = new ArrayList<ActoresPeliculas>();
    						//Por cada nombre de actor en el array
    						for (String nombreActor : nombresActores) {
    							//Crea un nuevo actor
    							Actor actor = new Actor();
    							//Asignale un nombre
    							actor.setNombre(nombreActor);
    							//Añadelo a la base de datos
    							gestorActores.insert(actor);
    							//Y añadelo a la lista de actores
    							ActoresPeliculasId actorId = new ActoresPeliculasId(pelicula, actor);
    							ActoresPeliculas actorPeliculas = new ActoresPeliculas(actorId);
    							actores.add(actorPeliculas);
    						}
    						//Asigna a la película los actores
    						pelicula.setActoresPeliculas(actores);								
							}
    					
    					//Si el campo de directores no está vacío
    					if (!txtDirectores.getText().isBlank()) {		
    						//Divide los nombres y guardalos en un array
    						String[] nombresDirectores = txtDirectores.getText().split(",");
    						//Crea la lista que se asignará a la película
    						DirectorDaoImpl gestorDirectores = new DirectorDaoImpl(session);
    						List<DirectoresPeliculas> directores = new ArrayList<DirectoresPeliculas>();
    						//Por cada nombre de director en el array
    						for (String nombreDirector : nombresDirectores) {
    							//Crea un nuevo director
    							Director director = new Director();
    							//Asignale un nombre
    							director.setNombre(nombreDirector);
    							//Añadelo a la base de datos
    							gestorDirectores.insert(director);
    							//Y añadelo a la lista de directores
    							DirectoresPeliculasId directorId = new DirectoresPeliculasId(pelicula, director);
    							DirectoresPeliculas directorPeliculas = new DirectoresPeliculas(directorId);
    							directores.add(directorPeliculas);
    						}
    						//Asigna a la película los directores
    						pelicula.setDirectoresPelicula(directores);
							}
    					
    					//Crea un nuevo objeto Date con los datos del campo de la fecha de estreno
    					@SuppressWarnings("deprecation")
    					Date fechaEstreno = new Date(dateFechaEstreno.getValue().getYear(), dateFechaEstreno.getValue().getMonthValue(), dateFechaEstreno.getValue().getDayOfMonth()); 
    					//Asigna a la película la fecha de estreno
    					pelicula.setReleaseDate(fechaEstreno);
    					
    					//Crea un nuevo objeto Date con los datos del campo de la fecha de visualización
    					@SuppressWarnings("deprecation")
    					Date fechaVisualizacion = new Date(dateFechaVisualizacion.getValue().getYear(), dateFechaVisualizacion.getValue().getMonthValue(), dateFechaVisualizacion.getValue().getDayOfMonth()); 
    					//Asigna a la película la fecha de visualización 
    					pelicula.setFechaVisualizacionUsuario(fechaVisualizacion);
    					
    					//Si el campo localización no está vacío
    					if (!txtLocalizacion.getText().isBlank()) {
    						//Crea un nuevo objeto localizacion
    						LocalizacionDaoImpl gestorLocalizacion = new LocalizacionDaoImpl(session);
    						Localizacion localizacion = new Localizacion();
    						//Asignale como nombre el texto del campo
    						localizacion.setNombre(txtLocalizacion.getText());
    						//Insertalo en la base de datos
    						gestorLocalizacion.insert(localizacion);
    						//Y asignaselo a la película
    						pelicula.setLocalizacion(localizacion);								
							}
    					
    					//Asigna a la película la valoracion como valoración general y del usuario
    					pelicula.setValoracion(spnValoracion.getValue());
    					pelicula.setValoracionUsuario(spnValoracion.getValue());
    					
    					//Asigna a la película los comentarios del usuario
    					pelicula.setComentariosUsuario(txtComentarios.getText());
    					
    					//Asigna a la película la URL de la imagen del poster
    					pelicula.setCartel(txtUrl.getText());
    					
    					//Inserta la película en la base de datos
    					gestorPelicula.insert(pelicula);
    					    					
    					//Si todo va bien, muestra un mensaje de confirmación
    					lblResultadoAlta.setTextFill(Paint.valueOf("Green"));
    					lblResultadoAlta.setText("¡Pelicula dada de alta con éxito!");
    				//Si la valoración no es un número entre 0 y 10
						} else {
							//Muestra un mensaje de error
							lblResultadoAlta.setTextFill(Paint.valueOf("Red"));
		  				lblResultadoAlta.setText("La valoración debe ser solo un número entre 0 y 10.");
						}
    			//Si la imagen no es aceptada
    			} else {
    				//Muestra un mensaje diciendo que el formato es invalido
    				lblResultadoAlta.setTextFill(Paint.valueOf("Red"));
    				lblResultadoAlta.setText("Formato de URL invalido (solo .png y .jpg)");
    			}
    		//Si el URL no lleva a una imagen
    		} else {
    			//Muestra un mensaje de error
    			lblResultadoAlta.setTextFill(Paint.valueOf("Red"));
  				lblResultadoAlta.setText("URL de imagen inválido.");
				}
    	//Si los campos obligatorios no están rellenos
			} else {
				//Muestra un mensaje de error
				lblResultadoAlta.setTextFill(Paint.valueOf("Red"));
				lblResultadoAlta.setText("Recuerde rellenar los campo obligatorios (marcados con un * rojo)");
			}
    }
    
    /**
     * Se asegura de que todos los campos obligatorios tienen datos válidos insertados
     * @return
     */
    private boolean camposObligatoriosAreNull() {
			if (txtTitulo.getText().isBlank() || cmbGenero.getValue() == null || dateFechaEstreno.getValue() == null || dateFechaVisualizacion.getValue() == null || txtUrl.getText().isBlank()) {
				return true;
			} else {
				return false;				
			}
		}
    
		@FXML
		/**
		 * Cierra esta ventana y vuelve a la pantalla principal de la app.
		 * @param event
		 */
    void btnVolverPressed(MouseEvent event) {
    	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
    	navegacion.navegaAPantalla();
    	NavegacionPantallas.cerrarVentanaActual(event);
    }
    
    @FXML
    /**
     * Da un efecto de sombra al botón
     * @param event
     */
    void btnVolverEntered(MouseEvent event) {
    	DropShadow shadow = new DropShadow();
  		shadow.setColor(new Color(0.0, 0.95, 1.0, 1.0));
  		shadow.setSpread(0.18);
  		btnVolver.setEffect(shadow);
    }
    
    @FXML
    /**
     * Quitá el efecto de sombra al botón
     * @param event
     */
    void btnVolverExited(MouseEvent event) {
    	btnVolver.setEffect(null);
    }
    
    @FXML
    /**
     * Da un efecto de sombra al botón
     * @param event
     */
    void btnAltaEntered(MouseEvent event) {
    	DropShadow shadow = new DropShadow();
  		shadow.setColor(new Color(0.3421, 0.3421, 0.3421, 1.0));
  		shadow.setSpread(0.18);
  		btnAlta.setEffect(shadow);
    }
    
    @FXML
    /**
     * Quitá el efecto de sombra al botón
     * @param event
     */
    void btnAltaExited(MouseEvent event) {
    	btnAlta.setEffect(null);
    }

}
