package hjelpeklasser;

import java.util.*;
import java.util.stream.Stream;

public class SBinTre<T> implements Beholder<T> {
    private static final class Node<T> // en indre nodeklasse
    {
        private T verdi;                 // nodens verdi
        private Node<T> venstre, høyre;  // venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h)  // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }
    } // class Node

    private Node<T> rot;                       // peker til rotnoden
    private int antall;                        // antall noder
    private final Comparator<? super T> comp;  // komparator
    private int endringer;                     // antall endringer

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public int antall()        // antall verdier i treet
    {
        return antall;
    }

    public int antall(T verdi) {
        Node<T> p = rot;
        int antallVerdi = 0;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else {
                if (cmp == 0) antallVerdi++;
                p = p.høyre;
            }
        }
        return antallVerdi;
    }

    public T min()                 // skal returnere treets minste verdi
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;                            // starter i roten
        while (p.venstre != null) p = p.venstre;    // går mot venstre
        return p.verdi;                             // den minste verdien
    }

    public T maks() {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot, q = rot;
        while (p.høyre != null) {
            p = p.høyre;
            if (comp.compare(p.verdi, q.verdi) > 0) q = p;
        }
        return q.verdi;
    }

    public T gulv(T verdi) {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        T gulv = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) p = p.venstre;  // gulv(verdi) ligger til venstre
            else {
                gulv = p.verdi;            // nodeverdien er en kandidat
                p = p.høyre;
            }
        }

        return gulv;
    }

    public T tak(T verdi) {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }

        Node<T> p = rot;
        T tak = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) {
                tak = p.verdi;
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.høyre;
            } else {
                return p.verdi;
            }
        }

        return tak;
    }

    public T mindre(T verdi) {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        T mindre = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp <= 0) {
                p = p.venstre;
            } else {
                mindre = p.verdi;
                p = p.høyre;
            }
        }

        return mindre;
    }

    public T større(T verdi) {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");
        if (verdi == null) throw new NullPointerException("Ulovlig nullverdi!");

        Node<T> p = rot;
        T større = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) {
                større = p.verdi;  // en kandidat
                p = p.venstre;
            } else                 // den må ligge til høyre
            {
                p = p.høyre;
            }
        }

        return større;
    }

    public Liste<T> intervallsøk(T fraverdi, T tilverdi) {
        Stakk<Node<T>> s = new TabellStakk<>();

        Node<T> p = rot;
        while (p != null)    // leter etter fraverdi
        {
            int cmp = comp.compare(fraverdi, p.verdi);
            if (cmp < 0) {
                s.leggInn(p);
                p = p.venstre;
            } else if (cmp > 0) p = p.høyre;
            else break;
        }

        if (p == null) p = s.taUt();  // neste i inorden

        Liste<T> liste = new TabellListe<>();

        while (p != null && comp.compare(p.verdi, tilverdi) < 0) {
            liste.leggInn(p.verdi);

            if (p.høyre != null) {
                p = p.høyre;
                while (p.venstre != null) {
                    s.leggInn(p);
                    p = p.venstre;
                }
            } else if (!s.tom()) p = s.taUt();
            else p = null;
        }

        return liste;
    }

    public boolean tom()       // er treet tomt?
    {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        while (!tom()) fjernMin();
    }

    public String toString() {
        StringJoiner s = new StringJoiner(",", "[", "]");
        if (!tom()) inorden(x -> s.add(x != null ? x.toString() : "Null"));
        return s.toString();
    }

    private static <T> void inorden(Node<T> p, Oppgave<? super T> oppgave) {
        if (p.venstre != null) inorden(p.venstre, oppgave);
        oppgave.utførOppgave(p.verdi);
        if (p.høyre != null) inorden(p.høyre, oppgave);
    }

    public void inorden(Oppgave<? super T> oppgave) {
        if (!tom()) inorden(rot, oppgave);
    }

    public final boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        endringer++;
        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean inneholder(T verdi)     // skal ligge i klassen SBinTre
    {
        if (verdi == null) return false;            // treet har ikke nullverdier

        Node<T> p = rot;                            // starter i roten
        while (p != null)                           // sjekker p
        {
            int cmp = comp.compare(verdi, p.verdi);   // sammenligner
            if (cmp < 0) p = p.venstre;               // går til venstre
            else if (cmp > 0) p = p.høyre;            // går til høyre
            else return true;                         // cmp == 0, funnet
        }
        return false;                               // ikke funnet
    }

    public boolean inneholderEffektiv(T verdi)          // ny versjon
    {
        if (verdi == null) return false;          // treet har ikke nullverdier

        Node<T> p = rot;                          // starter i roten
        Node<T> q = null;                         // hjelpepeker

        while (p != null)                         // sjekker p
        {
            if (comp.compare(verdi, p.verdi) < 0)   // sammenligner
            {
                p = p.venstre;                        // går til venstre
            } else {
                q = p;                                // oppdaterer q
                p = p.høyre;                          // går til høyre
            }
        }

        return q == null ? false : comp.compare(verdi, q.verdi) == 0;
    }

    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        endringer++;
        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public void fjernMin()  // hører til klassen SBinTre
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        if (rot.venstre == null) rot = rot.høyre;  // rotverdien er minst
        else {
            Node<T> p = rot.venstre, q = rot;
            while (p.venstre != null) {
                q = p;  // q er forelder til p
                p = p.venstre;
            }
            // p er noden med minst verdi
            q.venstre = p.høyre;
        }
        endringer++;
        antall--;  // det er nå én node mindre i treet
    }

    private static int høyde(Node<?> p)  // ? betyr vilkårlig type
    {
        if (p == null) return -1;          // et tomt tre har høyde -1

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);                 // kaller hjelpemetoden
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> sbintre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }

    public static <T> SBinTre<T> sbintre(Stream<T> s, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);             // komparatoren c
        s.forEach(tre::leggInn);                       // bygger opp treet
        return tre;                                    // treet returneres
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre(Stream<T> s) {
        return sbintre(s, Comparator.naturalOrder());  // naturlig ordning
    }

    private static <T> Node<T> balansert(T[] a, int v, int h)  // en rekursiv metode
    {
        if (v > h) return null;                       // tomt intervall -> tomt tre

        int m = (v + h) / 2;                            // midten
        T verdi = a[m];                               // midtverdien

        while (v < m && verdi.equals(a[m - 1])) m--;    // til venstre

        Node<T> p = balansert(a, v, m - 1);           // venstre subtre
        Node<T> q = balansert(a, m + 1, h);           // høyre subtre

        return new Node<>(verdi, p, q);               // rotnoden
    }

    public static <T> SBinTre<T> balansert(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);          // oppretter et tomt tre
        tre.rot = balansert(a, 0, a.length - 1);    // bruker den rekursive metoden
        tre.antall = a.length;                      // setter antallet
        return tre;                                 // returnerer treet
    }

    public static <T extends Comparable<? super T>> SBinTre<T> balansert(T[] a) {
        return balansert(a, Comparator.naturalOrder());
    }

    public Iterator<T> iterator()  // returnerer en iterator
    {
        return new InordenIterator();
    }

    public Iterator<T> riterator(){
        return new OmvendtOrdenIterator();
    }

    public Iterator<T> riterator(T verdi){
        return new OmvendtOrdenIterator(verdi);
    }

    private class InordenIterator implements Iterator<T> {
        private Stakk<Node<T>> s = new TabellStakk<>();
        private Node<T> p = null;
        private int iteratorendring;

        public InordenIterator(T verdi){
            if (verdi == null) throw new IllegalArgumentException("Verdi kan ikke være null");

            Node<T> q = rot;

            while (q != null){
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp < 0){
                    s.leggInn(q);
                    q = q.venstre;
                }
                else if (cmp > 0){
                    q = q.høyre;
                }
                else break;
            }

            if (q != null) p = q;
            else if (!s.tom()) p = s.taUt();

            iteratorendring = endringer;
        }

        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                s.leggInn(q);                  // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

        private InordenIterator()          // konstruktør
        {
            if (tom()) return;               // treet er tomt
            p = først(rot);                  // bruker hjelpemetoden
            iteratorendring = endringer;
        }

        @Override
        public T next() {

            if (iteratorendring != endringer) throw new ConcurrentModificationException("Det er gjort endringer");
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                        // tar vare på verdien

            if (p.høyre != null) p = først(p.høyre);  // p har høyre subtre
            else if (s.tom()) p = null;               // stakken er tom
            else p = s.taUt();                        // tar fra stakken

            return verdi;                             // returnerer verdien
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

    }

    private class OmvendtOrdenIterator implements Iterator{
        private Stakk<Node<T>> s = new TabellStakk<>();
        private Node<T> p = null;
        private Node<T> q = null;                        // nodepeker
        private int iteratorendring;

        private Node<T> siste(Node<T> q){
            while (q.høyre != null){
                s.leggInn(q);
                q = q.høyre;
            }
            return q;
        }

        public OmvendtOrdenIterator(){
            if (tom()) return;
            p = siste(rot);
            iteratorendring = endringer;

        }

        public OmvendtOrdenIterator(T verdi){
            if (verdi == null) throw new IllegalArgumentException("Verdi kan ikke være null");

            Node<T> q = rot;

            while (q != null){
                int cmp = comp.compare(verdi, q.verdi);

                if (cmp > 0){
                    s.leggInn(q);
                    q = q.venstre;
                }
                else if (cmp < 0){
                    q = q.høyre;
                }
                else break;
            }
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next() {
            if (iteratorendring != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;               // tar vare på verdien i noden p
            q = p;                           // q oppdateres før p flyttes


            if (p.venstre != null) p = siste(p.venstre);  // p har høyre subtre
            else if (!s.tom()) p = s.taUt();             // p har ikke høyre subtre
            else p = null;                               // stakken er tom

            return verdi;                    // returnerer verdien
        }
    }

}
