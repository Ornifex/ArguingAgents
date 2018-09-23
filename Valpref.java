package AbstractArgumentationFramework;

public class Valpref {
    public final Value a;
    public final Value b;

    /*
        Given a specific audience, Value a is preferred over Value b
     */
    public Valpref(Value a, Value b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "[Value " + a.getName() + " is preferred over " + b.getName() + "]";
    }
}
