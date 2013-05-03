package net.ogiekako.algorithm.graph.problems;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.util.Random;
public class SunnyGraph {
    /**
     * O(n^3).
     * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2347
     * http://acm-icpc.aitea.net/index.php?2011%2FPractice%2F%C5%DF%A5%B3%A5%F3%A5%C6%A5%B9%A5%C8%2F%B9%D6%C9%BE
     * @param undirectedGraph
     * @param v
     * @return
     */
    public static boolean isSunnyGraph(boolean[][] undirectedGraph, int v) {
        if(undirectedGraph.length%2!=1)throw new IllegalArgumentException();
        if(v<0 || v>=undirectedGraph.length)throw new IllegalArgumentException();
        int n = undirectedGraph.length;
        double[][] adj=new double[n][n];
        Random rnd = new Random();
        for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)if(undirectedGraph[i][j]){
            if(i==v || j==v){
                adj[i][j] = rnd.nextDouble()*2;
                adj[j][i] = rnd.nextDouble()*2;
            }else{
                double d = rnd.nextDouble()*2;
                adj[i][j] = d;
                adj[j][i] = -d;
            }
        }
        double det = Matrix.determinantDestructive(adj);
        return Math.abs(det) > 1e-9;
    }
}
