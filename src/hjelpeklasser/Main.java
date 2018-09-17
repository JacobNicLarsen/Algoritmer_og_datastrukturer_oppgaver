package hjelpeklasser;

//import javafx.scene.control.Tab;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //System.out.println(a1(0));
        System.out.println(1234%10);
        System.out.println(tverrsum(1234));


    }

    public static int a(int n)           // n må være et ikke-negativt tall
    {
        if (n == 0) return 1;              // a0 = 1
        else if (n == 1) return 2;         // a1 = 2
        else return 2*a(n-1) + 3*a(n-2);   // to rekursive kall
    }

    public static int a1(int n){
        if(n<0) throw new IllegalArgumentException("n må være større enn 0");

        int x = 0, y = 1, z = 1;

        for (int i = 0; i < n; i++) {
            z = 2*y + 3*x;
            x = y;
            y = z;
        }

        return z;
    }

    public static int tverrsum(int n){

        int sum = 0;

        while (n > 0){
            sum += n%10;
            n /= 10;

        }
        return sum;
    }
}
