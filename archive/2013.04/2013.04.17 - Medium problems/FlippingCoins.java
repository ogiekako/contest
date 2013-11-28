package tmp;

import net.ogiekako.algorithm.dataStructure.segmentTree.SegTree;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class FlippingCoins {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        SegTree.Coin array = new SegTree.Coin(n);
        for (int i = 0; i < m; i++) {
            int Q = in.nextInt(), A = in.nextInt(), B = in.nextInt() + 1;
            if (Q == 0) array.add(A, B, true);
            else out.println(array.convolution(A, B));
        }
    }
}
