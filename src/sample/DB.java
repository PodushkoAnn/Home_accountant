package sample;

import sample.money_sources.Cash;
import sample.money_sources.CreditCard;
import sample.money_sources.DebitCard;
import sample.money_sources.Source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:money.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    public static boolean connected() {
//        System.out.println(connection != null);
        return (connection != null);
    }

    public static void addMoney(){

    }

    public static void spendMoney(float amount){
        //прописать метод
    }

    public static ArrayList<String> getCategories(){
        ArrayList<String> s = new ArrayList<>();
        s.add("дом");
        s.add("дорога");
        s.add("ребенок");
        s.add("продукты");
        return s;
    }



    public static ArrayList<Source> getSources(){
        float cash = 50000f;
        float debitCard = 70000f;
        float creditCard = 120000f;
        ArrayList<Source> s = new ArrayList<>();
        s.add(new Cash("наличные", cash));
        s.add(new DebitCard("дебетовая карта", debitCard));
        s.add(new CreditCard("кредитная карта", creditCard));
        return s;
    }

    public static ArrayList<String> getSourcesNames(ArrayList<Source> sources){
        ArrayList<String> str = new ArrayList<>();
        for(Source s: sources){
            str.add(s.getName());
        }
        return str;
    }

    public static List<String> getCurrencies(){
        List<String> s = new ArrayList<>();
        s.add("RUB");
        s.add("EUR");
        s.add("USD");
//        System.out.println(s.toString());
        return s;
    }
}
