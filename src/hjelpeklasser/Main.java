package hjelpeklasser;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hei algDat");


        //int b[] = {1,2,3,4,5,6,7};
        //int[] a = Tabell.randPerm(20);
        /*
        for(int k : a) System.out.println(k + " ");

        int m = Tabell.maks(a);

        System.out.println("\nStørste verdien ligger på plass " + m);

        */
        //System.out.println(Tabell.maks(b, 4,6));


        /*
        Tabell.printArray(b);
        Tabell.snu(b,2,4);
        Tabell.printArray(b);
        */


        int[] c = {6,2,4,3,1,5};
        //int[] a = Tabell.randPerm(20); // tilfeldig permutasjon av 1 . . 20
        int[] b = Tabell.sort(c);  // metoden returnerer en tabell

        Tabell.skriv(b);

    }



}
