package src;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TheKingsArmyDiv1 {
    public int getNumber(String[] state) {
        int[] a = new int[state[0].length()];
        for (int i = 0; i < a.length; i++) {
            a[i] = (state[0].charAt(i) == 'H' ? 1 : 0)
                    + (state[1].charAt(i) == 'H' ? 2 : 0);
        }
        return solve(a);
    }

    int solve(int[] a) {
        int n = a.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == 0) {
                if (i > 0 && a[i-1] > 0) {// !!!
                    a[i] = a[i - 1];
                    res++;
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (a[i] == 0) {
                if (i < n - 1) {
                    a[i] = a[i + 1];
                    res++;
                }
            }
        }
        if (a[0] == 0) return -1;
        int cnt1 = f(a, 1);
        int cnt2 = f(a, 2);
        return res + Math.min(cnt1, cnt2);
    }

    private void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int f(int[] a, int b) {
        int n = a.length;
        int res = 0;
        boolean hasZero = false;
        for (int i = 0; i < n; i++) {
            if ((a[i] & b) == 0) hasZero = true;
        }
        for (int i = 0; i < n; ) {
            int j = i;
            if ((a[j] & b) == b) {
                boolean zero = false;
                while (j < n && (a[j] & b) == b) {
                    if ((a[j] & (3 - b)) == 0) zero = true;
                    j++;
                }
                if (zero) res++;
            }
            i = j + 1;
        }
        return res + (hasZero ? 1 : 0);
    }

    void test() {
        Random rnd = new Random();
        String S="",T="";
        for (int i = 0; i < 200; i++) {
            S += rnd.nextBoolean() ? "H" : "S";
            T += rnd.nextBoolean() ? "H" : "S";
        }
        System.out.println(S);
        System.out.println(T);
        for (int i = 0; i < 1000; i++) {
            int n = 10;
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = rnd.nextInt(4);
            }
            int exp = new TheKingsArmyDiv1().solve(a);
            int res = solve2(a);
            if (exp != res) {
                debug(a, exp, res);
                throw new AssertionError();
            }
        }
    }

    int key(int[] a) {
        int key = 0;
        for (int b : a) key = key * 4 + b;
        return key;
    }

    private int solve2(int[] a) {
        boolean ok = true;
        boolean bad = true;
        for (int b : a) {
            if (b != 3) ok = false;
            if (b != 0) bad = false;
        }
        if (ok) return 0;
        if (bad) return -1;

        int[] memo = new int[1 << a.length * 2];
        Arrays.fill(memo, Integer.MAX_VALUE);
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(a.clone());
        memo[key(a)] = 0;
        int n = a.length;
        while (!queue.isEmpty()) {
            a = queue.poll();
            int key = key(a);
            if (key == (1<<a.length*2)-1)return memo[key];
            int nStep = memo[key] + 1;
            for (int i = 1; i <= 2; i++) {
                for (int j = 0; j < n; j++) {
                    if (a[j] == i) {
                        a[j] = 3;
                        go(a, queue, memo, nStep);
                        a[j] = i;
                    }
                    for (int k = -1; k <= 1; k += 2) {
                        if (n > j + k && j + k >= 0 && (a[j] & i) > 0 && (a[j + k] & i) == 0) {
                            a[j + k] ^= i;
                            go(a, queue, memo, nStep);
                            a[j + k] ^= i;
                        }
                    }
                }
            }
        }
        throw new AssertionError();
    }

    private void go(int[] a, Queue<int[]> queue, int[] memo, int step) {
        int key = key(a);
        if (memo[key] > step) {
            memo[key] = step;
            queue.offer(a.clone());
        }
    }

    public static void main(String[] args) {
        new TheKingsArmyDiv1().test();
    }

    /*
    SHHSHSSHHS
    SSSSHSHHSS

SSSSSSSSSSHSHSSSHS
SSSSSSHSHSHSSSSSHH
14

    -> 6
     */
}
