package oop.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import offline.dictionary.Dictionary;
import offline.speech.Voice;
import org.controlsfx.control.textfield.TextFields;

import java.util.stream.Collectors;

public class OfflineTab {
    @FXML private TextField input;
    @FXML private WebView output;

    private Dictionary dict = new Dictionary();

    public void initialize() {
        dict.ReadFileEN_VN();
        TextFields.bindAutoCompletion(input, t -> {
            return dict.listKeyEN_VN.stream().filter(elem ->
            {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }

    @FXML
    protected void onTranslateButtonClick() {
        String srcText = input.getText();
        output.getEngine().loadContent("");
        try {
            output.getEngine().loadContent(dict.LookUpEN_VN(srcText).getDetail());
        } catch (Exception e) {
            output.getEngine().loadContent("Không tìm thấy từ khóa.");
        }
    }

    @FXML
    public void onSpeakButtonClick() {
        try {
            Voice voice = new Voice();
            voice.Say(input.getText());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
