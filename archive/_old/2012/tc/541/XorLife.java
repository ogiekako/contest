package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;
import java.util.HashMap;

public class XorLife {
    public long countAliveCells(String[] _field, int K) {
        boolean[][] field = new boolean[_field.length][_field[0].length()];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[i].length; j++) field[i][j] = _field[i].charAt(j) == 'o';
        return solve(field, K);
    }
    HashMap<Cond, Long> memo = new HashMap<Cond, Long>();
    private long solve(boolean[][] field, int K) {
        boolean empty = true;
        for (boolean[] bs : field) for (boolean b : bs) if (b) empty = false;
        if (empty) return 0;
        field = cut(field);
        if (K == 0) {
            long res = 0;
            for (boolean[] bs : field) for (boolean b : bs) if (b) res++;
            return res;
        } else if ((K & 1) == 1) {
            return solve(next(field), K - 1);
        } else {
            Cond cond = new Cond(field, K);
            if (memo.containsKey(cond)) return memo.get(cond);

            long res = 0;
            for (int di = 0; di < 2; di++)
                for (int dj = 0; dj < 2; dj++) {
                    boolean[][] nField = new boolean[field.length][field[0].length];
                    for (int i = 0; i < field.length; i++)
                        for (int j = 0; j < field[0].length; j++)
                            if (i % 2 == di && j % 2 == dj) {
                                nField[i / 2][j / 2] = field[i][j];
                            }
                    res += solve(nField, K / 2);
                }
            memo.put(cond, res);
            return res;
        }
    }

    class Cond {
        boolean[][] field;
        int K;
        public Cond(boolean[][] field, int K) {
            this.field = field;
            this.K = K;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cond cond = (Cond) o;
            for (int i = 0; i < field.length; i++) if (!Arrays.equals(field[i], cond.field[i])) return false;
            if (K != cond.K) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int res = 0;
            for (boolean[] bs : field) res = res * 29 + Arrays.hashCode(bs);
            return res * 29 + K;
        }
    }

    private boolean[][] cut(boolean[][] field) {
        int minX = -1, maxX = -1;
        loop:
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j]) {
                    minX = i; break loop;
                }
        loop:
        for (int i = field.length - 1; i >= 0; i--)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j]) {
                    maxX = i; break loop;
                }
        int minY = -1, maxY = -1;
        loop:
        for (int j = 0; j < field[0].length; j++)
            for (int i = 0; i < field.length; i++)
                if (field[i][j]) {
                    minY = j; break loop;
                }
        loop:
        for (int j = field[0].length - 1; j >= 0; j--)
            for (int i = 0; i < field.length; i++)
                if (field[i][j]) {
                    maxY = j; break loop;
                }
        boolean[][] res = new boolean[maxX - minX + 1][maxY - minY + 1];
        for (int i = 0; i < res.length; i++)
            for (int j = 0; j < res[0].length; j++) res[i][j] = field[minX + i][minY + j];
        return res;
    }

    private boolean[][] next(boolean[][] field) {
        boolean[][] res = new boolean[field.length + 2][field[0].length + 2];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j]) {
                    res[i][j + 1] ^= true;
                    res[i + 1][j] ^= true;
                    res[i + 1][j + 1] ^= true;
                    res[i + 1][j + 2] ^= true;
                    res[i + 2][j + 1] ^= true;
                }
        return res;
    }
}

