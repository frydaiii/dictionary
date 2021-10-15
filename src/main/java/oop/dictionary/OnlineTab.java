package oop.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import online.microsoft.Translator;

public class OnlineTab {
    @FXML
    private TextField input;

    @FXML
    private TextField output;

    @FXML
    protected void onTranslateButtonClick() {
        Translator trans = new Translator();
        String sourceText = input.getText();
        String destText = "";
        try {
            destText = trans.newRequest("en", "vi", sourceText);
        } catch (Exception e) {
            destText = "Error";
        }
        output.setText(destText);
    }
}
