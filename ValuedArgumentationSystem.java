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

    private Set<Argument> InTransVAFS(Set<Argument> mu) {
        Argument in = new Argument("null");

        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK) {
                x.setLabel(Label.IN);
                in = x;
                break;
            }
        }
        for (Argument y : mu) {
            if (attacks(in, y)) {
                y.setLabel(Label.OUT);
            }
        }
        for (Argument z : mu) {
            if (attacks(z,in) && z.getLabel() != Label.OUT) {
                z.setLabel(Label.MUST_OUT);
            }
        }
        return mu;
    }

    private Set<Argument> UndecTransVAFS(Set<Argument> mu) {
        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK) {
                x.setLabel(Label.UNDEC);
                break;
            }
        }
        return mu;
    }

    /*
        Based on algorithm 5 for enumerating preferred extensions for valued abstract argumentation frameworks from
        "Algorithms for decision problems in argument systems under preferred semantics" Nofal, Atkinson, and Dunne
        (2014).
     */
    public void findPreferredExtensions(Set<Set<Argument>> PEXT, Set<Argument> mu) {
    }

}
