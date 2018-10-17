package hjelpeklasser;

public class LenketKø<T> implements Kø<T>  {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;        // nodens verdi
        private Node<T> neste;  // peker til neste node

        Node(Node<T> neste)     // nodekonstruktør
        {
            verdi = null; this.neste = neste;
        }
    }   // class Node

    private Node<T> fra, til;  // fra: først i køen, til: etter den siste
    private int antall;        // antall i køen

    private static final int START_STØRRELSE = 8;

    public LenketKø(int størrelse)  // konstruktør
    {
        til = fra = new Node<>(null);  // lager den første noden

        Node<T> p = fra;               // en hjelpevariabel
        for (int i = 1; i < størrelse; i++)
        {
            p = new Node<>(p);           // lager resten av nodene
        }
        fra.neste = p;                 // for å få en sirkel

        antall = 0;                    // ingen verdier foreløpig
    }

    public LenketKø()  // standardkonstruktør
    {
        this(START_STØRRELSE);
    }

    @Override
    public boolean leggInn(T verdi) {
        til.verdi = verdi;              // legger inn bakerst

        if (til.neste == fra)           // køen vil bli full - må utvides
        {
            til.neste = new Node<>(fra);  // ny node mellom til og fra
        }

        til = til.neste;                // flytter til
        antall++;                       // øker antallet

        return true;                    // vellykket innlegging
    }

    @Override
    public T kikk() {
        return null;
    }

    @Override
    public T taUt() {
        return null;
    }

    public int antall()
    {
        return antall;
    }

    public boolean tom()
    {
        return fra == til;  // eller antall == 0
    }

    @Override
    public void nullstill() {

    }

    // resten av metodene skal inn her

} // class LenketKø
