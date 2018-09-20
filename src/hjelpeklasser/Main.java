package hjelpeklasser;

//import javafx.scene.control.Tab;



import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        /*
        int[] a = {5,2,7,3,9,1,8,4,6};
        double[] d = {5.7,3.14,7.12,3.9,6.5,7.1,7.11};
        String[] s = {"Sohil","Per","Thanh","Fatima","Kari","Jasmin"};
        char[] c = {'a','b','c','e','d','u'};

        int k = Tabell.maks(a);     // posisjonen til den største i a
        int l = Tabell.maks(d);     // posisjonen til den største i d
        int m = Tabell.maks(s);     // posisjonen til den største i s
        int j = Tabell.maks(c);

        System.out.println(a[k] + "  " + d[l] + "  " + s[m] + " " + c[j]);
*/

        String a = "Karianne", b = "Kari";

        //System.out.println(a.compareTo(b));
        System.out.println(Boolean.compare(true, false));

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

}
