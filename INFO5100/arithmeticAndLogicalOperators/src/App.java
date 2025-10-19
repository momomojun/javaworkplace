import java.util.Scanner;

public class App {
    // Write and run the program that performs as follows:
    // Q1: As the program starts, its prompts in the terminal “Enter any word:”
    // You enter a word and then press the “Enter” key.
    // Q2: The program calculates the length of the entered word (suppose it is Y)
    // and
    // the time passed between the prompt output in item (a) and you pressed “Enter”
    // key in item (b) (suppose the time lapsed is Z seconds)
    // Q3: If you press “Enter” key without typing a word, the system types the
    // output
    // “You did not enter any word” and stops. If you entered a not empty word, then
    // the length of the word is less or equal 5, the word is classified as “short”,
    // if the length of the word is between 5 and 10, the word is classified as
    // “medium”, otherwise the word is classified and “long”.
    // Q4: If you entered word is not empty, the program output consists of four
    // lines
    // as follows:
    // Your word is XXX
    // It is a short/medium/long word
    // The length of the word is Y
    // Your reaction time is Z seconds
    public static void main(String[] args) throws Exception {
        // Q1: As the program starts, its prompts in the terminal “Enter any word:”
        // You enter a word and then press the “Enter” key.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter any word:");
        // Q2: The program calculates the length of the entered word (suppose it is Y)
        // and the time passed between the prompt output in item (a) and you pressed
        // “Enter”
        // key in item (b) (suppose the time lapsed is Z seconds)
        long startTime = System.currentTimeMillis();
        String input = scanner.nextLine();
        long endTime = System.currentTimeMillis();
        // Q3: If you press “Enter” key without typing a word, the system types the
        // output
        // “You did not enter any word” and stops. If you entered a not empty word, then
        // the length of the word is less or equal 5, the word is classified as “short”,
        // if the length of the word is between 5 and 10, the word is classified as
        // “medium”, otherwise the word is classified and “long”.
        if (input.isEmpty()) {
            System.out.println("You did not enter any word");
        } else {
            int length = input.length();
            String wordLengthLevel = "";
            if (length <= 5) {
                wordLengthLevel = "short";
            } else if (length <= 10) {
                wordLengthLevel = "medium";
            } else if (length > 10){
                wordLengthLevel = "long";
            }
            // Q4: If you entered word is not empty, the program output consists of four
            // lines as follows:
            // Your word is XXX
            // It is a short/medium/long word
            // The length of the word is Y
            // Your reaction time is Z seconds
            // Calculate reaction time in seconds
            // we need to divide by 1000.0 to get a double value
            // if we divide by 1000, it will be an integer division
            double reactionTime = (endTime - startTime) / 1000.0;
            System.out.println("Your word is " + input);
            System.out.println("It is a " + wordLengthLevel + " word");
            System.out.println("The length of the word is " + length);
            System.out.printf("Your reaction time is %.2f seconds%n", reactionTime);
        }
        scanner.close();
    }
}
