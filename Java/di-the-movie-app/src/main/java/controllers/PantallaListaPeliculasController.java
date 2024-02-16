package controllers;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import persistence.HibernateUtil;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.Pelicula;
import resources.CeldaPelicula;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

/**
 * Pantalla donde se muestra las películas insertadas por el usuario, además permite buscarlas por título e incluso borrarlas.
 */
public class PantallaListaPeliculasController {
	
	@FXML
  private static Pane mainPane;
	
	@FXML
	private TextField txtBuscarPelicula;

  @FXML
  private Button btnBuscar;
  
  @FXML
  private Button btnVolver;

  @FXML
  private ImageView imgVector;
  
  @FXML
  private ImageView img;
  
  private static List<Pelicula> listPeliculas;
  private static List<Pane> listaCeldasPeliculas;
  protected static Pelicula peliculaActual;
  private static boolean buscar = false;
  protected static String tituloBuscar = "";
  
  @FXML
  static void initialize() {
  	final Session session = HibernateUtil.getSession();
  	//Si se ha buscado una película via la barra de busqueda
  	PeliculaDaoImpl buscadorPeliculas = new PeliculaDaoImpl(session);
  	if (buscar && !tituloBuscar.isBlank()) {
  		//Añade a la lista de películas solo las que incluyan los caracteres de la búsqueda
  		listPeliculas = buscadorPeliculas.searchMovieByTitle(tituloBuscar, Integer.parseInt("" + PantallaLoginController.currentUser.getId()));
  	//Si no se busca nada o la barra de busqueda está vacía
		} else {
			//Muestra todas las películas añadidas por el usuario
			listPeliculas = buscadorPeliculas.searchByUser(PantallaLoginController.currentUser);			
		}
  	/**
  	 * Nota: Esta variable determina la posición vertical de la primera celda de peliculas a mostrar,
  	 * de aquí en adelante esta variable se aumenta para la creación del resto de celdas.
  	 */
  	int posY = 220;
  	listaCeldasPeliculas = new ArrayList<Pane>();
  	//Por cada pelicula del usuario, ya sea por nombre o en general
  	for (Pelicula pelicula : listPeliculas) {
  		//Crea una nueva instancia de celda de película
  		final CeldaPelicula celdaPelicula = new CeldaPelicula(posY, pelicula);
  		//Crea un nuevo botón
  		final Button btnBorrarPelicula = new Button();
  		//Asignale atributos como su id de CSS, un icono y posición en la celda
  		btnBorrarPelicula.setId("btnBorrarPelicula");
  		btnBorrarPelicula.setLayoutX(1300);
  		btnBorrarPelicula.setLayoutY(15);
  		btnBorrarPelicula.setGraphic(new ImageView(new Image("./resources/btn-borrar.png", 35, 35, false, false)));
  		
  		/**
  		 * Añade una sombra al botón cuando el ratón entra a el
  		 */
  		btnBorrarPelicula.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DropShadow shadow = new DropShadow();
					shadow.setColor(new Color(0.4, 0.4, 0.4, 0.4645));
					shadow.setSpread(0.18);
					btnBorrarPelicula.setEffect(shadow);
				}
			});
  		
  		/**
  		 * Le quita la sombra al botón cuando el ratón sale de el
  		 */
  		btnBorrarPelicula.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					btnBorrarPelicula.setEffect(null);
				}
			});
  		
  		/**
  		 * Borra la película mostrada en la celda donde está el botón pulsado
  		 */
  		btnBorrarPelicula.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					PeliculaDaoImpl borradorPeliculas = new PeliculaDaoImpl(session);
					borradorPeliculas.delete(celdaPelicula.getPelicula());
					mainPane.getChildren().removeAll(listaCeldasPeliculas);
					initialize();
				}
			});
  		
  		/**
  		 * Subraya el título de la película de la celda al pasar el ratón por encima
  		 *
  		 * Nota: Con esto muestra que es interactivo con el ratón
  		 */
  		celdaPelicula.getLabelTituloPulsable().setOnMouseEntered(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				celdaPelicula.getLabelTituloPulsable().setUnderline(true);
  			}
			});
  		
  		/**
  		 * Quita el subrayado del título de la película de la celda
  		 */
  		celdaPelicula.getLabelTituloPulsable().setOnMouseExited(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				celdaPelicula.getLabelTituloPulsable().setUnderline(false);
  			}
			});
  		
  		/**
  		 * Al pulsar en el título de la película, se muestra una ventana con datos extendidos de la película
  		 * 
  		 * Nota: Es como una extensión de las celdas
  		 */
  		celdaPelicula.getLabelTituloPulsable().setOnMousePressed(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				peliculaActual = celdaPelicula.getPelicula();
  				NavegacionPantallas navegacionPantallas = new NavegacionPantallas("Información pelicula", Constantes.PANTALLA_INFORMACION_PELICULA, Constantes.CSS_INFORMACION_PELICULA);
  				navegacionPantallas.navegaAPantalla();
  			}
			});
  		
  		//Añade el botón de borrar película a la celda
  		celdaPelicula.getCeldaPelicula().getChildren().add(btnBorrarPelicula);
  		//Añade la celda a la lista de celdas
  		listaCeldasPeliculas.add(celdaPelicula.getCeldaPelicula());
  		/**
  		 * Aumenta el valor de la posición vertical
  		 * 
  		 * Nota: Ver líneas 63-66
  		 */
  		posY += 260;
		}
  	//Añade todas las celdas de la lista de celdas a la pantalla de lista de películas
  	mainPane.getChildren().addAll(listaCeldasPeliculas);
  	//Ajusta la altura de la pantalla de lista de películas según la cantidad de celdas visibles
  	mainPane.setPrefHeight(posY + 50);
  	/**
  	 * Nota: Para evitar que la pantalla de lista sea muy pequeña en caso haya muy pocas celdas,
  	 * si su altura es menor que la predeterminada, vuelve a tener ese valor
  	 */
  	if (mainPane.getPrefHeight() < 1024) {
			mainPane.setPrefHeight(1024);
		}
  	
  }

  @FXML
  /**
   * Busca películas por los caracteres en la barra de busqueda
   * @param event
   */
  void btnBuscarPressed(MouseEvent event) {
  	if (!txtBuscarPelicula.getText().isBlank()) {
			tituloBuscar = txtBuscarPelicula.getText();
			buscar = true;
			mainPane.getChildren().removeAll(listaCeldasPeliculas);
			initialize();
		} else {
			tituloBuscar = "";
			buscar = false;
			mainPane.getChildren().removeAll(listaCeldasPeliculas);
			initialize();
		}
  }

  @FXML
  /**
   * Añade una sombra al botón
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
   * Quita la sombra del botón
   * @param event
   */
  void btnVolverExited(MouseEvent event) {
  	btnVolver.setEffect(null);
  }
  
  @FXML
  /**
   * Añade una sombra al botón
   * @param event
   */
  void btnBuscarEntered(MouseEvent event) {
  	DropShadow shadow = new DropShadow();
		shadow.setColor(new Color(0.0, 0.0, 0.0, 1.0));
		shadow.setSpread(0.18);
		btnBuscar.setEffect(shadow);
  }
  
  @FXML
  /**
   * Quita la sombra del botón
   * @param event
   */
  void btnBuscarExited(MouseEvent event) {
  	btnBuscar.setEffect(null);
  }

  @FXML
  /**
   * Cierra la ventana de la lista de películas y vuelve a la pantalla principal
   * @param event
   */
  void btnVolverPressed(MouseEvent event) {
  	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
  	navegacion.navegaAPantalla();
  	NavegacionPantallas.cerrarVentanaActual(event);
  }

}
