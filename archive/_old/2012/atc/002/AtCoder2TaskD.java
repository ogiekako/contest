package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AtCoder2TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int h = in.nextInt();
        in.nextInt();
        char[][] cs = new char[h][];
        for (int i = 0; i < h; i++) cs[i] = in.next().toCharArray();
        char res = solve(cs);
        out.println(res);
    }

    int INF = 1 << 28;
    public char solve(char[][] board) {
        char tmpC = straight(board);
        if (tmpC != 0) return tmpC;
        long O = 0, X = 0;
        ArrayList<Entry> es = new ArrayList<Entry>();
        for (char[] row : board) {
            int f = 0;
            while (f < row.length) {
                if (row[f] == '.') {
                    f++; continue;
                }
                if (row[f] != 'o') throw new RuntimeException();
                int lastO = f;
                int numO = 0;
                while (row[f] != 'x') {
                    if (row[f] == 'o') {
                        O += numO * (f - lastO - 1);
                        numO++;
                        lastO = f;
                    }
                    f++;
                }
                int count = f - lastO - 2;
                int firstX = -1;
                int numX = 0;
                while (f < row.length && row[f] != 'o') {
                    if (row[f] == 'x') {
                        if (firstX == -1) firstX = f;
                        X += f - firstX - numX;
                        numX++;
                    }
                    f++;
                }
                Entry e = new Entry(numO - 1, numX - 1, count);
                es.add(e);
            }
        }
        Collections.sort(es, Collections.<Object>reverseOrder());
        boolean first = true;
        for (Entry e : es) {
            if (e.count % 2 == 0) {
                O += e.numO * e.count / 2;
                X += e.numX * e.count / 2;
            } else {
                if (first) {
                    O += e.numO * (e.count + 1) / 2;
                    X += e.numX * (e.count - 1) / 2;
                } else {
                    X += e.numX * (e.count + 1) / 2;
                    O += e.numO * (e.count - 1) / 2;
                }
                first = !first;
            }
        }
        if (O > X || (!first && O == X)) return 'o';
        return 'x';
    }

    class Entry implements Comparable<Entry> {
        int numO;
        int numX;
        long count;
        int num;

        Entry(int numO, int numX, int count) {
            this.numO = numO;
            this.numX = numX;
            this.count = count;
            num = numO + numX;
        }

        public int compareTo(Entry o) {
            return num - o.num;
        }
    }

    private char straight(char[][] board) {
        int minO = INF, minX = INF;
        for (char[] row : board) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == 'x') {
                    minX = Math.min(minX, i);
                    break;
                } else if (row[i] == 'o') break;
            }
            for (int i = row.length - 1; i >= 0; i--) {
                if (row[i] == 'o') {
                    minO = Math.min(minO, row.length - 1 - i);
                    break;
                } else if (row[i] == 'x') break;
            }
        }
        if (minO < INF || minX < INF) return minO <= minX ? 'o' : 'x';
        return 0;
    }
}
