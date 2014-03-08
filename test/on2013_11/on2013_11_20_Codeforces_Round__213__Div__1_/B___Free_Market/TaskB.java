package on2013_11.on2013_11_20_Codeforces_Round__213__Div__1_.B___Free_Market;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), d = in.nextInt();
        boolean[] possible = new boolean[(int) (n * 1e4 + 1e4 + 10)];
        possible[0] = true;
        for (int i = 0; i < n; i++) {
            int c = in.nextInt();
            for (int j = possible.length - 1; j >= 0; j--) if (possible[j]) possible[j + c] = true;
        }
        int cur = 0;
        int step = 0;
        for(;;){
            int nxt = cur + d;
            while(!possible[nxt])nxt--;
            if(nxt == cur)break;
            step++;
            cur = nxt;
        }
        out.printFormat("%d %d\n", cur, step);
    }
}
