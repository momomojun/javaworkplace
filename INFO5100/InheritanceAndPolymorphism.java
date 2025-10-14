public class InheritanceAndPolymorphism {
    // Design and develop the program describing different vehicles like car,
    // motorbike, aircraft, ship.
    // Q1:Start with the abstract class Vehicle and
    // Q2:the derived classes "Car","Motorbike". "Aircraft", "Ship".
    // Q3:Add the appropriate attributes and methods as you understand the problem
    // Q4:Run the program answering questions or addressing some issues (on your
    // choice)

    // Q4:Run the program answering questions
    public static void main(String[] args) {
        Car smallcar = new Car("Ford", "01", 5, "road", "gasoline");
        Motorbike motor = new Motorbike("Kawasaki", "02", 10, "road", "gasoline");
        Aircraft plane = new Aircraft("Boeing", "03", 15, "air", "aviation fuel");
        Ship luxuryShip = new Ship("Ferretti", "04", 20, "water", "diesel");

        Vehicle[] vehicles = { smallcar, motor, plane, luxuryShip };
        for (Vehicle v : vehicles) {
            v.printInformation();
            v.move();
            System.out.println();
        }

        smallcar.addFuel(50);
        smallcar.getFuelLevel();

        motor.addFuel(10);
        motor.getFuelLevel();

        plane.addFuel(100);
        plane.getFuelLevel();

        luxuryShip.addFuel(250);
        luxuryShip.getFuelLevel();

        System.out.println();

        System.out.println("Inner Class (Engine) Information");
        Car.Engine carEngine = smallcar.new Engine("V12", 700);
        carEngine.printInformation();

        Motorbike.Engine motorEngine = motor.new Engine("RSV4 1100", 220);
        motorEngine.printInformation();

        Aircraft.Engine aircraftEngine = plane.new Engine("Pw4000-100", 10000);
        aircraftEngine.printInformation();

        Ship.Engine shipEngine = luxuryShip.new Engine("Penta D6-440", 440);
        shipEngine.printInformation();

    }
}

// Q1:Start with the abstract class "Vehicle"
abstract class Vehicle {
    protected String brand;
    protected String ID;
    protected int age;
    protected String type;
    protected int fuelLevel;

    public Vehicle(String brand, String ID, int age, String type) {
        this.ID = ID;
        this.age = age;
        this.type = type;
        this.brand = brand;
        this.fuelLevel = 0;
    }

    public abstract void move();

    public void printInformation() {
        System.out.println("Vehicle name: " + brand);
        System.out.println("Vehicle ID: " + ID);
        System.out.println("Vehicle age: " + age);
        System.out.println("Vehicle type: " + type);
    }

}

interface Fuel {
    // is this variable useful ?
    // int fuelLevel = 0;
    void addFuel(int fuelAmount);

    int getFuelLevel();
}

// Q2:the derived classes "Car","Motorbike". "Aircraft", "Ship".
class Car extends Vehicle implements Fuel {
    String fueltype;

    // meet some problem about the subclass constructor
    public Car(String brand, String ID, int age, String type, String fueltype) {
        super(brand, ID, age, type);
        this.fueltype = fueltype;
        this.fuelLevel = 0;
    }

    // do I need to add static ? about the method from interface
    public void addFuel(int fuelAmount) {
        fuelLevel = fuelLevel + fuelAmount;
    }

    public int getFuelLevel() {
        System.out.println("Now the fuel level of car is " + fuelLevel);
        return fuelLevel;
    }

    // do I need to add static ? about the method from superclass
    public void move() {
        System.out.println(brand + " car is driving to the destination");
    }

    // inner class
    class Engine {
        String name;
        int horsepower;

        public Engine(String name, int horsepower) {
            this.name = name;
            this.horsepower = horsepower;
        }

        public void printInformation() {
            System.out.println("The engine of the car is " + name + " and the horsepower is " + horsepower);
        }

    }
}

// Q2:the derived classes "Car","Motorbike". "Aircraft", "Ship".
class Motorbike extends Vehicle implements Fuel {
    String fueltype;

    public Motorbike(String brand, String ID, int age, String type, String fueltype) {
        super(brand, ID, age, type);
        this.fueltype = fueltype;
        this.fuelLevel = 0;
    }

    public void addFuel(int fuelAmount) {
        fuelLevel = fuelLevel + fuelAmount;
    }

    public int getFuelLevel() {
        System.out.println("Now the fuel level of motorbike is " + fuelLevel);
        return fuelLevel;
    }

    public void move() {
        System.out.println(brand + " motorbike is driving to the destination");
    }

    // inner class
    class Engine {
        String name;
        int horsepower;

        public Engine(String name, int horsepower) {
            this.name = name;
            this.horsepower = horsepower;
        }

        public void printInformation() {
            System.out.println("The engine of the motorbike is " + name + " and the horsepower is " + horsepower);
        }

    }
}

// Q2:the derived classes "Car","Motorbike". "Aircraft", "Ship".
class Aircraft extends Vehicle implements Fuel {
    String fueltype;

    public Aircraft(String brand, String ID, int age, String type, String fueltype) {
        super(brand, ID, age, type);
        this.fueltype = fueltype;
        this.fuelLevel = 0;
    }

    public void addFuel(int fuelAmount) {
        fuelLevel = fuelLevel + fuelAmount;
    }

    public int getFuelLevel() {
        System.out.println("Now the fuel level of aircraft is " + fuelLevel);
        return fuelLevel;
    }

    public void move() {
        System.out.println(brand + " aircraft is flying to the destination");
    }

    class Engine {
        String name;
        int horsepower;

        public Engine(String name, int horsepower) {
            this.name = name;
            this.horsepower = horsepower;
        }

        public void printInformation() {
            System.out.println("The engine of the aircraft is " + name + " and the horsepower is " + horsepower);
        }

    }
}

// Q2:the derived classes "Car","Motorbike". "Aircraft", "Ship".
class Ship extends Vehicle implements Fuel {
    String fueltype;

    public Ship(String brand, String ID, int age, String type, String fueltype) {
        super(brand, ID, age, type);
        this.fueltype = fueltype;
        this.fuelLevel = 0;
    }

    public void addFuel(int fuelAmount) {
        fuelLevel = fuelLevel + fuelAmount;
    }

    public int getFuelLevel() {
        System.out.println("Now the fuel level of ship is " + fuelLevel);
        return fuelLevel;
    }

    public void move() {
        System.out.println(brand + " ship is sailing to the destination");
    }

    class Engine {
        String name;
        int horsepower;

        public Engine(String name, int horsepower) {
            this.name = name;
            this.horsepower = horsepower;
        }

        public void printInformation() {
            System.out.println("The engine of the ship is " + name + " and the horsepower is " + horsepower);
        }

    }
}