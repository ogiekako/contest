package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int tc = 1; ; tc++) {
            int N = in.nextInt();// in
            int G = in.nextInt();// gate
            int U = in.nextInt();// out
            if ((N | G | U) == 0) return;
            out.printf("Case %d: ", tc);
            Gate[] is = new Gate[N];
            Gate[] gs = new Gate[G];
            for (int i = 0; i < N; i++) is[i] = new Gate();
            for (int i = 0; i < G; i++) gs[i] = new Gate();

            for (int i = 0; i < G; i++) {
                char c = in.nextChar();
                gs[i].c = c;
                if (c == 'n') {
                    gs[i].source = new Gate[]{
                            get(is, gs, in.next())
                    };
                } else {
                    gs[i].source = new Gate[]{get(is, gs, in.next()), get(is, gs, in.next())};
                }
            }

            int[] outputs = new int[U];
            for (int i = 0; i < U; i++) outputs[i] = in.nextInt() - 1;

            int B = in.nextInt();
            boolean[][] testIn = new boolean[B][N];
            boolean[][] testOut = new boolean[B][U];
            for (int i = 0; i < B; i++) {
                for (int j = 0; j < N; j++) testIn[i][j] = in.nextInt() == 1;
                for (int j = 0; j < U; j++) testOut[i][j] = in.nextInt() == 1;
            }

            setAllNormal(gs);
            boolean ok = test(is, gs, outputs, testIn, testOut);
            if (ok) {
                out.println("No faults detected");
            } else {
                int failId = -1;
                char failType = 0;
                loop:
                for (int i = 0; i < gs.length; i++) {
                    for (char t : "01i".toCharArray()) {
                        setAllNormal(gs);
                        gs[i].type = t;
                        if (test(is, gs, outputs, testIn, testOut)) {
                            if (failId == -1) {
                                failId = i;
                                failType = t;
                            } else {
                                failId = -2;
                                break loop;
                            }
                        }
                    }
                }
                if (failId < 0) {
                    out.println("Unable to totally classify the failure");
                } else {
                    if (failType == 'i') {
                        out.printf("Gate %d is failing; output inverted\n", failId + 1);
                    } else if (failType == '0') {
                        out.printf("Gate %d is failing; output stuck at 0\n", failId + 1);
                    } else if (failType == '1') {
                        out.printf("Gate %d is failing; output stuck at 1\n", failId + 1);
                    } else throw new RuntimeException();
                }
            }
        }
    }

    private boolean test(Gate[] is, Gate[] gs, int[] outputs, boolean[][] testIn, boolean[][] testOut) {
        for (int i = 0; i < testIn.length; i++) {
            if (!test(is, gs, outputs, testIn[i], testOut[i])) return false;
        }
        return true;
    }

    private boolean test(Gate[] is, Gate[] gs, int[] outputs, boolean[] testIn, boolean[] testOut) {
        for (int i = 0; i < is.length; i++) {
            is[i].ready = true;
            is[i].value = testIn[i];
        }
        for (Gate g : gs) g.ready = false;
        int count = 0;
        while (count < gs.length) {
            for (int i = 0; i < gs.length; i++) {
                if (!gs[i].ready && gs[i].canCalc()) {
                    gs[i].calcValue();
                    count++;
                }
            }
        }
        for (int i = 0; i < outputs.length; i++) {
            if (gs[outputs[i]].value != testOut[i]) return false;
        }
        return true;
    }

    private void setAllNormal(Gate[] gs) {
        for (Gate g : gs) g.type = 'n';
    }

    private Gate get(Gate[] is, Gate[] gs, String str) {
        if (str.charAt(0) == 'i') return is[Integer.valueOf(str.substring(1)) - 1];
        if (str.charAt(0) == 'g') return gs[Integer.valueOf(str.substring(1)) - 1];
        throw new RuntimeException();
    }

    class Gate {
        char c;
        Gate[] source;
        boolean value;
        boolean ready;
        char type;// 'n', 'i', '0', '1'

        boolean canCalc() {
            for (Gate g : source) if (!g.ready) return false;
            return true;
        }

        boolean calcValue() {
            ready = true;
            if (type == 'n') return value = calcValueNormal();
            if (type == 'i') return value = !calcValueNormal();
            if (type == '0') return value = false;
            if (type == '1') return value = true;
            throw new RuntimeException();
        }

        boolean calcValueNormal() {
            if (c == 'n') {
                return !source[0].value;
            } else if (c == 'o') {
                return source[0].value || source[1].value;
            } else if (c == 'a') {
                return source[0].value && source[1].value;
            } else if (c == 'x') {
                return source[0].value ^ source[1].value;
            }
            throw new RuntimeException();
        }
    }
}
