package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddCardController {

    @FXML
    Button ok;

    @FXML
    Button cancel;

    @FXML
    TextField cardName;

    @FXML
    TextField date;

    @FXML
    private RadioButton btn1;

    @FXML
    private RadioButton btn2;

    public void addCard(ActionEvent actionEvent) {
        System.out.println("Card added");
    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
