package eksempelklasser;

public class Student extends Person {

    private final Studium studium;

    public Student(String fornavn, String etternavn, Studium studium)
    {
        super(fornavn, etternavn);
        this.studium = studium;
    }

    public String toString(){
        return super.toString() + " " + studium.name();
    }

    public Studium getStudium() {
        return studium;
    }
}
