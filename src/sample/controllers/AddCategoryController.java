package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.MoneyHandler;
import static sample.Messages.showAlert;

public class AddCategoryController {

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField addCategory;

    @FXML
    private Label success;


    public void returnToMain(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addCategory(ActionEvent actionEvent) {
        System.out.println("Категория " + addCategory.getText() + " добавлена");
        if(addCategory.getText().isEmpty()) showAlert(8);
        else {
            MoneyHandler.addCategory(addCategory.getText());
            success.setVisible(true);
            addCategory.clear();
        }
    }
}
