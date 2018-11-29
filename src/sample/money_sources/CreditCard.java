package sample.money_sources;

import java.util.Date;

public class CreditCard extends Source {

    float overrun;
    Date expired;

    public CreditCard(String name) {
        super();
        this.name = name;
        type = "кредитная карта";
    }

}
