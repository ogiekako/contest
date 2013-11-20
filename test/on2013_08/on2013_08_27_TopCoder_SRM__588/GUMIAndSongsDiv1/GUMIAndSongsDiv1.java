package on2013_08.on2013_08_27_TopCoder_SRM__588.GUMIAndSongsDiv1;


import net.ogiekako.algorithm.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class GUMIAndSongsDiv1 {
    public int maxSongs(int[] duration, int[] tone, int T) {
        Pair<Integer, Integer>[] p = new Pair[duration.length];
        for (int i = 0; i < p.length; i++) p[i] = Pair.of(tone[i], duration[i]);
        Arrays.sort(p);
        for (int res = duration.length; res > 1; res--) {
            for (int i = 0; i < duration.length; i++)
                for (int j = i + res - 1; j < duration.length; j++) {
                    int sum = p[j].first - p[i].first;
                    List<Integer> ds = new ArrayList<Integer>();
                    for (int k = i; k <= j; k++) ds.add(p[k].second);
                    Collections.sort(ds);
                    for (int k = 0; k < res; k++) {
                        sum += ds.get(k);
                    }
                    if (sum <= T) return res;
                }
        }
        for (int d : duration) if (d <= T) return 1;
        return 0;
    }
}
