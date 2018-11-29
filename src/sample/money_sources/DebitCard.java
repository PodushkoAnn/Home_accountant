package sample.money_sources;

import java.util.Date;

public class DebitCard extends Source {

    Date expired;

    public DebitCard(String name) {
        super();
        this.name = name;
        type = "дебетовая карта";
        this.currency = "RUB";
    }
}
