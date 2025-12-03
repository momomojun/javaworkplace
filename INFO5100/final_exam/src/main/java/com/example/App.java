package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App 
{
    public static void main( String[] args )
    {
        String filePath = "C:\\Users\\ZJ\\Desktop\\myFile.txt";

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            
            writer.write(" A new string");
            writer.close();
            
            System.out.println("We make it!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
