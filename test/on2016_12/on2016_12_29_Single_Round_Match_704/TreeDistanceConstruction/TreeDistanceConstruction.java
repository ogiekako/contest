package on2016_12.on2016_12_29_Single_Round_Match_704.TreeDistanceConstruction;



import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TreeDistanceConstruction {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] construct(int[] ds) {
        int n = ds.length;
        int max = 0;
        for(int d:ds)max=Math.max(max,d);
        Queue<Integer>[] cnt = new Queue[max + 1];
        for (int i = 0; i < max + 1; i++)
            cnt[i] = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            cnt[ds[i]].add(i);
        }

        int[] path = new int[max + 1];
        int[] res = new int[2 * (n-1)];
        int ptr = 0;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<max+1;i++){
            int d = Math.max(i, max-i);
            min = Math.min(min, d);
            if(cnt[d].isEmpty()) return new int[0];
            path[i] = cnt[d].poll();
            if(i>0){
                res[ptr++] = path[i-1];
                res[ptr++] = path[i];
            }
        }
        for(int i=0;i<=min;i++){
            if(!cnt[i].isEmpty()) return new int[0];
        }
        for(int i=min+1;i<=max;i++){
            while(!cnt[i].isEmpty()){
                res[ptr++] = cnt[i].poll();
                res[ptr++] = path[i - 1];
            }
        }

        return res;
    }
}
