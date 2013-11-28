package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String head = in.next();
        String[] name = new String[24];
        for (int i = 0; i < 24; i++) name[i] = in.next();
        int n = in.nextInt();
        TreeSet<String> set = new TreeSet<String>();
        set.add(head);
        String[][] ss = new String[n][2];
        int[][] time = new int[n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                ss[i][j] = in.next();
                set.add(ss[i][j]);
                time[i][j] = toi(in.next());
            }
        }
        String[] dict = set.toArray(new String[0]);
        int m = dict.length;
        int[][] ds = new int[m][m];
        for (int i = 0; i < m; i++) for (int j = 0; j < m; j++) ds[i][j] = -1;
        int MOD = 24 * 60;
        for (int i = 0; i < n; i++) {
            int s = Arrays.binarySearch(dict, ss[i][0]);
            int t = Arrays.binarySearch(dict, ss[i][1]);
            ds[s][t] = time[i][1] - time[i][0];
            ds[t][s] = time[i][0] - time[i][1];
            if (ds[s][t] < 0) ds[s][t] += MOD;
            if (ds[t][s] < 0) ds[t][s] += MOD;
        }
        for (int i = 0; i < m; i++) ds[i][i] = 0;
        for (int k = 0; k < m; k++)
            for (int i = 0; i < m; i++)
                for (int j = 0; j < m; j++)
                    if (ds[i][k] != -1 && ds[k][j] != -1)
                        ds[i][j] = (ds[i][k] + ds[k][j]) % MOD;

        int to = Arrays.binarySearch(dict, in.next());
        int when = toi(in.next());
        int add = ds[to][Arrays.binarySearch(dict, head)];
        out.println(tos((when + add) % MOD, name));
//        out.println(tos((when - add + MOD) % MOD, name));
    }

    private String tos(int time, String[] name) {
        return name[time / 60];
    }

    private int toi(String s) {
        String[] ss = s.split(":");
        return 60 * Integer.valueOf(ss[0]) + Integer.valueOf(ss[1]);
    }
}
