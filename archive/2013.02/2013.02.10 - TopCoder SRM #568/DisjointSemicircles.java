package tmp;

import net.ogiekako.algorithm.graph.denseGraph.DenseGraphUtils;

import java.util.Arrays;

public class DisjointSemicircles {
    int N;
    int known;
    int[] starts, ends;
    boolean[] isKnown;
    int[] normalizedLabels;

    // O(min{2^k N, (2(N-k)-1)!!N^2)}) : k = #known labels
    // N=25, N-k = 6 -> (2(N-k)-1)!!N^2 = 6496875
    // N=25, N-k = 7 -> 2^k N = 2^18 * 25 = 6553600
    public String getPossibility(int[] labels) {
        prepare(labels);
        boolean res = N - known <= 6 ? solveCheckingAllPairings() : solveCheckingAllSidesOfKnownPairs();
        return res ? "POSSIBLE" : "IMPOSSIBLE";
    }

    // O(2^k N) : k = #known labels
    private boolean solveCheckingAllSidesOfKnownPairs() {
        boolean[] parityUnknownCounts = new boolean[N * 2 + 1];
        for (int i = 1; i < parityUnknownCounts.length; i++)
            parityUnknownCounts[i] = parityUnknownCounts[i - 1] ^ !isKnown[i - 1];
        int[][] graphTailsLists = new int[N * 2 + 1][N * 2 + 1];
        boolean[][] parityLowerCountsLists = new boolean[N * 2 + 1][N * 2 + 1];
        int[] edgeCounts = new int[N * 2 + 1];
        boolean res = false;
        for (int mask = 0; mask < 1 << known; mask += 2) {// we can fix the side of the first pair.
            boolean[] isUpperSide = new boolean[known];
            for (int i = 0; i < known; i++) isUpperSide[i] = (mask >> i & 1) == 0;

            Arrays.fill(edgeCounts, 0);
            addEdge(graphTailsLists, edgeCounts, parityLowerCountsLists, 0, N * 2, false);
            for (int i = 0; i < known; i++) {
                addEdge(graphTailsLists, edgeCounts, parityLowerCountsLists, starts[i], starts[i] + 1, false);
                addEdge(graphTailsLists, edgeCounts, parityLowerCountsLists, ends[i], ends[i] + 1, false);
                boolean parityUnknownCount = parityUnknownCounts[ends[i]] ^ parityUnknownCounts[starts[i]];
                boolean parityLowerCount = isUpperSide[i] && parityUnknownCount;
                addEdge(graphTailsLists, edgeCounts, parityLowerCountsLists, starts[i], ends[i], parityLowerCount);
            }
            res |= isValidSidesSelection(isUpperSide) && is2Colorable(graphTailsLists, edgeCounts, parityLowerCountsLists);
        }
        return res;
    }

    private boolean isValidSidesSelection(boolean[] sides) {
        return isValidSidesSelection(sides, true) && isValidSidesSelection(sides, false);
    }

    private boolean isValidSidesSelection(boolean[] sides, boolean side) {
        int[] stack = new int[known];
        int tail = 0;
        boolean res = true;
        for (int i = 0; i < N * 2; i++) {
            int label = normalizedLabels[i];
            if (label < 0 || sides[label] != side) continue;
            if (starts[label] == i) {
                stack[tail++] = label;
            } else {
                int popped = stack[--tail];
                res &= popped == label;
            }
        }
        return res;
    }

    private boolean is2Colorable(int[][] graphTailsLists, int[] edgeCounts, boolean[][] colorXorsLists) {
        int n = graphTailsLists.length;
        boolean[] visited = new boolean[n];
        boolean[] colors = new boolean[n];
        boolean res = true;
        for (int i = 0; i < n; i++)
            if (!visited[i]) {
                boolean isColorable = colorabilityDfs(i, true, visited, colors, graphTailsLists, edgeCounts, colorXorsLists);
                res &= isColorable;
            }
        return res;
    }

    private boolean colorabilityDfs(int pos, boolean color, boolean[] visited, boolean[] colors, int[][] graphTails, int[] edgeCounts, boolean[][] colorXorsLists) {
        visited[pos] = true;
        colors[pos] = color;
        boolean res = true;
        for (int i = 0; i < edgeCounts[pos]; i++) {
            int tail = graphTails[pos][i];
            boolean nextColor = color ^ colorXorsLists[pos][i];
            if (visited[tail] && colors[tail] != nextColor) res = false;
            if (!visited[tail])
                res &= colorabilityDfs(tail, nextColor, visited, colors, graphTails, edgeCounts, colorXorsLists);
        }
        return res;
    }

    private void addEdge(int[][] graphTailsLists, int[] edgeCounts, boolean[][] parityLowerCountsLists, int from, int to, boolean value) {
        graphTailsLists[from][edgeCounts[from]] = to;
        parityLowerCountsLists[from][edgeCounts[from]++] = value;
        graphTailsLists[to][edgeCounts[to]] = from;
        parityLowerCountsLists[to][edgeCounts[to]++] = value;
    }

    // O((2(N-k)-1)!!N^2) : k = #kown labels
    private boolean solveCheckingAllPairings() {
        return checkAllPairingsDfs(known, 0);
    }

    private boolean checkAllPairingsDfs(int ptr, int from) {
        if (ptr >= N) return getPairAssignmentsPossibility();
        while (isKnown[from]) from++;
        starts[ptr] = from;
        isKnown[from] = true;
        boolean res = false;
        for (int to = from; to < isKnown.length; to++) {
            if (!isKnown[to]) {
                isKnown[to] = true;
                ends[ptr] = to;
                res |= checkAllPairingsDfs(ptr + 1, from);
                isKnown[to] = false;
            }
        }
        isKnown[from] = false;
        return res;
    }

    private boolean getPairAssignmentsPossibility() {
        boolean[][] graph = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) if (i != j) graph[i][j] = intersect(starts[i], ends[i], starts[j], ends[j]);
        return DenseGraphUtils.isBipartite(graph);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private boolean intersect(int s1, int t1, int s2, int t2) {
        if (s1 > s2) return intersect(s2, t2, s1, t1);
        return s2 < t1 && t1 < t2;
    }

    private void prepare(int[] labels) {
        N = labels.length / 2;
        known = 0;
        for (int label : labels) if (label >= 0) known++;
        known /= 2;

        starts = new int[N];
        ends = new int[N];
        int[] newLabelOf = new int[N];
        Arrays.fill(newLabelOf, -1);
        for (int i = 0, ptr = 0; i < labels.length; i++) {
            if (labels[i] >= 0) {
                if (newLabelOf[labels[i]] == -1) {
                    newLabelOf[labels[i]] = ptr;
                    starts[ptr] = i;
                    ptr++;
                } else {
                    int newLabel = newLabelOf[labels[i]];
                    ends[newLabel] = i;
                }
            }
        }

        isKnown = new boolean[N * 2];
        for (int i = 0; i < labels.length; i++) isKnown[i] = labels[i] >= 0;

        normalizedLabels = new int[N * 2];
        Arrays.fill(normalizedLabels, -1);
        for (int i = 0; i < known; i++) normalizedLabels[starts[i]] = normalizedLabels[ends[i]] = i;
    }
}
