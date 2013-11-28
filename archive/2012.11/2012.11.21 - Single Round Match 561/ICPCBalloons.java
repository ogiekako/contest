package tmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICPCBalloons {
    public int minRepaintings(int[] balloonCount, String balloonSize, int[] maxAccepted) {
        List<Integer> mid1 = new ArrayList<Integer>();
        List<Integer> lar1 = new ArrayList<Integer>();
        for (int i = 0; i < balloonCount.length; i++) {
            if (balloonSize.charAt(i) == 'L') lar1.add(balloonCount[i]);
            else mid1.add(balloonCount[i]);
        }
        Collections.sort(mid1);
        Collections.sort(lar1);
        int M1 = 0, L1 = 0;
        for (int i : mid1) M1 += i;
        for (int i : lar1) L1 += i;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 1 << maxAccepted.length; i++) {
            List<Integer> mid = new ArrayList<Integer>();
            List<Integer> large = new ArrayList<Integer>();
            int M = 0, L = 0;
            for (int j = 0; j < maxAccepted.length; j++) {
                if (i << 31 - j < 0) {
                    M += maxAccepted[j];
                    mid.add(maxAccepted[j]);
                } else {
                    L += maxAccepted[j];
                    large.add(maxAccepted[j]);
                }
            }
            if (M > M1 || L > L1) continue;
            Collections.sort(mid);
            Collections.sort(large);
            int val = M + L;
            for (int j = mid.size() - 1, k = mid1.size() - 1; j >= 0; j--, k--) {
                if (k >= 0) {
                    val -= Math.min(mid.get(j), mid1.get(k));
                }
            }
            for (int j = large.size() - 1, k = lar1.size() - 1; j >= 0; j--, k--) {
                if (k >= 0) {
                    val -= Math.min(large.get(j), lar1.get(k));
                }
            }
            res = Math.min(res, val);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
