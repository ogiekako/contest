package tmp;

import net.ogiekako.algorithm.exception.UnsatisfiableException;
import net.ogiekako.algorithm.graph.denseGraph.DenseGraphUtils;

public class MagicMolecule {
    public int maxMagicPower(int[] magicPower, String[] magicBond) {
        int n = magicPower.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) if (i != j && magicBond[i].charAt(j) == 'N') graph[i][j] = true;
        int all = 0;
        for (int aMagicPower : magicPower) all += aMagicPower;
        int minSelected = (2 * n + 2) / 3;
        int maxRemoved = n - minSelected;
        try {
            return all - DenseGraphUtils.vertexCoverUpTo(graph, magicPower, maxRemoved);
        } catch (UnsatisfiableException e) {
            return -1;
        }
    }

}
