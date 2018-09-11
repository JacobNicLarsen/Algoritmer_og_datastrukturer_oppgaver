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
    public static void snu(int[] a, int v, int h)  // snur intervallet a[v:h]
    {
        vhKontroll(a.length,v,h);
        while (v < h) bytt(a, v++, h--);
    }

    /**
     * Turns around the number in an array
     * @param a
     */
    public static void snu(int[]a){

       snu(a,0,a.length-1);
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

    /**
     * Fines the second max value in an array using binary tree
     * @param a Array
     * @return second max value
     */
    public static int[] nestMaksTree(int[] a)   // en turnering
    {
        int n = a.length;                // for å forenkle notasjonen

        if (n < 2) // må ha minst to verdier!
            throw new IllegalArgumentException("a.length(" + n + ") < 2!");

        int[] b = new int[2*n];          // turneringstreet
        System.arraycopy(a,0,b,n,n);     // legger a bakerst i b

        for (int k = 2*n-2; k > 1; k -= 2)   // lager turneringstreet
            b[k/2] = Math.max(b[k],b[k+1]);

        int maksverdi = b[1], nestmaksverdi = Integer.MIN_VALUE;

        for (int m = 2*n - 1, k = 2; k < m; k *= 2)
        {
            int tempverdi = b[k+1];  // ok hvis maksverdi er b[k]
            if (maksverdi != b[k]) { tempverdi = b[k]; k++; }
            if (tempverdi > nestmaksverdi) nestmaksverdi = tempverdi;
        }

        return new int[] {maksverdi,nestmaksverdi}; // størst og nest størst

    } // nestMaks

    public static void kopier(int[] a, int i, int[] b, int j, int ant){
        for (int n = i + ant; i < n; ) b[j++] = a[i++];
    }
    //1.3.1
    public static void snu(int[]a, int v){
        snu(a,v,a.length);
    }



    // Array out of boundsIndexBug

    public static boolean nestePermutasjon(int[] a)
    {
        int i = a.length - 2;                    // i starter nest bakerst
        while (i >= 0 && a[i] > a[i + 1]) i--;   // går mot venstre
        if (i < 0) return false;                 // a = {n, n-1, . . . , 2, 1}

        int j = a.length - 1;                    // j starter bakerst
        while (a[j] < a[i]) j--;                 // stopper når a[j] > a[i]
        bytt(a,i,j); snu(a,i + 1);               // bytter og snur

        return true;                             // en ny permutasjon
    }


    /**
     * Returnerer antall invensjoner i en tabell a
     * @param a Array
     * @return antall invensjoner
     */
    public static int inversjoner(int[] a)
    {
        int antall = 0;  // antall inversjoner
        for (int i = 0; i < a.length - 1; i++)
        {
            for (int j = i + 1; j < a.length; j++)
            {
                if (a[i] > a[j]) antall++;  // en inversjon siden i < j
            }
        }
        return antall;
    }


    /**
     * Sjekker om en tabell er sortert
     * @param a Array
     * @return if the array is sorted
     */
    public static boolean erSortert(int[] a)  // legges i samleklassen Tabell
    {
        for (int i = 1; i < a.length; i++)      // starter med i = 1
            if (a[i-1] > a[i]) return false;      // en inversjon

        return true;
    }


    /**
     * Bobbler opp det storste tallet i en array og returnerer antall byttinger
     * @param a Array
     * @return Antall byttinger
     */
    public static int boble(int[] a)      // legges i samleklassen Tabell
    {
        int antall = 0;                     // antall ombyttinger i tabellen
        for (int i = 1; i < a.length; i++)  // starter med i = 1
        {
            if (a[i - 1] > a[i])              // sammenligner to naboverdier
            {
                bytt(a, i - 1, i);              // bytter om to naboverdier
                antall++;                       // teller opp ombyttingene
            }
        }
        return antall;                      // returnerer
    }

    /**
     * Bobblesorterer en tabell
     * @param a Array
     */
    public static void boblesortering(int[] a)     // hører til klassen Tabell
    {
        for (int n = a.length; n > 1; n--)           // n reduseres med 1 hver gang
        {
            for (int i = 1; i < n; i++)                // går fra 1 til n
            {
                if (a[i - 1] > a[i]) bytt(a, i - 1, i);  // sammenligner/bytter
            }
        }
    }


    /**
     * Sorterer tabellen med utvalg
     * @param a Array
     */
    public static void utvalgssortering(int[] a)
    {
        for (int i = 0; i < a.length - 1; i++)
            bytt(a, i, min(a, i, a.length));  // to hjelpemetoder
    }

    public static void utvalgsortInternett(int arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void utvalgssortering(int[] a, int fra, int til){

        for(int i = fra; i<til; i++){
            bytt(a,i,min(a,i,til));
        }
    }


    /**
     * Kjører linjærsøk i tabellen a
     * @param a Array
     * @param verdi Int
     * @return index
     */
    public static int lineærsøk(int[] a, int verdi) // legges i class Tabell
    {
        if (a.length == 0 || verdi > a[a.length-1])
            return -(a.length + 1);  // verdi er større enn den største

        int i = 0; for(; a[i] < verdi; i++);  // siste verdi er vaktpost

        return verdi == a[i] ? i : -(i + 1);   // sjekker innholdet i a[i]
    }


    public static int lineærsøkvenstre(int[] a, int verdi){

        if(a.length == 0 || verdi > a[a.length-1])
            return -(a.length +1 );

        int i = a.length -1; for(;a[i] < verdi; i--);

        return verdi == a[i] ? i : (i + 1);
    }

    public static int lineærsøk(int[] a, int k, int verdi){

        if(a.length == 0 || verdi > a[a.length - 1])
            return -(a.length + 1);

        int i = a.length - 1; for(;a[i] < verdi; i--);

        return verdi == a[i] ? i : (i + k); /// Ikker ferdig
    }
}