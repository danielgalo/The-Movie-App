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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.PeliculaDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

				// Obtener los ids de los géneros asociados a la película
				JsonArray genreIdsArray = result.getAsJsonArray("genre_ids");

				int[] genreIds = new int[genreIdsArray.size()];
				for (int i = 0; i < genreIdsArray.size(); i++) {
					genreIds[i] = genreIdsArray.get(i).getAsInt();
				}

				// Crear película con los resultados
				pelicula = new PeliculaDTO(title, overview, releaseDate, urlImagen, genreIds);

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
