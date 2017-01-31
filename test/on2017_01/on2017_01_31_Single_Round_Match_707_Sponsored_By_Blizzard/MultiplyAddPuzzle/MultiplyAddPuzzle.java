package on2017_01.on2017_01_31_Single_Round_Match_707_Sponsored_By_Blizzard.MultiplyAddPuzzle;



import java.util.Arrays;

public class MultiplyAddPuzzle {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long minimalSteps(long s, long t, long a, long b) {
        if(s==t)return 0;
        if (a == 0){
            if(b==0) {
                return t==0 ? 1 : -1;
            }
            if(b==1){
                return -1;
            }
            int res=0;
            while(t>=s){
                if(t==s)return res;
                if(t%b!=0)return -1;
                t/=b;
                res++;
            }
            return -1;
        }
        if (b==0) {
            if (t-s >= 0 && (t-s)%a==0) return (t-s) / a;
            if (t % a == 0) return t / a + 1;
            return -1;
        }
        if (b==1) {
            if (t-s >= 0 && (t-s)%a==0) return (t-s) / a;
            return -1;
        }
        long res = Long.MAX_VALUE;
        long B = 1;
        long INF = (long) 2e18;
        for(int k=0;;k++, B*=b){
            if (s > INF / B) break;
            long r = t - s * B;
            if (r < 0) {
                break;
            }
            if (r % a != 0) {
                if (B > INF / b) {
                    break;
                }
                continue;
            }
            r /= a;
            long val = k;
            for(int i=0;i<k;i++){
                val += r % b;
                r /= b;
            }
            val += r;
            if (val < res) {
                res = val;
//                debug(k, val);
            }
            if (B > INF / b) {
                break;
            }
        }
        return res == Long.MAX_VALUE ? -1 : res;
    }
}
