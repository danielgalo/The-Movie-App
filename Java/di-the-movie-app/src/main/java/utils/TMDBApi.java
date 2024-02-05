package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dto.DetallesDTO;
import dto.PeliculaDTO;
import dto.PersonaCreditosDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import persistence.entities.Company;
import persistence.entities.Genero;

/**
 * Clase usada para recibir datos de la API de The Movie Database
 */
public class TMDBApi {

	/** Clave API */
	private static final String API_KEY = "1e5c1817e84de2c0de8d34bc91651a00";

	/**
	 * Constructor privado
	 */
	private TMDBApi() {

	}

	/** Resultados de búsqueda */
	static JsonArray results;

	/**
	 * Busca películas por título
	 * 
	 * @param movieTitle       título de la película
	 * @param posicionPelicula posición de la película a devolver dentro de los
	 *                         resultados
	 * @return Objeto Pelicula con la posición dada
	 */
	public static PeliculaDTO getPeliculaByTitulo(String movieTitle, int posicionPelicula) {
		PeliculaDTO pelicula = null;

		try {

			// Codificar el título para incluirlo en la URL de la solicitud
			String encodedTitle = URLEncoder.encode(movieTitle, "UTF-8");

			URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + encodedTitle
					+ "&language=es");

			// Realizar petición
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();
			connection.disconnect();

			// Utilizar GSON para parsear el JSON obtenido de la petición
			JsonObject searchResult = JsonParser.parseString(response.toString()).getAsJsonObject();
			results = searchResult.getAsJsonArray("results");

			if (results.size() > 0) {

				// Tomar el resultado de la posición aportada
				JsonObject result = results.get(posicionPelicula).getAsJsonObject();

				// Obtener datos
				String title = result.get("original_title").getAsString();
				String overview = result.get("overview").getAsString();
				String releaseDate = result.get("release_date").getAsString();
				String posterPath = result.get("poster_path").getAsString();
				String urlImagen = "https://image.tmdb.org/t/p/w500" + posterPath;
				Long id = result.get("id").getAsLong();
				double voteAverage = result.get("vote_average").getAsDouble();

				// Obtener los ids de los géneros asociados a la película
				JsonArray genreIdsArray = result.getAsJsonArray("genre_ids");

				int[] genreIds = new int[genreIdsArray.size()];
				for (int i = 0; i < genreIdsArray.size(); i++) {
					genreIds[i] = genreIdsArray.get(i).getAsInt();
				}

				// Crear película con los resultados
				pelicula = new PeliculaDTO(title, overview, releaseDate, urlImagen, genreIds, id, voteAverage);

				for (int i : genreIds) {
					System.out.println(i);
				}

			} else {
				System.out.println("No se encontraron resultados para la búsqueda.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pelicula;
	}

	/**
	 * Devuelve los detalles de una película por su id
	 * 
	 * @param id id de la película a obtener los detalles
	 * @return Objeto detalles con los detalles de la película
	 */
	public static DetallesDTO getDetallesById(Long id) {

		DetallesDTO detalles = null;

		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/movie/" + id.toString() + "?language=en-US").get()
					.addHeader("accept", "application/json")
					.addHeader("Authorization",
							"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZTVjMTgxN2U4NGRlMmMwZGU4ZDM0YmM5MTY1MWEwMCIsInN1YiI6IjY1NmVlZDEzNjUxN2Q2MDBjYzQzMWQyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ISS3oHobccfNtISmXqBs_dPb3jOm0s9LDQdbN7t1fEQ")
					.build();

			Response response = client.newCall(request).execute();

			String responseBody = response.body().string();
			JsonObject result = JsonParser.parseString(responseBody).getAsJsonObject();

			detalles = new DetallesDTO();
			detalles.setAdult(result.get("adult").getAsBoolean());
			detalles.setBudget(result.get("budget").getAsInt());
			detalles.setId(result.get("id").getAsInt());

			// Parse production_companies array
			JsonArray productionCompaniesArray = result.getAsJsonArray("production_companies");
			List<Company> productionCompaniesList = new ArrayList<>();

			for (JsonElement productionCompanyElement : productionCompaniesArray) {
				JsonObject productionCompanyObject = productionCompanyElement.getAsJsonObject();
				Company productionCompany = new Company();
				productionCompany.setName(productionCompanyObject.get("name").getAsString());
				productionCompaniesList.add(productionCompany);
			}

			detalles.setProductionCompanies(productionCompaniesList);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return detalles;
	}

	/**
	 * Consigue los actores de una película
	 * 
	 * @param id id de la pelicula
	 * @return lista con los actores de los créditos de la película
	 */
	public static List<PersonaCreditosDTO> getActoresByPeliculaId(Long id) {

		return getCreditsById(id, "Acting");
	}

	/**
	 * Consigue los directores de una película
	 * 
	 * @param id id de la pelicula
	 * @return lista con los actores de los créditos de la película
	 */
	public static List<PersonaCreditosDTO> getDirectoresByPeliculaId(Long id) {

		return getCreditsById(id, "Directing");
	}

	/**
	 * Consigue los creditos de una pelicula por su id
	 * 
	 * @param id
	 * @return
	 */
	private static List<PersonaCreditosDTO> getCreditsById(Long id, String role) {
		List<PersonaCreditosDTO> personas = new ArrayList<>();

		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/movie/" + id.toString() + "/credits?language=en-US").get()
					.addHeader("accept", "application/json")
					.addHeader("Authorization",
							"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZTVjMTgxN2U4NGRlMmMwZGU4ZDM0YmM5MTY1MWEwMCIsInN1YiI6IjY1NmVlZDEzNjUxN2Q2MDBjYzQzMWQyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ISS3oHobccfNtISmXqBs_dPb3jOm0s9LDQdbN7t1fEQ")
					.build();
			Response response = client.newCall(request).execute();

			String responseBody = response.body().string();
			JsonObject searchResult = JsonParser.parseString(responseBody).getAsJsonObject();
			results = searchResult.getAsJsonArray("cast");

			if (results != null && !results.isEmpty()) {

				for (int i = 0; i < results.size(); i++) {

					JsonObject result = results.get(i).getAsJsonObject();
					String name = result.get("name").getAsString();
					String knownForDepartment = result.get("known_for_department").getAsString();

					// Si son actores
					if (knownForDepartment.equals(role)) {
						PersonaCreditosDTO pers = new PersonaCreditosDTO(name, knownForDepartment);
						personas.add(pers);
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return personas;

	}

	/**
	 * Devuelve una lista con todos los géneros
	 */
	public static List<Genero> getAllGenres() {

		List<Genero> generos = new ArrayList<>();

		try {

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url("https://api.themoviedb.org/3/genre/movie/list?language=es")
					.get().addHeader("accept", "application/json")
					.addHeader("Authorization",
							"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZTVjMTgxN2U4NGRlMmMwZGU4ZDM0YmM5MTY1MWEwMCIsInN1YiI6IjY1NmVlZDEzNjUxN2Q2MDBjYzQzMWQyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ISS3oHobccfNtISmXqBs_dPb3jOm0s9LDQdbN7t1fEQ")
					.build();

			Response response = client.newCall(request).execute();

			String responseBody = response.body().string();
			JsonObject searchResult = JsonParser.parseString(responseBody).getAsJsonObject();
			results = searchResult.getAsJsonArray("genres");

			if (!results.isEmpty()) {

				for (int i = 0; i < results.size(); i++) {
					JsonObject result = results.get(i).getAsJsonObject();

					int id = result.get("id").getAsInt();
					String name = result.get("name").getAsString();

					Genero genero = new Genero();
					genero.setId(Long.valueOf(id));
					genero.setNombre(name);
					generos.add(genero);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return generos;

	}

	public static int getResultsLength() {
		return results.size();
	}

}
