package AbstractArgumentationFramework;

import java.util.*;

/*
    Based on Bench-Capon's Valued Abstract Argumentation System, (VAF) is a four tuple: S = <A, R, V, η>,
    where A is a set of arguments, R is a subset of the Cartesian product A x A, V is a non-empty set of social values,
    and η : A -> V maps arguments in A to values in V.
 */

public class ValuedArgumentationSystem extends ArgumentationSystem {
    private final Set<Value> val;
    private final Set<Valpref> valprefs;

    private int max;

    public ValuedArgumentationSystem(Set<Argument> A, Set<Attack> R, Set<Value> val, Set<Valpref> valprefs) {
        super(A, R);
        this.val = val;
        this.valprefs = valprefs;
    }

    public boolean attacks(Argument a, Argument b) {
        for(Attack att : this.getAttacks()) {
            if (att.a.getName().equals(a.getName()) && att.b.getName().equals(b.getName())) return true;
        }
        return false;
    }

    public boolean pref(ValuedArgument a, ValuedArgument b) {
        for (Valpref vp : valprefs) {
            if(vp.a == a.getValue() && vp.b == b.getValue()) {
                return true;
            }
        }
        return false;
    }

    private Set<Argument> InTransVAFS(Set<Argument> mu) {
        Argument in = new ValuedArgument("null", null);

        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK) {
                x.setLabel(Label.IN);
                in = x;
                break;
            }
        }

        for (Argument y : mu) {
            if (attacks(in, y) && pref((ValuedArgument) in, (ValuedArgument) y)) {
                y.setLabel(Label.OUT);
            }
        }
        for (Argument z : mu) {
            if (attacks(z,in) && z.getLabel() != Label.OUT && pref((ValuedArgument) z, (ValuedArgument) in)) {
                z.setLabel(Label.MUST_OUT);
            }
        }
        return mu;
    }

    /*
        Based on algorithm 8 for enumerating alpha-preferred extensions for valued abstract argumentation frameworks from
        "Algorithms for decision problems in argument systems under preferred semantics" Nofal, Atkinson, and Dunne
        (2014).
     */
    public void findAlphaPreferredExtensions(Set<Set<Argument>> PEXT, Set<Argument> mu) {
        if (containsLabel(mu, Label.BLANK)) {
            Set<Argument> mu1 = new LinkedHashSet<>(mu.size());
            Set<Argument> mu2 = new LinkedHashSet<>(mu.size());

            for(Argument next : mu) {
                mu1.add(next.clone());
                mu2.add(next.clone());
            }
            findAlphaPreferredExtensions(PEXT, InTransVAFS(mu1));
            findAlphaPreferredExtensions(PEXT, UndecTrans(mu2));
        } else if (!containsLabel(mu, Label.MUST_OUT)) {
            LinkedHashSet<Argument> extension = new LinkedHashSet<>();
            for (Argument argument : mu) {
                if (argument.getLabel().equals(Label.IN)) extension.add(argument.clone());
            }
            if (extension.size() > max) {
                PEXT.clear();
                max = extension.size();
            }
            if (extension.size() == max) PEXT.add(extension);
        }
    }

    public Set<Value> getValues() {
        return this.val;
    }

    public Set<Valpref> getValprefs() {
        return this.valprefs;
    }

    public String toString() {
        return "S = <A, R, V, h>,\n" +
                "\tA, h = " + getArguments() + ",\n" +
                "\tR = " + getAttacks()   + ",\n" +
                "\tV = " + getValues()    + ",\n" +
                "\tVP  = " + getValprefs();
    }

}
