import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
public class App {
    // Q1: Open a Notepad application on your computer, write phrase “This is my Java
    // read test”, and save it under filename “my_test_file.txt”
    // Read this file from your Java program and print the phrase in the Java
    // terminal.
    // Q2: Enter phrase “Java write test” into your Java program from the Java terminal.
    // Q3: Save this phrase in the same file “my_test_file.txt” using your Java program,
    // Open file “my_test_file.txt” with Notepad and make sure that your last phrase
    // is written into the file.
    // Q4: Connect to the database from your Java program and read anyone record from
    // it,
    // Q5: Modify the record in the Java program.
    // Q6: Update the appropriate record in the database.
    // Q7:Make sure you restore (rollback) the previous information in the database to
    // continue your database class assignments
    public static void main(String[] args) throws Exception {
        // Q1: Open a Notepad application on your computer, write phrase “This is my Java
        // read test”, and save it under filename “my_test_file.txt”
        // Read this file from your Java program and print the phrase in the Java
        // terminal.
        String filename = "src/my_test_file.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        System.out.println(line);
        reader.close();
        // Q2: Enter phrase “Java write test” into your Java program from the Java terminal.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter phrase to write to file:");
        String input = scanner.nextLine();
        // Q3: Save this phrase in the same file “my_test_file.txt” using your Java program,
        // Open file “my_test_file.txt” with Notepad and make sure that your last phrase
        // is written into the file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.newLine();
        writer.write(input);
        System.out.println("Phrase has written to file");
        writer.close();
        scanner.close();
    }
}
