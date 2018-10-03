package AbstractArgumentationFramework;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Argument a = new Argument("a");
        Argument b = new Argument("b");
        Argument c = new Argument("c");
        Argument d = new Argument("d");
        Argument e = new Argument("e");
        Argument f = new Argument("f");
        Argument g = new Argument("g");

        Set<Set<Argument>> PreferredExtensions = new LinkedHashSet<>();

        /*ArgumentationSystem S = new ArgumentationSystem(
                new LinkedHashSet<>(Arrays.asList(a, b, c, d)),
                new LinkedHashSet<>(Arrays.asList(new Attack(a, b), new Attack(b, c), new Attack(d, c)))
        );/*

        ArgumentationSystem S = new ArgumentationSystem(
                new LinkedHashSet<>(Arrays.asList(b, c, d)),
                new LinkedHashSet<>(Arrays.asList(new Attack(b, c), new Attack(c, d), new Attack(d, c)))
        );

        /*ArgumentationSystem S = new ArgumentationSystem(
                new LinkedHashSet<>(Arrays.asList(b, c, d, e, f, g)),
                new LinkedHashSet<>(Arrays.asList(new Attack(b, c), new Attack(c, d), new Attack(d, e), new Attack(e, f), new Attack(f, g), new Attack(g, f), new Attack(b, e)))
        );*/

        /*
            Popov v. Hayashi example adapted from "Arguments, Values and Baseballs: Representation of Popov v. Hayashi",
            Wyner, Bench-capon, and Atkinson (2007)
        */
        System.out.println("Abstract Argumentation Framework Example");
        {
            Argument a1 = new Argument("a1");
            Argument a2 = new Argument("a2");
            Argument a3 = new Argument("a3");
            Argument a4 = new Argument("a4");
            Argument a5 = new Argument("a5");
            Argument a6 = new Argument("a6");
            Argument a7 = new Argument("a7");
            Argument a8 = new Argument("a8");

            ArgumentationSystem S = new ArgumentationSystem(
                    new LinkedHashSet<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8)),
                    new LinkedHashSet<>(Arrays.asList(new Attack(a1, a2), new Attack(a1, a3), new Attack(a2, a1), new Attack(a3, a1),
                            new Attack(a3, a4), new Attack(a3, a6), new Attack(a4, a3), new Attack(a5, a4),
                            new Attack(a6, a3), new Attack(a7, a6), new Attack(a8, a7)))
            );

            System.out.println(S);

            S.findPreferredExtensions(PreferredExtensions, S.getArguments());

            System.out.println(PreferredExtensions.size() + " Preferred Extensions: ");
            for (Set<Argument> set : PreferredExtensions) {
                System.out.println("\t" + set);
            }
        }

        System.out.println("\n\nValued Abstract Argumentation Framework Example");
        { //Valued Argumentation Framework Example
            Value v1 = new Value("Public order");
            Value v2 = new Value("Fairness");
            Value v3 = new Value("Clarity");
            Value v4 = new Value("Fact");
            Value v5 = new Value("Popov Sues Assailants");

            ValuedArgument a1 = new ValuedArgument("a1", v1); //
            ValuedArgument a2 = new ValuedArgument("a2", v2); //
            ValuedArgument a3 = new ValuedArgument("a3", v3); //
            ValuedArgument a4 = new ValuedArgument("a4", v1); //
            ValuedArgument a5 = new ValuedArgument("a5", v4); //
            ValuedArgument a6 = new ValuedArgument("a6", v2); //
            ValuedArgument a7 = new ValuedArgument("a7", v5); //
            ValuedArgument a8 = new ValuedArgument("a8", v4); //

            ValuedArgumentationSystem VS = new ValuedArgumentationSystem(
                    new LinkedHashSet<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8)),
                    new LinkedHashSet<>(Arrays.asList(new Attack(a1, a2), new Attack(a1, a3), new Attack(a2, a1), new Attack(a3, a1),
                            new Attack(a3, a4), new Attack(a3, a6), new Attack(a4, a3), new Attack(a5, a4),
                            new Attack(a6, a3), new Attack(a7, a6), new Attack(a8, a7))),
                    new LinkedHashSet<>(Arrays.asList(v2, v1, v3, v4, v5)),
                    new LinkedHashSet<>(Arrays.asList(new Valpref(v2, v1), new Valpref(v2, v3), new Valpref(v2, v4),
                            new Valpref(v2, v5)))
            );

            System.out.println(VS);

            VS.findAlphaPreferredExtensions(PreferredExtensions, VS.getArguments());

            System.out.println(PreferredExtensions.size() + " Alpha Preferred Extensions: ");
            for (Set<Argument> set : PreferredExtensions) {
                System.out.println("\t" + set);
            }
        }
    }
}
