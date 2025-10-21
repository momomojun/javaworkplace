package readingfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.sql.*;

/**
 * Hello world!
 */
public class App {
    // Q1: Open a Notepad application on your computer, write phrase “This is my
    // Java
    // read test”, and save it under filename “my_test_file.txt”
    // Read this file from your Java program and print the phrase in the Java
    // terminal.
    // Q2: Enter phrase “Java write test” into your Java program from the Java
    // terminal.
    // Q3: Save this phrase in the same file “my_test_file.txt” using your Java
    // program,
    // Open file “my_test_file.txt” with Notepad and make sure that your last phrase
    // is written into the file.
    // Q4: Connect to the database from your Java program and read anyone record
    // from
    // it,
    // Q5: Modify the record in the Java program.
    // Q6: Update the appropriate record in the database.
    // Q7:Make sure you restore (rollback) the previous information in the database
    // to
    // continue your database class assignments
    public static void main(String[] args) throws Exception {
        String filename = "src/main/java/readingfile/my_test_file.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        System.out.println(line);
        reader.close();

        // Q2: Enter phrase “Java write test” into your Java program from the Java
        // terminal.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter phrase to write to file:");
        String input = scanner.nextLine();

        // Q3: Save this phrase in the same file “my_test_file.txt” using your Java
        // program,
        // Open file “my_test_file.txt” with Notepad and make sure that your last phrase
        // is written into the file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.newLine();
        writer.write(input);
        System.out.println("Phrase has written to file");
        writer.close();

        // Q4: Connect to the database from your Java program and read anyone record
        // from it,
        String url = "jdbc:sqlite:sample.db";
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false); // Enable transaction for rollback
        System.out.println("Having connected to database: sample.db");
        // Create a test table (if not exists)
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    grade INTEGER
                );
                """;
        conn.createStatement().execute(createTableSQL);
        // Insert one sample record if table is empty
        String checkSQL = "SELECT COUNT(*) FROM students";
        ResultSet rsCount = conn.createStatement().executeQuery(checkSQL);
        rsCount.next();
        if (rsCount.getInt(1) == 0) {
            String insertSQL = "INSERT INTO students (name, grade) VALUES ('Alice', 85)";
            conn.createStatement().executeUpdate(insertSQL);
            System.out.println("Inserted sample record: Alice, 85");
        }
        conn.commit();
        //read anyone record from it,
        String selectSQL = "SELECT * FROM students LIMIT 1";
        ResultSet rs = conn.createStatement().executeQuery(selectSQL);
        rs.next();
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int grade = rs.getInt("grade");
        System.out.println("\nRead record -> ID: " + id + ", Name: " + name + ", Grade: " + grade);

        // Q5: Modify the record in the Java program.
        System.out.println("\nEnter new grade for this student:");
        int newGrade = scanner.nextInt();

        // Q6: Update the appropriate record in the database.
        String updateSQL = "UPDATE students SET grade = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateSQL);
        pstmt.setInt(1, newGrade);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        System.out.println("Updated record successfully.");
        // Verify update
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM students WHERE id = " + id);
        rs2.next();
        System.out.println("New record -> ID: " + rs2.getInt("id") + ", Name: " + rs2.getString("name") + ", Grade: "
                + rs2.getInt("grade"));

        // Q7:Make sure you restore (rollback) the previous information in the database
        // to continue your database class assignments
        System.out.println("\nRolling back changes...");
        conn.rollback();
        System.out.println("Rollback complete. Changes reverted.");

        // Check rollback result
        ResultSet rs3 = conn.createStatement().executeQuery("SELECT * FROM students WHERE id = " + id);
        rs3.next();
        System.out.println("Restored record -> ID: " + rs3.getInt("id") + ", Name: " + rs3.getString("name")
                + ", Grade: " + rs3.getInt("grade"));

        conn.close();
        scanner.close();
    }
}
