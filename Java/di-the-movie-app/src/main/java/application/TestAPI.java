package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestAPI {
	
	private static final String API_KEY = "bd20833fe416765f4800bcff775ea22d";

	public static void main(String[] args) {
		try {
      // URL de la API de TMBD para obtener información de una película
      String url = "https://api.themoviedb.org/3/movie/550?api_key=" + API_KEY;

      // Crear una conexión HTTP
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");

      // Obtener la respuesta de la API
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = reader.readLine()) != null) {
          response.append(line);
      }
      reader.close();

      // Imprimir la respuesta
      System.out.println(response.toString());
		} catch (IOException e) {
      e.printStackTrace();
  	}
		
		
		
	}

}
