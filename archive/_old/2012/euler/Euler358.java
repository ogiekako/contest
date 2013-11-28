package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Euler358 {
    /*
00000000137 から始まり,56789 でおわる数.
n 桁とすると, それが Cyclic number であるためには,
1/(n+1) の循環節が,n 桁であることが必要十分.
...56789 * n = 9999999...
n = ...   09891
     */
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String beginning = "00000000137";
        String lower = "09891";
        for (long higher = 7298; ; higher++) {
            long number = higher * 100000 + (Integer.valueOf(lower));
            long res = divide(number, beginning);
            if (res != -1) {
                out.println(res); return;
            }
        }
    }

    private long divide(long number, String beginning) {
        long divisible = 1;
        int i = 0;
        long res = 0;
        do {
            divisible *= 10;
            int k = (int) (divisible / number);
            if (i < beginning.length() && k != beginning.charAt(i) - '0') return -1;
            res += k;
            i++;
            divisible -= k * number;
        } while (divisible != 1);
        System.err.println(i + " " + number);
        if (i != number - 1) return -1;
        return res;
    }
}
