package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import dto.DetallesDTO;
import dto.PeliculaDTO;
import dto.PersonaCreditosDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import persistence.HibernateUtil;
import persistence.dao.ActorDaoImpl;
import persistence.dao.CompanyDaoImpl;
import persistence.dao.DirectorDaoImpl;
import persistence.dao.GeneroDaoImpl;
import persistence.dao.LocalizacionDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.entities.Actor;
import persistence.entities.ActoresPeliculas;
import persistence.entities.ActoresPeliculasId;
import persistence.entities.Company;
import persistence.entities.CompanyPelicula;
import persistence.entities.CompanyPeliculaId;
import persistence.entities.Director;
import persistence.entities.DirectoresPeliculas;
import persistence.entities.DirectoresPeliculasId;
import persistence.entities.GeneroPelicula;
import persistence.entities.GeneroPeliculaId;
import persistence.entities.Localizacion;
import persistence.entities.Pelicula;
import persistence.entities.User;
import utils.ControllerUtils;
import utils.NavegacionPantallas;
import utils.TMDBApi;
import utils.constants.Constantes;

/**
 * Controlador para la pantalla de alta de películas mediante la API.
 */
public class PantallaAltaAPIController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnAlta;

	@FXML
	private Button btnAnterior;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnConsultaManual;

	@FXML
	private Button btnSiguiente;

	@FXML
	private Pane cabecera;

	@FXML
	private ImageView imgPelicula;

	@FXML
	private Label lblCabecera;

	@FXML
	private Label lblResTitulo;

	@FXML
	private Label lblDescripcion;

	@FXML
	private Label lblDatosPelicula;

	@FXML
	private Label lblTituloIntroducir;

	@FXML
	private TextField txtTituloPelicula;

	@FXML
	private Spinner<Double> spinnerValoracionUsuario;

	@FXML
	private Pane panelPrincipal;

	@FXML
	private TextArea txtAreaComentarios;

	@FXML
	private TextField txtLocalizacion;

	@FXML
	private DatePicker dateFechaVisUsuario;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblError;

	@FXML
	private Label lblInfo;

	/** Posición de la película en la búsqueda */
	private int posicionPelicula;

	/** Imagen predeterminada cuando no hay imagen disponible */
	private static final Image EMPTY_IMAGE = new Image("/resources/found-icon-20.jpg");

	/**
	 * Inicializa el controlador después de que se haya cargado la raíz del archivo
	 * FXML. Configura la imagen predeterminada y realiza las asignaciones de los
	 * elementos de la interfaz.
	 */
	@FXML
	void initialize() {

		// Inicializar imagen por defecto
		imgPelicula.setImage(EMPTY_IMAGE);
		lblDescripcion.setWrapText(true);

		// Configurar el SpinnerValueFactory para permitir valores de 0 a 10 con
		// incrementos de 0.5
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 0.5);
		spinnerValoracionUsuario.setValueFactory(valueFactory);

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3);
		shadow.setColor(new Color(0, 0, 0, 0.35));

		ControllerUtils.setShadowLabels(shadow, lblCabecera, lblTituloIntroducir, lblDatosPelicula);
		ControllerUtils.setShadowTxtFields(shadow, txtTituloPelicula);
		ControllerUtils.setShadowButtons(shadow, btnAlta, btnAnterior, btnBuscar, btnSiguiente, btnConsultaManual);
		ControllerUtils.setShadowPanes(shadow, panelPrincipal);

		// Aplicar efectos de sombra a componentes

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Alta". Obtiene la película
	 * mediante la API y la inserta en la base de datos.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAltaPressed(MouseEvent event) {
		// Si el valor de la fecha de visualización (campo obligatorio) no es nulo
		if (dateFechaVisUsuario.getValue() != null) {
			// Inserta la pelicula
			insertPelicula();
		} else {
			showError("La fecha de visualización es obligatoria");
		}
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Volver". Vuelve al menú
	 * principal
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnVolverPressed(MouseEvent event) {
		NavegacionPantallas pantallaPrincipal = new NavegacionPantallas("Pantalla Principal",
				Constantes.PANTALLA_PRINCIPAL, Constantes.CSS_PANTALLA_PRINCIPAL);
		pantallaPrincipal.navegaAPantalla();

		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Inserta la película que aparece en la pantalla en la base de datos
	 */
	private void insertPelicula() {
		Session session = null;

		try {
			// Obtener la sesión de HibernateUtil
			session = HibernateUtil.getSession();

			// Obtener película por el título introducido
			String titulo = txtTituloPelicula.getText();
			PeliculaDTO peliDto = TMDBApi.getPeliculaByTitulo(titulo, posicionPelicula);

			if (peliDto != null) {

				// Obtener usuario realizador de la búsqueda
				User usuario = PantallaLoginController.currentUser;

				// DAOs de entidades
				GeneroDaoImpl generoDao = new GeneroDaoImpl(session);
				PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);
				CompanyDaoImpl compDao = new CompanyDaoImpl(session);
				ActorDaoImpl actorDao = new ActorDaoImpl(session);
				DirectorDaoImpl directorDao = new DirectorDaoImpl(session);
				LocalizacionDaoImpl locDao = new LocalizacionDaoImpl(session);

				// Entidad pelicula a insertar
				Pelicula pelicula = new Pelicula();

				// Patrón de formato para fechas
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat releaseDateFormat = new SimpleDateFormat(pattern);

				// Obtener la fecha de lanzamiento
				Date releaseDate = releaseDateFormat.parse(peliDto.getReleaseDate());
				// Obtener el año de lanzamiento
				SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
				int year = Integer.parseInt(yearDateFormat.format(releaseDate));

				// Obtener fecha de visualización
				LocalDate localDate = dateFechaVisUsuario.getValue();
				Date fechaVisualizacion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

				// Datos de la entidad pelicula a insertar
				setDatosPelicula(peliDto, usuario, pelicula, releaseDate, year, fechaVisualizacion);

				// Conseguir géneros para la pelicula
				List<GeneroPelicula> generosPelicula = setGenerosPelicula(peliDto, generoDao, pelicula);

				// Id en la API de la pelicula
				Long peliculaIdApi = peliDto.getId();
				// Conseguir las compañias para la pelicula
				List<CompanyPelicula> compsPelis = setCompaniesPelicula(compDao, pelicula, peliculaIdApi);

				// Conseguir actores para la pelicula
				List<ActoresPeliculas> actoresPelis = setActoresPelicula(peliDto, actorDao, pelicula, peliculaIdApi);

				// Conseguir directores para la pelicula
				List<DirectoresPeliculas> directoresPelis = setDirectoresPelicula(peliDto, directorDao, pelicula,
						peliculaIdApi);

				// Asignar localizacion a pelicula
				setLocalizacionPelicula(locDao, pelicula);
				// Asociar directores a la peícula
				pelicula.setDirectoresPelicula(directoresPelis);
				// Asociar actores a la película
				pelicula.setActoresPeliculas(actoresPelis);
				// Asociar géneros a la película
				pelicula.setGeneroPelicula(generosPelicula);
				// Asociar compañias a la pelicula
				pelicula.setCompPelicula(compsPelis);
				// Insertar película
				peliDao.insert(pelicula);

				// Mostrar mensaje de confirmación
				showInfo("Película dada de alta correctamente");
			} else {
				showError("No se pudo encontrar la película");
			}
			// Si hay error, quitarlo
			if (!lblError.getText().isBlank()) {
				lblError.setText("");
			}

		} catch (Exception e) {
			showError("Ocurrió un problema dando de alta la película");
			e.printStackTrace();
		} finally {
			// Cerrar la sesión al finalizar
			if (session != null) {
				HibernateUtil.closeSession();
			}
		}
	}

	/**
	 * Busca una localizacion en la base de datos, si existe se asigna a la
	 * película, si no se crea e inserta y se asigna a la película
	 * 
	 * @param locDao   DAO de la entidad Localizacion
	 * @param pelicula Pelicula a asignar la localizacion
	 */
	private void setLocalizacionPelicula(LocalizacionDaoImpl locDao, Pelicula pelicula) {
		// Obtener input de localización
		String inputLoc = txtLocalizacion.getText();
		Localizacion localizacionPeli = null;

		// Si se ha introducido algo, buscar localizacion
		if (inputLoc != null && !inputLoc.isBlank()) {

			localizacionPeli = locDao.getLocalizacionByNombre(inputLoc);

			if (localizacionPeli == null) {
				// Si no se encuentra, insertarla
				Localizacion nuevaLoc = new Localizacion();
				nuevaLoc.setNombre(inputLoc);
				pelicula.setLocalizacion(nuevaLoc);
				locDao.insert(nuevaLoc);

			} else {
				// Si ya está
				pelicula.setLocalizacion(localizacionPeli);
			}
		}
	}

	/**
	 * Inserta los directores de la película y los asocia a las tablas
	 * correspondientes
	 * 
	 * @param peliDto       DTO de peli con datos del JSON dado por la API
	 * @param directorDao   DAO del director
	 * @param pelicula      entidad pelicula a asignar los directores
	 * @param peliculaIdApi id de la pelicula dentro de la API
	 * @return Lista de la relación entre directores y películas
	 */
	private List<DirectoresPeliculas> setDirectoresPelicula(PeliculaDTO peliDto, DirectorDaoImpl directorDao,
			Pelicula pelicula, Long peliculaIdApi) {
		// Por cada director de la película dentro de los creditos, asociarla a la
		// película para la tabla directores_peliculas e insertarla en su propia tablas
		List<DirectoresPeliculas> directoresPelis = new ArrayList<>();
		List<PersonaCreditosDTO> directores = TMDBApi.getDirectoresByPeliculaId(peliculaIdApi);

		// Si hay directores
		if (directores != null && !directores.isEmpty()) {
			// Obtener actores de la pelicula, insertarlos
			for (PersonaCreditosDTO personaCreditosDTO : directores) {

				Director director = new Director();
				director.setNombre(personaCreditosDTO.getName());
				directorDao.insert(director);

				DirectoresPeliculas dp = new DirectoresPeliculas(new DirectoresPeliculasId(pelicula, director));
				directoresPelis.add(dp);

			}

		} else {
			System.out.println("----------No hay directores-------------" + "pelicula: " + peliDto.getTitulo());
		}
		return directoresPelis;
	}

	/**
	 * Inserta los actores de la película y los asocia a las tablas correspondientes
	 * 
	 * @param peliDto       DTO de peli con datos del JSON dado por la API
	 * @param actorDao      DAO del actor
	 * @param pelicula      entidad pelicula a asignar los directores
	 * @param peliculaIdApi id de la pelicula dentro de la API
	 * @return Lista de la relación entre actores y películas
	 */
	private List<ActoresPeliculas> setActoresPelicula(PeliculaDTO peliDto, ActorDaoImpl actorDao, Pelicula pelicula,
			Long peliculaIdApi) {
		// Por cada actor de la película dentro de los creditos, asociarla a la
		// película para la tabla actores_peliculas e insertarla en su propia tablas
		List<ActoresPeliculas> actoresPelis = new ArrayList<>();
		List<PersonaCreditosDTO> actores = TMDBApi.getActoresByPeliculaId(peliculaIdApi);

		// Si hay actores
		if (actores != null && !actores.isEmpty()) {
			// Obtener actores de la pelicula, insertarlos
			for (PersonaCreditosDTO personaCreditosDTO : actores) {

				// Conseguir actor
				Actor actor = new Actor();
				actor.setNombre(personaCreditosDTO.getName());

				// Insertarlo
				actorDao.insert(actor);

				// Relacion con pelicula
				ActoresPeliculas ap = new ActoresPeliculas(new ActoresPeliculasId(pelicula, actor));
				actoresPelis.add(ap);

			}
		} else {
			System.out.println("----------No hay actores-------------" + "pelicula: " + peliDto.getTitulo());
		}
		return actoresPelis;
	}

	/**
	 * Inserta las compañías de la película y las asocia a las tablas
	 * correspondientes
	 * 
	 * @param compDao       DAO de la compañia
	 * @param pelicula      entidad pelicula a asignar los directores
	 * @param peliculaIdApi id de la pelicula dentro de la API
	 * @return Lista de la relación entre compañia y películas
	 */
	private List<CompanyPelicula> setCompaniesPelicula(CompanyDaoImpl compDao, Pelicula pelicula, Long peliculaIdApi) {
		DetallesDTO detallesPeli = TMDBApi.getDetallesById(peliculaIdApi);

		List<Company> comps = detallesPeli.getProductionCompanies();
		List<CompanyPelicula> compsPelis = new ArrayList<>();

		// Por cada compañía de la película dentro de los detalles, asociarla a la
		// película para la tabla company_pelicula e insertarla en su propia tabla
		for (Company company : comps) {

			compDao.insert(company);

			CompanyPelicula compPeli = new CompanyPelicula(new CompanyPeliculaId(pelicula, company));
			compsPelis.add(compPeli);
		}
		return compsPelis;
	}

	/**
	 * Asocia los géneros ya existentes en la base de datos a las películas
	 * correspondientes
	 * 
	 * @param peliDto   DTO de la pelicula con datos del JSON obtenido por la API
	 * @param generoDao DAO de genero
	 * @param pelicula  pelicula a asociar los géneros
	 * @return Lista con la relación entre generos y peliculas
	 */
	private List<GeneroPelicula> setGenerosPelicula(PeliculaDTO peliDto, GeneroDaoImpl generoDao, Pelicula pelicula) {
		List<GeneroPelicula> generosPelicula = new ArrayList<>();

		// Obtener todos los géneros de la película
		for (int generoId : peliDto.getGenreIds()) {
			GeneroPelicula gp = new GeneroPelicula(
					new GeneroPeliculaId(pelicula, generoDao.getGeneroById(Long.valueOf(generoId))));
			generosPelicula.add(gp);
		}
		return generosPelicula;
	}

	/**
	 * Asocia datos no dependientes de otras entidades a la entidad película
	 * 
	 * @param peliDto            DTO de la pelicula con datos del JSON obtenido por
	 *                           la API
	 * @param usuario            Usuario actual
	 * @param pelicula           entidad pelicula
	 * @param releaseDate        fecha de lanzamiento de la pelicula
	 * @param year               año de lanzamiento de la pelicula
	 * @param fechaVisualizacion fecha en el que el usuario ha visto la pelicula
	 */
	private void setDatosPelicula(PeliculaDTO peliDto, User usuario, Pelicula pelicula, Date releaseDate, int year,
			Date fechaVisualizacion) {
		pelicula.setTitulo(peliDto.getTitulo());
		pelicula.setOverview(peliDto.getOverview());
		pelicula.setReleaseDate(releaseDate);
		pelicula.setCartel(peliDto.getImg());
		pelicula.setComentariosUsuario(txtAreaComentarios.getText());
		pelicula.setValoracionUsuario(spinnerValoracionUsuario.getValue());
		pelicula.setFechaVisualizacionUsuario(fechaVisualizacion);
		pelicula.setYear(year);
		pelicula.setUsuario(usuario);
		pelicula.setValoracion(peliDto.getVoteAverage());
	}

	/**
	 * Habilita elementos de input para insertar películas, antes de buscarla
	 */
	private void habilitaElementosInput() {

		spinnerValoracionUsuario.setDisable(false);
		txtAreaComentarios.setDisable(false);
		txtLocalizacion.setDisable(false);
		dateFechaVisUsuario.setDisable(false);
		btnAlta.setDisable(false);
		btnSiguiente.setDisable(false);
		btnAnterior.setDisable(false);

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Anterior". Muestra la película
	 * anterior en la búsqueda.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnAnteriorPressed(MouseEvent event) {

		if (posicionPelicula > 0) {

			// Habilitar botón de alta si está deshabilitado, mostrar película
			if (btnAlta.isDisabled()) {
				btnAlta.setDisable(false);
			}

			posicionPelicula--;
			setPelicula();

		} else {

			// Informar si no hay película, deshabilitar botón de alta
			lblResTitulo.setText("No hay película anterior");
			imgPelicula.setImage(EMPTY_IMAGE);
			lblDescripcion.setText("");
			btnAlta.setDisable(true);

		}
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Buscar". Inicia una nueva
	 * búsqueda de películas.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnBuscarPressed(MouseEvent event) {

		// Hablilita elementos para insertar en base de datos
		habilitaElementosInput();

		// Mostrar película
		posicionPelicula = 0;
		setPelicula();

	}

	/**
	 * Maneja el evento cuando se presiona el botón "Consulta Manual". Navega a la
	 * pantalla de consulta manual.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnConsultaManualPressed(MouseEvent event) {
		NavegacionPantallas navegacion = new NavegacionPantallas("Consulta Manual", Constantes.PANTALLA_ALTA_MANUAL,
				Constantes.CSS_ALTA_MANUAL);
		navegacion.navegaAPantalla();
		NavegacionPantallas.cerrarVentanaActual(event);
	}

	/**
	 * Maneja el evento cuando se presiona el botón "Siguiente". Muestra la película
	 * siguiente en la búsqueda si hay.
	 *
	 * @param event Evento del mouse.
	 */
	@FXML
	void btnSiguientePressed(MouseEvent event) {

		if (posicionPelicula >= TMDBApi.getResultsLength()) {

			// Informar si no hay película, deshabilitar botón de alta
			lblResTitulo.setText("No hay película siguiente.");
			lblDescripcion.setText("");
			imgPelicula.setImage(EMPTY_IMAGE);
			btnAlta.setDisable(true);

		} else {

			// Habilitar botón de alta si está deshabilitado, mostrar película
			if (btnAlta.isDisabled()) {
				btnAlta.setDisable(false);
			}

			posicionPelicula++;
			setPelicula();
		}
	}

	/**
	 * Obtiene una película de la API con el título introducido y la muestra en la
	 * interfaz.
	 */
	private void setPelicula() {
		// Consigo el título introducido
		String tituloPelicula = txtTituloPelicula.getText();
		// Obtengo película
		PeliculaDTO pelicula = TMDBApi.getPeliculaByTitulo(tituloPelicula, posicionPelicula);

		if (pelicula != null) {
			// Si hay error, quitarlo
			if (!lblError.getText().isBlank()) {
				lblError.setText("");
			}
			lblResTitulo.setText(pelicula.getTitulo() + " (" + pelicula.getReleaseDate() + ")");
			lblDescripcion.setText(pelicula.getOverview());
			InputStream stream = null;
			try {
				stream = new URL(pelicula.getImg()).openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image image = new Image(stream);

			// Mostrar la imagen en un ImageView
			imgPelicula.setImage(image);
		} else {
			showError("No se pudo encontrar la película");
		}
	}

	/**
	 * Muestra un mensaje de error
	 * 
	 * @param message
	 */
	private void showError(String message) {
		lblError.setText(message);
	}

	/**
	 * Muestra un mensaje de información
	 * 
	 * @param message
	 */
	private void showInfo(String message) {
		lblInfo.setText(message);
	}
}
