package on2017_09.on2017_09_27_Single_Round_Match_721.GraphAndPairs;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphAndPairs {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] construct(int d, int k) {
        List<Integer> ds = new ArrayList<>();
        while (k > 0) {
            int kk;
            for (kk = 1;; kk++) {
                int num = d > 2 ? kk * kk : kk * (2 * kk - 1);
                if(num > k) break;
            }
            kk--;
            int num = d > 2 ? kk * kk : kk * (2 * kk - 1);
            k -= num;
            ds.add(kk);
        }
        List<Integer> result = new ArrayList<>();
        result.add(1000);
        int o = 0;
        for (int deg : ds) {
            int m = deg * 2 + (d - 2);

            for (int i = 0; i < d - 2; i++) {
                result.add(o + i);
                result.add(o + i + 1);
            }
            for (int i = 0; i < deg * 2; i++) {
                result.add(i < deg ? o : o + d - 2);
                result.add(o + d - 1 + i);
            }
            o += m + 1;
        }
//        debug(o);
        if (o >= 1000) throw new AssertionError();
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        GraphAndPairs graphAndPairs = new GraphAndPairs();
        for (int d = 2; d <= 50; d++) {
            for (int k = 50000 - 50; k <= 50000; k++) {
//                debug(d, k);
                graphAndPairs.construct(d, k);
            }
        }
    }
}
