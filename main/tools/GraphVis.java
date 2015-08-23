package tools;

import net.ogiekako.algorithm.graph.*;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GraphVis {

    private void doitWithNM(Scanner in) {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] cs = new char[n][n];
        ArrayUtils.fill(cs, 'N');
        for (int i = 0; i < m; i++) {
            int s = in.nextInt() - 1;
            int t = in.nextInt() - 1;
            cs[s][t] = 'Y';
        }
        String[] data = new String[n];
        for (int i = 0; i < n; i++) data[i] = new String(cs[i]);
        doitWithYN(data);
    }

    public void doitWithYN(String[] data) {
        String fileName = "test.dot";
        String filePath = "module/src/" + GraphVis.class.getPackage().getName() + "/" + fileName;
        System.err.println(filePath);
        File file = new File(filePath);
        PrintWriter pw;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        show(pw, data);
    }

    private void show(PrintWriter out, String[] data) {
        out.println("digraph sample{");
        out.println("\t" + "graph [rankdir = LR];");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i].charAt(j) == 'Y') {
                    out.printf("%d -> %d;\n", i, j);
                }
            }
        }
        out.println("}");
        out.flush();
    }

    public static void output(Graph graph) {
        output(graph, "graph");
    }

    public static void output(Graph graph, String fileName) {
        String filePath = System.getProperty("user.home") + "/Desktop/" + fileName + ".dot";
        File file = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            output(graph, pw);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void output(Graph graph, PrintWriter out) {
        out.println("digraph foo{");
        out.println("\t" + "graph [rankdir = LR];");
        for (int i = 0; i < graph.size(); i++)
            for (Edge e : graph.edges(i)) {
                int j = e.to();
                String label = generateLabel(e);
                if (!label.isEmpty())
                    out.printf(" %d -> %d [ label = \"%s\" ];\n", i, j, label);
            }
        out.println("}");
        out.flush();
    }

    private static String generateLabel(Edge e) {
        String res = "";
        if (e.cost() != 0) res += e.cost();
        if (e.residue() > 0) {
            res += String.format("%s%d/%d", res.length() > 0 ? " " : "", e.flow(), e.residue() + e.flow());
        }
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(3);
        graph.add(new FlowEdge(0, 1, 10));
        graph.add(new SimpleEdge(1, 2));
        graph.add(new WeightedEdge(2, 0, 4));
        output(graph);
    }

    public static void output(boolean[][] graph) {
        int n = graph.length;
        Graph g = new Graph(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (graph[i][j]) {
                    g.add(new SimpleEdge(i, j));
                }
        output(g);
    }

}
