package hjelpeklasser;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TabellListe<T> implements Liste<T> {

    private T[]a;
    private int antall;
    private int endringer;

    @SuppressWarnings("unchecked")          // pga. konverteringen: Object[] -> T[]
    public TabellListe(int størrelse)       // konstruktør
    {
        a = (T[])new Object[størrelse];       // oppretter tabellen
        antall = 0;                           // foreløpig ingen verdier
    }

    public TabellListe()                    // standardkonstruktør
    {
        this(10);                             // startstørrelse på 10
    }

    public TabellListe(T[] b)                    // en T-tabell som parameter
    {
        this(b.length);                            // kaller den andre konstruktøren

        for (T verdi : b)
        {
            if (verdi != null) a[antall++] = verdi;  // hopper over null-verdier
        }
    }


    public int antall() {
        return antall;
    }

    public boolean tom(){
        return antall == 0;
    }

    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);   // false: indeks = antall er ulovlig
        return a[indeks];                // returnerer er tabellelement
    }

    public int indeksTil(T verdi){
        for (int i = 0; i < antall; i++) {
            if(a[i].equals(verdi)) return i;
        }
        return -1;
    }


    @Override
    public String toString(){
        if(tom()) return "[]";

        StringBuilder j = new StringBuilder();
        j.append('[').append(a[0]);

        for(int i = 1; i < antall; i++){
            j.append(',').append(' ').append(a[i]);
        }
        j.append(']');
        return j.toString();
    }





    @SuppressWarnings("unchecked")
    public void nullstill() {
        if(antall > 10)   a = (T[])new Object[10];
        else for (int i = 0; i < antall; i++) a[i] = null;

        antall = 0;
        endringer++;
    }


    @Override
    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }


    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig");
        indeksKontroll(indeks, false);

        T gammel = a[indeks];
        a[indeks] = verdi;

        endringer++;

        return gammel;
    }

    @Override
    public boolean fjern(T verdi) {
        Objects.requireNonNull(verdi,"null er ulovlig");

        Iterator<T> iterator = iterator();

        for (int i = 0; i < antall; i++) {
            if(a[i].equals(verdi)){
                antall--;
                System.arraycopy(a,i + 1, a, i, antall - i);

                a[antall] = null;

                endringer++;
                return true;
            }
        }

        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        T verdi = a[indeks];

        antall--;
        System.arraycopy(a,indeks + 1, a, indeks, antall - indeks);
        a[antall] = null;

        return verdi;
    }


    public boolean inneholder(T verdi){
        return indeksTil(verdi) != -1;
    }


    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig");

        if(antall == a.length){
            utvid();
        }

        a[antall++] = verdi;
        endringer ++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi,"null er ulovlig");

        if(antall == a.length) utvid();

        System.arraycopy(a,indeks,a,indeks + 1, antall - indeks);

        a[indeks] = verdi;
        antall ++;

        endringer ++;
    }

    private void utvid(){
        a = Arrays.copyOf(a,(3*antall/2 + 1));
    }

    public boolean fjernHvis(Predicate<? super T> p){

        Objects.requireNonNull(p);
        int nyttAntall = antall;

        for (int i = 0, j = 0; j < antall; j++){
            if(p.test(a[j])) nyttAntall--;
            else a[i++] = a[j];
        }

        for(int i = nyttAntall; i<antall; i++)a[i] = null;

        boolean fjernet = nyttAntall < antall;

        antall = nyttAntall;
        return fjernet;
    }

    public void forEach(Consumer<? super T> action){
        for (int i = 0; i < antall; i++) {
            action.accept(a[i]);
        }
    }





    private class TabellListeIterator implements Iterator<T>{
        private int denne = 0;
        private boolean fjernOK = false;
        private int iteratorendringer = endringer;

        @Override
        public boolean hasNext() {
            return denne < antall;
        }

        @Override
        public T next() {

            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException("listen er endret!");

            if(!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");

            T denneVerdi = a[denne];
            denne++;
            fjernOK = true;
            return denneVerdi;
        }

        public void remove(){
            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException("listen er endret!");

            if(!fjernOK) throw new IllegalStateException("Ulovelig tilstand");

            fjernOK = false;

            antall--;
            denne--;

            System.arraycopy(a,denne + 1, a, denne, antall - denne);

            a[antall] = null;

            endringer++;
            iteratorendringer++;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action){
            while (denne < antall){
                action.accept(a[denne++]);
            }
        }
    }



}
