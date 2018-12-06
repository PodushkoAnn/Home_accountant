package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.*;

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
    private MyTextField addSum;

    @FXML
    private void initialize(){
        chooseSource.setItems(FXCollections.observableArrayList(MoneyHandler.getSourcesNames()));
        addSum.setCorrectInput();
    }

    public void returnToMain(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addMoney(ActionEvent actionEvent) {
        String str = addSum.getText();
        if(str.isEmpty()) Messages.showAlert(5);
        else if(chooseSource.getValue() == null) Messages.showAlert(4);
        else {
            MoneyHandler.addMoney(Float.parseFloat(addSum.getCorrectValue(str)), chooseSource.getValue().toString());
            addSum.clear();
            //здесь нужно вывести label что деньги успешно зачислены
        }
    }

    public void handleChoise(ActionEvent actionEvent) {
        currency.setText(MoneyHandler.getCurrencyBySourceName(chooseSource.getValue().toString()));
        currency.setVisible(true);
    }
}
