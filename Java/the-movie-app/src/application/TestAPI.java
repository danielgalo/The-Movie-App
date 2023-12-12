package application;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestAPI {

	public static void main(String[] args) {
		try {
			
			OkHttpClient client = new OkHttpClient();
			
			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/authentication")
					.get()
					.addHeader("accept", "application/json")
					.build();
			Response response = client.newCall(request).execute();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
