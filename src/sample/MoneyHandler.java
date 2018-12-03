package sample;

import sample.money_sources.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoneyHandler {

    static ArrayList<Source> sources;
    static ArrayList<String> sourceNames;
    static List<String> currencies;
    static ArrayList<String> categories;

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

    public static List<String> getCurrencies(){
        return DB.getCurrencies();
    }

    public static ArrayList<String> getCategories(){
        return DB.getCategories();
    }
//
//    public static void addMoney(float amount, Source to){
//        float oldAmount = DB.getAmountByName(to.getName());
//        float newAmount = oldAmount + amount;
//        DB.setMoney(newAmount, to.getName(), "source");
//    }

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

    public static void transferMoney(Source from, Source to, float sum) {
        System.out.println("Перевели " + sum + " денег с " + from.getName() + " на " + to.getName());
    }

    public static String getCurrencyBySourceName(String name) {
        return DB.getCurrencyByName(name);
    }
}
