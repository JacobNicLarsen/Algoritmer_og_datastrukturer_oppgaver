package hjelpeklasser;

@FunctionalInterface
public interface Oppgave<T> {
    public abstract void utførOppgave(T t);

    static <T> Oppgave<T> kontrollutskrift(){
        return t-> System.out.print(t + " ");
    }

    default Oppgave<T> deretter(Oppgave<? super T> oppgave){
        return t -> {
            utførOppgave(t);
            oppgave.utførOppgave(t);
        };
    }
}
