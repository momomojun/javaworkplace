import java.util.Scanner;
public class App {
    // Develop the following program:
    // Q1:Design and develop a program that reads from the terminal.
    // Q2:Use “try … catch” to avoid any errors during the input.
    public static void main(String[] args) throws Exception {
        // Q1:Design and develop a program that reads from the terminal.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter 3 integer:");
        // Q2:Use “try … catch” to avoid any errors during the input.
        try {
            int num1 = scanner.nextInt();
            int num2 = scanner.nextInt();
            int num3 = scanner.nextInt();
            System.out.println("The number of the entering integers is: " + num1 + ", " + num2 + ", " + num3);
        } catch (Exception e) {
            System.out.println("You have entered invalid integers.");
        } finally {
            scanner.close();
        }
    }
}
