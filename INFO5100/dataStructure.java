import java.util.ArrayList;
import java.util.Arrays;
//Q1: Given String str = “Oakland”; Write a code to
//find the length of the string
//find a character with index 2 in the string.
//extract a substring “land” from str
//convert all letters in str to capital letters “OAKLAND”.
//Q2: Given int[] abc = {1,3,5,2,5}; Write a code to
//find the length of the array
//find the last member of the array.
//Q3: Create an ArrayList that consists of the following strings:
// “Austin”, “Houston”, “Oakland”, “Paris”, “San Francisco”, “Seattle”. Remove “Paris’ from the ArrayList.
public class dataStructure {
    public static void main(String agrs[]) {
        // Q1: Given String str = “Oakland”; Write a code to
        // find the length of the string
        // find a character with index 2 in the string.
        // extract a substring “land” from str
        // convert all letters in str to capital letters “OAKLAND”.
        String str = "Oakland";
        System.out.println("Length of the string: " + str.length());
        System.out.println("Character at index 2: " + str.charAt(2));
        System.out.println("Substring 'land': " + str.substring(3));
        System.out.println("Uppercase: " + str.toUpperCase());
        // Q2: Given int[] abc = {1,3,5,2,5}; Write a code to
        // find the length of the array
        // find the last member of the array.
        int[] abc = {1, 3, 5, 2, 5};
        System.out.println("Length of the array: " + abc.length);
        System.out.println("Last member of the array: " + abc[abc.length - 1]);
        // Q3: Create an ArrayList that consists of the following strings:
        // “Austin”, “Houston”, “Oakland”, “Paris”, “San Francisco”,
        // “Seattle”. Remove “Paris’ from the ArrayList.
        ArrayList<String> cities = new ArrayList<>(
            Arrays.asList("Austin", "Houston", "Oakland", "Paris", "San Francisco", "Seattle"));
        System.out.println("Original ArrayList: " + cities);
        cities.remove("Paris");
        System.out.println("ArrayList after removing 'Paris': " + cities);
    }
}
