package tmp;

import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.SplayTree;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class RestaurantRating {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] x = new int[N];
        int[] v = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = in.nextInt();
            if (x[i] == 1) v[i] = in.nextInt();
        }
        solve(x, v, out);
    }

    public void solve(int[] x, int[] v, PrintWriter out) {
        int N = x.length;
        SplayTree root = new SplayTree(Integer.MAX_VALUE);
        int size = 0;
        for (int i = 0; i < N; i++) {
            if (x[i] == 1) {
                root = root.insertAndGetNewRoot(v[i]);
                size++;
            } else {
                int k = size - size / 3;
                if (k == size) {
                    out.println("No reviews yet");
                } else {
                    root = root.getKth(k);
                    out.println(root.getKey());
                }
            }
        }
    }
}
