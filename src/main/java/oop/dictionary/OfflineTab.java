package oop.dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import offline.dictionary.Dictionary;
import offline.dictionary.Explain;
import offline.speech.Voice;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OfflineTab {


    @FXML private TextField input= new TextField();;
    @FXML private WebView output;
    @FXML private TextField inputAdd = new TextField();
    @FXML private TextField outputAdd= new TextField();
    @FXML private TextField pronounceAdd= new TextField();
    @FXML private TextField attributeAdd= new TextField();


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
    protected void onSpeakButtonClick() {
        try {
            Voice voice = new Voice();
            voice.Say(input.getText());
        } catch (Exception e) {

        }
    }


    @FXML
    protected void addAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("OfflineTab2.fxml"));
        App.getPrimaryStage().setScene(new Scene(root));
    }

    private void submittedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setContentText(inputAdd.getText() + " đã được thêm");
        alert.show();
    }

    private void validationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setContentText("Nhập đầy đủ các trường có dấu *");
        alert.show();
    }

    @FXML
    public void submitAdd(){
        String inputTextAdd = inputAdd.getText();
        boolean checkExist = (dict.LookUpEN_VN(inputTextAdd) != null);

        String outputTextAdd = outputAdd.getText();
        String pronounceTextAdd = pronounceAdd.getText();
        String attributeTextAdd = attributeAdd.getText();
//        System.out.println(dict.LookUpEN_VN(inputTextAdd).getDetail());

        if (inputTextAdd.equals("") || outputTextAdd.equals("")) {
            validationAlert();
            return;
        }

        if(!checkExist) {
            try {
                dict.addNewWord(inputTextAdd, attributeTextAdd, outputTextAdd, pronounceTextAdd);
                submittedAlert();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(inputTextAdd + " đã tồn tại. Bạn có muốn ghi đè không?");
            alert.setContentText("Choose your option?");
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();

            // nếu người dùng muốn ghi đè thì ghi đè tù cũ
            if(result.get() == buttonTypeYes) {
                try {
                    dict.updateWord(inputTextAdd, attributeTextAdd, outputTextAdd, pronounceTextAdd);
                    submittedAlert();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if(result.get() == buttonTypeNo) {
                // không làm gì cả
            }
        }

    }

    @FXML
    public void backScreen() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        App.getPrimaryStage().setScene(new Scene(root));
    }

    @FXML
    protected void deleteAction() {
        String srcText = input.getText();
        try {
            dict.deleteWord(srcText);
            output.getEngine().loadContent(srcText + " đã được xóa");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setContentText(srcText + " đã được xóa");
            alert.show();
        } catch (Exception e) {
            output.getEngine().loadContent("Không tìm thấy từ khóa.");
        }
    }
}
