package AbstractArgumentationFramework;

public class Value {
    private final String name;

    public Value(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {return this.name;}
}
