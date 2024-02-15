package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import application.TMAMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import persistence.HibernateUtil;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.Pelicula;
import resources.CeldaPelicula;
import utils.NavegacionPantallas;
import utils.constants.Constantes;

public class PantallaListaPeliculasController {
	
	@FXML
  private Pane mainPane;
	
	@FXML
	private TextField txtBuscarPelicula;

  @FXML
  private Button btnBuscar;

  @FXML
  private Button btnFiltrar;

  @FXML
  private Button btnVolver;

  @FXML
  private ImageView imgVector;
  
  @FXML
  private ImageView img;
  
  private boolean buscar = false;
  private String tituloBuscar = "";
  private List<Pelicula> listPeliculas;
  private List<Pane> listaCeldasPeliculas;
  protected static Pelicula peliculaActual;
  
  @FXML
  void initialize() {
  	final Session session = HibernateUtil.getSession();
  	
  	PeliculaDaoImpl buscadorPeliculas = new PeliculaDaoImpl(session);
  	if (buscar && !tituloBuscar.isBlank()) {
  		listPeliculas = buscadorPeliculas.searchMovieByTitle(tituloBuscar, Integer.parseInt("" + PantallaLoginController.currentUser.getId()));
		} else {
			listPeliculas = buscadorPeliculas.searchByUser(PantallaLoginController.currentUser);			
		}
  	
  	int posY = 220;
  	 listaCeldasPeliculas = new ArrayList<Pane>();
  	for (Pelicula pelicula : listPeliculas) {
  		final CeldaPelicula celdaPelicula = new CeldaPelicula(posY, pelicula);
  		
  		final Button btnBorrarPelicula = new Button();
  		btnBorrarPelicula.setId("btnBorrarPelicula");
  		btnBorrarPelicula.setLayoutX(1300);
  		btnBorrarPelicula.setLayoutY(15);
  		btnBorrarPelicula.setGraphic(new ImageView(new Image("./resources/btn-borrar.png", 35, 35, false, false)));
  		
  		btnBorrarPelicula.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DropShadow shadow = new DropShadow();
					shadow.setColor(new Color(0.4, 0.4, 0.4, 0.4645));
					shadow.setSpread(0.18);
					btnBorrarPelicula.setEffect(shadow);
				}
			});
  		
  		btnBorrarPelicula.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					btnBorrarPelicula.setEffect(null);
				}
			});
  		
  		btnBorrarPelicula.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					PeliculaDaoImpl borradorPeliculas = new PeliculaDaoImpl(session);
					borradorPeliculas.delete(celdaPelicula.getPelicula());
					mainPane.getChildren().removeAll(listaCeldasPeliculas);
					initialize();
				}
			});
  		
  		celdaPelicula.getLabelTituloPulsable().setOnMouseEntered(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				celdaPelicula.getLabelTituloPulsable().setUnderline(true);
  			}
			});
  		
  		celdaPelicula.getLabelTituloPulsable().setOnMouseExited(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				celdaPelicula.getLabelTituloPulsable().setUnderline(false);
  			}
			});
  		
  		celdaPelicula.getLabelTituloPulsable().setOnMousePressed(new EventHandler<MouseEvent>() {
  			@Override
  			public void handle(MouseEvent event) {
  				peliculaActual = celdaPelicula.getPelicula();
  				NavegacionPantallas navegacionPantallas = new NavegacionPantallas("Información pelicula", Constantes.PANTALLA_INFORMACION_PELICULA, Constantes.CSS_INFORMACION_PELICULA);
  				navegacionPantallas.navegaAPantalla();
  			}
			});
  		
  		celdaPelicula.getCeldaPelicula().getChildren().add(btnBorrarPelicula);
  		listaCeldasPeliculas.add(celdaPelicula.getCeldaPelicula());
  		posY += 260;
		}
  	mainPane.getChildren().addAll(listaCeldasPeliculas);
  	mainPane.setPrefHeight(posY + 50);
  	if (mainPane.getPrefHeight() < 1024) {
			mainPane.setPrefHeight(1024);
		}
  }

  @FXML
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
  void btnBuscarEntered(MouseEvent event) {
  	DropShadow shadow = new DropShadow();
		shadow.setColor(new Color(0.0, 0.0, 0.0, 1.0));
		shadow.setSpread(0.18);
		btnBuscar.setEffect(shadow);
  }
  
  @FXML
  void btnBuscarExited(MouseEvent event) {
  	btnBuscar.setEffect(null);
  }

  @FXML
  void btnVolverPressed(MouseEvent event) {
  	NavegacionPantallas navegacion = new NavegacionPantallas("Pantalla Principal", Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
  	navegacion.navegaAPantalla();
  	NavegacionPantallas.cerrarVentanaActual(event);
  }

}