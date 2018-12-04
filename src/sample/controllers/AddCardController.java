package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.MoneyHandler;

import java.util.Date;


public class AddCardController {

    @FXML
    Button ok;

    @FXML
    Button cancel;

    @FXML
    TextField cardName;

    @FXML
    ComboBox currency;

    @FXML
    TextField date;

    @FXML
    private ToggleGroup group;

    private ObservableList<String> currencies;

    @FXML
    private void initialize(){
        currencies = FXCollections.observableArrayList(MoneyHandler.getCurrencies());
        currency.setItems(currencies);
    }

    public void addCard(ActionEvent actionEvent) {

        String name = cardName.getText();
        String expiredDate = date.getText();
        String curr = (String)currency.getValue();
        RadioButton btn = (RadioButton) group.getSelectedToggle();
        String type = btn.getText();
        MoneyHandler.addCard(name, type, curr, expiredDate);
        cardName.clear();
        date.clear();
        System.out.println("Карта " + name + " годна до " + expiredDate + ", тип " + type + " валюта " + curr + " добавлена");
    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


}
