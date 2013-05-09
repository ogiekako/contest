package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.SimpleEdge;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Stack;

/**
 * abstract helper class to make recursive function non-recursive.
 * <p/>
 * run(vertex) does the same thing as the following code whereas it is non-recursive:
 * <pre><code>
 *     void run(Edge e){
 *         if(!enter(e))return;
 *         for(Edge e2 : graph.edges(e.to()))if(e != e2.transposed()){
 *             run(e2);
 *             process(e2);
 *         }
 *         exit(e);
 *     }
 * </code></pre>
 *
 * @param <V> Result type of the dfs algorithm.
 */
public abstract class DFS<V> {
    protected final int n;
    protected final Graph graph;
    protected final boolean[] visited;
    private final ListIterator<Edge>[] is;

    public DFS(Graph graph) {
        this.graph = graph;
        n = graph.size();
        visited = new boolean[n];
        //noinspection unchecked
        is = new ListIterator[n];
        for (int i = 0; i < n; i++) is[i] = graph.edges(i).listIterator();
    }

    public V run() {
        return run(ArrayUtils.createOrder(n));
    }

    public V run(int[] order) {
        Arrays.fill(visited, false);
        for (int vertex : order)
            if (!visited[vertex]) {
                enterRoot(vertex);
                run(vertex);
            }
        return result();
    }

    private void run(int vertex) {
        Stack<Edge> path = new Stack<Edge>();
        Edge parent = new SimpleEdge(-1, vertex);
        path.push(null);
        enter(parent);
        while (!path.isEmpty()) {
            visited[parent.to()] = true;
            boolean found = false;
            for (; is[parent.to()].hasNext();
                 is[parent.to()].next()
                    ) {
                Edge e = is[parent.to()].next(); is[parent.to()].previous();
                if (e.transposed() != parent) {
                    if (enter(e)) {
                        found = true;
                        path.push(parent);
                        is[parent.to()].next();
                        parent = e;
                        break;
                    }
                }
            }
            if (!found) {
                exit(parent);
                if (parent.from() >= 0)
                    process(parent);
                parent = path.pop();
            }
        }
    }

    protected V result() {return null;}

    /**
     * enter a new root vertex of a dfs tree.
     */
    protected void enterRoot(int root) {}

    /**
     * enter the vertex e.to() using the edge e.
     * return true if continue dfs from e.to().
     */
    protected boolean enter(Edge e) {return true;}

    protected void exit(Edge e) {}

    protected void process(Edge e) {}
}
