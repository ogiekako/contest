package on2018_05.on2018_05_03_2018_TopCoder_Open_Algorithm.StablePairsDiv1;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StablePairsDiv1 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] findMaxStablePairs(int n, int c, int k) {
        List<Integer> rev = new ArrayList<>();
        for (int i=0, s=n+(n-1);i<k;i++) {
            if (s <= 2) return new int[0];
            if (s%2 == 1) {
                rev.add(s/2 + 1);
                rev.add(s/2);
            } else {
                rev.add(s/2+1);
                rev.add(s/2-1);
            }
            s -= c;
        }
        int[] r = new int[rev.size()];
        for (int i = 0; i < r.length; i++) {
            r[i] = rev.get(r.length - 1 - i);
            if (i > 0 && r[i-1] >= r[i]) return new int[0];
            if (r[i] > n || r[i] <= 0) return new int[0];
        }
        return r;
    }
}
