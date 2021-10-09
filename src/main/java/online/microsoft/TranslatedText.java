package online.microsoft;

/**
 *  This class is implementing to parse response
 *  from POST translation query.*/

public class TranslatedText {
    /**
     *      An array of translation results.
     *      The size of the array matches the number of target languages
     *      specified through the to query parameter.*/
    public TranslatedTextDescription[] translations;
}
