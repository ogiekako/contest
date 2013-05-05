package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.utils.TestUtils;
import org.junit.Test;

import java.util.Random;

public class DenseGraphUtilsTest {
    @Test
    public void testIsConnected() {
        int n = 100;
        boolean[][] graph = new boolean[n][n];
        Random rnd = new Random(1240128L);
        TestUtils.setRandom(rnd);
        for (int i = 0; i < 10000; i++){
            int q = rnd.nextInt(3);
            if(q==0){
                int[] e = TestUtils.getRandomEmptyEdge(graph, 100, rnd);
                if(e==null){i--;continue;}
                int x=e[0], y=e[1];
                graph[x][y] = graph[y][x] = true;
            }else if(q==1){
                int[] e = TestUtils.getRandomExistingEdge(graph, 100, rnd);
                if(e==null){i--;continue;}
                int x=e[0], y=e[1];
                graph[x][y] = graph[y][x] = false;
            }else{
                int[] e = TestUtils.generateRandomDistinctIntArray(n,2);
                int x=e[0], y=e[1];
                boolean res = DenseGraphUtils.isConnected(graph, x, y);
                boolean exp = isConnectedUnionFind(graph, x, y);
                if(res!=exp)throw null;
            }
        }
    }

    private boolean isConnectedUnionFind(boolean[][] graph, int x, int y) {
        int n = graph.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) for (int j = 0; j < i; j++)if(graph[i][j]){
            uf.union(i,j);
        }
        return uf.find(x,y);
    }
}
