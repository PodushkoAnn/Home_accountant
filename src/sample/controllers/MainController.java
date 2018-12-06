package sample.controllers;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import sample.*;
import sample.money_sources.Source;
import java.io.IOException;


import static sample.Messages.showAlert;

public class MainController {

    private ObservableList<Source> moneySources;
    private ObservableList<String> categories;
    private ObservableList<String> sources;

    @FXML
    private GridPane gp;

    @FXML
    private MyTextField sum;

    @FXML
    private ComboBox source = new ComboBox();

    @FXML
    private ComboBox category = new ComboBox();

    @FXML
    private Button bt;

    @FXML
    private Label curr;

    @FXML
    private TableView <Source> currentBalance;

    @FXML
    private TableColumn<Source, String> moneySource;

    @FXML
    private TableColumn<Source, Float> amount;

    @FXML
    private TableColumn<Source, String> type;

    @FXML
    private TableColumn<Source, String> currency;

    @FXML
    private MenuButton mb;


    @FXML
    private void initialize(){

        refreshLists();
        initData();
        setTable(currentBalance);

        sum.setCorrectInput();
    }

    private void initData(){

        float total = 0;
        for(Source s: MoneyHandler.getSources()){
            total += s.getAmount();
        }
        moneySources.add(new Source("ИТОГО", total));
    }

    private void setTable(TableView table){

        moneySource.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));

        table.setItems(moneySources);
    }

    public void spendMoney(ActionEvent actionEvent) {
        String str = sum.getText();
        if(!str.isEmpty()) {
            if(category.getValue() == null) showAlert(0);
            else {
                MoneyHandler.spendMoney(Float.parseFloat(sum.getCorrectValue(str)), source.getValue().toString());
                sum.clear();
                refreshTable();
            }
        } else {
            showAlert(5);
        }
    }

    public void refreshTable() {
        moneySources = FXCollections.observableArrayList(MoneyHandler.getSources());
        currentBalance.setItems(moneySources);
        initData();
    }

    public void handleMenu(ActionEvent actionEvent) {

        String item = ((MenuItem)actionEvent.getSource()).getText();
        System.out.println("меню кликнули на " + item);
        System.out.println(setSceneName(item));

        try {
            openDialog(actionEvent, setSceneName(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDialog(ActionEvent actionEvent, String sceneName) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load((getClass().getClassLoader().getResource("sample/fxml/" + sceneName + ".fxml")));
        stage.setTitle(setSceneTitle(sceneName));
        stage.setResizable(false);
        stage.setScene(new Scene(root, 400,300));
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(gp.getScene().getWindow());
        stage.showAndWait();
        refreshTable();
        refreshLists();
    }

    private void refreshLists() {
        moneySources = FXCollections.observableArrayList(MoneyHandler.getSources());
        categories = FXCollections.observableArrayList(MoneyHandler.getCategories());
        sources = FXCollections.observableArrayList(MoneyHandler.getSourcesNames());
        source.setItems(sources);
        category.setItems(categories);
    }

    private String setSceneName(String menu){
        switch(menu){
            case "Пополнить баланс": return "addMoneyScene";
            case "Добавить карту": return "addCardScene";
            case "Перевести между счетами": return "transferMoneyScene";
            case "Добавить категорию расходов": return "addCategoryScene";
        }
        return null;
    }

    private String setSceneTitle(String sceneName){
        switch(sceneName){
            case "addMoneyScene": return "Пополнение баланса";
            case "addCardScene": return "Добавление карты";
            case "transferMoneyScene": return "Перевод между счетами и картами";
            case "addCategoryScene": return "Добавление категории расходов";
        }
        return null;
    }

    public void viewChoise(ActionEvent actionEvent) {
        String sourceName = (String) source.getValue();
        curr.setText(MoneyHandler.getCurrencyBySourceName(sourceName));
        curr.setVisible(true);
    }

}
