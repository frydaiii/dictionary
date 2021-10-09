package online.microsoft;

/**
 * This class is implementing to parse response
 * from GET supported voices.*/

public class VoiceDescription {
    public String Name;
    public String DisplayName;
    public String LocalName;
    public String ShortName;
    public String Gender;
    public String Locale;
    public String SampleRateHertz;
    public String VoiceType;
    public String Status;

    @Override
    public String toString() {
        return "VoiceDescription{"
                + "Name='" + Name + '\''
                + ", DisplayName='" + DisplayName + '\''
                + ", LocalName='" + LocalName + '\''
                + ", ShortName='" + ShortName + '\''
                + ", Gender='" + Gender + '\''
                + ", Locale='" + Locale + '\''
                + ", SampleRateHertz='" + SampleRateHertz + '\''
                + ", VoiceType='" + VoiceType + '\''
                + ", Status='" + Status + '\''
                + '}';
    }
}
