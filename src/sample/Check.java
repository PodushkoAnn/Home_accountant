package sample;

public class Check {

    public static boolean isCorrect(String str){
        return inputSum(str) >= 0;
    }

    public static float inputSum(String str){
        return Float.parseFloat(str);
    }

}
