package controllers;

import java.util.List;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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
  
  @FXML
  void initialize() {
  	Session session = HibernateUtil.getSession();
  	
  	PeliculaDaoImpl buscadorPeliculas = new PeliculaDaoImpl(session);
  	List<Pelicula> listPeliculas = buscadorPeliculas.searchByUser(PantallaLoginController.currentUser);
  	
  	int posY = 220;
  	for (Pelicula pelicula : listPeliculas) {
  		CeldaPelicula n = new CeldaPelicula(25, posY, pelicula);
  		mainPane.getChildren().add(n.getCeldaPelicula());
  		posY += 260;
		}
  	mainPane.setPrefHeight(posY + 50);
  	if (mainPane.getPrefHeight() < 1024) {
			mainPane.setPrefHeight(1024);
		}
  }

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
