package sample;

import javafx.scene.control.Alert;

public class Messages {

    Alert alert;

    public static void showAlert(int i){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch(i){
            case 0:
                alert.setTitle("Не выбрана категория расходов");
                alert.setContentText("Введите категорию");
                alert.showAndWait();
                break;
            case 1:
                alert.setTitle("Неверная сумма");
                alert.setContentText("Введите сумму в корректном формате");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("ОШИБКА");
                alert.setContentText("Отсутствует подключение к базе данных, перезапустите приложение");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("НЕИЗВЕСТНАЯ ОШИБКА");
                alert.setContentText("Перезапустите приложение, при повторении обратитесь к разработчику");
                alert.showAndWait();
                break;

        }
    }
}
