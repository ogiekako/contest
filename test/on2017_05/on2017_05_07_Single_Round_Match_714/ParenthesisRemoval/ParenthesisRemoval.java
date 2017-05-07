package on2017_05.on2017_05_07_Single_Round_Match_714.ParenthesisRemoval;



import java.util.Arrays;

public class ParenthesisRemoval {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9+7);
    public int countWays(String s) {
        int n = s.length();
        long res = 1;
        int sum = 0;
        for(int i=n-1;i>=0;i--){
            if (s.charAt(i)=='(') {
                res = res * sum % MOD;
                sum--;
            } else {
                sum++;
            }
        }
        return (int) res;
    }
}
