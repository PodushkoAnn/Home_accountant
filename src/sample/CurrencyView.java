package sample;

import java.text.DecimalFormat;

public class CurrencyView {

    public static String priceWithDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String priceToString(Double price) {
        String toShow = priceWithoutDecimal(price);
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(price);
        } else {
            return priceWithoutDecimal(price);
        }
    }
}

//    Чуть правильнее и быстрее будет использовать DecimalFormat.
//
//        String formattedDouble = new DecimalFormat("#0.00").format(0.1321231);
//    Само собой, строку формата "#0.00" стоит вынести куда-нибудь в константу, возможно, общую на все приложение.
//
//        Целиком вытаскивать объект new DecimalFormat("#0.00") в константу, напротив не стоит из соображений потокобезопасности.
//
//        Выигрыш в производительности по сравнению со String.format() может быть что-то около 2х раз.
// Плюс DecimalFormat использует настройки локали для разделителя целой и дробной частей.
