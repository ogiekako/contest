package src;


import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.SplayTreeList;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

// Problem I : RMQ
// Range Minimum Query with rotation
public class AOJ1508 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), q = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        SplayTreeList.Min array = new SplayTreeList.Min();
        for (int i = 0; i < n; i++) {
            array.add(a[i]);
        }
        for (int i = 0; i < q; i++) {
            int x = in.nextInt(), y = in.nextInt(), z = in.nextInt();
            if (x == 0) {// shift
                //   y  z
                //   0123
                //-> 3012
                array.insert(y + 1, array.cut(y, z));
            } else if (x == 1) {// min
                int res = array.query(y, z + 1);
                out.println(res);
            } else {// set
                array.set(y, z);
            }
        }
    }
}

