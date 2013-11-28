package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TrampolinTask {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int init = in.nextInt() - 1;
        int[] is = new int[n];
        boolean[] ts = new boolean[n];
        for (int i = 0; i < n; i++) is[i] = in.nextInt();
        String s = in.next();
        int m = 0;
        int init2 = -1;
        for (int i = 0; i < n; ) {
            int j = i;
            while (j < n && is[i] == is[j]) j++;
            if (i <= init && init < j) {
                init2 = m;
            }
            m++;
            i = j;
        }
        int[] heights = new int[m];
        int[] counts = new int[m];
        boolean[] trampolins = new boolean[m];
        m = 0;
        for (int i = 0; i < n; ) {
            int j = i;
            boolean tranmpolin = false;
            while (i < n && is[i] == is[j]) {
                tranmpolin |= s.charAt(i) == 'T';
                i++;
            }
            heights[m] = is[j];
            counts[m] = i - j;
            trampolins[m] = tranmpolin;
            m++;
        }
        int res = solve(heights, counts, trampolins, init2);
        out.println(res);
    }

    private int solve(int[] heights, int[] counts, boolean[] trampolins, int init) {
        int n = heights.length;
        boolean[] visited = new boolean[n];
        int[] max = getMax(heights, counts, visited);
        int res = max[init];
        int res2 = flying(heights, counts, trampolins, init);
        return Math.max(res, res2);
    }

    private int flying(int[] heights, int[] counts, boolean[] trampolins, int init) {
        int n = heights.length;
        boolean[] visited = new boolean[n];
        boolean[] canFly = getFly(heights, trampolins);
        if (!canFly[init]) return 0;
        int res = 0;
        for (int i = 0; i < n; i++)
            if (!visited[i] && canFly[i]) {
                visited[i] = true;
                res += counts[i];
            }
        int[] maxes = getMax(heights, counts, visited);
        int max = 0;
        for (int i = 0; i < n; i++) if (!visited[i]) max = Math.max(max, maxes[i]);
        return res + max;
    }

    private boolean[] getFly(int[] heights, boolean[] trampolins) {
        int n = heights.length;
        boolean[] res = new boolean[n];
        for (int i = 0; i < n; i++) if (trampolins[i]) res[i] = true;
        for (int i = 1; i < n; i++) if (heights[i - 1] < heights[i] && res[i - 1]) res[i] = true;
        for (int i = n - 2; i >= 0; i--) if (heights[i + 1] < heights[i] && res[i + 1]) res[i] = true;
        return res;
    }

    private int[] getMax(int[] heights, int[] counts, boolean[] visited) {
        int n = heights.length;
        int[] res = new int[n];
        System.arraycopy(counts, 0, res, 0, n);
        for (int i = 1; i < n; i++) {
            if (heights[i - 1] < heights[i]) res[i] = Math.max(res[i], res[i - 1] + (visited[i] ? 0 : counts[i]));
        }
        for (int i = n - 2; i >= 0; i--) {
            if (heights[i + 1] < heights[i]) res[i] = Math.max(res[i], res[i + 1] + (visited[i] ? 0 : counts[i]));
        }
        return res;
    }
}
