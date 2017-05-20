package on2017_05.on2017_05_21_2017_TopCoder_Open_Algorithm.FoxAndCake2;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class FoxAndCake2 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String isPossible(int c, int s) {
        int a = Math.min(c,s);
        int b = Math.max(c,s);
        if (a >= 8) return "Possible";
        return solve(a,b) ? "Possible" : "Impossible";
    }

    private boolean solve(int a, int b) {
        if (a == 0 || b ==0) return false;
        if (MathUtils.gcd(a,b) > 1) return true;
        for(int i=2;i<=a;i++)for(int j=2;j<=10;j++) {
            if (MathUtils.gcd(i,j) > 1 && solve(a-i, b-j)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        FoxAndCake2 foxAndCake2 = new FoxAndCake2();
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                boolean res = foxAndCake2.solve(i,j);
//                System.out.print(res ? "Y" : "N");
                if (i < j && 2 < i) {
                    System.out.printf("%d, %d -> %b\n", i, j, res);
                }
            }
        }
        for (int i = 8; i < 20; i++) {
            for (int j = 8; j < 20; j++) {
                System.out.print(foxAndCake2.solve(i,j) ? "Y" : "N");
            }
            System.out.println();
        }
    }
}
