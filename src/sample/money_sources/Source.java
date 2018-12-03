package sample.money_sources;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Source {
    private int id;
    String type;
    String name;
    String currency;
    private float amount;

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Source() {
    }

    public Source(String name) {
        this.name = name;
        this.amount = 0;
        this.currency = "RUB";
    }

    public Source(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public Source(String name, String type, float amount, String currency) {
        this.type = type;
        this.name = name;
        this.currency = currency;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
