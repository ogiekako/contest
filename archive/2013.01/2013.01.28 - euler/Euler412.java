package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;
import java.util.HashMap;

public class Euler412 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int m = in.nextInt(), n = in.nextInt(), mod = in.nextInt();
        long res = solve(m, n, mod);
        out.println(res);
    }

    //    i<n-m && j<n-m -> 2n-1-i-j
//    i>=n-m || j>=n-m -> 2n-1-i-j-m
    private long solve(int n, int m, long mod) {
        long res = 1;
        int count = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i < n - m || j < n - m) {
                    count++;
                    res = res * count % mod;
                    long value = i < n - m && j < n - m ? 2 * n - 1 - i - j : 2 * n - 1 - i - j - m;
                    res = res * MathUtils.inverse(value, (int) mod) % mod;
                }
        return res;
    }

    private long solveStupid(int m, int n, long mod) {
        Field init = new Field(new boolean[m][m]);
        HashMap<Field, Long> memo = new HashMap<Field, Long>();
        return dfs(m, n, mod, memo, init);
    }

    private long dfs(int m, int n, long mod, HashMap<Field, Long> memo, Field cur) {
        if (memo.containsKey(cur)) return memo.get(cur);
        int count = cur.count();
        if (count == m * m - n * n) {
            memo.put(cur, 1L);
            return 1;
        }
        long res = 0;
        int colIndex = m - 1;
        for (int rowIndex = 0; rowIndex < m; rowIndex++) {
            if (colIndex < 0 || cur.isFilled(rowIndex, colIndex)) continue;
            while (colIndex >= 0 && !cur.isFilled(rowIndex, colIndex)) colIndex--;
            colIndex++;
            if (isInside(rowIndex, colIndex, m, n)) {
                Field next = cur.set(rowIndex, colIndex);
                res += dfs(m, n, mod, memo, next);
                if (res >= mod) res -= mod;
            }
            colIndex--;
        }
        memo.put(cur, res);
        return res;
    }

    private boolean isInside(int rowIndex, int colIndex, int m, int n) {
        if (rowIndex < 0 || colIndex < 0) throw new IllegalArgumentException();
        if (rowIndex < m - n) return colIndex < m;
        return colIndex < m - n;
    }

    class Field {
        boolean[][] field;
        Field(boolean[][] field) {
            this.field = copy(field);
        }

        private boolean[][] copy(boolean[][] arrays) {
            boolean[][] res = new boolean[arrays.length][];
            for (int i = 0; i < res.length; i++) res[i] = arrays[i].clone();
            return res;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Field obj = (Field) o;
            return Arrays.deepEquals(field, obj.field);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(field);
        }

        public int count() {
            int res = 0;
            for (boolean[] row : field) for (boolean b : row) if (b) res++;
            return res;
        }

        public boolean isFilled(int rowIndex, int colIndex) {
            return field[rowIndex][colIndex];
        }

        public Field set(int rowIndex, int colIndex) {
            if (field[rowIndex][colIndex]) throw new IllegalStateException();
            field[rowIndex][colIndex] = true;
            Field res = new Field(field);
            field[rowIndex][colIndex] = false;
            return res;
        }
    }
}
