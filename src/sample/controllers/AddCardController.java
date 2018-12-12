package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.MoneyHandler;

import java.time.LocalDate;

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
    DatePicker date;

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
        LocalDate expiredDate = date.getValue();
        String curr = (String)currency.getValue();
        RadioButton btn = (RadioButton) group.getSelectedToggle();

        date.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                System.out.println("ChangedValue" + date.getEditor().getText());
            }
        });

        LocalDate d = date.getValue();
        System.out.println(d);

//        if(name.isEmpty()) System.out.println("Введите имя карты");
//        else if(currency.getValue() == null) System.out.println("Выберите валюту");
//        else if(!group.getSelectedToggle().isSelected()) System.out.println("Выберите тип карты");
//        else if(expiredDate == null) System.out.println("Введите дату");
//        else {
//            String type = btn.getText();
//            MoneyHandler.addCard(name, type, curr, expiredDate.toString());
//            cardName.clear();
////        date.clear();
//            System.out.println("Карта " + name + " годна до " + expiredDate + ", тип " + type + " валюта " + curr + " добавлена");
//        }

    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


}
