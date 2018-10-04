package hjelpeklasser;

import java.util.*;

public abstract class AbstraktListe<T> extends AbstraktBeholder<T> {


    public abstract void leggInn(int index, T t);

    protected void indeksKontroll(int indeks)
    {
        if (indeks < 0 )
            throw new IndexOutOfBoundsException("Indeks " +
                    indeks + " er negativ!");
        else if (indeks >= antall())
            throw new IndexOutOfBoundsException("Indeks " +
                    indeks + " >= antall(" + antall() + ") noder!");
    }

    public boolean leggInn(T t){
        leggInn(antall(),t);
        return true;
    }

    public T hent(int indeks){
        indeksKontroll(antall());

        Iterator<T> iterator = iterator();

        for(int k = 0; k < indeks; k++) iterator.next();

        return iterator.next();
    }

    public int indexTil(T t){

        Iterator<T> iterator = iterator();

        for(int index = 0; iterator.hasNext();index++){
            if(iterator.next().equals(t)) return index;
        }
        return -1;
    }

    public T oppdater(int index, T t){
        indeksKontroll(index);

        T gammel = fjern(index);

        leggInn(index, t);

        return gammel;
    }

    public T fjern(int index){
        indeksKontroll(index);

        Iterator<T> iterator = iterator();

        for(int k = 0; k < index; k++) iterator.hasNext();

        T temp = iterator.next();
        iterator.remove();

        return temp;
    }

}
