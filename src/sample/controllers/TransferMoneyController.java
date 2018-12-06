package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.MoneyHandler;
import sample.MyTextField;
import sample.money_sources.Source;

import static sample.Messages.showAlert;

public class TransferMoneyController {

    private ObservableList<String> moneySources = FXCollections.observableArrayList(MoneyHandler.getSourcesNames());


    @FXML
    private ComboBox from = new ComboBox();

    @FXML
    private ComboBox to = new ComboBox();

    @FXML
    private MyTextField sum;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label curr1;

    @FXML
    private Label curr2;

    @FXML
    private void initialize(){

        from.setItems(moneySources);
        to.setItems(moneySources);
        sum.setCorrectInput();
    }

    public void transferMoney(ActionEvent actionEvent) {
        String str = sum.getText();
        if(!str.isEmpty()){
            if(from.getValue() == null || to.getValue() == null) showAlert(4);
            else if(from.getValue() == to.getValue()) showAlert(6);
            //здесь нужно будет изменить ветку на предложение перевести валюту по курсу ЦБ РФ
            // или по выбранному пользователем курсу
            else if(!curr1.getText().equals(curr2.getText())) showAlert(7);
            else {
                MoneyHandler.transferMoney(from.getValue().toString(),
                        to.getValue().toString(), Float.parseFloat(sum.getCorrectValue(str)));
                sum.clear();
            }
        } else {
            showAlert(5);
        }
    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleChoiseFrom(ActionEvent actionEvent) {
        curr1.setText(MoneyHandler.getCurrencyBySourceName(from.getValue().toString()));
        curr1.setVisible(true);
    }

    public void handleChoiseTo(ActionEvent actionEvent) {
        curr2.setText(MoneyHandler.getCurrencyBySourceName(to.getValue().toString()));
        curr2.setVisible(true);
    }
}
