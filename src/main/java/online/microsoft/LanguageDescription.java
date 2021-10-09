package online.microsoft;

/**
 *  This class is implementing to help Languages class
 *  parse response from Microsoft.*/

public class LanguageDescription {
    public String name;
    public String nativeName;
    public String dir;

    @Override
    public String toString() {
        return "LanguageDescription{"
                + "name='" + name + '\''
                + ", nativeName='" + nativeName + '\''
                + ", dir='" + dir + '\''
                + '}';
    }
}
