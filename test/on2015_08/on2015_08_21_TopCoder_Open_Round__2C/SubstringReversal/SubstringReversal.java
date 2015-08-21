package on2015_08.on2015_08_21_TopCoder_Open_Round__2C.SubstringReversal;



import net.ogiekako.algorithm.misc.coordinateTransformation.P;

import java.util.Arrays;
import java.util.Random;

public class SubstringReversal {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] solve(String S) {
        boolean sorted = true;
        for (int i = 0; i < S.length() - 1; i++) {
            if (S.charAt(i) > S.charAt(i + 1)) sorted = false;
        }
        if (sorted) {
            return new int[]{0, 0};
        }
        for (int L = 0; L < S.length(); L++) {
            boolean max = true;
            for (int j = L + 1; j < S.length(); j++) {
                if (S.charAt(L) > S.charAt(j)) max = false;
            }
            if (max) continue;
            String best = S;
            int R = L;
            for (int j = L + 1; j < S.length(); j++) {
                String cur = rev(S, L, j + 1);
                if (best.compareTo(cur) > 0) {
                    best = cur;
                    R = j;
                }
            }
            return new int[]{L,R};
        }
        throw new AssertionError();
    }

    private String rev(String S, int L, int R) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            if (L <= i && i < R) {
                b.append(S.charAt(R - 1 - (i - L)));
            } else {
                b.append(S.charAt(i));
            }
        }
        return b.toString();
    }

    public static void main(String[] args) {
        new SubstringReversal().test();
    }

    private void test() {
        Random rnd = new Random(12L);
        for (int i = 0; i < 100; i++) {
            String s = "";
            for (int j = 0; j < 500; j++) {
                s += (char)(rnd.nextInt(3) + 'a');
            }
            int[] res = solve(s);
            int[] exp = solveNaive(s);

            System.err.println(Arrays.toString(res));
            if(!Arrays.equals(res,exp)){
                debug(s);
            }
        }
    }

    private int[] solveNaive(String S) {
        int n = S.length();
        String best = S;
        int x=0,y=0;
        for (int i = 0; i < n; i++) {
            for (int j =i; j < n; j++) {
                String cur = rev(S,i,j+1);
                if(best.compareTo(cur) > 0) {
                    best = cur;
                    x=i;y=j;
                }
            }
        }
        return new int[]{x,y};
    }
}
