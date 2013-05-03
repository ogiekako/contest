package net.ogiekako.algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 2:57
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private int vertexCount;
    private EdgeList[] edges;

    public boolean isDigraph() {
        return true;
    }

    public void remove(Edge edge) {
        edges[edge.from()].remove(edge);
        Edge rev = edge.reversed();
        if (rev != null) {
            edges[rev.from()].remove(rev);
        }
    }

    public static Graph of(List<Integer>[] graph) {
        Graph res = new Graph(graph.length);
        for(int i=0;i<res.size();i++)for(int j:graph[i]){
            res.add(i,j);
        }
        return res;
    }

    public void addWeighted(int from, int to, long cost) {
        add(new WeightedEdge(from, to, cost));
    }

    public void addFlow(int from, int to, long capacity){
        add(new FlowEdge(from, to, capacity));
    }

    private static class EdgeList extends ArrayList<Edge> {
        EdgeList() {super(0);}
    }

    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new EdgeList[vertexCount];
        for (int i = 0; i < vertexCount; i++) edges[i] = new EdgeList();
    }

    public void add(Edge edge) {
        edges[edge.from()].add(edge);
        Edge rev = edge.reversed();
        if (rev != null) {
            edges[rev.from()].add(rev);
        }
    }

    public void add(int from, int to) {
        add(new SimpleEdge(from, to));
    }

    public List<Edge> edges(int vertex) {
        return edges[vertex];
    }

    public int size() {
        return vertexCount;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "edges=" + (edges == null ? null : Arrays.asList(edges)) +
                '}';
    }
}
