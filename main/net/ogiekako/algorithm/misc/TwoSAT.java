package net.ogiekako.algorithm.misc;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.SimpleEdge;
import net.ogiekako.algorithm.graph.algorithm.SCC;
import net.ogiekako.algorithm.utils.Pair;

public class TwoSAT {
    /*
   clause[i][0] or clause[i][1] という条件をすべて満たすことできるか?
   x_j : 2j
   not x_j : 2j + 1
   で表す.
   できる場合は, assignment を返し, できない場合はnull を返す.

    アルゴリズム: www.cs.tau.ac.il/~safra/Complexity/2SAT.ppt , http://en.wikipedia.org/wiki/2-satisfiability#Strongly_connected_components
   not c0 -> c1
   not c1 -> c0
   という枝を加えたグラフを考える.
   すると,パス a -> b があると,パス not b -> not a がある (枝 a -> b があれば,枝 not b -> not a があるから)
   まず, 強連結成分分解 して, おなじ連結成分に not x, x の両方が含まれていないことが必要.
   これが満たされている場合は, 以下のようにすれば正しい割り当てを作れる.
   トポロジカル順序で見ていく
    1. まだ決まっていない頂点 xを false にする.(not x -> x は, トポロジカル順序より存在しない)
    2. その強連結成分をすべてfalse にする.
    3. false にした頂点の否定をすべて true にする.
    4. 1にもどる
    証明:
    強連結成分内で, x -> y, y -> x が存在するということは, not x -> not y, not y -> not x があるので, not x, not y もおなじ共連結成分に含まれている.
    よって, 3でtrueにされた頂点を含む強連結成分は同時に true がassignされる.

    これでうまくいかないとすれば, それは1 で, trueでなければならない頂点をfalse にしてしまう場合.
    つまり, y -> x のパスがあり, y にすでにtrueが割り当てられているということだが, それは, not y が y よりさらに前にあることを意味する.
    not x -> not y のパスがあることより, not x は, x より前にあることになり, 割り当て方に矛盾.

    問題:
    http://poj.org/problem?id=3683, verified
    http://poj.org/problem?id=3207
    http://zakir.is-programmer.com/posts/21610.html
    
     */
    // 
    public static boolean[] twoSAT(int variableCount, int[][] clauses) {
        Graph graph = new Graph(variableCount * 2);
        for (int[] clause : clauses) {
            int x = clause[0], y = clause[1];
            // not x -> y
            graph.add(new SimpleEdge(x ^ 1, y));
            // not y -> x
            graph.add(new SimpleEdge(y ^ 1, x));
        }
        Pair<int[], int[][]> tmpPair = SCC.sccWithComponents(graph);
        int[][] scc = tmpPair.second;
        boolean[] determined = new boolean[variableCount];
        boolean[] assignment = new boolean[variableCount];
        for (int[] vs : scc) {
            if (determined[vs[0] >> 1]) continue;
            // assign false
            for (int v : vs) {
                // v and not v is in the same component
                if (determined[v >> 1]) return null;
                determined[v >> 1] = true;
                assignment[v >> 1] = (v & 1) == 1;
            }
        }
        return assignment;
    }
}
