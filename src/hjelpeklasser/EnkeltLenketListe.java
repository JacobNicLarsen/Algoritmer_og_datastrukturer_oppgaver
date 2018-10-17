package hjelpeklasser;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class EnkeltLenketListe<T> implements Liste<T>, Kø<T> {


    private static final class Node<T>       // en indre nodeklasse
    {
        private T verdi;                       // nodens verdi
        private Node<T> neste;                 // den neste noden

        private Node(T verdi, Node<T> neste)    // konstruktør
        {
            this.verdi = verdi;
            this.neste = neste;
        }
    }  // Node

    private Node<T> hode, hale;  // pekere til første og siste node

    private int antall;          // antall verdier/noder i listen

    public EnkeltLenketListe()   // standardkonstruktør
    {
        hode = hale = null;        // hode og hale til null
        antall = 0;                // ingen verdier - listen er tom
    }

    public EnkeltLenketListe(T[] a)
    {
        this();  // alle variabelene er nullet

        // Finner den første i a som ikke er null
        int i = 0; for (; i < a.length && a[i] == null; i++);

        if (i < a.length)
        {
            Node<T> p = hode = new Node<>(a[i], null);  // den første noden
            antall = 1;                                 // vi har minst en node

            for (i++; i < a.length; i++)
            {
                if (a[i] != null)
                {
                    p = p.neste = new Node<>(a[i], null);   // en ny node
                    antall++;
                }
            }
            hale = p;
        }
    }

    @Override
    public boolean leggInn(T verdi)   // verdi legges bakerst
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) hode = hale = new Node<>(verdi, null);  // tom liste
        else hale = hale.neste = new Node<>(verdi, null);         // legges bakerst

        antall++;        // en mer i listen
        return true;     // vellykket innlegging
    }

    @Override
    public T kikk() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return hent(0);    // henter den første
    }

    @Override
    public T taUt() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return fjern(0);   // returnerer (og fjerner) den første
    }

    @Override
    public void leggInn(int indeks, T verdi)    // verdi til posisjon indeks
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);        // true: indeks = antall er lovlig

        if (indeks == 0)                     // ny verdi skal ligge først
        {
            hode = new Node<>(verdi, hode);    // legges først
            if (antall == 0) hale = hode;      // hode og hale peker på samme node
        } else if (indeks == antall)           // ny verdi skal ligge bakerst
        {
            hale = hale.neste = new Node<>(verdi, null);  // legges bakerst
        } else {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) p = p.neste;

            p.neste = new Node<>(verdi, p.neste);  // verdi settes inn i listen
        }

        antall++;                            // listen har fått en ny verdi
    }

    @Override
    public boolean inneholder(T t) {
        return (indeksTil(t) != -1);
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T t) {

        if(t == null) return -1;

        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            if(p.verdi.equals(t)) return i;
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T t) {
        Objects.requireNonNull(t);
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = t;

        return gammelVerdi;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if(indeks == 0) {
            temp = hode.verdi;
            hode = hode.neste;
            if(antall == 1) hale = null;
        }
        else{
            Node<T> p = finnNode(indeks -1);
            Node<T> q = p.neste;
            temp = q.verdi;

            if(q == hale) hale = p;
            p.neste = q.neste;
        }
        antall--;
        return temp;
    }

    @Override
    public boolean fjern(T t) {
        if(t == null) return false;

        Node<T> q = hode, p = null;

        while (q !=null){
            if(q.verdi.equals(t)) break;
            p = q;
            q = q.neste;
        }

        if(q == null) return false;
        else if(q == hode) hode = hode.neste;
        else p.neste = q.neste;

        if(q == hale) hale = p;

        q.verdi = null;
        q.neste = null;

        antall--;

        return true;
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
        Node<T> p = hode, q = null;

        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }


        hode = hale = null;
        antall = 0;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom())
        {
            Node<T> p = hode;
            s.append(p.verdi);

            p = p.neste;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }

        s.append(']');

        return s.toString();
    }

    private Node<T> finnNode(int indeks)
    {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) p = p.neste;
        return p;
    }
}
