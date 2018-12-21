package sample;

import sample.money_sources.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyHandler {

    static ArrayList<Source> sources;

    public static List<Source> getSources(){
        sources = DB.getSources();
        for(Source s: sources){
            s.setCurrency(DB.getCurrencyByName(s.getName()));
            s.setType(DB.getTypeByName(s.getName()));
        }
        return sources;
    }

    public static ArrayList<String> getSourcesNames(){
        return DB.getSourceNames();
    }

    public static ArrayList<String> getCurrencies(){
        return DB.getCurrencies();
    }

    public static ArrayList<String> getCategories(){
        return DB.getCategories();
    }

    public static void addMoney(float amount, String source){
        float oldAmount = DB.getAmountByName(source);
        float newAmount = oldAmount + amount;
        DB.setMoney(newAmount, source, "source");
    }

    public static void spendMoney(float amount, String source){
        float oldAmount = DB.getAmountByName(source);
        float newAmount = oldAmount - amount;
        DB.setMoney(newAmount, source, "source");
    }

    public static void addExpence(float amount, String source, String category){

        System.out.println("Потратили " + amount + " " + source + " " + category);
        spendMoney(amount, source);
        DB.addExpence(amount, convertCategoryToInt(category), LocalDate.now(), getSourceId(source) ,
                convertCurrencyToInt(getCurrencyBySourceName(source)));
    }

    public static void transferMoney(String from, String to, float sum) {
        spendMoney(sum, from);
        addMoney(sum, to);
        System.out.println("Перевели " + sum + " денег с " + from + " на " + to);
    }

    public static String getCurrencyBySourceName(String name) {
        return DB.getCurrencyByName(name);
    }

    public static void addCard(String name, String type, String currency, String date){

        int cardType = convertTypeToInt(type);
        int curr = convertCurrencyToInt(currency);
        if (curr == -1) {
            System.out.println("Ошибка в преобразовании валюты");
        } else if(cardType == -1){
            System.out.println("Ошибка в преобразовании типа карты");
        } else {
            DB.addCard(name, cardType, curr);
            System.out.println("Карта успешно добавлена");
        }
    }

    public static HashMap<String, Float> getExpencesBySelectedCurrency(String currency){
        int currencyId = convertCurrencyToInt(currency);
        HashMap<Integer, Float> expences = DB.getExpence(currencyId);
        HashMap<String, Float> chart = new HashMap<>();
        for(Map.Entry<Integer, Float> entry : expences.entrySet()){
            System.out.println("ключ: " + entry.getKey() + ", значение " + entry.getValue());
            chart.put(getCategoryById(entry.getKey()), entry.getValue());
        }
        return chart;
    }

    private static int convertCurrencyToInt(String name) {
//        System.out.println(DB.getIdByName(name,"currency"));
        return DB.getIdByName(name,"currency");
    }

    private static int convertTypeToInt(String type) {
        switch(type){
            case "Наличные": return 1;
            case "Кредитная": return 2;
            case "Дебетовая": return 3;
        }
        return -1;
    }

    private static int convertCategoryToInt(String name){
        return DB.getIdByName(name, "category");
    }

    private static int getSourceId(String name){
        return DB.getIdByName(name, "source");
    }

    public static void addCategory(String categoryName) {
        DB.addCategory(categoryName);
    }

    private static String getCategoryById(int id){
        String name = DB.getCategoryById(id);
        return name;
    }
}
