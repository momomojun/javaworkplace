package edu.neu.mgen;
import java.util.Scanner;

/**
 * Hello world!
 */
// jun zhai first commit on java maven projecgt
//this is my second commit to test
//Use your existing program "Hello World", write and run the code for the following:
//Q1:declare and initialize variables of types "int", "long", "double", "boolean", and "char" - two variables for each type.
//Q2:convert initialized variables of type "int" to "long"
//Q3:convert initialized variables of type "long" to "int"
//Q4:enter values for variables from the terminal.
//Q5:write the code and run the program for various arithmetic and logical operation with the variables.
public class App {
    public static void main(String[] args) {
        //Q1:declare and initialize variables of types "int", "long", "double", "boolean", and "char" - two variables for each type.
        int aInt = 10;
        int bInt = 20;
        long aLong = 10000L;
        long bLong = 20000L;
        double aDouble = 1.1;
        double bDouble = 2.2;
        boolean aBoolean = true;
        boolean bBoolean = false;
        char aChar = 'A';
        char bChar = 'B';
        System.out.println("It is my first Java program");

        //Q2:convert initialized variables of type "int" to "long"
        long aintToLong = aInt;
        long bintToLong = bInt;
        System.out.println("int to long: " + aintToLong + " and " + bintToLong);

        //Q3:convert initialized variables of type "long" to "int"
        int alongToInt = (int) aLong;
        int blongToInt = (int) bLong;
        System.out.println("long to int: " + alongToInt + " and " + blongToInt);

        //Q4:enter values for variables from the terminal.
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter int value");
        aInt = scanner.nextInt();
        System.out.println("please enter long value");
        aLong = scanner.nextLong();
        System.out.println("please enter double value");
        aDouble = scanner.nextDouble();
        System.out.println("please enter boolean value");
        aBoolean = scanner.nextBoolean();
        System.out.println("please enter char value");
        aChar = scanner.next().charAt(0);

        System.out.println("all input values: " + aInt + " " + aLong + " " + aDouble + " " + aBoolean + " " + aChar);

        //Q5:write the code and run the program for various arithmetic and logical operation with the variables.
        System.out.println("Performing arithmetic and logical operations:");
        System.out.println("aInt: " + aInt + " bInt: " + bInt + " aLong: " + aLong + " bLong: " + bLong);
        System.out.println("aDouble: " + aDouble + "bDouble" + bDouble + " aBoolean: " + aBoolean + " bBoolean: " + bBoolean);
        int sum = aInt + bInt;
        System.out.println("aInt and bInt plus: " + sum);
        long subtraction = bLong - aLong;
        System.out.println("bLong minus aLong: " + subtraction);
        int product = aInt * bInt;
        System.out.println("aInt mutiply bInt: " + product);
        double division = bDouble / aDouble;
        System.out.println("bDouble division aDouble: " + division);
        double remainder = 5 % 4;
        System.out.println("remainder of 5 % 4: " + remainder);
        boolean andResult = aBoolean&&bBoolean;
        System.out.println("And Result of aboolean and bboolean: " + andResult);
        Boolean orResult = aBoolean||bBoolean;
        System.out.println("OR Result of aboolean and bboolean: " + orResult);
        Boolean notResult = aBoolean = !bBoolean;
        System.out.println("Not Result of aboolean: " + notResult);
    }
}
