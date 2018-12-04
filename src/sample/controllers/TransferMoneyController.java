package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.MoneyHandler;
import sample.money_sources.Source;

public class TransferMoneyController {

    private ObservableList<String> moneySources = FXCollections.observableArrayList(MoneyHandler.getSourcesNames());


    @FXML
    private ComboBox from = new ComboBox();

    @FXML
    private ComboBox to = new ComboBox();

    @FXML
    private TextField sum;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize(){

        from.setItems(moneySources);
        to.setItems(moneySources);
    }

    public void transferMoney(ActionEvent actionEvent) {
        Source fromSource = new Source((String)from.getValue());
        Source toSource = new Source((String)to.getValue());
        float quantity = Float.parseFloat(sum.getText());
        MoneyHandler.transferMoney(fromSource.getName(), toSource.getName(), quantity);
        sum.clear();
    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
