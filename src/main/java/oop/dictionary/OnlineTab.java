package oop.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import online.microsoft.Speech;
import online.microsoft.Translator;

import java.util.HashMap;
import java.util.TreeMap;

public class OnlineTab {
    @FXML private TextField input;
    @FXML private TextField output;
    @FXML private ComboBox srcLangsMenu = new ComboBox();
    @FXML private ComboBox destLangsMenu = new ComboBox();
    @FXML private Text inputInfo;
    @FXML private Text outputInfo;

    private Translator trans = new Translator();
    private Speech speaker = new Speech();

    public void initialize() {
        try {
            TreeMap<String, String> langs = new TreeMap<String, String>(trans.GetSupportedLanguages());
            srcLangsMenu.getItems().addAll(langs.keySet());
            srcLangsMenu.getSelectionModel().selectFirst();
            destLangsMenu.getItems().addAll(langs.keySet());
            destLangsMenu.getSelectionModel().selectFirst();
        } catch (Exception e) {
        }
    }

    @FXML
    protected void onTranslateButtonClick() {
        output.setText("");
        String resultText = "";
        try {
            HashMap<String, String> langs = trans.GetSupportedLanguages();

            String srcLang = srcLangsMenu.getValue().toString();
            String srcLangCode = langs.get(srcLang);

            String destLang = destLangsMenu.getValue().toString();
            String destLangCode = langs.get(destLang);

            String srcText = input.getText();
            resultText = trans.newRequest(srcLangCode, destLangCode, srcText);
        } catch (Exception e) {
            resultText = "Error";
        }
        output.setText(resultText);
    }

    @FXML
    protected void onInputSpeakButtonClick() {
        try {
            HashMap<String, String> langs = trans.GetSupportedLanguages();
            String langCode = langs.get(srcLangsMenu.getValue());
            String speechCode = speaker.GetSpeechCode(langCode);
            if (speechCode == "") {
                inputInfo.setText("This language does not support speech.");
            } else {
                speaker.Say(speechCode,input.getText());
            }
        } catch (Exception e) {
        }
    }

    @FXML
    protected void onOutputSpeakButtonClick() {
        try {
            HashMap<String, String> langs = trans.GetSupportedLanguages();
            String langCode = langs.get(destLangsMenu.getValue());
            String speechCode = speaker.GetSpeechCode(langCode);
            if (speechCode == "") {
                outputInfo.setText("This language does not support speech.");
            } else {
                speaker.Say(speechCode,output.getText());
            }
        } catch (Exception e) {
        }
    }
}
