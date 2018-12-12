package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import static sample.Messages.showAlert;

public class Main extends Application {

    public static Stage ps;

    @Override
    public void start(Stage primaryStage) throws Exception{

        try{
            DB.connect();
            System.out.println("База подключилась");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(3);
        }

        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));

        ps = primaryStage;
        ps.setTitle("Home accountant");

        ps.setScene(new Scene(root, 550, 400));
        ps.getScene().getStylesheets().add("sample/resources/style.css");

        ps.show();

        ps.setOnCloseRequest(e -> {
            if(DB.connected()) {
                try {
                    DB.disconnect();
                    System.out.println("База отключена");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

}

