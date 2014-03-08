package on2014_02.on2014_02_16_TopCoder_Open_Round__3A.YetAnotherANDProblem;



public class YetAnotherANDProblem {
    public String test(int[] X, int K, int[] queries) {
        String res = "";
        for(int q : queries) {
            res += solve(X,K,q);
        }
        return res;
    }
    private char solve(int[] X, int K, int Q) {
        int n = X.length;
        if(K == 1){
            for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)if((X[i] & X[j]) == Q)return '+';
            return '-';
        }
        for(int i=0;i<1<<n;i++)if(3 <= Integer.bitCount(i) && Integer.bitCount(i) <= 1L<<K){
            int value = Integer.MAX_VALUE;
            for(int j=0;j<n;j++)if(i<<31-j<0)value &= X[j];
            if(value == Q)return '+';
        }
        return '-';
    }
}
