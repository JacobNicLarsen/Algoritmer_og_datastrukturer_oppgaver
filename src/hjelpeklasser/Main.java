package hjelpeklasser;

//import javafx.scene.control.Tab;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] a = Tabell.randPerm(20); // en tabell
        int antall = 10;                                   // antall verdier


        Tabell.innsettingssortering(a,2,10);

        System.out.println(Arrays.toString(a));

    }
}
