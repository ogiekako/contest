package net.ogiekako.algorithm.misc.dp.connectedRelation.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.util.Arrays.deepToString;
import static java.util.Arrays.fill;
/**
 * 白は隣り合わず,黒は木になっているような置き方で,
 * 黒の最大数を求めよ.不可能なら-1を返せ.
 */
public class ScribblingWitch {
    public static void main(String[] args) {
        new ScribblingWitch().run();
    }

    static void debug(Object... os) {
        System.err.println(deepToString(os));
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        int h = sc.nextInt(), w = sc.nextInt();
        char[][] cs = new char[h][w];
        for (int i = 0; i < h; i++) cs[i] = sc.next().toCharArray();
        int res = solve(cs);
        System.out.println(res);
    }

    @SuppressWarnings("unchecked")
    private int solve(char[][] cs) {
        h = cs.length; w = cs[0].length;
        HashMap<E, Integer>[] dp = new HashMap[2];
        for (int i = 0; i < 2; i++) dp[i] = new HashMap<E, Integer>();
        int[] is = new int[w + 1];
        fill(is, -1);
        E init = new E(-1, w - 1, is, false);
        int cur = 0, nxt = 1;
        dp[cur].put(init, 0);
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                dp[nxt].clear();
                for (Entry<E, Integer> ent : dp[cur].entrySet()) {
                    E e = ent.getKey();
                    int val = ent.getValue();

                    if (cs[i][j] != '#') {
                        int nval = val;
                        E ne = e.nxt(false);
                        if (ne != null) {
                            if (!dp[nxt].containsKey(ne) || dp[nxt].get(ne) < nval) {
                                dp[nxt].put(ne, nval);
                            }
                        }
                    }
                    if (cs[i][j] != '.') {
                        int nval = val + 1;
                        E ne = e.nxt(true);
                        if (ne != null) {
                            if (!dp[nxt].containsKey(ne) || dp[nxt].get(ne) < nval) {
                                dp[nxt].put(ne, nval);
                            }
                        }
                    }
                }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
        int res = -1;
        for (Entry<E, Integer> ent : dp[cur].entrySet()) {
            E e = ent.getKey();
            int val = ent.getValue();
            boolean ok = true;
            for (int i : e.is) if (i > 0) ok = false;
            if (ok) res = max(res, val);
        }
        return res;
    }

    int h, w;

    class E {
        int x, y;// 最後に置かれたのがどこか
        int[] is;
        boolean emergency;// false : 連結, true : 次に黒が置かれたら途切れる
        E(int x, int y, int[] is, boolean emergency) {
            this.x = x; this.y = y; this.is = is; this.emergency = emergency;
        }

        // null if impossible
        E nxt(boolean black) {
            int nx = x;
            int ny = y + 1;
            boolean nemergency = false;
            if (ny == w) {
                ny = 0; nx++;
            }
            int[] nis = new int[is.length];
            for (int i = 0; i < nis.length - 1; i++) nis[i] = is[i + 1];
            if (!black) {
                nis[nis.length - 1] = -1;
                if (nx > 0 && nis[0] >= 0) {
                    boolean ok = false;
                    for (int i = 1; i < nis.length; i++) if (nis[i] == nis[0]) ok = true;
                    if (!ok) nemergency = true;
                }
                if (nx > 0 && nis[0] == -1) return null;
                if (ny > 0 && is[is.length - 1] == -1) return null;
            } else {
                if (emergency) return null;
                if (ny > 0 && nx > 0 && is[0] >= 0 && is[1] >= 0 && is[is.length - 1] >= 0) return null;
                int a = -1, b = -1;
                if (nx > 0) a = nis[0];
                if (ny > 0) b = is[is.length - 1];
                if (a >= 0 && b >= 0) {
                    if (a == b) return null;
                    nis[nis.length - 1] = a;
                    for (int i = 0; i < nis.length; i++) if (nis[i] == b) nis[i] = a;
                } else if (a >= 0) nis[nis.length - 1] = a;
                else if (b >= 0) nis[nis.length - 1] = b;
                else nis[nis.length - 1] = nis.length;
            }
            norm(nis);
            return new E(nx, ny, nis, nemergency);
        }

        void norm(int[] is) {
            int j = 0;
            int m = 0;
            for (int i : is) m = max(m, i);
            m++;
            int[] js = new int[m];
            fill(js, -1);
            for (int i = 0; i < is.length; i++) {
                if (is[i] >= 0) {
                    if (js[is[i]] == -1) js[is[i]] = j++;
                    is[i] = js[is[i]];
                }
            }
        }


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (emergency ? 1231 : 1237);
            result = prime * result + Arrays.hashCode(is);
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            E other = (E) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (emergency != other.emergency)
                return false;
            if (!Arrays.equals(is, other.is))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "E [emergency=" + emergency + ", is=" + Arrays.toString(is) + ", x=" + x + ", y=" + y + "]";
        }

        private ScribblingWitch getOuterType() {
            return ScribblingWitch.this;
        }
    }
}
