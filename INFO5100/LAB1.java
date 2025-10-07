import java.util.ArrayList;
import java.util.Arrays;

public class LAB1 {
    // 2 PART
    //First  choose the max number for z array
    //second using arraylist to switch the first letter and last letter in each name

    // choose max number of x and y array
    public static int[] maxnumber(int[] Xarray, int[] Yarray){
        int result[] = new int[Xarray.length];
        int i;

        for(i = 0; i < Xarray.length; i++){
            if(Xarray[i] >= Yarray[i]){
                result[i] = Xarray[i];
            }
            else{
                result[i] = Yarray[i];
            }
        }
        return result;
    }
    //output array
    public static void outputarray(int[] Xarray, int[] Yarray, int[] Zarray){
        System.out.print("Array x = { ");
        for(int i = 0; i < Xarray.length - 1; i++){
            System.out.print(Xarray[i]+", ");
        }
        System.out.print(Xarray[Xarray.length - 1]+" ");
        System.out.println("}");

        System.out.print("Array y = { ");
        for(int i = 0; i < Yarray.length - 1; i++){
            System.out.print(Yarray[i]+", ");
        }
        System.out.print(Yarray[Yarray.length - 1]+" ");
        System.out.println("}");

        System.out.print("Array z = x + y = { ");
        for(int i = 0; i < Zarray.length - 1; i++){
            System.out.print(Zarray[i]+", ");
        }
        System.out.print(Zarray[Zarray.length - 1]+" ");
        System.out.println("}");
    }

    //switch letter
    public static ArrayList<String> switchletter(ArrayList<String> name){
        ArrayList<String> result = new ArrayList<>();
        String tempname;
        int i, j;
        for(i = 0 ; i < name.size(); i++){
            tempname = name.get(i);
            //switch first and last letter
            //check the length, if smaller than 2, add imediately
            if(tempname.length() < 2){
                result.add(tempname);
                continue; //is the continue right ?
            }
            char lastchar = tempname.charAt(tempname.length() - 1);
            lastchar = Character.toUpperCase(lastchar);
            char firstchar = tempname.charAt(0);
            firstchar = Character.toLowerCase(firstchar);
            String midname = tempname.substring(1, tempname.length() - 1).toLowerCase();
            String resultname = new StringBuilder().append(lastchar).append(midname).append(firstchar).toString();
            result.add(resultname);
        }
        return result;
    }

    //output Arraylist
    public static void outputarraylist(ArrayList<String> name, ArrayList<String> result){
        System.out.print("Names = { ");
        for(int i = 0; i < name.size() - 1; i++){
            System.out.print(name.get(i) + ", ");
        }
        System.out.print(name.get(name.size() - 1) + " ");
        System.out.println("}");

        System.out.print("Names (switched) = { ");
        for(int i = 0; i < result.size() - 1; i++){
            System.out.print(result.get(i) + ", ");
        }
        System.out.print(result.get(result.size() - 1) + " ");
        System.out.println("}");
    }

    // remember snapshot or 
    public static void main(String[] args){
        //Part 1
        int[] Xarray = {
            1,3,5,7,9
        };
        int[] Yarray = {
            10,8,6,4,2
        };
        int[] Zarray = maxnumber(Xarray, Yarray);
        outputarray(Xarray, Yarray, Zarray);

        //Part 2
        ArrayList<String> name = new ArrayList<>(Arrays.asList("Sun", "Mercury", "Venis", "Earth", "Jupiter"));
        ArrayList<String> resultname = new ArrayList<>(switchletter(name));
        outputarraylist(name, resultname);
    }
}
