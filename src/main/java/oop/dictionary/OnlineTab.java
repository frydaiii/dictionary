package oop.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import online.microsoft.Translator;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Handler;

public class OnlineTab {
    @FXML private TextField input;
    @FXML private TextField output;
    @FXML private ComboBox srcLangsMenu = new ComboBox();
    @FXML private ComboBox destLangsMenu = new ComboBox();

    public void initialize() {
        Translator trans = new Translator();
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
            Translator trans = new Translator();
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
}
