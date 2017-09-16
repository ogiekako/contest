package net.ogiekako.algorithm.misc.dp.connectedRelation.problems;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class TDPC_S {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        MyScanner in = new MyScanner(inputStream);
        MyPrintWriter out = new MyPrintWriter(outputStream);
        TaskS solver = new TaskS();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskS {
        public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
            Mint.set1e9_7();

            int h = in.nextInt(), w = in.nextInt();
            int[] init = new int[h];
            Arrays.fill(init, -1);
            init[0] = 0;
            State initState = new State(init, 0);
            HashMap<State, Mint>[] dp = new HashMap[2];
            for (int i = 0; i < 2; i++) {
                dp[i] = new HashMap<>();
            }
            int cur = 0, nxt = 1;
            dp[cur].put(initState, Mint.ONE);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    dp[nxt].clear();
                    for (Map.Entry<State, Mint> e : dp[cur].entrySet()) {
                        State key = e.getKey();
                        State s1 = key.next(j, false);
                        if (s1 != null) {
                            Mint tmp = dp[nxt].get(s1);
                            if (tmp == null) tmp = Mint.ZERO;
                            dp[nxt].put(s1, tmp.add(e.getValue()));
                        }
                        State s2 = key.next(j, true);
                        Mint tmp = dp[nxt].get(s2);
                        if (tmp == null) tmp = Mint.ZERO;
                        dp[nxt].put(s2, tmp.add(e.getValue()));
                    }
                    int tmp = cur;
                    cur = nxt;
                    nxt = tmp;
                }
            }

            Mint res = Mint.ZERO;
            for (Map.Entry<State, Mint> e : dp[cur].entrySet()) {
                State s = e.getKey();
                if (s.a[h - 1] == s.o) {
                    res = res.add(e.getValue());
                }
            }
            out.println(res);
        }

        class State {
            int[] a;
            int o;

            public State(int[] a, int o) {
                this.a = a;
                this.o = o;
            }

            public State next(int i, boolean black) {
                int[] na = a.clone();
                if (!black) {
                    na[i] = -1;
                    int p = -1;
                    for (int j = 0; j < na.length; j++) {
                        if (na[j] == o) {
                            p = j;
                        }
                    }
                    if (p < 0) return null;
                    ArrayUtils.normalize(na);
                    return new State(na, na[p]);
                } else {
                    int p = -1;
                    for (int j = 0; j < na.length; j++) {
                        if (a[j] == o) p = j;
                    }
                    if (p < 0) throw new AssertionError();
                    if (i > 0 && a[i] >= 0 && a[i - 1] >= 0) {
                        for (int j = 0; j < na.length; j++) {
                            if (na[j] == a[i - 1]) na[j] = a[i];
                        }
                    } else if (a[i] >= 0) {
                        na[i] = a[i];
                    } else if (i > 0 && a[i - 1] >= 0) {
                        na[i] = a[i - 1];
                    } else {
                        na[i] = na.length;
                    }
                    ArrayUtils.normalize(na);
                    return new State(na, na[p]);
                }
            }


            public boolean equals(Object o1) {
                if (this == o1) return true;
                if (o1 == null || getClass() != o1.getClass()) return false;

                State state = (State) o1;

                if (o != state.o) return false;
                return Arrays.equals(a, state.a);
            }


            public int hashCode() {
                int result = Arrays.hashCode(a);
                result = 31 * result + o;
                return result;
            }

        }

    }

    static class ArrayUtils {
        public static void normalize(int[] is) {
            int[] id = new int[is.length + 1];
            Arrays.fill(id, -1);
            int p = 0;
            for (int i : is) if (i >= 0 && id[i] == -1) id[i] = p++;
            for (int i = 0; i < is.length; i++) if (is[i] >= 0) is[i] = id[is[i]];
        }

    }

    static class MyPrintWriter {
        PrintWriter out;

        public MyPrintWriter(OutputStream outputStream) {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public MyPrintWriter(Writer writer) {
            out = new PrintWriter(writer);
        }

        public void println(Object... os) {
            if (os.length == 0) {
                out.println();
                return;
            }
            for (int i = 0; i < os.length - 1; i++) {
                out.print(os[i]);
                out.print(' ');
            }
            out.println(os[os.length - 1]);
        }

        public void close() {
            out.close();
        }

    }

    static class MyScanner {
        private final InputStream in;
        private static final int BUFSIZE = 65536;
        int bufLen;
        int bufPtr;
        byte[] buf = new byte[BUFSIZE];

        public MyScanner(InputStream in) {
            this.in = in;
        }

        public int read() {
            if (bufLen == -1)
                throw new InputMismatchException();
            if (bufPtr >= bufLen) {
                bufPtr = 0;
                try {
                    bufLen = in.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (bufLen <= 0)
                    return -1;
            }
            return buf[bufPtr++];
        }

        public int nextInt() {
            long v = nextLong();
            if (v < Integer.MIN_VALUE || v > Integer.MAX_VALUE)
                throw new IllegalArgumentException("out of range: " + v);
            return (int) v;
        }

        public long nextLong() {
            int c = read();
            if (c == -1) throw new NoSuchElementException();
            while (c != '-' && (c < '0' || '9' < c)) {
                c = read();
                if (c == -1) throw new NoSuchElementException();
            }
            if (c == '-') return -nextLong();
            long res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while ('0' <= c && c <= '9');
            return res;
        }

    }

    static class Mint {
        private static int MOD = 0;
        public static Mint ZERO = new Mint.UninitializedMint();
        public static Mint ONE = new Mint.UninitializedMint();
        final long x;

        private Mint() {
            x = 0;
        }

        private Mint(long x) {
            if (x < 0 || x >= getMod())
                throw new IllegalArgumentException("x should be in the valid range, but was " + x);
            this.x = x;
        }

        public static void set1e9_7() {
            setMod((int) (1e9 + 7));
        }

        public static void setMod(int mod) {
            if (mod <= 0) throw new IllegalArgumentException("mod should be positive: " + mod);
            if (!BigInteger.valueOf(mod).isProbablePrime(10))
                throw new IllegalArgumentException("mod must be a prime." + mod);
            MOD = mod;

            ZERO = of(0);
            ONE = of(1);
        }

        public static int getMod() {
            if (MOD == 0) throw new IllegalArgumentException("Please set mod first.");
            return MOD;
        }

        public static long normalize(long x) {
            int m = getMod();
            if (0 <= x) {
                if (x < m) return x;
                if (x < m * 2) return x - m;
                return x % m;
            }
            if (-m <= x) return x + m;
            x %= m;
            return x == 0 ? 0 : x + m;
        }

        public static Mint of(long value) {
            return new Mint(normalize(value));
        }

        public Mint add(Mint other) {
            return of(x + other.x);
        }


        public String toString() {
            return "" + x;
        }


        public boolean equals(Object o) {
            if (!(o instanceof Mint)) return false;
            Mint other = (Mint) o;
            return x == other.x;
        }


        public int hashCode() {
            return Long.hashCode(x);
        }

        private static class UninitializedMint extends Mint {
            private UninitializedMint() {
            }


            public Mint add(Mint other) {
                throw new IllegalArgumentException("Uninitialized mod");
            }


            public String toString() {
                throw new IllegalArgumentException("Uninitialized mod");
            }


            public boolean equals(Object o) {
                throw new IllegalArgumentException("Uninitialized mod");
            }


            public int hashCode() {
                throw new IllegalArgumentException("Uninitialized mod");
            }

        }

    }
}