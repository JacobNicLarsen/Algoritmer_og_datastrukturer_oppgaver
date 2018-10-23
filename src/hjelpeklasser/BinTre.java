package hjelpeklasser;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringJoiner;

public class BinTre<T>           // et generisk binærtre
{
    private static final class Node<T>  // en indre nodeklasse
    {
        private T verdi;            // nodens verdi
        private Node<T> venstre;    // referanse til venstre barn/subtre
        private Node<T> høyre;      // referanse til høyre barn/subtre

        private Node(T verdi, Node<T> v, Node<T> h)    // konstruktør
        {
            this.verdi = verdi; venstre = v; høyre = h;
        }

        private Node(T verdi) { this.verdi = verdi; }  // konstruktør

    } // class Node<T>

    private Node<T> rot;      // referanse til rotnoden
    private int antall;       // antall noder i treet

    public BinTre() { rot = null; antall = 0; }          // konstruktør

    public BinTre(int[] posisjon, T[] verdi)  // konstruktør
    {
        if (posisjon.length > verdi.length) throw new
                IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) leggInn(posisjon[i],verdi[i]);
    }

    public final void leggInn(int posisjon, T verdi) {
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;    // nodereferanser

        int filter = Integer.highestOneBit(posisjon) >> 1;   // filter = 100...00

        while (p != null && filter > 0)
        {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;  // bitforskyver filter
        }

        if (filter > 0) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") mangler forelder!");
        else if (p != null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes fra før!");

        p = new Node<>(verdi);          // ny node

        if (q == null) rot = p;         // tomt tre - ny rot
        else if ((posisjon & 1) == 0)   // sjekker siste siffer i posisjon
            q.venstre = p;                // venstre barn til q
        else
            q.høyre = p;                  // høyre barn til q

        antall++;                       // en ny verdi i treet
    }

    public int antall() { return antall; }               // returnerer antallet

    public boolean tom() { return antall == 0; }         // tomt tre?

    private Node<T> finnNode(int posisjon)  // finner noden med gitt posisjon
    {
        if (posisjon < 1) return null;

        Node<T> p = rot;   // nodereferanse
        int filter = Integer.highestOneBit(posisjon >> 1);   // filter = 100...00

        for (; p != null && filter > 0; filter >>= 1)
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;

        return p;   // p blir null hvis posisjon ikke er i treet
    }

    public boolean finnes(int posisjon)
    {
        return finnNode(posisjon) != null;
    }

    public T hent(int posisjon)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        return gammelverdi;
    }

    public int nodeType(int posisjon){

        if (posisjon < 1)
            throw new IllegalArgumentException("Posisjonen må være større enn 0");

        Node<T> p = finnNode(posisjon);

        if (p == null) return  -1;
        else if (p.venstre != null && p.høyre != null) return 0; // Noden er en indre node
        else return 1; //Noden er en bladnode
    }

    public T fjern(int posisjon){
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;               // hjelpepekere
        int filter = Integer.highestOneBit(posisjon >> 1);   // binært siffer

        while (p != null && filter > 0)
        {
            q = p;
            p = (filter & posisjon) == 0 ? p.venstre : p.høyre;
            filter >>= 1;
        }

        if (p == null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er utenfor treet!");

        if (p.venstre != null || p.høyre != null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er ingen bladnode!");

        if (p == rot) rot = null;
        else if (p == q.venstre) q.venstre = null;
        else q.høyre = null;

        antall--;  //
        return p.verdi;
    }

    public void nivåorden(Oppgave<? super T> oppgave)                // skal ligge i class BinTre
    {
        if (tom()) return;                   // tomt tre

        Kø<Node<T>> kø = new TabellKø<>();   // Se Avsnitt 4.2.2
        kø.leggInn(rot);                     // legger inn roten

        while (!kø.tom())                    // så lenge som køen ikke er tom
        {
            Node<T> p = kø.taUt();             // tar ut fra køen
            oppgave.utførOppgave(p.verdi);

            if (p.venstre != null) kø.leggInn(p.venstre);
            if (p.høyre != null) kø.leggInn(p.høyre);

        }
    }

    public int[] nivåer()   // returnerer en tabell som inneholder nivåantallene
    {
        if (tom()) return new int[0];       // en tom tabell for et tomt tre

        int[] a = new int[8];               // hjelpetabell
        Kø<Node<T>> kø = new TabellKø<>();  // hjelpekø
        int nivå = 0;                       // hjelpevariabel

        kø.leggInn(rot);    // legger roten i køen

        while (!kø.tom())   // så lenge som køen ikke er tom
        {
            // utvider a hvis det er nødvendig
            if (nivå == a.length) a = Arrays.copyOf(a,2*nivå);

            int k = a[nivå] = kø.antall();  // antallet på dette nivået

            for (int i = 0; i < k; i++)  // alle på nivået
            {
                Node<T> p = kø.taUt();

                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }

            nivå++;  // fortsetter på neste nivå
        }

        return Arrays.copyOf(a, nivå);  // fjerner det overflødige
    }

    private static <T> void preorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        while (true) {
            oppgave.utførOppgave(p.verdi);                       // utfører oppgaven

            if (p.venstre != null) preorden(p.venstre, oppgave);  // til venstre barn
            if (p.høyre == null) return;      // metodekallet er ferdig
            p = p.høyre;
        }
    }



    public void preorden(Oppgave<? super T> oppgave)
    {
        if (!tom()) preorden(rot,oppgave);  // sjekker om treet er tomt
    }

    public void preordenItrativ(Oppgave<? super T> oppgave)   // ny versjon
    {
        if (tom()) return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;    // starter i roten

        while (true)
        {
            oppgave.utførOppgave(p.verdi);

            if (p.venstre != null)
            {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null)  // her er p.venstre lik null
            {
                p = p.høyre;
            }
            else if (!stakk.tom())     // her er p en bladnode
            {
                p = stakk.taUt();
            }
            else                       // p er en bladnode og stakken er tom
                break;                   // traverseringen er ferdig
        }
    }

    private static <T> void inorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        while (true) {
            if (p.venstre != null) inorden(p.venstre, oppgave);
            oppgave.utførOppgave(p.verdi);
            if (p.høyre == null) return;      // metodekallet er ferdig
            p = p.høyre;
        }

    }

    public void inorden(Oppgave <? super T> oppgave)
    {
        if (!tom()) inorden(rot,oppgave);
    }

    public void inordenItrativ(Oppgave<? super T> oppgave)  // iterativ inorden
    {
        if (tom()) return;            // tomt tre

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;   // starter i roten og går til venstre
        for ( ; p.venstre != null; p = p.venstre) stakk.leggInn(p);

        while (true)
        {
            oppgave.utførOppgave(p.verdi);      // oppgaven utføres

            if (p.høyre != null)          // til venstre i høyre subtre
            {
                for (p = p.høyre; p.venstre != null; p = p.venstre)
                {
                    stakk.leggInn(p);
                }
            }
            else if (!stakk.tom())
            {
                p = stakk.taUt();   // p.høyre == null, henter fra stakken
            }
            else break;          // stakken er tom - vi er ferdig

        } // while
    }

    private static <T> void postorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p.venstre != null) postorden(p.venstre,oppgave);
        if (p.høyre != null) postorden(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public void postorden(Oppgave <? super T> oppgave)
    {
        if (!tom()) postorden(rot,oppgave);
    }

    public void postordenItrativ(Oppgave<? super T> oppgave){
        Stakk<Node> stack = new TabellStakk<>();
        while(true) {
            while(rot != null) {
                stack.leggInn(rot);
                stack.leggInn(rot);
                rot = rot.venstre;
            }

            // Check for empty stack
            if(stack.tom()) return;
            rot = stack.taUt();

            if(!stack.tom() && stack.kikk() == rot) rot = rot.høyre;

            else {

                System.out.print(rot.verdi + " "); rot = null;
            }
        }


    }



    private void nullstill(Node<T> p){
        if (p.venstre != null) {
            nullstill(p.venstre);
            p.venstre = null;
        }
        if (p.høyre != null) {
            nullstill(p.høyre);
            p.venstre = null;
        }
        p.verdi = null;
    }

    public void nullstill(){
        nullstill(rot);
    }

    public String toString(){
        StringJoiner s = new StringJoiner(",", "[", "]");
        if (!tom()) inorden(x -> s.add(x != null ? x.toString():"Null"));
        return s.toString();
    }

    public T førstInorden()
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.venstre != null) p = p.venstre;

        return p.verdi;
    }

    public T førstPostorden()
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p.verdi;
        }
    }

    private static
    <T> Node<T> trePreorden(T[] preorden, int rot, T[] inorden, int v, int h)
    {
        if (v > h) return null;  // tomt intervall -> tomt tre
        int k = v; T verdi = preorden[rot];
        while (!verdi.equals(inorden[k])) k++;  // finner verdi i inorden[v:h]

        Node<T> venstre = trePreorden(preorden, rot + 1, inorden, v, k - 1);
        Node<T> høyre   = trePreorden(preorden, rot + 1 + k - v, inorden, k + 1, h);

        return new Node<>(verdi, venstre, høyre);
    }
    public static <T> BinTre<T> trePreorden(T[] preorden, T[] inorden)
    {
        BinTre<T> tre = new BinTre<>();
        tre.rot = trePreorden(preorden, 0, inorden, 0, inorden.length - 1);

        tre.antall = preorden.length;
        return tre;
    }

    private static <T> Node<T> random(int n, Random r)
    {
        if (n == 0) return null;                      // et tomt tre
        else if (n == 1) return new Node<>(null);     // tre med kun en node

        int k = r.nextInt(n);    // k velges tilfeldig fra [0,n>

        Node<T> venstre = random(k,r);     // tilfeldig tre med k noder
        Node<T> høyre = random(n-k-1,r);   // tilfeldig tre med n-k-1 noder

        return new Node<>(null,venstre,høyre);
    }

    public static <T> BinTre<T> random(int n)
    {
        if (n < 0) throw new IllegalArgumentException("Må ha n >= 0!");

        BinTre<T> tre = new BinTre<>();
        tre.antall = n;

        tre.rot = random(n,new Random());   // kaller den private metoden

        return tre;
    }


} // class BinTre<T>
