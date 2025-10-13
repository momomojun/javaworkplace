import java.util.ArrayList;

public class ClassAndOOP {
    // There are two classes "EngClass" and "Student".
    // Q1 : Class "Student" describes a student. You may use just three attributes
    // for simplicity: student ID, first name, and last name.
    // Q2 : Class "EngClass" describes an engineering class that includes students.
    // For simplicity, you may limit the attributes of this class with just a list
    // of students.
    // Q3 and Q4: In the program, you should be able to add/delete students to the engineering
    // class
    // Q5: and read the list of students.
    public static void main(String[] args) {
        EngClass Eng = new EngClass();
        Eng.addStudents("1", "Jun", "Zhai");
        Eng.addStudents("2", "Mo", "Tommy");
        Eng.addStudents("3", "John", "Spring");
        Eng.readStudents();
        Eng.deleteStudents("1");
        Eng.addStudents("4", "Jupiter", "Zeus");
        Eng.readStudents();
    }

}

// Q2:Class "EngClass" describes an engineering class that includes students.
// For simplicity, you may limit the attributes of this class with just a list
// of students.
// A2: next line
class EngClass {
    ArrayList<Student> engineeringClass;

    public EngClass() {
        engineeringClass = new ArrayList<>();
    }

    // confused about the static
    // Q3: add students to the engineering class
    // A3: next line
    public void addStudents(String studentID, String firstname, String lastname) {
        Student newStudent = new Student(studentID, firstname, lastname);
        engineeringClass.add(newStudent);
    }

    // Q4 : delete students to the engineering class
    // A4 : next line
    public void deleteStudents(String studentID) {
        for (int i = 0; i < engineeringClass.size(); i++) {
            if (engineeringClass.get(i).studentID.equals(studentID)) {
                engineeringClass.remove(i);
                break;
            }
        }
    }

    // Q5: output,read the list of students.
    // A5: next line
    public void readStudents() {
        for (Student s : engineeringClass) {
            System.out.println(s.studentID + " " + s.firstName + " " + s.lastName);
        }
        System.out.println();
    }
}

// Q1: Class "Student" describes a student. You may use just three attributes
// for simplicity: student ID, first name, and last name.
// A1: next line
class Student {
    String studentID;
    String firstName;
    String lastName;

    public Student(String sID, String fName, String lName) {
        this.studentID = sID;
        this.firstName = fName;
        this.lastName = lName;
    }
}