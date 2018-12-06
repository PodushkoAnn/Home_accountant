package sample;

import javafx.scene.control.TextField;

public class MyTextField extends TextField {

    private final int INPUT_LENGTH = 12;

    public void setCorrectInput(){
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(\\d+[.,]?\\d{0,2})?")) {
                this.setText(oldValue);
            }

            if(this.getText().length() > INPUT_LENGTH){
                String s = this.getText().substring(0, INPUT_LENGTH);
                this.setText(s);
            }
        });
    }

    public String getCorrectValue(String oldValue){
        return oldValue.replace(',', '.');
    }
}
