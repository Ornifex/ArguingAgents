package AbstractArgumentationFramework;

public class ValuedArgument extends Argument {
    private final Value value;

    public ValuedArgument(String name, Value value) {
        super(name);
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public String toString() {
        return "[Argument: " + getName() + ", Label: " + getLabel() + ", Value: " + getValue() + "]";
    }
}
