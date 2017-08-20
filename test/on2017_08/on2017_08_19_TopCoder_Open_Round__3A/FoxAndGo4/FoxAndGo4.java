package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.FoxAndGo4;



import java.util.*;

public class FoxAndGo4 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    List<Integer>[] graph;
    String[] str;
    int m;
    int[] id;
    HashMap<State, Integer> memo;

    public int score(int[] p) {
        memo = new HashMap<>();
        n = p.length + 1;
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < p.length; i++) {
            graph[p[i]].add(i + 1);
        }
        str = new String[n];
        for (int i = 0; i < n; i++) {
            str[i] = asStr(i);
        }
        int m = 0;
        HashMap<String, Integer> map = new HashMap<>();
        id = new int[n];
        for (int i = 0; i < n; i++) {
            if (map.containsKey(str[i])) {
                id[i] = map.get(str[i]);
            } else {
                id[i] = m;
                map.put(str[i], m);
                m++;
            }
        }
        return n - 1 - recur(asIs(graph[0]), true);
    }

    private int[] asIs(List<Integer> integers) {
        int[] res = new int[integers.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = integers.get(i);
        }
        return res;
    }

    class State {
        int[] ids;
        boolean black;

        public State(int[] ids, boolean black) {
            this.ids = ids;
            this.black = black;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (black != state.black) return false;
            return Arrays.equals(ids, state.ids);

        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(ids);
            result = 31 * result + (black ? 1 : 0);
            return result;
        }
    }

    private int recur(int[] trees, boolean black) {
        if (trees.length == 0) return 0;
        int[] is = new int[trees.length];
        for (int i = 0; i < is.length; i++) {
            is[i] = id[trees[i]];
        }
        Arrays.sort(is);
        State s = new State(is, black);
        Integer tmp = memo.get(s);
        if (tmp != null) {
            return tmp;
        }
        int res;
        if (black) {
            res = 0;
            for (int i = 0; i < trees.length; i++) {
                int[] nTrees = new int[trees.length - 1 + graph[trees[i]].size()];
                int p = 0;
                for (int j = 0; j < trees.length; j++) {
                    if (i != j) nTrees[p++] = trees[j];
                }
                for (int j : graph[trees[i]]) {
                    nTrees[p++] = j;
                }
                if (p != nTrees.length) throw new AssertionError();
                res = Math.max(res, recur(nTrees, !black) + 1);
            }
        } else {
            res = Integer.MAX_VALUE;
            for (int i = 0; i < trees.length; i++) {
                int[] nTrees = new int[trees.length - 1];
                int p = 0;
                for (int j = 0; j < trees.length; j++) {
                    if (i != j) nTrees[p++] = trees[j];
                }
                if (p != nTrees.length) throw new AssertionError();
                res = Math.min(res, recur(nTrees, !black) + 1);
            }
        }
        memo.put(s, res);
        return res;
    }

    private String asStr(int v) {
        List<String> cs = new ArrayList<>();
        for (int i : graph[v]) {
            cs.add(asStr(i));
        }
        Collections.sort(cs);
        StringBuilder b = new StringBuilder();
        b.append("(");
        for (String c : cs) b.append(c);
        b.append(")");
        return b.toString();
    }
}
