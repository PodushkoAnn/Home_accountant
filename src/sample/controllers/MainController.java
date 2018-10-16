package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DB;
import sample.money_sources.Source;

import java.io.IOException;

import static sample.Messages.showAlert;


public class MainController {

    private ObservableList<Source> moneySources = FXCollections.observableArrayList(DB.getSources());
    private ObservableList<String> categories = FXCollections.observableArrayList(DB.getCategories());
    private ObservableList<String> sources = FXCollections.observableArrayList(DB.getSourcesNames(DB.getSources()));
    private ObservableList<String> options = FXCollections.observableArrayList("Пополнить баланс",
            "Добавить карту", "Перевести между счетами");

    @FXML
    private TextField sum;

    @FXML
    private Label currency;

    @FXML
    private ComboBox source = new ComboBox();

    @FXML
    private ComboBox category = new ComboBox();

    @FXML
    private ComboBox menu = new ComboBox();

    @FXML
    private Button bt;

    @FXML
    private TableView <Source> currentBalance;

    @FXML
    private TableColumn<Source, String> moneySource;

    @FXML
    private TableColumn<Source, Float> amount;

    @FXML
    private void initialize(){

        source.setItems(sources);
        category.setItems(categories);
        menu.setItems(options);
        initData();
        moneySource.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currentBalance.setItems(moneySources);
    }


    private void initData(){
    //получает данные по картам/налику из бд
        float total = 0;

        for(Source s: DB.getSources()){
            total += s.getAmount();
        }

        moneySources.add(new Source("ИТОГО", total));
    }


    public void onClickMethod(ActionEvent actionEvent) {
        // нужно добавить условия выпадающих списков и в зависимости от этого источник списания денег
        if(!sum.getText().isEmpty()) {
            String str = sum.getText();
            if (!isCorrect(str)) showAlert(1);
            else if(category.getValue() == null) showAlert(0);
            else {
                bt.setText("Clicked");
                DB.spendMoney(inputSum(str));
                sum.clear();
            }
        }
    }

    public void handleMenu(ActionEvent actionEvent) {
        System.out.println("меню кликнули на " + menu.getValue());
        if(menu.getValue().equals("Пополнить баланс")) {
            try {
                openDialog(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Опять косяк в методе");
            }
        }
    }

    private void openDialog(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load((getClass().getClassLoader().getResource("sample/fxml/secondScene.fxml")));
        stage.setTitle("Добавить денег");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 200,150));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();

    }

    private boolean isCorrect(String str){
        try{
            inputSum(str);
        } catch (NumberFormatException e){
            //вообще исключить введение букв в поле. Тогда и алерты не нужны

            return false;
        }
        return true;
    }

    private float inputSum(String str){
        return Float.parseFloat(str);
    }


}
