package translate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URLEncoder;

public class Online {
	public static final String libreTrans(String sentence) {
		try {
			String transRequest = "?q=" + URLEncoder.encode(sentence, "UTF-8") + "&target=vi&source=en";

			HttpClient client = HttpClient.newBuilder()
					.followRedirects(HttpClient.Redirect.ALWAYS)
					.build();

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(Constant.TRANSLATE_URL + transRequest))
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return response.body();
		} catch (IOException e) {
			return "";
		} catch (InterruptedException e) {
			return "";
		}
	}
}
