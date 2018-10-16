package sample;


import java.util.Scanner;
import java.util.regex.Pattern;

public class Tests {

    public static String CheckNums(int num1, int num2) {

        // code goes here
    /* Note: In Java the return type of a function and the
       parameter types being passed are defined, so this return
       call must match the return type of the function.
       You are free to modify the return type. */
       if(num1 == num2) return "-1";
       else if(num2 > num1) return "true";
       else return "false";

    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        int y = s.nextInt();
        System.out.print(CheckNums(x,y));
    }

}
