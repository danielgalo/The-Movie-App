package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.PeliculaDTO;

/**
 * Clase usada para recibir datos de la API de The Movie Database
 */
public class TMDBApi {

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
	public static PeliculaDTO getPelicula(String movieTitle, int posicionPelicula) {
		PeliculaDTO pelicula = null;

		try {
			String encodedTitle = URLEncoder.encode(movieTitle, "UTF-8");
			URL url = new URL(
					"https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + encodedTitle);

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

			// Utilizar GSON para parsear el JSON
			JsonObject searchResult = JsonParser.parseString(response.toString()).getAsJsonObject();
			JsonArray results = searchResult.getAsJsonArray("results");

			if (!results.isEmpty()) {
				JsonObject result = results.get(posicionPelicula).getAsJsonObject();

				String title = result.get("original_title").getAsString();
				String overview = result.get("overview").getAsString();
				String releaseDate = result.get("release_date").getAsString();
				String posterPath = result.get("poster_path").getAsString();
				String urlImagen = "https://image.tmdb.org/t/p/w500" + posterPath;

				pelicula = new PeliculaDTO(title, overview, releaseDate, urlImagen);

			} else {
				System.out.println("No se encontraron resultados para la búsqueda.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pelicula;
	}

	public static int getResultsLength() {
		return results.size();
	}

}
