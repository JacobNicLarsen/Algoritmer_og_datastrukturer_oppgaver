package eksempelklasser;

@FunctionalInterface
public interface Funksjon<T,R> {
    R anvend(T t);
}

