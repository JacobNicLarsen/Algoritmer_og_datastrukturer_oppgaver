package eksempelklasser;

import java.lang.reflect.Array;
import java.util.*;


public enum  Måned {

    JAN (1, "januar"),
    FEB (2, "februar"),
    MAR (3, "mars"),
    APR (4, "april"),
    MAI (5, "mai"),
    JUN (6, "juni"),
    JUL (7, "juli"),
    AUG (8, "august"),
    SEP (9, "september"),
    OKT (10, "oktober"),
    NOV (11, "november"),
    DES (12, "desember");

    private int mndNr;
    private String mndNavn;

    Måned(int mndNr, String mndNavn){
        this.mndNr = mndNr;
        this.mndNavn = mndNavn;
    }

    @Override
    public String toString(){
        return mndNavn;
    }

    public static String toString(int mndNr){
        if(mndNr < 1 || mndNr > 12){
            throw new IllegalArgumentException("Tallet må være mellom 1 - 12");
        }
        return Måned.values()[mndNr -1].toString();

    }

    public int getMndNr() {
        return mndNr;
    }

    public String getMndNavn() {
        return mndNavn;
    }

    public static Måned[] sommer(){
        return Arrays.copyOfRange(values(),5,8);
    }

    public static Måned[] høst(){
        return Arrays.copyOfRange(values(),3,5);
    }

    public static Måned[] vår(){
        return Arrays.copyOfRange(values(),8,10);
    }

    public static Måned[] vinter(){
        return new Måned[] {NOV, DES, JAN, FEB, MAR};
    }

}
