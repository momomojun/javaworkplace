package com.example;

// Q1:The program describes animals.
// Animals can be birds, land, fish animals
// Q2:Design and develop a program that describes the animals using their common
// features (like typical size, typical weight, predator or vegetarian and some
// other features) and features specific for fish, birds, and land animals only
// (like species name, number of legs, wingspan, number of fins, etc.).
// Q3: Classes “Bird”, “LandAnimal”, and “Fish” are derived from class “Animals”

// Q2:Design and develop a program that describes the animals using their common
// features (like typical size, typical weight, predator or vegetarian and some
// other features) and features specific for fish, birds, and land animals only
// (like species name, number of legs, wingspan, number of fins, etc.).
class Animals {
    String speciesName;
    double typicalSize;// meters
    double typicalWeight;// kg
    String features;

    public Animals(String speciesName, double typicalSize, double typicalWeight, String features) {
        this.speciesName = speciesName;
        this.typicalSize = typicalSize;
        this.typicalWeight = typicalWeight;
        this.features = features;
    }
}
//Q3: Classes “Bird” derived from class “Animals”
class Bird extends Animals {
    double wingspan;

    public Bird(String speciesName, double typicalSize, double typicalWeight, String features, double wingspan) {
        super(speciesName, typicalSize, typicalWeight, features);
        this.wingspan = wingspan;
    }
}
//Q3: Classes “LandAnimal” derived from class “Animals”
class LandAnimal extends Animals {
    int numberOfLegs;

    public LandAnimal(String speciesName, double typicalSize, double typicalWeight, String features, int numberOfLegs) {
        super(speciesName, typicalSize, typicalWeight, features);
        this.numberOfLegs = numberOfLegs;
    }
}
//Q3: Classes “Fish” derived from class “Animals”
class Fish extends Animals {
    int numberOfFins;

    public Fish(String speciesName, double typicalSize, double typicalWeight, String features, int numberOfFins) {
        super(speciesName, typicalSize, typicalWeight, features);
        this.numberOfFins = numberOfFins;
    }
}
// Q1:The program describes animals.
// Animals can be birds, land, fish animals
// Main class
public class App {
    public static void main(String[] args) {
        Bird haliaeetusLeucocephalus = new Bird("HaliaeetusLeucocephalus", 1.1, 5.5, "Predator", 2.1);
        LandAnimal tiger = new LandAnimal("Tiger", 2.3, 250.0, "Predator", 4);
        Fish shark = new Fish("Shark", 1.5, 4500.0, "Predator", 7);

        System.out.println("Bird: " + haliaeetusLeucocephalus.speciesName + ", Size: " + haliaeetusLeucocephalus.typicalSize + " meters, Weight: " + haliaeetusLeucocephalus.typicalWeight + " kg, Features: " + haliaeetusLeucocephalus.features + ", Wingspan: " + haliaeetusLeucocephalus.wingspan + " meters");
        System.out.println("Land Animal: " + tiger.speciesName + ", Size: " + tiger.typicalSize + " meters, Weight: " + tiger.typicalWeight + " kg, Features: " + tiger.features + ", Number of Legs: " + tiger.numberOfLegs);
        System.out.println("Fish: " + shark.speciesName + ", Size: " + shark.typicalSize + " meters, Weight: " + shark.typicalWeight + " kg, Features: " + shark.features + ", Number of Fins: " + shark.numberOfFins);
    }
}
