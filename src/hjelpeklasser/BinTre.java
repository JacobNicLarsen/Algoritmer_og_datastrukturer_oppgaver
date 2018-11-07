package hjelpeklasser;

import java.util.*;

public class BinTre<T> implements Iterable<T>           // et generisk binærtre
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
    private int enderinger;

    public BinTre() { rot = null; antall = 0; enderinger = 0; }          // konstruktør

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
        enderinger++;
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

        enderinger++;

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
        enderinger ++;
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

    public Iterator<T> iterator(){
        return new InordenIterator();
    }

    public Iterator<T> omvendtordenIetrator(){
        return new OmvendtordenIterator();
    }

    public Iterator<T> preordenIterator(){
        return new PreordenIterator();
    }

    private class PreordenIterator implements Iterator<T>{
        private  Stakk<Node<T>> stakk;
        private Node<T> p;
        private int iteratorendringer;

        public PreordenIterator(){
            if(tom()) return;
            stakk = new TabellStakk<>();
            p = første();
            iteratorendringer = enderinger;
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next()  // neste er med hensyn på preorden
        {
            if (!hasNext()) throw new NoSuchElementException();

            if (iteratorendringer != enderinger)
                throw new ConcurrentModificationException("Treet er endret");

            T verdi = p.verdi;

            if (p.venstre != null)                  // går til venstre
            {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null) p = p.høyre;  // går til høyre
            else if (stakk.tom()) p = null;             // ingen flere i treet
            else p = stakk.taUt();                      // tar fra satkken

            return verdi;
        }


        private Node<T> første(){
            if(tom())
                throw new IllegalStateException("Treet er tomt");
            return rot;
        }
    }

    private class OmvendtordenIterator implements Iterator<T>{
        private Stakk<Node<T>> stakk;
        private Node<T> p;
        private int iteratorendringer;

        public OmvendtordenIterator(){
            stakk = new TabellStakk<>();
            if (tom()) return;
            p = siste(rot);
            iteratorendringer = enderinger;
        }

        private Node<T> siste(Node<T> q){
            while (q.høyre != null){
                stakk.leggInn(q);
                q = q.høyre;
            }
            return q;
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException();

            if (iteratorendringer != enderinger)
                throw new ConcurrentModificationException("Treet er endret");

            T verdi = p.verdi;               // tar vare på verdien i noden p

            if (p.venstre != null)           // p har venstre subtre
                p = siste(p.venstre);
            else if (stakk.tom()) p = null;      // stakken er tom
            else  p = stakk.taUt();              // tar fra stakken

            return verdi;                    // returnerer verdien
        }
    }

    private class InordenIterator implements Iterator<T>{

        private Stakk<Node<T>> stakk;
        private Node<T> p = null;
        private int iteratorendringer;

        private InordenIterator(){
            if(tom()) return;
            stakk = new TabellStakk<>();
            p = først(rot);
            iteratorendringer = enderinger;
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("Inger verdier!");

            if (iteratorendringer != enderinger)
                throw new ConcurrentModificationException("Treet er endret");

            T verdi = p.verdi;

            if (p.høyre != null) p = først(p.høyre);
            else if (stakk.tom()) p = null;
            else p = stakk.taUt();

            return verdi;
        }

        private Node<T> først(Node<T> q)   // en hjelpemetode
        {
            while (q.venstre != null)        // starter i q
            {
                stakk.leggInn(q);              // legger q på stakken
                q = q.venstre;                 // går videre mot venstre
            }
            return q;                        // q er lengst ned til venstre
        }

    }

    private static int antall(Node<?> p)  // ? betyr vilkårlig type
    {
        if (p == null) return 0;            // et tomt tre har 0 noder

        return 1 + antall(p.venstre) + antall(p.høyre);
    }

    public int antallRekusjon()
    {
        return antall(rot);                 // kaller hjelpemetoden
    }

    private static <T> int posisjon(Node<T> p, int k, T verdi)
    {
        if (p == null) return -1;                  // ligger ikke i et tomt tre
        if (verdi.equals(p.verdi)) return k;       // verdi ligger i p
        int i = posisjon(p.venstre,2*k,verdi);     // leter i venstre subtre
        if (i > 0) return i;                       // ligger i venstre subtre
        return posisjon(p.høyre,2*k+1,verdi);      // leter i høyre subtre
    }

    public int posisjon(T verdi)
    {
        return posisjon(rot,1,verdi);  // kaller den private metoden
    }

    private static <T> boolean inneholder(Node<T> p, T verdi)
    {
        if (p == null) return false;    // kan ikke ligge i et tomt tre
        return verdi.equals(p.verdi) || inneholder(p.venstre,verdi)
                || inneholder(p.høyre,verdi);
    }

    public boolean inneholder(T verdi)
    {
        return inneholder(rot,verdi);   // kaller den private metoden
    }


    private static void høyde(Node<?> p, int nivå, IntObject o)
    {
        if (nivå > o.get()) o.set(nivå);
        if (p.venstre != null) høyde(p.venstre, nivå + 1, o);
        if (p.høyre != null) høyde(p.høyre, nivå + 1, o);
    }

    public int høyde()
    {
        IntObject o = new IntObject(-1);
        if (!tom()) høyde(rot, 0, o);
        return o.get();
    }

    public boolean erMintre(Comparator<? super T> c) // legges i BinTre
    {
        if (rot == null) return true;    // et tomt tre er et minimumstre
        else return erMintre(rot,c);     // kaller den private hjelpemetoden
    }

    private static <T> boolean erMintre(Node<T> p, Comparator<? super T> c)
    {
        if (p.venstre != null)
        {
            if (c.compare(p.venstre.verdi,p.verdi) < 0) return false;
            if (!erMintre(p.venstre,c)) return false;
        }
        if (p.høyre != null)
        {
            if (c.compare(p.høyre.verdi,p.verdi) < 0) return false;
            if (!erMintre(p.høyre,c)) return false;
        }
        return true;
    }

    public String minimumsGrenen(Comparator<? super T> c){
        StringJoiner s = new StringJoiner(", ", "[", "]");
        Node<T> p = rot;

        while (p != null){
            s.add(p.verdi.toString());
            if (p.høyre == null) p = p.venstre;
            else if (p.verdi == null) p = p.høyre;
            else {
                int cmp = c.compare(p.venstre.verdi, p.høyre.verdi);
                p = cmp <= 0 ? p.venstre : p.høyre;
            }
        }
        return s.toString();
    }


    private static int høyde(Node<?> p, IntObject o)
    {
        if (p == null) return -1;                // et tomt tre har høyde -1
        int h1 = høyde(p.venstre, o);            // høyden til venstre subtre
        int h2 = høyde(p.høyre, o);              // høyden til høyre subtre
        int avstand = h1 + h2 + 2;               // avstanden mellom to noder
        if (avstand > o.get()) o.set(avstand);   // sammenligner/oppdaterer
        return Math.max(h1, h2) + 1;             // høyden til treet med p som rot
    }

    public int diameter()
    {
        IntObject o = new IntObject(-1);         // lager et tallobjekt
        høyde(rot, o);                           // traverserer
        return o.get();                          // returnerer diameter
    }


    private static int antallBladnoder(Node<?> p)
    {
        if (p.venstre == null && p.høyre == null) return 1;

        return (p.venstre == null ? 0 : antallBladnoder(p.venstre))
                + (p.høyre == null ? 0 : antallBladnoder(p.høyre));
    }

    public int antallBladnoder()
    {
        return rot == null ? 0 : antallBladnoder(rot);
    }

    public int makspos()
    {
        IntObject o = new IntObject(-1);
        if (!tom()) makspos(rot, 1, o);
        return o.get();
    }

    private static void makspos(Node<?> p, int pos, IntObject o)
    {
        if (pos > o.get()) o.set(pos);
        if (p.venstre != null) makspos(p.venstre, 2*pos, o);
        if (p.høyre != null) makspos(p.høyre, 2*pos + 1, o);
    }

} // class BinTre<T>
