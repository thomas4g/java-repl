package net.thomasshields.java_repl;

import java.util.Scanner;

public class JavaRepl {
    public static void main(String... args) throws Exception {
        Scanner repl = new Scanner(System.in);
        JavaProgram jp = new JavaProgram();
        while (true) {
            System.out.print("> ");
            jp.addLine(repl.nextLine());
            jp.compile();
            Class.forName(jp.getClassName()).getMethod("main", String[].class).invoke(null, (Object)(new String[0]));
        }
    }
}
