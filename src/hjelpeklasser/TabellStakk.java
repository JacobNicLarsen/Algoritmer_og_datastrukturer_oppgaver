package hjelpeklasser;

import java.util.*;

public class TabellStakk<T> implements Stakk<T>
{
    private T[] a;                     // en T-tabell
    private int antall;                // antall verdier på stakken

    public TabellStakk()               // konstruktør - tabellengde 8
    {
        this(8);
    }

    @SuppressWarnings("unchecked")     // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde)     // valgfri tabellengde
    {
        if (lengde < 0)
            throw new IllegalArgumentException("Negativ tabellengde!");

        a = (T[])new Object[lengde];     // oppretter tabellen
        antall = 0;                      // stakken er tom
    }

    @Override
    public void leggInn(T verdi) {
        if(antall == a.length)
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2*antall);

        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        return a[antall-1];    // returnerer den øverste verdien
    }

    @Override
    public T taUt() {
        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        antall--;             // reduserer antallet

        T temp = a[antall];   // tar var på det øverste objektet
        a[antall] = null;     // tilrettelegger for resirkulering

        return temp;          // returnerer den øverste verdien
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        while (antall > 0){
            a[antall] = null;
            antall--;
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append('[');

        if(!tom()){

            s.append(a[antall-1]);
            for (int i = antall - 2; i >= 0; i--) {
                s.append(',').append(' ').append(a[i]);
            }
        }

        s.append(']');

        return s.toString();
    }

    public static <T> void snu(Stakk<T> A){
        Stakk<T> B = new TabellStakk<>(A.antall());
        Stakk<T> C = new TabellStakk<>(A.antall());

        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) C.leggInn(B.taUt());
        while (!C.tom()) A.leggInn(C.taUt());
    }

    public static <T> void snu2(Stakk<T> A){

        Stakk<T> B = new TabellStakk<>(A.antall());

        for(int j = A.antall() - 1; j > 0; j--) {
            T temp = A.taUt();
            for (int i = 0; i < j ; i++) {
                B.leggInn(A.taUt());
            }
            A.leggInn(temp);
            while (!B.tom()) A.leggInn(B.taUt());
        }

    }

    public static <T> void kopier(Stakk<T> A, Stakk<T> B){
        Stakk<T> C = new TabellStakk<>(A.antall());

        while (!A.tom()) C.leggInn(A.taUt());
        while (!C.tom()){
            T temp = C.taUt();
            A.leggInn(temp);
            B.leggInn(temp);
        }
    }

    public static <T> void kopier2(Stakk<T> A, Stakk<T> B){

        T temp;
        int n = A.antall();
        while (n > 0){
            for (int i = 0; i < n; i++) B.leggInn(A.taUt());
            temp = B.kikk();
            for (int i = 0; i < n; i++) A.leggInn(B.taUt());
            B.leggInn(temp);
            n--;
        }
    }

    public static <T> void sorter(Stakk<T> A, Comparator<? super T> c){
        int n = A.antall();

        Stakk<T> B = new TabellStakk<>(n);

        while (n > 0) {
            T temp = A.taUt();


            for (int i = 0; i < n - 1; i++) {
                if (c.compare(A.kikk(), temp) > 0) {
                    B.leggInn(temp);
                    temp = A.taUt();
                }
                else{
                    B.leggInn(A.taUt());
                }
            }
            A.leggInn(temp);

            for (int i = 0; i < n - 1; i++) A.leggInn(B.taUt());
            n--;
        }
    }

    public static <T> void sorter2(Stakk<T> A, Comparator<T> c) // Kompendie sort
    {
        Stakk<T> B = new TabellStakk<T>();
        T temp; int n = 0;

        while (!A.tom())
        {
            temp = A.taUt();
            n = 0;
            while (!B.tom() && c.compare(temp,B.kikk()) < 0)
            {
                n++; A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            for (int i = 0; i < n; i++) B.leggInn(A.taUt());
        }

        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static void sjekkParenteser(String tekst, char v, char h)
    {
        Stakk<Character> stakk = new TabellStakk<Character>();

        for (int i = 0; i < tekst.length(); i++)
        {
            if (tekst.charAt(i) == v) stakk.leggInn(v); // venstre-parentes
            else if (tekst.charAt(i) == h)  // høyre-parentes
            {
                if (stakk.tom())
                {
                    System.out.println("Det mangler en venstre-parentes");
                    return;
                }
                stakk.taUt();
            }
        }

        if (!stakk.tom())
            System.out.println("Det mangler en høyre-parentes");
    }





}  // class TabellStakk
