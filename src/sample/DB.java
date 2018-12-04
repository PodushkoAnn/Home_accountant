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

    public static ArrayList<String> getCurrencies() {
        String req = "Select name from currency";
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

    public static ArrayList<Source> getSources(){
        ArrayList<Source> list = new ArrayList<>();
        String request = "Select name, amount from source";
        try {
            ResultSet result = stmt.executeQuery(request);
            while(result.next()){
                String name = result.getString(1);
                float amount = result.getFloat(2);
                list.add(new Source(name, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getTypeByName(String name) {
        return getColumnValue(name, "type", "source_type");
    }

    public static String getCurrencyByName(String name){
        return getColumnValue(name, "currency", "currency");
    }

    public static Float getAmountByName(String name){
        String req = String.format("Select amount from source where name = '%s'", name);
        try {
            ResultSet result = stmt.executeQuery(req);
            if(result.next()){
                return result.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getColumnValue(String name, String columnName, String tableName){

        String req = String.format("Select " + columnName + " from source where name = '%s'", name);

        try {
            ResultSet result = stmt.executeQuery(req);
            if(result.next()){
                return stmt.executeQuery(String.format("Select name from " + tableName + " where id = '%d'",
                        result.getInt(1))).getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setMoney(float amount, String columnName, String tableName){

        String update = String.format("UPDATE '%s' SET amount='%f' WHERE name='%s'", tableName, amount, columnName);
        try {
            stmt.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ай-яй-яй, кури БД!");
        }
        System.out.println("Денег на: " + columnName + " " + amount);
    }

    public static void addCard(String name, int type, int currency){

        String insert = "INSERT INTO source" + "(type, name, amount, currency) VALUES" + "(?,?,?,?)";
        try {
            pstmt = connection.prepareStatement(insert);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setFloat(3, 0);
            pstmt.setInt(4, currency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении карты в бд");
        }
    }

    public static void addCategory(String name){
        String insert = "INSERT INTO category" + "(name) VALUES" + "(?)";
        try {
            pstmt = connection.prepareStatement(insert);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
