package hjelpeklasser;

import eksempelklasser.Komparator;

import java.util.NoSuchElementException;

public class TabellKø<T> implements Kø<T>
{
    private T[] a;      // en tabell
    private int fra;    // posisjonen til den første i køen
    private int til;    // posisjonen til første ledige plass

    @SuppressWarnings("unchecked")      // pga. konverteringen: Object[] -> T[]
    public TabellKø(int lengde)
    {
        if (lengde < 1)
            throw new IllegalArgumentException("Må ha positiv lengde!");

        a = (T[])new Object[lengde];

        fra = til = 0;    // a[fra:til> er tom
    }

    public TabellKø()   // standardkonstruktør
    {
        this(8);
    }


    @Override
    public boolean leggInn(T t) {
        a[til] = t;                       // ny verdi bakerst i køen
        til++;                            // øker til med 1
        if (til == a.length) til = 0;     // hopper til 0
        if (fra == til)                   // sjekker om tabellen er full
            a = utvidTabell(2*a.length);    // dobler tabellen
        return true;
    }

    private T[] utvidTabell(int lengde){
        @SuppressWarnings("unchecked")
        T[] b = (T[])new Object[lengde];

        // kopierer intervallet a[fra:a.length> over i b
        System.arraycopy(a,fra,b,0,a.length-fra);

        // kopierer intervallet a[0:fra> over i b
        System.arraycopy(a,0,b,a.length-fra, fra);

        fra = 0; til = a.length;

        return b;
    }

    @Override
    public T kikk() {
        if(fra == til)
            throw new NoSuchElementException("Køen er tom! Går ikke ann å kikke");
        return a[fra];
    }

    @Override
    public T taUt() {
        if (fra == til)
            throw new NoSuchElementException("Køen er tom! Går ikke ann å ta ut");

        T temp = a[fra];
        a[fra] = null;
        fra++;
        if (fra == a.length) fra = 0;
        return temp;
    }

    @Override
    public int antall() {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    @Override
    public boolean tom() {
        return til == fra;
    }

    @Override
    public void nullstill() {
        while (fra != til){
            a[fra++] = null;
            if (fra == a.length) fra = 0;
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append('[');

        int sFra = fra, sTil = til;

        if (!(fra == til)){
            s.append(a[sFra++]);
            while (sFra!= sTil){
                s.append(',').append(' ').append(a[sFra++]);
                if(sFra == a.length) sFra = 0;
            }
        }

        s.append(']');
        return s.toString();
    }

    public int indeksTil(T verdi){
        int indeks = fra;
        while (!(indeks == til)){
            if(verdi.equals(a[indeks])) return fra <= indeks ? indeks - fra : a.length + indeks - fra;

            indeks++;
            if (indeks == a.length) indeks = 0;
        }

        return -1;
    }

    public static <T> void snu(Stakk<T> A)
    {
        Kø<T> B = new TabellKø<T>();
        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static <T> void snu(Kø<T> A){
        Stakk<T> B = new TabellStakk<>();
        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static <T> void snuToQue(Kø<T> A){
        Kø<T> B = new TabellKø<>();
        Kø<T> C = new TabellKø<>();

        while (!A.tom()){
            while (!B.tom()) C.leggInn(B.taUt());
            B.leggInn(A.taUt());
            while (!C.tom()) B.leggInn(C.taUt());
        }
        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static <T> void snuOneQuqVar(Kø<T> A){
        Kø<T> B = new TabellKø<T>();
        int n = A.antall() - 1;

        while (n >=0){
            for (int i = 0; i < n; i++) A.leggInn(A.taUt());
            B.leggInn(A.taUt());
            n--;
        }

        while (!B.tom()) A.leggInn(B.taUt());
    }

    public static <T> void sorter(Kø<T> A, Komparator<? super T> c){
        if (A.antall() <= 1) return;

        Kø<T> B = new TabellKø<T>();
        Kø<T> C = new TabellKø<T>();

        B.leggInn(A.taUt());

        while (!A.tom())
        {
            T averdi = A.taUt();
            while (!B.tom() && c.compare(B.kikk(),averdi) < 0)
                C.leggInn(B.taUt());

            C.leggInn(averdi);

            while (!B.tom()) C.leggInn(B.taUt());
            Kø<T> D = B; B = C; C = D;
        }

        while (!B.tom()) A.leggInn(B.taUt());
    }
} // class TabellKø
