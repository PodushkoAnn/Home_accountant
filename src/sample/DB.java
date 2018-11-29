package sample;

import sample.money_sources.*;

import java.sql.*;
import java.util.*;

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
        return (connection != null);
    }

    public static ArrayList<String> getSourceNames(){
        String req = "Select name from source";
        return getList(req);
    }

    public static ArrayList<String> getCategories() {
        String req = "Select name from category";
        return getList(req);
    }

    private static ArrayList<String> getList(String request){
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet result = stmt.executeQuery(request);
            while(result.next()){
                list.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getCurrency(String name){

        String req = String.format("Select currency from source where name = '%s'", name);

        try {
            ResultSet result = stmt.executeQuery(req);
            if(result.next()){
                return stmt.executeQuery(String.format("Select name from currency where id = '%d'",
                        result.getInt(1))).getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addMoney(float amount, Source source){
        System.out.println("Добавили денег " + amount + " на " + source.getName());
    }

    public static void spendMoney(float amount, Source source){
        System.out.println("Сняли денег " + amount + " c " + source.getName());
    }

    public static void addMoney(float amount){
        System.out.println("Добавили денег " + amount);
    }

    public static void spendMoney(float amount){
        System.out.println("Сняли денег " + amount);
    }






}
