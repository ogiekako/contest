package on_2012.on2012_8_15.xortravelingsalesman;


// Paste me into the FileEdit configuration dialog

import java.util.LinkedList;
import java.util.Queue;

public class XorTravelingSalesman {
    public int maxProfit(int[] cityValues, String[] roads) {
        int N = cityValues.length;
        boolean[][] can = new boolean[N][1024];
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(0); que.offer(cityValues[0]);
        can[0][cityValues[0]] = true;
        int res = 0;
        while (!que.isEmpty()) {
            int cur = que.poll(), val = que.poll();
            res = Math.max(res, val);
            for (int i = 0; i < N; i++)
                if (roads[cur].charAt(i) == 'Y' && !can[i][val ^ cityValues[i]]) {
                    que.offer(i); que.offer(val ^ cityValues[i]); can[i][val ^ cityValues[i]] = true;
                }
        }
        return res;
    }
}

