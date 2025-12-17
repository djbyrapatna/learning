import java.util.ArrayList;

public class Basics{
    public static void main(String [] args){
        ArrayList<Integer>numbers = new ArrayList<>();
        for(int i=0; i <5; i++){
            numbers.add(i*2);
        }
        int sum = 0;
        for (int n: numbers){
            sum+=n;
        }
        System.out.println("Sum = "+ sum);
    }
}
