package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.handlers.ExchangeRate;
import sample.handlers.MoneyHandler;
import sample.MyTextField;
import sample.handlers.Rates;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Arrays;

import static sample.Messages.showAlert;

public class TransferMoneyController {

    private ObservableList<String> moneySources = FXCollections.observableArrayList(MoneyHandler.getSourcesNames());
    private static final String FILE_PATH = "src/sample/resources/dateOfLastRatesUpdate";

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
        if(str.isEmpty()) showAlert(5);
        else if(from.getValue() == null || to.getValue() == null) showAlert(4);
        else if(from.getValue() == to.getValue()) showAlert(6);
        else {
            float amount = Float.parseFloat(sum.getCorrectValue(str));
            float rate = 1f;
            if(!curr1.getText().equals(curr2.getText())) {
                try {
                    rate = ExchangeRate.getRateFromFile(curr1.getText(), curr2.getText());

                } catch (IOException | SAXException | XPathExpressionException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
            amount *= rate;
            float a = new BigDecimal(amount).setScale(2, RoundingMode.UP).floatValue();
            MoneyHandler.transferMoney(from.getValue().toString(), to.getValue().toString(), a);
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
        if (curr2.isVisible() && !curr2.getText().equals(curr1.getText())) {
            showRate(curr1.getText(), curr2.getText());
            refresh.setVisible(true);
        }
    }

    public void handleChoiseTo(ActionEvent actionEvent) {
        curr2.setText(MoneyHandler.getCurrencyBySourceName(to.getValue().toString()));
        curr2.setVisible(true);
        if (curr1.isVisible() && !curr1.getText().equals(curr2.getText())) {
            showRate(curr1.getText(), curr2.getText());
            refresh.setVisible(true);
        }
    }

    public void refreshRates(ActionEvent actionEvent){
        Rates.getRateFromCBRF();
        try {
            writeDate(Rates.getTodayDate());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showRate(String from, String to){
        try {
            rates.setText("Курс валюты " + ExchangeRate.getRateFromFile(from, to) + " на дату " + readDateFromFile());
            rates.setVisible(true);
        } catch (IOException | SAXException | XPathExpressionException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void writeDate(String date) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(date);
        writer.flush();
        writer.close();
    }

    private String readDateFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        char[] buf = new char[256];
        int c;
        while((c = reader.read(buf))>0){
            if(c < 256){
                buf = Arrays.copyOf(buf, c);
            }
            System.out.print(buf);
        }
        return new String(buf);
    }
}
