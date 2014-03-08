package net.ogiekako.research.dynamic;
import java.util.HashSet;
import java.util.Set;
public class ChromaticNumberParameterizedByClusterVertexDeletionNumber implements DynamicGraphAlgorithm {
    _UndirectedGraph G = new _UndirectedGraph();
    Set<Integer> X = new HashSet<>();

    @Override
    public void init(int n) {
        G.init(n);
    }
    @Override
    public void add(int u, int v) {
        G.add(u, v);
        if (!X.contains(u)) moveToSolution(u);
        if (!X.contains(v)) moveToSolution(v);
    }
    private void moveToSolution(int u) {

    }



//    private List<int[]> generateAllColoring(){
//
//    }

    @Override
    public void remove(int u, int v) {
        G.remove(u, v);
    }
    @Override
    public int compute() {
        return 0;
    }


    public static void main(String[] args) {
        int n = 4;
        int res = dfs(new int[n],0);
        System.out.println(res);
    }
    private static int dfs(int[] a, int i) {
        int n = a.length;
        if(i == n){
            int mask = 0;
            for(int aa:a)mask |= 1<<aa;
            if(Integer.bitCount(mask + 1) == 1)return 1;
            return 0;
        }
        int res = 0;
        for(int k=0;k<a.length;k++){
            a[i] = k;
            res += dfs(a, i + 1);
        }
        return res;
    }
}
