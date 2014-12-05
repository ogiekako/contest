package src;

public class ShoppingSurveyDiv1 {
    public int minValue(int N, int K, int[] s) {
        for(int res=0;res<=N;res++){
            int rest = 0;
            for(int t:s)rest += Math.max(0, t-res);
            if(rest <= (N-res) * (K-1)){
                return res;
            }
        }
        throw new AssertionError();
    }
}
