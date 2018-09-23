package AbstractArgumentationFramework;

public class Argument implements Cloneable {
    private final String name;
    private Label label;

    public Argument(String name) {
        this.name = name;
        this.label = Label.BLANK;
    }

    public Argument clone() {
        Argument clone;
        try {
            clone = (Argument) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return clone;
    }

    public String getName() {
        return name;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String toString() {
        return "[Argument: " + getName() + ", Label: " + getLabel() + "]";
    }
}
