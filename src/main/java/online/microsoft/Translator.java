package online.microsoft;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.*;
import java.util.*;

public class Translator {
    /**
     * This function request a translation query.*/
    public String newRequest(String srcLang, String destLang, String text) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.cognitive.microsofttranslator.com")
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", srcLang)
                .addQueryParameter("to", destLang)
                .build();

        // Instantiates the OkHttpClient.
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("[{\"Text\": \"" + text + "\"}]", mediaType);
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", Constant.TranslatorSubscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", Constant.Location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        /**
         * A successful response is a JSON array with one result for each string in the input array.
         * Which, in this case, has only one element.*/
        TranslatedText[] trans = new Gson().fromJson(response.body().string(), new TypeToken<TranslatedText[]>(){}.getType());
        return trans[0].translations[0].text;
    }

    /**
     * This function get supported languages from Microsoft.
     * Return result in <language code, LanguageDescription> format*/
    public HashMap<String, LanguageDescription> GetSupportedLanguages() throws IOException {
        // Instantiates the OkHttpClient.
        OkHttpClient client = new OkHttpClient();

        String url = Constant.GetSupportedLanguagesURL;
        Request request = new Request.Builder().url(url).get()
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        Languages langs = new Gson().fromJson(response.body().string(), new TypeToken<Languages>(){}.getType());
        return langs.translation;
    }

    public static void main(String[] args) {
        try {
            /**
             * Test code goes here.*/

            Translator translator = new Translator();
            String response = translator.newRequest("en", "vi", "hey brother");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}