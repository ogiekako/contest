package tmp;

import net.ogiekako.algorithm.dataStructure.intCollection.IntStack;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.utils.BigIntegerUtils;

import java.io.PrintWriter;
import java.math.BigInteger;

/*
1 -> 2
2 -> 5
3 -> 12
4 -> 30
5 -> 76
6 -> 196
7 -> 512
8 -> 1353
9 -> 3610
10 -> 9713
11 -> 26324
12 -> 71799
13 -> 196938
14 -> 542895
15 -> 1503312
16 -> 4179603
17 -> 11662902
18 -> 32652735
19 -> 91695540
20 -> 258215664
21 -> 728997192
22 -> 2062967382

1 -> 2
2 -> 4
3 -> 10
4 -> 24
5 -> 60
6 -> 152
7 -> 392
8 -> 1024
9 -> 2706
10 -> 7220
11 -> 19426
 */
public class SelfAvoidingWalk {
    int n;
    long[] three;
    int LEFT_PAREN = 0, RIGHT_PAREN = 1, EMPTY = 2;
    long MOD;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        n = in.nextInt();
        if (n < 0) throw new UnknownError();
        debug(n);
        three = new long[n];
        for (int i = 0; i < n; i++) three[i] = i == 0 ? 1 : three[i - 1] * 3;

        BigInteger result = BigIntegerUtils.computeUsingCRT(new BigIntegerUtils.Function() {
            public int calculate(int primeMod) {
                MOD = primeMod;
                return (int) solve();
            }
        });
        out.println(result);
    }

    public long solve() {
        HashMap<Long, Long> map = new HashMap<Long, Long>();
        long init = 0;
        init += three[0] * LEFT_PAREN;
        for (int i = 1; i < n; i++) init += three[i] * EMPTY;
        map.put(init, 1L);
        int maxSize = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                column = j;
//                debug(i, j);
//                debug("size", map.size());
                maxSize = Math.max(maxSize, map.size());
                HashMap<Long, Long> nMap = new HashMap<Long, Long>();
                for (Map.Entry<Long, Long> entry : map.entrySet()) {
                    long state = entry.getKey();
//                    debug("cur state",stateToString(state));
//                    if(!isValidState(state)){
//                        debug(i,j,stateToString(state));
//                        throw new AssertionError();
//                    }
                    long value = entry.getValue();
                    int left = getLeft(state);
                    if (j == 0 && left != EMPTY) continue;
                    int upper = get(state, j);
                    if (left == LEFT_PAREN && upper == RIGHT_PAREN) continue;
                    if (left == RIGHT_PAREN && upper == LEFT_PAREN) {
                        long nState = change(state, j, EMPTY);
                        add(nMap, nState, value);
                    } else if (left == RIGHT_PAREN && upper == RIGHT_PAREN) {
                        int p = findCorrespondingParenthesis(state, j, -1);
                        long nState = change(state, p, RIGHT_PAREN);
                        nState = change(nState, j, EMPTY);
                        add(nMap, nState, value);
                    } else if (left == LEFT_PAREN && upper == LEFT_PAREN) {
                        int p = findCorrespondingParenthesis(state, j, 1);
                        long nState = change(state, p, LEFT_PAREN);
                        nState = change(nState, j, EMPTY);
                        add(nMap, nState, value);
                    } else if (left == EMPTY && upper == EMPTY) {
                        long nState1 = change(state, j, LEFT_PAREN);
                        add(nMap, nState1, value);
                        long nState2 = state;
                        add(nMap, nState2, value);
                    } else {
                        long nState1 = change(state, j, EMPTY);
                        add(nMap, nState1, value);
                        long nState2 = change(state, j, left == EMPTY ? upper : left);
                        add(nMap, nState2, value);
                    }
                }
                map = nMap;
            }
        debug("maxSize", maxSize);
        debug("final map size", map.size());
//        for(long state : map.keySet()){
//            debug("state", stateToString(state));
//        }
        long goal = 0;
        for (int i = 0; i < n - 1; i++) goal += three[i] * EMPTY;
        goal += three[n - 1] * LEFT_PAREN;
        long res = map.get(goal);
        return res;
    }

    private String stateToString(long state) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) {
            b.append(conditionToChar(get(state, i)));
            if (i == column) b.append(conditionToChar(getLeft(state)));
        }
        return b.toString();
    }

    private char conditionToChar(int condition) {
        if (condition == LEFT_PAREN) return '(';
        if (condition == RIGHT_PAREN) return ')';
        if (condition == EMPTY) return '.';
        throw new AssertionError();
    }

    private boolean isValidState(long state, int column) {
        int leftCondition = getLeft(state);
        int left = 0;
//        boolean foundLeft = false;
        for (int i = 0; i < n; i++) {
            if (get(state, i) == LEFT_PAREN) left++;
            if (get(state, i) == RIGHT_PAREN) left--;

            if (left < 0) return false;
//            if(foundLeft && left==0)return false;
//            if(left > 0)foundLeft = true;
            if (i == column) {
                if (leftCondition == LEFT_PAREN) left++;
                if (leftCondition == RIGHT_PAREN) left--;

                if (left < 0) return false;
//                if(foundLeft && left==0)return false;
//                if(left > 0)foundLeft = true;
            }
        }
        return left == 1;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int findCorrespondingParenthesis(long state, int i, int d) {
        int stack = 0;
        stack += f(get(state, i));
        while (stack != 0) {
            i += d;
            stack += f(get(state, i));
        }
        return i;
    }

    private int f(int condition) {
        if (condition == LEFT_PAREN) return -1;
        if (condition == EMPTY) return 0;
        if (condition == RIGHT_PAREN) return 1;
        throw new AssertionError();
    }

    int column = 0;
    private void add(HashMap<Long, Long> map, long state, long value) {
//        if(!isValidState(state, column)){
//            debug(column, stateToString(state));
//            throw new AssertionError();
//        }
        Long cur = map.get(state);
        if (cur == null) {
            map.put(state, value);
        } else {
            long next = cur + value;
            if (next >= MOD) next -= MOD;
            map.put(state, next);
        }
    }

    private long change(long state, int i, int condition) {
        long nState = state - three[i] * get(state, i) + three[i] * condition;
        return nState;
    }

    private int getLeft(long state) {
        int numLeftParen = 0;
        int numRightParen = 0;
        for (int i = 0; i < n; i++) {
            int v = get(state, i);
            if (v == LEFT_PAREN) numLeftParen++;
            if (v == RIGHT_PAREN) numRightParen++;
        }
        if (numLeftParen == numRightParen) return LEFT_PAREN;
        if (numLeftParen == numRightParen + 2) return RIGHT_PAREN;
        if (numLeftParen == numRightParen + 1) return EMPTY;
        throw new AssertionError();
    }

    private int get(long state, int i) {
        if (i < 0) return EMPTY;
        return (int) (state / three[i] % 3);
    }

    public static void main(String[] args) {
        int N = 22;
        long[][] C = MathUtils.genCombTable(N + 2);
        long[] catalan = new logn[N + 2];
        for (int i = 0; i < catalan.length; i++) {
            catalan[i] = MathUtils.catalan(i);
        }
        for (int n = 1; n <= N; n++) {
            long count = 0;
            for (int m = 0; m * 2 + 1 <= n + 1; m++) {
                for (int j = 0; j <= m; j++) {
                    count += C[n + 1][m * 2 + 1] * catalan[j] * catalan[m - j];
                }
            }
            System.out.printf("upper bound of #dp keys : %d -> %d\n", n, count);
        }

        N = 10;
        for (int n = 1; n <= N; n++) {
            if (n % 2 != 0) continue;
            int len = (int) catalan[n / 2];
            boolean[][] A = new boolean[len][len];
            List<Integer> states = new ArrayList<Integer>();
            dfs(0, 0, 0, n, states);
            if (states.size() != len) throw new AssertionError();
            for (int i = 0; i < len; i++)
                for (int j = 0; j < len; j++) {
                    int s1 = states.get(i), s2 = states.get(j);
                    int[] a1 = gen(s1, n);
                    int[] a2 = gen(s2, n);
                    int k, p;
                    for (k = 0, p = -1; p != 0; k++) {
                        if (p == -1) p = 0;
                        if (k % 2 == 0) p = a1[p];
                        else p = a2[p];
                    }
                    A[i][j] = k == n;
                }

            int rank = Matrix.rank(A);
            System.err.printf("rank %d -> %d\n", n, rank);
            System.err.printf("#states %d -> %d\n", n, states.size());
        }
    }

    private static int[] gen(int state, int n) {
        int[] res = new int[n];
        IntStack stack = new IntStack(n);
        for (int i = 0; i < n; i++) {
            if (state << 31 - i >= 0) {
                stack.push(i);
            } else {
                int left = stack.pop();
                res[left] = i;
                res[i] = left;
            }
        }
        return res;
    }

    private static void dfs(int mask, int left, int p, int n, List<Integer> states) {
        if (p == n) {
            if (left == 0)
                states.add(mask);
            return;
        }
        if (left > 0) {
            // right
            dfs(mask | 1 << p, left - 1, p + 1, n, states);
        }
        // left
        dfs(mask, left + 1, p + 1, n, states);
    }
}
/*
2
4
10
24
60
152
392
1024
2706

*/