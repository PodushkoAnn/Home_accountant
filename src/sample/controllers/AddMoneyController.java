package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DB;
import sample.Messages;
import sample.MoneyHandler;
import sample.money_sources.Source;


public class AddMoneyController {

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label currency;

    @FXML
    private ComboBox chooseSource = new ComboBox();

    @FXML
    private TextField addSum;

    @FXML
    private void initialize(){

        chooseSource.setItems(FXCollections.observableArrayList(MoneyHandler.getSourcesNames()));

        //изменить паттерн, чтобы можно было вводить числа с 2 знаками после запятой
        addSum.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                addSum.setText(oldValue);
            }
        });
    }

    public void returnToMain(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addMoney(ActionEvent actionEvent) {

        if(addSum.getText().isEmpty()) Messages.showAlert(5);
        else if(chooseSource.getValue() == null) Messages.showAlert(4);
        else {
            float amount = Float.parseFloat(addSum.getText());
            DB.addMoney(amount, new Source(chooseSource.getValue().toString()));
            addSum.clear();
            //здесь нужно вывести label что деньги успешно зачислены
        }

    }
}
