package tmp;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class FoxAndMp3 {
    public String[] playList(int n) {
        Set<String> set = new TreeSet<String>();
        for (long i = 1; i <= n; i *= 10) {
            for (int j = 0; j < 50; j++) {
                long v = i + j;
                if (1 <= v && v <= n) {
                    String s = String.format("%d.mp3", v);
                    set.add(s);
                }
            }
        }
        String[] candidates = set.toArray(new String[0]);
        return sub(candidates);
    }

    String[] f(int n) {
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) {
            ss[i] = String.format("%d.mp3", i + 1);
        }
        Arrays.sort(ss);
        return sub(ss);
    }

    private String[] sub(String[] ss) {
        int n = Math.min(ss.length, 50);
        String[] res = new String[n];
        System.arraycopy(ss, 0, res, 0, n);
        return res;
    }

    public static void main(String[] args) {
        FoxAndMp3 instace = new FoxAndMp3();
        Random r = new Random(120381208L);
        for (int i = 0; i < 40000; i++) {
            System.err.println("i = " + i);
            int n = r.nextInt(10000);
            String[] a = instace.f(n);
            String[] b = instace.playList(n);
            if (!Arrays.equals(a, b)) {
                System.err.println(n);
                throw new AssertionError();
            }
        }
    }
}
