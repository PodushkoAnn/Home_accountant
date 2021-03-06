package sample;

import javafx.scene.control.Alert;

public class Messages {

    private static Alert alert;

    public static void showAlert(int i){
        alert = new Alert(Alert.AlertType.INFORMATION);
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
            case 4:
                alert.setTitle("Не выбран источник");
                alert.setContentText("Выберите карту / счет для зачисления");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Нулевая сумма");
                alert.setContentText("Введите сумму");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Неверный источник перевода");
                alert.setContentText("Выберите разные источники списания и зачисления денег");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Разные валюты");
                alert.setContentText("Валюта счета списания и зачисления денег должна совпадать");
                alert.showAndWait();
                break;
            case 8:
                alert.setTitle("Пустое значение");
                alert.setContentText("Введите имя категории расходов");
                alert.showAndWait();
                break;
        }
    }
}
