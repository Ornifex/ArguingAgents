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

        Argument a1 = new Argument("a1");
        Argument a2 = new Argument("a2");
        Argument a3 = new Argument("a3");
        Argument a4 = new Argument("a4");
        Argument a5 = new Argument("a5");
        Argument a6 = new Argument("a6");
        Argument a7 = new Argument("a7");
        Argument a8 = new Argument("a8");

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
        ArgumentationSystem S = new ArgumentationSystem(
                new LinkedHashSet<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8)),
                new LinkedHashSet<>(Arrays.asList(new Attack(a1, a2), new Attack(a1, a3), new Attack(a2, a1), new Attack(a3, a1),
                                                  new Attack(a3, a4), new Attack(a3, a6), new Attack(a4, a3), new Attack(a5, a4),
                                                  new Attack(a6, a3), new Attack(a7, a6), new Attack(a8, a7)))
        );

        System.out.println(S);

        S.findPreferredExtensions(PreferredExtensions, S.getArguments());

        System.out.println(PreferredExtensions.size() + " Preferred Extensions: ");
        for(Set<Argument> set : PreferredExtensions) {
            System.out.println("\t" + set);
        }
    }
}
