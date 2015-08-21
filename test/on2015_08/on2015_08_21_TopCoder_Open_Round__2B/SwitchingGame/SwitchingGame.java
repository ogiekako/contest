package on2015_08.on2015_08_21_TopCoder_Open_Round__2B.SwitchingGame;



import java.util.Arrays;
import java.util.Random;

public class SwitchingGame {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int timeToWin(String[] states) {
        int M = states[0].length();
        char[] cur = new char[M];
        for (int i = 0; i < M; i++) {
            cur[i] = '-';
        }
        int res = 0;
        for (int i = 0; i < states.length; i++) {
            boolean toPlus = false, toMinus = false;
            for (int j = 0; j < M; j++) {
                if (cur[j] == '-' && states[i].charAt(j) == '+') {
                    toPlus = true;
                }
                if (cur[j] == '+' && states[i].charAt(j) == '-') {
                    toMinus = true;
                }
            }
            if (toPlus) {
                res++;
                for (int j = 0; j < M; j++) {
                    if (cur[j] != '-')continue;
                    for (int k = i;k < states.length;k++) {
                        if(states[k].charAt(j) == '?') continue;
                        if(states[k].charAt(j) == '+') {
                            cur[j] = '+';
                        }
                        break;
                    }
                }
            }
            if (toMinus) {
                res++;
                for (int j = 0; j < M; j++) {
                    if (cur[j] != '+')continue;
                    for (int k = i;k < states.length;k++) {
                        if(states[k].charAt(j) == '?') continue;
                        if(states[k].charAt(j) == '-') {
                            cur[j] = '-';
                        }
                        break;
                    }
                }
            }
        }
        return res + states.length;
    }

    public static void main(String[] args) {
        int N = 50, M = 8;
        for(int i=0;i<100;i++) {
            System.err.println(i);
            String[] prob = gen(N, M);
            int res = new SwitchingGame().timeToWin(prob);
            int exp = solveNaitve(prob);
            if (res != exp) {
                debug(res, exp, prob);
            }
        }
    }

    private static int solveNaitve(String[] states) {
        int M = states[0].length();
        // 0: -, 1: +
        int[] dp = new int[1<<M];
        int INF = Integer.MAX_VALUE / 2;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(String state : states) {
            int[] nDp = new int[dp.length];
            Arrays.fill(nDp, INF);
            for (int i = 0; i < dp.length; i++) {
                if (dp[i] >= INF)continue;
                for (int j = 0; j < dp.length; j++) {
                    if (!match(j, state))continue;
                    int val = dp[i];
                    if (((~i) & j) > 0) val++;
                    if (((~j) & i) > 0) val++;
                    nDp[j] = Math.min(nDp[j], val);
                }
            }
            dp = nDp;
        }
        int res = Integer.MAX_VALUE;
        for(int v:dp)res = Math.min(res,v);
        return res + states.length;
    }

    private static boolean match(int mask, String state) {
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) == '-' && mask << 31 - i < 0)return false;
            if (state.charAt(i) == '+' && mask << 31 - i >= 0)return false;
        }
        return true;
    }

    static Random rnd = new Random(1209814L);
    private static String[] gen(int n, int m) {
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            res[i] = "";
            for (int j = 0; j < m; j++) {
                int  p = rnd.nextInt();
                res[i] += p == 0 ? '+' : p == 1 ? '-' : '?';
            }
        }
        return res;
    }
}
