package sample.money_sources;

import java.util.Date;

public class DebitCard extends Source {

    Date expire;

    public DebitCard(String name, float amount) {
        super(name, amount);
    }
}
