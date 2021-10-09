package online.microsoft;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Speech {

    /**
     * This function return all supported voices.*/
    public static VoiceDescription[] GetVoicesList() throws IOException {
        // Instantiates the OkHttpClient.
        OkHttpClient client = new OkHttpClient();

        String url = Constant.GetSupportedVoicesURL;
        Request request = new Request.Builder().url(url).get()
                .addHeader("Ocp-Apim-Subscription-Key", Constant.SpeechSubscriptionKey)
                .addHeader("Authorization", "Bearer " + Constant.BearToken)
                .build();
        Response response = client.newCall(request).execute();
        VoiceDescription[] voices = new Gson().fromJson(response.body().string(), new TypeToken<VoiceDescription[]>(){}.getType());
        return voices;
    }

    /**
     * This function return supported voice of a language,
     * specific by its language code.*/
    public static VoiceDescription[] GetVoicesList(String langCode) throws IOException {
        VoiceDescription[] voices = GetVoicesList();
        ArrayList<VoiceDescription> voicesOfThisLang = new ArrayList<VoiceDescription>();

        for (VoiceDescription v: voices) {
            String codeOfVoice = v.Locale.substring(0, 2);
            if (codeOfVoice.equals(langCode) == true) {
                voicesOfThisLang.add(v);
            }
        }

        /**
         * Convert result to an array, instead of ArrayList.*/
        VoiceDescription[] result = new VoiceDescription[voicesOfThisLang.size()];
        return voicesOfThisLang.toArray(result);
    }

    /**
     * Synthesize to speaker output.*/
    public static void Say(String lang, String text) {
        SpeechConfig speechConfig = SpeechConfig
                .fromSubscription(Constant.SpeechSubscriptionKey, Constant.Location);
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        System.out.println(speechConfig.getSpeechRecognitionLanguage());
        speechConfig.setSpeechSynthesisLanguage(lang);

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(text);
    }

    public static void main(String[] args) {
        try {
            /**
             * Test code goes here.*/

            Say("en-US", "hey buddy, how are you today?");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
