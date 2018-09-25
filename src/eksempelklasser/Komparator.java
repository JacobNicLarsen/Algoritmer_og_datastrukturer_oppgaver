package eksempelklasser;

@FunctionalInterface
public interface Komparator<T> {
        int compare(T x, T y);            // en abstrakt metode
}
