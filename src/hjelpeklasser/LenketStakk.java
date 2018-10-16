package hjelpeklasser;

import java.util.*;

public class LenketStakk<T> implements Stakk<T>
{
    private static final class Node<T>       // en «nøstet» nodeklasse
    {

        private T verdi;
        private Node<T> neste;
        private Node(T verdi, Node<T> neste)   // nodekonstruktør
        {
            this.verdi = verdi;
            this.neste = neste;
        }


    } // class Node
    private Node<T> hode;             // stakkens topp

    private int antall;               // antall på stakken
    public LenketStakk()              // konstruktør
    {
        hode = null;
        antall = 0;
    }


    // Her skal de andre metodene fra grensesnittet Stakk<T> stå

    @Override
    public void leggInn(T verdi) {
        hode = new Node<>(verdi, hode);
        antall ++;
    }

    @Override
    public T kikk() {
        if (antall == 0)
            throw new IllegalStateException("Stacken er tom");

        return hode.verdi;
    }

    @Override
    public T taUt() {
        if (antall == 0)
            throw new IllegalStateException("Stacken er tom");

        antall--;

        T temp = hode.verdi;
        hode = hode.neste;

        return temp;
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
        hode = null;
        antall = 0;
    }

}  // class LenketStakk
