package net.ogiekako.algorithm.graph.denseGraph;

public class SparseGraph {

    /**
     * 木において,頂点間の距離が,distanceであるペアの数.
     * O(n log n).
     * 重心分解を用いて,各部分木に対して,再帰的に,その根を通る長さdistanceのパスの数を求めている.
     * 毎回木のサイズが1/2になるため,
     * http://www.codeforces.com/contest/161/problem/D
     *
     * @param tree
     * @param distance
     * @return
     */
    public static long countPairs(Vertex[] tree, int distance) {
        return countPairs0(tree[0], distance, new int[distance + 1]);
    }

    private static long countPairs0(Vertex root, int distance, int[] vertexCounts) {
        root = centroid(root);
        long res = 0;
        // subtree
        root.used = true;
        for (Edge_ e : root) if (!e.to.used) res += countPairs0(e.to, distance, vertexCounts);
        root.used = false;

        setDistance(root, null, 0, 1, vertexCounts);
        for (Edge_ e : root)
            if (!e.to.used) {
                setDistance(e.to, root, 1, -1, vertexCounts);
                res += calcPairsThroughRoot(e.to, root, 1, distance, vertexCounts);
            }
        vertexCounts[0] = 0;
        return res;
    }

    private static long calcPairsThroughRoot(Vertex subtreeVertex, Vertex parent, int depth, int desiredDistance, int[] vertexCount) {
        if (depth >= vertexCount.length) return 0;
        long res = vertexCount[desiredDistance - depth];
        for (Edge_ e : subtreeVertex)
            if (!e.to.used && e.to != parent)
                res += calcPairsThroughRoot(e.to, subtreeVertex, depth + 1, desiredDistance, vertexCount);
        return res;
    }

    private static void setDistance(Vertex current, Vertex parent, int distanceFromRoot, int add, int[] vertexCounts) {
        if (distanceFromRoot >= vertexCounts.length) return;
        vertexCounts[distanceFromRoot] += add;
        for (Edge_ e : current)
            if (!e.to.used && e.to != parent) setDistance(e.to, current, distanceFromRoot + 1, add, vertexCounts);
    }

    /**
     * 木に関して,そこで分解したときに,n/2以下がないような点.
     * 根からのサイズを計算し,再び根から,n/2より大きいサイズが子にない点で切ればいい.
     * あったら,その部分木について計算.
     * O(tree size).
     *
     * @param treeRoot
     * @return
     */
    public static Vertex centroid(Vertex treeRoot) {
        setSizes(treeRoot, null);
        return centroid0(treeRoot, null, treeRoot.size);
    }


    private static Vertex centroid0(Vertex current, Vertex parent, int totalSize) {
        for (Edge_ e : current)
            if (!e.to.used && e.to != parent && e.to.size > totalSize / 2) return centroid0(e.to, current, totalSize);
        return current;
    }

    /**
     * O(tree size)
     *
     * @param treeRoot
     * @param parent
     * @return
     */
    public static int setSizes(Vertex treeRoot, Vertex parent) {
        treeRoot.size = 1;
        for (Edge_ e : treeRoot) if (!e.to.used && e.to != parent) treeRoot.size += setSizes(e.to, treeRoot);
        return treeRoot.size;
    }

}
