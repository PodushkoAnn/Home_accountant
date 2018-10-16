package sample.money_sources;

import java.util.Date;

public class CreditCard extends Source {

    Date expire;

    public CreditCard(String name, float amount) {
        super(name, amount);
    }

}
