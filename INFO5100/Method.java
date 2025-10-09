//package INFO5100;

public class Method {
    //inverse
    // capital letter bigger ,other lower
    //prints the original and resultant arrays
    //Run this method by calling it twice in the same program for two different arrays shown below 
    //without changing the code in the method

    //inverse
    public static String[] inversearray(String[] name){
        int i, j;
        //int stringlength = name[0].length(); //single array length,"Anne" is 4 
        //nonono!!! the length is unstatic in name[i],anne is 4, but jessica is 7!!!
        int numlength = name.length; // the num of all array,"Anne","John" is 2
        String[] result = new String[numlength];
        //char tempchar;
        //String tempname;
        for(i = 0; i < numlength; i++){
            result[i] = "";
            int stringlength = name[i].length();
            for(j = stringlength - 1; j >= 0; j--){
                result[i] = result[i] + name[i].charAt(j);
            }
        }
        return result;
    }

    //lower and upper
    public static String[] lowerupper(String[] name){
        String[] result = new String[name.length];
        for(int i = 0; i < name.length; i++){
            result[i] = name[i].toLowerCase();
            String capital = result[i].substring(0, 1).toUpperCase() + result[i].substring(1);
            result[i] = capital;
        }
        return result;
    }

    //Output need inverse
    public static void outputString(String[] name, int type){
        if(type == 1){
            System.out.println("Original array:");
            for(int i = 0; i < name.length; i++){
                System.out.print("\"" + name[i] + "\"");
                if(i != name.length - 1){
                    System.out.println(",");
                }
                else{
                    System.out.println();
                }
            }
        }
        else if(type == 2){
            System.out.println("Resultant array:");
            for(int i = 0; i < name.length; i++){
                System.out.print("\"" + name[i] + "\"");
                if(i != name.length - 1){
                    System.out.println(",");
                }
                else{
                    System.out.println();
                }
            }
        }
        System.out.println("End of the array");
        System.out.println();
    }
    // inverseorder
    public static String[] inverseorder(String[] name){
        String[] result = new String[name.length];
        int j = name.length;
        for(int i = 0; i < name.length; i++){
            result[i] = name[--j];
        }
        return result;
    }
    
    //process
    public static void process(String[] name){
        String[] temp = new String[name.length];
        temp = name.clone();
        name = inversearray(name);
        name = lowerupper(name);
        name = inverseorder(name);
        outputString(temp, 1);
        outputString(name, 2);
    }
    public static void main(String[] args){
        String[] name1 = {"Anne", "John", "Alex", "Jessica"};
        String[] name2 = {"Sun", "Mercury", "Venis", "Earth", "Mars", "Jupiter"};
        
        process(name1);
        process(name2);
    }
}
