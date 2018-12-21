package sample.controllers;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import sample.*;
import sample.money_sources.Source;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static sample.Messages.showAlert;

public class MainController {

    private ObservableList<Source> moneySources;
    private ObservableList<String> categories;
    private ObservableList<String> sources;
    private ObservableList<String> pieChartList;

    @FXML
    private GridPane gp;

    @FXML
    private MyTextField sum;

    @FXML
    private ComboBox source = new ComboBox();

    @FXML
    private ComboBox category = new ComboBox();

    @FXML
    private ComboBox pieChartCurrency = new ComboBox();

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
    private PieChart pie;


    @FXML
    private void initialize(){
        refreshLists();
        pieChartCurrency.setValue("RUB");
        initData();
        setTable(currentBalance);
        setChart();
        sum.setCorrectInput();

    }

    private void initData(){
        float total = 0;
        for(Source s: MoneyHandler.getSources()){
            total += s.getAmount();
        }
//        moneySources.add(new Source("ИТОГО", total));
    }

    private void setTable(TableView table){

        moneySource.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));

        table.setItems(moneySources);
    }

    public void setChart(){

        HashMap<String, Float> chart = MoneyHandler.getExpencesBySelectedCurrency((String)pieChartCurrency.getValue());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Map.Entry<String, Float> entry : chart.entrySet()){
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
//        pie.setTitle("В текущем месяце");
        pie.setData(pieChartData);
    }

    public void spendMoney(ActionEvent actionEvent) {
        String str = sum.getText();
        if(!str.isEmpty()) {
            if(category.getValue() == null) showAlert(0);
            else {
                MoneyHandler.addExpence(Float.parseFloat(sum.getCorrectValue(str)), source.getValue().toString(), category.getValue().toString());
                sum.clear();
                refreshTable();
                setChart();
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
        pieChartList = FXCollections.observableArrayList(MoneyHandler.getCurrencies());
        source.setItems(sources);
        category.setItems(categories);
        pieChartCurrency.setItems(pieChartList);
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
