package AbstractArgumentationFramework;

public class Attack {
    public final Argument a;
    public final Argument b;

    public Attack(Argument a, Argument b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "[Argument " + a.getName() + " attacks Argument " + b.getName() + "]";
    }
}

