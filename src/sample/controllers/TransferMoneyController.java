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
import org.xml.sax.SAXException;
import sample.ExchangeRate;
import sample.MoneyHandler;
import sample.MyTextField;
import sample.Rates;
import sample.money_sources.Source;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

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
    private Button refresh;

    @FXML
    private Label curr1;

    @FXML
    private Label curr2;

    @FXML
    private Label rates;

    @FXML
    private void initialize(){

        from.setItems(moneySources);
        to.setItems(moneySources);
        sum.setCorrectInput();
    }

    public void transferMoney(ActionEvent actionEvent) {
        String str = sum.getText();
        float amount = Float.parseFloat(sum.getCorrectValue(str));
        float rate = 1f;
        if(str.isEmpty()) showAlert(5);
        else if(from.getValue() == null || to.getValue() == null) showAlert(4);
        else if(from.getValue() == to.getValue()) showAlert(6);
        else {
            if(!curr1.getText().equals(curr2.getText())) {
                try {
                    rate = ExchangeRate.getRateFromFile(curr1.getText(), curr2.getText());
                } catch (IOException | SAXException | XPathExpressionException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
            amount *= rate;
            MoneyHandler.transferMoney(from.getValue().toString(), to.getValue().toString(), amount);
            sum.clear();
        }
    }

    public void returnToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleChoiceFrom(ActionEvent actionEvent) {
        curr1.setText(MoneyHandler.getCurrencyBySourceName(from.getValue().toString()));
        curr1.setVisible(true);
    }

    public void handleChoiseTo(ActionEvent actionEvent) {
        curr2.setText(MoneyHandler.getCurrencyBySourceName(to.getValue().toString()));
        curr2.setVisible(true);
    }

    public void refreshRates(ActionEvent actionEvent){
        Rates.getRateFromCBRF();
    }
}
