package on2012_6_8.crossingtheriver;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class CrossingTheRiver {
    String POSSIBLE = "POSSIBLE";
    String IMPOSSIBLE = "IMPOSSIBLE";
   public String isItEvenPossible(int waterWidth, int landWidth, int[] blockHeight, int depth) {
		int numDepth = 0;
       for(int i:blockHeight)if(i==depth)numDepth++;
       if(numDepth >= waterWidth)return POSSIBLE;
       Arrays.sort(blockHeight);
       int n = blockHeight.length;
       if(waterWidth + landWidth > n)return IMPOSSIBLE;
       int[] cnt = new int[101];
       for(int b:blockHeight)cnt[b]++;
       for(int d=depth;d<=depth+1;d++) for (int a = depth; a < 101; a++) for (int b = a-depth; b <= a-depth+1; b++)
           for (int c = 0; c < 101; c++) {
               if(can(d,a,b,c,waterWidth,landWidth,cnt.clone()))return POSSIBLE;
           }
       return IMPOSSIBLE;
   }

    private boolean can(int d, int a, int b, int c, int waterWidth, int landWidth, int[] cnt) {
        int needW = a-d+1;
        if(needW > waterWidth)return false;
        int needL = c-b+1;
        if(needL > landWidth)return false;
        for(int i=d;i<=a;i++)if(cnt[i]-- ==0)return false;
        for(int i=b;i<=c;i++)if(cnt[i]-- ==0)return false;
        int canW = 0;
        for(int i=d;i<=a;i++)canW+=cnt[i];
        int cntL = 0;
        for(int i=b;i<=c;i++)cntL+=cnt[i];
        if(needW + canW < waterWidth)return false;
        if(needL + cntL < landWidth)return false;
        int from = Math.max(b,d);
        int to  =Math.min(c,a);
        if(from<=to){
            int both = 0;
            for(int i=from;i<=to;i++)both += cnt[i];
            if(needW + needL + canW + cntL - both < waterWidth + landWidth)return false;
        }
        return true;
    }
}

