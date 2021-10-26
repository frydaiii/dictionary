package online.microsoft;

public class Constant {
    public static final String GetSupportedLanguagesURL = "https://api.cognitive.microsofttranslator.com/languages?api-version=3.0&scope=translation";
    public static final String GetSupportedVoicesURL = "https://eastasia.tts.speech.microsoft.com/cognitiveservices/voices/list";

    public static final String TranslatorSubscriptionKey = "c119bb098a0149fa9b5d26a05757bba9";
    public static final String SpeechSubscriptionKey = "a2ed02d148204c9592227b49a0fc44e7";

    /**
     * Add location, also known as region. The default is global.
     * This is required if using a Cognitive Services resource.*/
    public static final String Location = "eastasia";

    /**
     * Bearer token for east asia region.*/
    public static final String BearToken = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJyZWdpb24iOiJlYXN0YXNpYSIsInN1YnNjcmlwdGlvbi1pZCI6IjU1ZWY3ODU0YjQwZDRjMWM5MDhlNjNlZDQ1MTMzODUyIiwicHJvZHVjdC1pZCI6IlNwZWVjaFNlcnZpY2VzLkYwIiwiY29nbml0aXZlLXNlcnZpY2VzLWVuZHBvaW50IjoiaHR0cHM6Ly9hcGkuY29nbml0aXZlLm1pY3Jvc29mdC5jb20vaW50ZXJuYWwvdjEuMC8iLCJhenVyZS1yZXNvdXJjZS1pZCI6Ii9zdWJzY3JpcHRpb25zLzNiYTI4Yzg2LTc2ODktNDM1MS1iZDNkLTVmOTllOWNhOGNhOS9yZXNvdXJjZUdyb3Vwcy9vb3BfcHJvamVjdC9wcm92aWRlcnMvTWljcm9zb2Z0LkNvZ25pdGl2ZVNlcnZpY2VzL2FjY291bnRzL29vcC1wcm9qZWN0LXR0cyIsInNjb3BlIjoic3BlZWNoc2VydmljZXMiLCJhdWQiOiJ1cm46bXMuc3BlZWNoc2VydmljZXMuZWFzdGFzaWEiLCJleHAiOjE2MzM3NjcxMTIsImlzcyI6InVybjptcy5jb2duaXRpdmVzZXJ2aWNlcyJ9.-xZGff7nNpBUyz2br1-w2eZLannfDzZCz5ql_wWwovw";
}
