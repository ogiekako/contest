package net.ogiekako.algorithm.graph.denseGraph;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/22
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class Vertex extends ArrayList<Edge_>{
    public final int id;
    public Vertex(int id){
        this.id=id;
    }
    boolean used;
    int size;
}
