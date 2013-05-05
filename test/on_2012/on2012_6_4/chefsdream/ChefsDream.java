package on_2012.on2012_6_4.chefsdream;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChefsDream {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < N; i++) {
            int t = in.nextInt();
            if (!map.containsKey(t)) map.put(t, new ArrayList<Integer>());
            map.get(t).add(i);
        }
        int res = 0;
        for (List<Integer> list : map.values()) {
            int left = Integer.MIN_VALUE;
            for (int cur : list) {
                if (left + K <= cur) {
                    left = cur;
                    res++;
                }
            }
        }
        out.println(res);
    }
}
