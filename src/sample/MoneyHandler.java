package sample;

import sample.money_sources.*;


import java.util.ArrayList;
import java.util.List;

public class MoneyHandler {

    static ArrayList<Source> sources;
    static ArrayList<String> sourceNames;
    static List<String> currencies;
    static ArrayList<String> categories;

    public static List<Source> getSources(){
        sources = new ArrayList<>();
        sourceNames = DB.getSourceNames();

        return sources;
    }

    public static ArrayList<String> getSourcesNames(){
        return DB.getSourceNames();
    }

    public static List<String> getCurrencies(){
        currencies = new ArrayList<>();
        currencies.add("RUB");
        currencies.add("EUR");
        currencies.add("USD");
//        System.out.println(currencies.toString());
        return currencies;
    }

    public static ArrayList<String> getCategories(){
        return DB.getCategories();
    }

    public static void transferMoney(Source from, Source to, float sum) {
        System.out.println("Перевели " + sum + " денег с " + from.getName() + " на " + to.getName());
    }

    public static String getCurrencyBySourceName(String name) {
        return DB.getCurrency(name);
    }
}
