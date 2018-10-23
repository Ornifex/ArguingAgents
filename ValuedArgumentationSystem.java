package AbstractArgumentationFramework;

import java.util.*;

/*
    Based on Bench-Capon's Valued Abstract Argumentation System, (VAF) is a four tuple: S = <A, R, V, η>,
    where A is a set of arguments, R is a subset of the Cartesian product A x A, V is a non-empty set of social values,
    and η : A -> V maps arguments in A to values in V.
 */

public class ValuedArgumentationSystem extends ArgumentationSystem {
    private final Set<Value> val;
    private List<Integer> q;

    private int max;

    public ValuedArgumentationSystem(Set<Argument> A, Set<Attack> R, Set<Value> val, List<Integer> q) {
        super(A, R);
        this.val = val;
        this.q = q;
    }

    public boolean pref(ValuedArgument a, ValuedArgument b, List<Integer> q) {
        int cnt = 0, x = 9999, y = 9999;
        for (Value v : val) {
            if (v == a.getValue()) x = q.get(cnt);
            if (v == b.getValue()) y = q.get(cnt);
            cnt++;
        }
        return (x <= y);
    }

    private Set<Argument> InTransVAFS(Set<Argument> mu, List<Integer> q) {
        Argument in = new ValuedArgument("null", null);

        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK) {
                x.setLabel(Label.IN);
                in = x;
                break;
            }
        }

        for (Argument y : mu) {
            if (attacks(in, y) && pref((ValuedArgument) in, (ValuedArgument) y, q)) {
                y.setLabel(Label.OUT);
            }
        }

        for (Argument z : mu) {
            if (attacks(z,in) && z.getLabel() != Label.OUT && pref((ValuedArgument) z, (ValuedArgument) in, q)) {
                z.setLabel(Label.MUST_OUT);
            }
        }

        return mu;
    }

    public boolean containsBlankValue(Set<Argument> mu, Value v) {
        for (Argument x : mu) {
            if (x.getLabel() == Label.BLANK && ((ValuedArgument)x).getValue() == v) return true;
        }
        return false;
    }

    public boolean containsBlankValueQ(Set<Argument> mu, List<Integer> q) {
        int n = 0, m = 0;
        for (Value v : val) {
            n++;
            for (int qv : q) {
                m++;
                if (n != m) continue;
                if (qv == 0 && containsBlankValue(mu, v)) {
                    return true;
                }
            }
            m = 0;
        }
        return false;
    }

    public boolean labelIn(Set<Argument> W, Argument x, Set<Argument> mu, List<Integer> q) {
        W.add(x);


        return true;
    }

    public boolean labelOut(Set<Argument> W, Argument y, Set<Argument> mu, List<Integer> q) {
        W.add(y);
        for (Argument s : mu) {
            if (attacks(s, y) && s.getLabel() != Label.BLANK) {
                Set<Argument> W1 = new LinkedHashSet<>(W.size());

                for (Argument next : W) {
                    W1.add(next.clone());
                }
                if (labelIn(W1, s, mu, q)) {
                    y.setLabel(Label.OUT);
                }
            }
        }
        return false;
    }

    /*
        Enumerate all preferred extensions by enumerating all specific audiences
     */
    public void findPreferredExtensions(Set<Set<Argument>> PEXT, Set<Argument> mu, List<Integer> q, int i) {
        int n = 0, m = 0;
        for (Value v : val) {
            n++;
            List<Integer> q1 =  new LinkedList<>();
            q1.addAll(q);
            Set<Argument> mu1 = new LinkedHashSet<>(mu.size());

            for(Argument next : mu) {
                mu1.add(next.clone());
            }

            for (int qv : q1) {
                m++;
                if (n != m) continue;
                if (qv == 0 && containsBlankValue(mu, v)) {
                    qv = i;
                }
            }
            m = 0;

            for (Argument z : mu1) {
                if (z.getLabel() == Label.BLANK && ((ValuedArgument) z).getValue() == v) {
                    Set<Argument> W = new LinkedHashSet<>();
                    labelIn(W, z, mu, q);
                }
            }
            findPreferredExtensions(PEXT, mu1, q1, i++);
        }
        if (!containsBlankValueQ(mu, q)) {
            findAlphaPreferredExtensions(PEXT, mu, q);
        }
    }

    /*
        Based on algorithm 8 for enumerating alpha-preferred extensions for valued abstract argumentation frameworks from
        "Algorithms for decision problems in argument systems under preferred semantics" Nofal, Atkinson, and Dunne
        (2014). Gives preferred extensions for a given audience as specified by q.
     */
    public void findAlphaPreferredExtensions(Set<Set<Argument>> PEXT, Set<Argument> mu, List<Integer> q) {
        if (containsLabel(mu, Label.BLANK)) {
            Set<Argument> mu1 = new LinkedHashSet<>(mu.size());
            Set<Argument> mu2 = new LinkedHashSet<>(mu.size());

            for(Argument next : mu) {
                mu1.add(next.clone());
                mu2.add(next.clone());
            }
            findAlphaPreferredExtensions(PEXT, InTransVAFS(mu1, q), q);
            findAlphaPreferredExtensions(PEXT, UndecTrans(mu2), q);
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

    public List<Integer> getQ() {
        return this.q;
    }

    public void setQ(List<Integer> q) { this.q = q;}


    public String toString() {
        return "S = <A, R, V, h>,\n" +
                "\tA, h = " + getArguments() + ",\n" +
                "\tR = " + getAttacks()   + ",\n" +
                "\tV = " + getValues()    + ",\n";
    }

}
