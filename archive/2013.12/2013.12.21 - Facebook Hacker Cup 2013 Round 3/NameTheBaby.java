package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.AhoCorasick;
import net.ogiekako.algorithm.string.AutomatonState;
import net.ogiekako.algorithm.utils.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class NameTheBaby {
    int L;
    long K;
    int N;
    String[] ss;
    int[][] next;
    boolean[] isGoal;
    private AutomatonState[] states;
    int m;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        System.err.println("#Test " + testNumber);
        L = in.nextInt();
        K = in.nextLong() - 1;
        N = in.nextInt();
        ss = new String[N];
        for (int i = 0; i < N; i++) {
            ss[i] = in.next();
        }
        String res = solve();
        out.printFormat("Case #%d: %s\n", testNumber, res);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private String solve() {
        AhoCorasick ac = new AhoCorasick(StringUtils.lowercaseLetters);
        ac.construct(ss);
        states = ac.allStates().toArray(new AutomatonState[0]);
        m = states.length;
        debug("m", m);
        next = new int[m][26];
        for(int i=0;i<m;i++)for(int j=0;j<26;j++)next[i][j] = states[i].next(j).getIndex();
        isGoal = new boolean[m];
        for(int i=0;i<m;i++)isGoal[i] = states[i].getMatchedFromLonger() != null && !states[i].getMatchedFromLonger().isEmpty();
        String res = "";
        if (calc(res) < K) return "unnamed baby :(";
        for (int i = 0; i < L; i++) {
            for (char c = 'a'; ; c++) {
                if (c > 'z') throw new AssertionError(K + " ");
                String cur = res + c;
                long count = calc(cur);
                if (count <= K) {
                    K -= count;
                } else {
                    res = cur;
                    break;
                }
            }
        }
        return res;
    }
    private long calc(String pre) {
        int n = L - pre.length();
        BigInteger ALL = BigInteger.ONE;
        for (int i = 0; i < n; i++) ALL = ALL.multiply(BigInteger.valueOf(26));
        AutomatonState cur = states[0];
        boolean exist = false;
        for (char c : pre.toCharArray()) {
            cur = cur.next(c - 'a');
            if (cur.getMatchedFromLonger() != null && !cur.getMatchedFromLonger().isEmpty()) exist = true;
        }
        if (exist) return toLong(ALL);
        int m = states.length;
        BigInteger[] dp = new BigInteger[m];
        Arrays.fill(dp, BigInteger.ZERO);
        dp[cur.getIndex()] = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            BigInteger[] nDp = new BigInteger[m];
            Arrays.fill(nDp, BigInteger.ZERO);
            for (int j = 0; j < m; j++)
                for (int k = 0; k < 26; k++) {
                    int nj = next[j][k];
                    if(isGoal[nj])continue;
//                    AutomatonState nxt = states[j].next(k);
//                    if (nxt.getMatchedFromLonger() != null && !nxt.getMatchedFromLonger().isEmpty()) continue;
                    nDp[nj] = nDp[nj].add(dp[j]);
//                    nDp[nxt.getIndex()] = nDp[nxt.getIndex()].add(dp[j]);
                }
            dp = nDp;
        }
        BigInteger res = ALL;
        for (BigInteger b : dp) res = res.subtract(b);
        return toLong(res);
    }
    private long toLong(BigInteger res) {return res.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) >= 0 ? Long.MAX_VALUE : res.longValue();}

    public static void main(String[] args) {
        int L = 100, N =200;
        long K = (long) 1e18;
        Random rnd = new Random(12412409L);
        for(int r=0;r<100;r++){
            System.err.println("r: " + r);
            String[] ss = new String[N];
            for (int i = 0; i < N; i++) {
                int m = rnd.nextInt(L) + 1;
                ss[i] = "";
                for (int j = 0; j < m; j++) {
                    ss[i] += (char)('a' + rnd.nextInt(26));
                }
            }
            NameTheBaby ins = new NameTheBaby();
            ins.L = L;
            ins.N = N;
            ins.ss = ss;
            ins.K = K;
            String res = ins.solve();
            System.err.println("res: " + res);
        }
    }
}
