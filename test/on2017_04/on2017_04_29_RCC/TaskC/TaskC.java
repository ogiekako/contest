package on2017_04.on2017_04_29_RCC.TaskC;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long l = in.nextLong(), r = in.nextLong();
        long res = solve(r) - solve(l - 1);
        out.println(res);
    }

    private long solve(long n) {
        long ten = 10;
        long res = 0;
        for (int i = 1; i < 10; i++) {
            if (i <= n) res++;
        }
        for (; ; ) {
            if(ten>n)return res;
            for (int j=1;j<10;j++) {
                if(ten * j <= n) {
                    if (ten*(j+1) > n) {
                        res += (n - (ten * j)) / 10 + (n%10 >= j ? 1 : 0);
                        return res;
                    }else {
                        res += ten / 10;
                    }
                }
            }

            ten *= 10;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            long res = new TaskC().solve(i);
            long exp = solveStupid(i);
            if (res != exp) {
                System.out.println(i + " " + res + " " + exp);
            }
        }
    }

    private static long solveStupid(int n) {
        long res = 0;
        for (int i = 1; i < n + 1; i++) {
            String s = "" + i;
            if (s.charAt(0) == s.charAt(s.length() - 1)) res++;
        }
        return res;
    }
}
