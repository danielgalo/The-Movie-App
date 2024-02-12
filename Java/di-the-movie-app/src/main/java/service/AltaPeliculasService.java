package service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import controllers.PantallaLoginController;
import persistence.HibernateUtil;
import persistence.dao.ActorDaoImpl;
import persistence.dao.CompanyDaoImpl;
import persistence.dao.DirectorDaoImpl;
import persistence.dao.GeneroDaoImpl;
import persistence.dao.LocalizacionDaoImpl;
import persistence.dao.PeliculaDaoImpl;
import persistence.dto.DetallesDTO;
import persistence.dto.PeliculaDTO;
import persistence.dto.PersonaCreditosDTO;
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
import utils.TMDBApi;

/**
 * Clase servicio para dar de alta películas
 */
public class AltaPeliculasService {

	/**
	 * Constructor privado
	 */
	private AltaPeliculasService() {

	}

	/**
	 * Inserta una película en la base de datos con todas sus relaciones, buscandola
	 * antes en la API de The Movie DataBase.
	 * 
	 * @param titulo             título de la película a buscar en la API
	 * @param posicionPelicula   posición de la película en los resultados de la
	 *                           búsqueda
	 * @param localDate          fecha de visualización de la película
	 * @param comentariosUsuario comentarios del usuario sobre la película
	 * @param valoracionUsuario  valoración del usuario de la película
	 * @param input              nombre de la localización de la película
	 * @return {@code 0} si se pudo dar de alta la película o {@code -1} si hubo
	 *         algún problema
	 */
	public static int insertPelicula(String titulo, int posicionPelicula, LocalDate localDate,
			String comentariosUsuario, double valoracionUsuario, String inputLoc) {
		Session session = null;

		try {
			// Obtener la sesión de HibernateUtil
			session = HibernateUtil.getSession();

			// Busca pelicula en API
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

				int usuarioId = Integer.parseInt(usuario.getId().toString());

				// Comprobar que no esté ya la película en la base de datos
				Pelicula peliculaEncontrada = peliDao.searchById(peliDto.getId(), usuarioId);

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

				Date fechaVisualizacion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

				// Datos de la entidad pelicula a insertar
				setDatosPelicula(peliDto, usuario, pelicula, releaseDate, year, fechaVisualizacion, comentariosUsuario,
						valoracionUsuario);

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
				setLocalizacionPelicula(locDao, pelicula, inputLoc);
				// Asociar directores a la peícula
				pelicula.setDirectoresPelicula(directoresPelis);
				// Asociar actores a la película
				pelicula.setActoresPeliculas(actoresPelis);
				// Asociar géneros a la película
				pelicula.setGeneroPelicula(generosPelicula);
				// Asociar compañias a la pelicula
				pelicula.setCompPelicula(compsPelis);

				if (peliculaEncontrada != null) {
					// Actualizar película
					pelicula = session.merge(pelicula);
				} else {
					// Insertar película
					peliDao.insert(pelicula);
				}

				return 0;
			} else {
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			// Cerrar la sesión al finalizar
			if (session != null) {
				HibernateUtil.closeSession();
			}
		}
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
	private static void setDatosPelicula(PeliculaDTO peliDto, User usuario, Pelicula pelicula, Date releaseDate,
			int year, Date fechaVisualizacion, String comentariosUsuario, double valoracionUsuario) {
		pelicula.setId(peliDto.getId());
		pelicula.setTitulo(peliDto.getTitulo());
		pelicula.setOverview(peliDto.getOverview());
		pelicula.setReleaseDate(releaseDate);
		pelicula.setCartel(peliDto.getImg());
		pelicula.setComentariosUsuario(comentariosUsuario);
		pelicula.setValoracionUsuario(valoracionUsuario);
		pelicula.setFechaVisualizacionUsuario(fechaVisualizacion);
		pelicula.setYear(year);
		pelicula.setUsuario(usuario);
		pelicula.setValoracion(peliDto.getVoteAverage());
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
	private static List<GeneroPelicula> setGenerosPelicula(PeliculaDTO peliDto, GeneroDaoImpl generoDao,
			Pelicula pelicula) {
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
	 * Inserta las compañías de la película y las asocia a las tablas
	 * correspondientes
	 * 
	 * @param compDao       DAO de la compañia
	 * @param pelicula      entidad pelicula a asignar los directores
	 * @param peliculaIdApi id de la pelicula dentro de la API
	 * @return Lista de la relación entre compañia y películas
	 */
	private static List<CompanyPelicula> setCompaniesPelicula(CompanyDaoImpl compDao, Pelicula pelicula,
			Long peliculaIdApi) {
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
	 * Inserta los actores de la película y los asocia a las tablas correspondientes
	 * 
	 * @param peliDto       DTO de peli con datos del JSON dado por la API
	 * @param actorDao      DAO del actor
	 * @param pelicula      entidad pelicula a asignar los directores
	 * @param peliculaIdApi id de la pelicula dentro de la API
	 * @return Lista de la relación entre actores y películas
	 */
	private static List<ActoresPeliculas> setActoresPelicula(PeliculaDTO peliDto, ActorDaoImpl actorDao,
			Pelicula pelicula, Long peliculaIdApi) {
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
			System.out.println("----------No hay actores------------- pelicula: " + peliDto.getTitulo());
		}
		return actoresPelis;
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
	private static List<DirectoresPeliculas> setDirectoresPelicula(PeliculaDTO peliDto, DirectorDaoImpl directorDao,
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
			System.out.println("----------No hay directores------------- pelicula: " + peliDto.getTitulo());
		}
		return directoresPelis;
	}

	/**
	 * Busca una localizacion en la base de datos, si existe se asigna a la
	 * película, si no se crea e inserta y se asigna a la película
	 * 
	 * @param locDao   DAO de la entidad Localizacion
	 * @param pelicula Pelicula a asignar la localizacion
	 */
	private static void setLocalizacionPelicula(LocalizacionDaoImpl locDao, Pelicula pelicula, String inputLoc) {
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
}
