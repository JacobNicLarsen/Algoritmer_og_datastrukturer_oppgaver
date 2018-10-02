package eksempelklasser;

import java.util.Objects;

public class Dato implements Comparable<Dato>
{
    private final int dag, mnd, år;              // instansvariabler

    public Dato(int dag, int mnd, int år)        // konstruktør
    {
        this.dag = dag; this.mnd = mnd; this.år = år;
    }

    public Dato(int dag, Måned måned, int år)  // konstruktør
    {
        this.dag = dag;
        mnd = måned.getMndNr();
        this.år = år;
    }

    public int compareTo(Dato d)                 // compareTo
    {
        if (år < d.år) return -1;
        else if (år > d.år) return 1;
        else if (mnd < d.mnd) return -1;
        else if (mnd > d.mnd) return 1;
        else return dag - d.dag;
    }

    public boolean equals(Object o)              // equals
    {
        if (o == this) return true;
        if (!(o instanceof Dato)) return false;
        Dato d = (Dato)o;
        return år == d.år && mnd == d.mnd && dag == d.dag;
    }

    public boolean equals(Dato d)                 // equals
    {
        return år == d.år && mnd == d.mnd && dag == d.dag;
    }

    public String toString()                     // toString
    {
        return "" + dag + '/' + mnd + '-' + år;
    }


    /*
    public int hashCode()
    {
        return Objects.hash(dag,mnd,år);
    }
    */
} // class Dato