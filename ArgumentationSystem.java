package AbstractArgumentationFramework;

import java.util.*;

/*
    Based on Dung's Abstract Argumentation System, (AF) is a pair: S = <A, R>,
    where A is a set of arguments, R is a subset of the Cartesian product A x A
 */
public class ArgumentationSystem {
    private Set<Argument> arguments;
    private final Set<Attack> attacks;

    private int max;

    public ArgumentationSystem(Set<Argument> A, Set<Attack> R) {
        this.arguments = A;
        this.attacks = R;
    }

    public Boolean containsLabel(Set<Argument> a, Label label) {
        for (Argument x : a) {
            if (x.getLabel() == label) return true;
        }
        return false;
    }

    public boolean attacks(Argument a, Argument b) {
        for(Attack att : this.getAttacks()) {
            if (att.a.getName().equals(a.getName()) && att.b.getName().equals(b.getName())) return true;
        }
        return false;
    }

    private boolean isConflictFree(Set<Argument> set) {
        for (Argument a : set) {
            for (Argument b : set) {
                if (attacks(a,b) || attacks(b,a)) return false;
            }
        }
        return true;
    }

    public Set<Argument> InTrans(Set<Argument> mu) {
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

    public Set<Argument> UndecTrans(Set<Argument> mu) {
        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK) {
                x.setLabel(Label.UNDEC);
                break;
            }
        }
        return mu;
    }

    /*
        Based on algorithm 1 for enumerating preferred extensions for abstract argumentation frameworks
        from "Algorithms for decision problems in argument systems under preferred semantics" Nofal, Atkinson, and Dunne
        (2014).
     */
    public void findPreferredExtensions(Set<Set<Argument>> PEXT, Set<Argument> mu) {
        if (containsLabel(mu, Label.BLANK)) {
            Set<Argument> mu1 = new LinkedHashSet<>(mu.size());
            Set<Argument> mu2 = new LinkedHashSet<>(mu.size());

            for(Argument next : mu) {
                mu1.add(next.clone());
                mu2.add(next.clone());
            }
            findPreferredExtensions(PEXT, InTrans(mu1));
            findPreferredExtensions(PEXT, UndecTrans(mu2));
        } else {
            if (!containsLabel(mu, Label.MUST_OUT)) {
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
    }

    public Set<Argument> getArguments() {
        return this.arguments;
    }

    public Set<Attack> getAttacks() {
        return this.attacks;
    }

    public String toString() {
        return "S = <A, R>,\n" +
                "\tA = " + getArguments() + ",\n" +
                "\tR = " + getAttacks();
    }
}
