package net.ogiekako.algorithm.graph.graphDouble.tests;

import net.ogiekako.algorithm.graph.graphDouble.EdgeD;
import net.ogiekako.algorithm.graph.graphDouble.GraphD;
import net.ogiekako.algorithm.graph.graphDouble.GraphAlgorithm;
import net.ogiekako.algorithm.graph.graphDouble.SimpleEdge;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class GraphGenerator {
    public static final GraphGenerator ACYCLIC = new GraphGenerator() {@Override public boolean isValid(GraphD graph) {
            return GraphAlgorithm.isAcyclic(graph);
    }};
    public static final GraphGenerator SIMPLE = new GraphGenerator() {@Override public boolean isValid(GraphD graph) {
        HashSet<EdgeD> dejavu = new HashSet<EdgeD>();
        for (int i = 0; i < graph.size(); i++)for(EdgeD e:graph.getEdges(i)){
            if(dejavu.contains(e))return false;
            if(e.from() == e.to())return false;
            dejavu.add(e);
        }
        return true;
    }};
    public static final GraphGenerator ANY = new GraphGenerator() {@Override public boolean isValid(GraphD graph) {
            return true;
    }};

    public abstract boolean isValid(GraphD graph);
    public GraphD generate(int vertexCount, int edgeCount){
        return generate(vertexCount, edgeCount, new Random());
    }

    public GraphD generate(int vertexCount, int edgeCount, Random rnd){
        GraphD graph = new GraphD(vertexCount);
        for (int i = 0; i < edgeCount; i++){
            int source = rnd.nextInt(vertexCount);
            int dest = rnd.nextInt(vertexCount);
            EdgeD edge = new SimpleEdge(source, dest);
            graph.add(edge);
            if(!isValid(graph)){
                graph.remove(edge);
                i--;
            }
        }
        return graph;
    }
}
