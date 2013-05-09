package net.ogiekako.algorithm.graph.denseGraph;

import java.util.ArrayList;

public class Vertex extends ArrayList<Edge_> {
    public final int id;
    public Vertex(int id) {
        this.id = id;
    }
    boolean used;
    int size;
}
