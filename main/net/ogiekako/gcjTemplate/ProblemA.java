package net.ogiekako.gcjTemplate;
import java.io.*;
import java.util.Scanner;

import static java.util.Arrays.deepToString;

public class ProblemA {
    static final boolean _SAMPLE = true;
    static final boolean _SMALL = true;

    static final boolean _PRACTICE = false;
    static final String _FOLDER = "/home/ogiekako/workspace/ProgrammingContest/src/GCJ/Japan/_2011/Final/";
    static final String _PROBLEM = _FOLDER + ProblemA.class.getSimpleName().substring(ProblemA.class.getSimpleName().length() - 1);
    void debug(Object... os) {
        System.err.println(deepToString(os));
    }
    public void preCalc() {

    }
    Scanner sc = new Scanner(System.in);
    public void run() {

    }

    void _doIt() {
        System.out.println(ProblemA.class.getSimpleName());
        preCalc();
        int oo = sc.nextInt();
        for (int o = 1; o <= oo; o++) {
            System.err.println(o);
            System.out.printf("Case #%d: ", o);
            run();
        }
    }

    public static void main(String... args) throws IOException {
        if (!_SAMPLE) {
            if (_SMALL) {
                int i = 0;
                while (new File(_SMALLNAME(i) + ".in").exists())
                    i++;
                i--;
                boolean test = false;
                if (new File(_SMALLNAME(i) + ".out").exists()) {
                    System.err.println("overwrite?(y/n)");
                    char c = (char) System.in.read();
                    test = c != 'y';
                }
                if (test) {
                    System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
                    System.setOut(new PrintStream(_PROBLEM + "-small-test.out"));
                    new ProblemA()._doIt();
                    FileReader f1 = new FileReader(_PROBLEM + "-small-test.out");
                    FileReader f2 = new FileReader(_SMALLNAME(i) + ".out");
                    BufferedReader br1 = new BufferedReader(f1);
                    BufferedReader br2 = new BufferedReader(f2);
                    for (int j = 1; ; j++) {
                        String s1 = br1.readLine();
                        String s2 = br2.readLine();
                        if (s1 == null && s2 == null) {
                            System.err.println("OK!");
                            break;
                        }
                        if (s1 == null || s2 == null || !s1.equals(s2)) {
                            System.err.println("failed at line " + j);
                            System.err.println("expected " + s2);
                            System.err.println("but " + s1);
                            break;
                        }
                    }
                } else {
                    System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
                    System.setOut(new PrintStream(_SMALLNAME(i) + ".out"));
                    new ProblemA()._doIt();
                }
            } else {
                System.setIn(new FileInputStream(_LARGENAME() + ".in"));
                System.setOut(new PrintStream(_LARGENAME() + ".out"));
                new ProblemA()._doIt();
            }
        } else new ProblemA()._doIt();
    }
    private static String _LARGENAME() {
        return _PROBLEM + "-large" + (_PRACTICE ? "-practice" : "");
    }
    private static String _SMALLNAME(int a) {
        return _PROBLEM + "-small" + (_PRACTICE ? a == 0 ? "-practice" : "" : "-attempt" + a);
    }
}
