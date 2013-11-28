package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class TenkaD {
    int H, W;
    char[][] map;

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        H = in.nextInt(); W = in.nextInt();
        map = new char[H + 2][W];
        for (int i = 0; i < H; i++) map[i] = in.next().toCharArray();
        for (int i = H; i < map.length; i++) for (int j = 0; j < W; j++) map[i][j] = '#';
        H += 2;
        int[] initArray = new int[W];
        Arrays.fill(initArray, -1);
        Condition init = new Condition(initArray, false);
        M[] dp = new M[2];
        for (int i = 0; i < 2; i++) dp[i] = new M();
        dp[0].put(init, 0);
        int cur = 0, nxt = 1;
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
//                debug("i,j", i, j);
                dp[nxt].clear();
                for (java.util.Map.Entry<Condition, Integer> e : dp[cur].entrySet()) {
                    Condition curCondition = e.getKey();
                    int count = e.getValue();
//                    debug(curCondition.relation, count);
                    for (int road = 0; road < 2; road++) {
                        Condition nxtCondition = curCondition.put(i, j, road == 1);
                        if (nxtCondition != null) {
                            Integer tmp = dp[nxt].get(nxtCondition);
                            if (tmp == null || tmp > count + road) dp[nxt].put(nxtCondition, count + road);
                        }
                    }
                }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
        if (dp[cur].isEmpty()) {
            out.println(-1);
        } else {
            if (dp[cur].size() != 1) throw new AssertionError();
            int res = -1;
            for (int tmp : dp[cur].values()) res = -tmp;
            for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) if (map[i][j] == '.') res++;
            out.println(res);
        }
    }

    class M extends HashMap<Condition, Integer> {}

    class Condition {
        int[] relation;
        boolean finished = false;
        // -2 : house not resolved
        // -1 : block

        Condition(int[] is, boolean finished) {
            this.relation = is;
            this.finished = finished;
        }

        Condition put(int i, int j, boolean road) {
            int[] nRelation = relation.clone();
            boolean nFinished = finished;
            if (!road) {
                if (relation[j] == -2) return null;
                if (relation[j] >= 0 && count(relation, relation[j]) == 1) {
                    if (finished) return null;
                    else nFinished = true;
                }
                if (map[i][j] == 'H') {
                    nRelation[j] = -2;
                    if (relation[j] >= 0) nRelation[j] = -1;
                    if (j > 0 && relation[j - 1] >= 0) nRelation[j] = -1;
                } else {
                    nRelation[j] = -1;
                }
            } else {
                if (map[i][j] == '#' || map[i][j] == 'H') return null;
                if (j > 0 && relation[j - 1] == -2) nRelation[j - 1] = -1;
                if (relation[j] >= 0) {
                    if (j > 0 && relation[j - 1] >= 0) {
                        substitute(nRelation, relation[j - 1], relation[j]);
                    }
                } else {
                    if (j > 0 && relation[j - 1] >= 0) {
                        nRelation[j] = relation[j - 1];
                    } else {
                        nRelation[j] = Math.max(-1, max(relation)) + 1;
                    }
                }
            }
            normalize(nRelation);
            return new Condition(nRelation, nFinished);
        }

        private void normalize(int[] array) {
            int[] js = new int[array.length];
            Arrays.fill(js, -1);
            for (int i = 0, j = 0; i < array.length; i++) {
                if (array[i] >= 0) {
                    if (js[array[i]] == -1) js[array[i]] = j++;
                    array[i] = js[array[i]];
                }
            }
        }

        private int max(int[] array) {
            int res = Integer.MIN_VALUE;
            for (int i : array) res = Math.max(res, i);
            return res;
        }

        private void substitute(int[] array, int from, int to) {
            for (int i = 0; i < array.length; i++) if (array[i] == from) array[i] = to;
        }

        private int count(int[] array, int target) {
            int res = 0;
            for (int i : array) if (i == target) res++;
            return res;
        }

        @Override
        public boolean equals(Object o) {
            Condition condition = (Condition) o;

            return Arrays.equals(relation, condition.relation) && finished == condition.finished;

        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(relation) << 1 | (finished ? 1 : 0);
        }
    }
}
