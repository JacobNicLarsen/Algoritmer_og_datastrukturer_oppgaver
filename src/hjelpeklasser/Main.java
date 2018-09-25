package hjelpeklasser;

//import javafx.scene.control.Tab;




import eksempelklasser.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {

        String[] s = {"21","18","8","13","20","6","16","25","3","10"};
        Tabell.innsettingssortering(s,(x,y) -> {
            int k = x.length() - y.length();
            return k != 0 ? k : x.compareTo(y);
        });
        for(String t : s) System.out.print(t + ", ");

        /*
        Person[] p = new Person[5];                       // en persontabell
        p[0] = new Person("Kari", "Svendsen");            // Kari Svendsen
        p[1] = new Person("Boris", "Zukanovic");          // Boris Zukanovic
        p[2] = new Person("Ali", "Kahn");                 // Ali Kahn
        p[3] = new Person("Azra", "Zukanovic");           // Azra Zukanovic
        p[4] = new Person("Kari", "Pettersen");           // Kari Pettersen

        class FornavnKomparator implements Komparator<Person>
        {
            public int compare(Person p1, Person p2)        // to personer
            {
                return p1.fornavn().compareTo(p2.fornavn());  // sammenligner fornavn
            }
        }

        /* 1.4.6.d
        Komparator<Person> c = new FornavnKomparator();   // en instans av klassen
        Tabell.innsettingssortering(p, c);                // se Programkode 1.4.6 b)

        System.out.println(Arrays.toString(p));

        1.4.6.e
        Komparator<Person> c = (p1,p2) -> p1.fornavn().compareTo(p2.fornavn());
        Tabell.innsettingssortering(p, c);                // se Programkode 1.4.6 b)
        System.out.println(Arrays.toString(p));


        1.4.6.f
        String[] s = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Tabell.innsettingssortering(s, (s1,s2) -> s1.length() - s2.length());

        System.out.println(Arrays.toString(s));


        Student[] s = new Student[10];                             // en studenttabell
        s[0] = new Student("Kari","Svendsen", Studium.Data);      // Kari Svendsen
        s[1] = new Student("Boris","Zukanovic", Studium.IT);      // Boris Zukanovic
        s[2] = new Student("Ali","Kahn", Studium.Anvendt);        // Ali Kahn
        s[3] = new Student("Azra","Zukanovic", Studium.IT);       // Azra Zukanovic
        s[4] = new Student("Kari","Pettersen", Studium.Data);     // Kari Pettersen
        s[5] = new Student("Ole","Pettersen", Studium.Elektro);     // Kari Pettersen
        s[6] = new Student("Rolf","Larsen", Studium.IT);     // Kari Pettersen
        s[7] = new Student("Heidi","Hansen", Studium.Elektro);     // Kari Pettersen
        s[8] = new Student("Kunt","Oleson", Studium.Data);     // Kari Pettersen
        s[9] = new Student("Oddvar","Heididotter", Studium.Enkeltemne);     // Kari Pettersen



        Tabell.innsettingssortering(s, (s1,s2) ->
        {
            int cmp = s1.getStudium().compareTo(s2.getStudium());
            return cmp != 0 ? cmp : s1.compareTo(s2);
        });
        for(Student t : s) System.out.print(t + ", ");
        System.out.println();
        Tabell.innsettingssortering(s, (s1,s2) -> s1.getStudium().compareTo(s2.getStudium()));
        System.out.println(Arrays.toString(s));

        Tabell.innsettingssortering(s, (s1, s2) ->{
            int k = s1.getStudium().compareTo(s2.getStudium());
            if (k != 0) return k;
            k = s1.fornavn().compareTo(s2.fornavn());
            if (k != 0) return k;
            return s1.etternavn().compareTo(s2.etternavn());
        });

        System.out.print("Løsningsforslag: ");
        for (Student t : s) System.out.print(t + ", ");








        Person[] p = new Person[7];                   // en persontabell

        p[0] = new Person("Kari","Svendsen");         // Kari Svendsen
        p[1] = new Person("Boris","Zukanovic");       // Boris Zukanovic
        p[2] = new Person("Ali","Kahn");              // Ali Kahn
        p[3] = new Person("Azra","Zukanovic");        // Azra Zukanovic
        p[4] = new Person("Kari","Pettersen");        // Kari Pettersen
        p[5] = new Person("Heidi","Iversen");
        p[6] = new Person("Johan","Persen");

        int m = Tabell.maks(p);                       // posisjonen til den største
        System.out.println(p[m] + " er størst");      // skriver ut den største

        Arrays.sort(p);               // generisk sortering
        System.out.println(Arrays.toString(p));       // skriver ut sortert

        Arrays.stream(p).max(Comparator.naturalOrder()).ifPresent(System.out::println);


        Person[] s = new Person[6];      // en Persontabell

        s[0] = new Student("Kari","Svendsen",Studium.Data);    // en student
        s[1] = new Person("Boris","Zukanovic");                // en person
        s[2] = new Student("Ali","Kahn",Studium.Anvendt);      // en student
        s[3] = new Person("Azra","Zukanovic");                 // en person
        s[4] = new Student("Kari","Pettersen",Studium.Data);   // en student
        s[5] = new Student("Vetle", "Strand", Studium.Elektro);

        Tabell.innsettingssortering(s);                     // Programkode 1.4.2 e)
        for (Person t : s) System.out.println(t);*/

    }

    public static int a(int n)           // n må være et ikke-negativt tall
    {
        if (n == 0) return 1;              // a0 = 1
        else if (n == 1) return 2;         // a1 = 2
        else return 2*a(n-1) + 3*a(n-2);   // to rekursive kall
    }

    public static int a1(int n){
        if(n<0) throw new IllegalArgumentException("n må være større enn 0");

        int x = 0, y = 1, z = 1;

        for (int i = 0; i < n; i++) {
            z = 2*y + 3*x;
            x = y;
            y = z;
        }

        return z;
    }

    public static int tverrsum(int n)
    {
        System.out.println("tverrsum(" + n + ") starter!");
        int sum = (n < 10) ? n : tverrsum(n / 10) + (n % 10);
        System.out.println("tverrsum(" + n + ") er ferdig!");
        return sum;
    }

    public static int digitalRoot(int n){

        while (n >= 10) n = tverrsum(n);
        return n;
    }

    public static int sifferRot(int n){
        n%=9;
        return n == 0 ? 9 : n;
    }

    public static int kvadratRekkeSum(int n){
        if(n == 1) return 1;
        else return kvadratRekkeSum(n-1) + n*n;
    }

    public static int rekke_sum(int v, int h){
        if(v==h){
            return v;
        }
        int m = (v+h)/2;
        return rekke_sum(v,m) + rekke_sum(m + 1, h);
    }

    public static int maks(int[] a, int v, int h)
    {
        if (v == h) return v;
        int m = (v + h)/2;  // midten
        int mv = maks(a,v,m);
        int mh = maks(a,m+1,h);

        return a[mv] >= a[mh] ? mv : mh;
    }
    public static int maks(int[] a)
    {
        return maks(a,0,a.length-1);
    }

    public static int fakultet(int n){

        return n < 2 ? 1 : fakultet(n - 1) * n;
    }

    public static int fib(int n)         // det n-te Fibonacci-tallet
    {
        if (n <= 1) return n;              // fib(0) = 0, fib(1) = 1
        else return fib(n-1) + fib(n-2);   // summen av de to foregående
    }

    public static int euklid(int a, int b)
    {
        System.out.println("euklid(" + a + "," + b + ") starter!");
        if (b == 0){
            System.out.println("euklid(" + a + "," + b + ") er ferdig!");
            return a;
        }
        int r = a % b;            // r er resten
        int k = euklid(b,r);
        System.out.println("euklid(" + a + "," + b + ") er ferdig!");
        return k;  // rekursivt kall
    }

    public static int sum(int k, int n)  // summen av tallene fra k til n
    {
        if (k == n) return k;              // summen av ett tall
        int m = (k + n)/2;                 // det midterste tallet
        return sum(k,m) + sum(m+1,n);
    }

    public static void sortedDouble(double[] n){
        Double[] a = new Double[n.length];

        for (int i = 0; i < n.length; i++) a[i] = n[i];

        Tabell.innsettingssortering(a);
        Tabell.skriv(a);
    }


}
