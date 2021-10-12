package online.microsoft;

/**
 * This class is implementing to help TranslatedText class
 * parse response from Microsoft.*/

public class TranslatedTextDescription {
    public String text;
    public String to;

    @Override
    public String toString() {
        return "TranslatedTextDescription{"
                + "text='" + text + '\''
                + ", to='" + to + '\''
                + '}';
    }
}
