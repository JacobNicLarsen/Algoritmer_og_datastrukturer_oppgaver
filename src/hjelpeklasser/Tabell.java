package hjelpeklasser;

import java.util.*;

public class Tabell {

    private Tabell(){}


    /**
     * Static method to changing two parameters in an int array
     * @param a Array
     * @param i index 1
     * @param j index 2
     */
    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Static method making a randomized array
     * @param n number of indexes in the array
     * @return returns the new array
     */
    public static int[] randPerm(int n)  // en effektiv versjon
    {
        Random r = new Random();         // en randomgenerator
        int[] a = new int[n];            // en tabell med plass til n tall

        Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

        for (int k = n - 1; k > 0; k--)  // løkke som går n - 1 ganger
        {
            int i = r.nextInt(k+1);        // en tilfeldig tall fra 0 til k
            bytt(a,k,i);                   // bytter om
        }

        return a;                        // permutasjonen returneres
    }


    /**
     * Static method randomizing an existing array
     * @param a Array
     */
    public static void randPerm(int[] a)  // stokker om a
    {
        Random r = new Random();     // en randomgenerator

        for (int k = a.length - 1; k > 0; k--)
        {
            int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
            bytt(a,k,i);
        }
    }

    /**
     * Static method finding the index of the highest number in an array from index, to index
     * @param a Array
     * @param fra from
     * @param til to
     * @return returns the index of the highest number
     */
    public static int maks(int[] a, int fra, int til)
    {

        if (fra < 0 || til > a.length || fra >= til)
        {
            throw new IllegalArgumentException("Illegalt intervall!");
        }


        fratilKontroll(a.length, fra, til);
        //vhKontroll(a.length,fra,til);

        int m = fra;              // indeks til største verdi i a[fra:til>
        int maksverdi = a[fra];   // største verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++)
        {
            if (a[i] > maksverdi)
            {
                m = i;                // indeks til største verdi oppdateres
                maksverdi = a[m];     // største verdi oppdateres
            }
        }

        return m;  // posisjonen til største verdi i a[fra:til>
    }

    /**
     * Static method finding the highest number in an array
     * @param a Array
     * @return returns the index of the highest number
     */
    public static int maks(int[] a)  // bruker hele tabellen
    {
        return maks(a,0,a.length);     // kaller metoden over
    }

    /**
     * Static method for returning the index of the lowest number in an array from index, to index
     * @param a array
     * @param fra from index
     * @param til to index
     * @return index of lowest valuable
     */
    public static int min(int[] a, int fra, int til){
        if(fra < 0 || til > a.length || fra >= til){
            throw new IllegalArgumentException("Illegal Argument!");
        }

        fratilKontroll(a.length, fra, til);

        int min = fra;
        int minverdi = a[fra];

        for(int i = fra + 1; i < til; i++){
            if(a[i] < minverdi){
                min = i;
                minverdi = a[min];
            }
        }
        return min;
    }


    /**
     * Returns index of lowest valuable in an array
     * @param a Array
     * @return index of lowest valuable
     */
    public static int min(int[] a){
        return min(a, 0, a.length);
    }

    /**
     * Static method to change the index of two parameters in an char array
     * @param a Array
     * @param i index 1
     * @param j index 2
     */
    public static void bytt(char[] a, int i, int j){
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Prints out array from index to index
     * @param a Array
     * @param i start index
     * @param j end index
     */
    public static void skriv(int[] a, int i, int j){
        if(i < 0 || j > a.length || i >= j){
            throw new IllegalArgumentException("Illegal Argument!");
        }

        for(int k = i; k < j; k++){
            System.out.print(a[k] + " ");
        }
    }

    /**
     * Prints out whole array
     * @param a array
     */
    public static void skriv(int[] a){
        for(int element : a) System.out.print(element + " ");
    }

    /**
     * Prints out Array from index to index with linebreak
     * @param a Array
     * @param i from index
     * @param j to index
     */
    public static void skrivln(int[] a, int i, int j){
        if(i < 0 || j > a.length || i >= j){
            throw new IllegalArgumentException("Illegal Argument!");
        }

        for(int k = i; k < j; k++){
            System.out.print(a[k] + " ");
        }
        System.out.println();
    }

    /**
     * Prints out whole array with linebreak at end
     * @param a Array
     */
    public static void skrivln(int[] a){
        for(int element : a) System.out.print(element + " ");
        System.out.println();
    }

    /**
     * Controlls the from, to values in the array throwing an exception
     * @param tablengde Length
     * @param fra from index
     * @param til to index
     */
    public static void fratilKontroll(int tablengde, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new ArrayIndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > tablengde)                          // til er utenfor tabellen
            throw new ArrayIndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + tablengde + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    /**
     * Test if closed table interval is legal
     * @param tablengde length of table
     * @param v left index
     * @param h right index
     */
    public static void vhKontroll(int tablengde, int v, int h)
    {
        if (v < 0)
            throw new ArrayIndexOutOfBoundsException("v(" + v + ") < 0");

        if (h >= tablengde)
            throw new ArrayIndexOutOfBoundsException
                    ("h(" + h + ") >= tablengde(" + tablengde + ")");

        if (v > h + 1)
            throw new IllegalArgumentException
                    ("v = " + v + ", h = " + h);
    }

    /**
     * test if the array is null
     * @param a Array
     */
    public static void arrayControll(int a[]){
        if(a == null){
            throw new NullPointerException("Array Null");
        }
    }

    /**
     * Turns around the number in an array from index to index
     * @param a array
     * @param v left index
     * @param h right index
     */
    public static void snu(int[] a, int v, int h){

        vhKontroll(a.length, v, h);
        while (v < h) bytt(a,v++,h--);
    }

    /**
     * Turns around the number in an array
     * @param a
     */
    public static void snu(int[]a){

        int v = 0;
        int h = a.length-1;

        vhKontroll(a.length, v, h);
        while (v < h)bytt(a,v++, h++);
    }


    /**
     * Findes the index of the second max value in an array
     * @param a Array
     * @return index of second max value
     */
    public static int[] nestMaks(int[] a)
    {
        int n = a.length;   // tabellens lengde

        if (n < 2) throw   // må ha minst to verdier!
                new java.util.NoSuchElementException("a.length(" + n + ") < 2!");

        int m = maks(a);  // m er posisjonen til tabellens største verdi

        int nm;     // nm skal inneholde posisjonen til nest største verdi

        if (m == 0)                            // den største ligger først
        {
            nm = maks(a,1,n);                    // leter i a[1:n>
        }
        else if (m == n-1)                     // den største ligger bakerst
        {
            nm = maks(a,0,n-1);                  // leter i a[0:n-1>
        }
        else
        {
            int mv = maks(a,0,m);                // leter i a[0:m>
            int mh = maks(a,m+1,n);              // leter i a[m+1:n>
            nm = a[mh] > a[mv] ? mh : mv;        // hvem er størst?
        }

        return new int[] {m,nm};      // m i posisjon 0 , nm i posisjon 1

    } // nestMaks

    /**
     * Sorts array from lowest to heighest
     * @param a Array
     * @return Sorted array
     */
    public static int[] sort(int[] a){

        if (a.length < 2) throw   // må ha minst to verdier!
                new java.util.NoSuchElementException("a.length(" + a.length + ") < 2!");

        int m;

        for(int k = a.length; k > 1; k--){

            m = maks(a,0,k);
            bytt(a,k - 1,m);
        }

        return a;
    }


}
