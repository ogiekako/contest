package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class MinimumSteinerTree {
    public class AOJ1040 {
        private int[] dx={1,0,-1,0};
        private int[] dy={0,1,0,-1};
        public void solve(int testNumber, MyScanner in, PrintWriter out) {
            while(solve2(testNumber,in,out));
        }
        public boolean solve2(int testNumber, MyScanner in, PrintWriter out) {
            int H=in.nextInt(),W=in.nextInt();
            if(H==0 && W==0)return false;
            Vertex[] vs = new Vertex[H*W];
            for (int i = 0; i < vs.length; i++) vs[i] = new Vertex(i);
            int terminal = 0;
            for (int i = 0; i < H; i++) for (int j = 0; j < W; j++){
                int k = in.nextInt();
                if(k==1)vs[i*W+j].color = terminal++;
                else vs[i*W+j].color = -1;
                for (int d = 0; d < 4; d++){
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if(0<=ni && ni<H && 0<=nj && nj<W){
                        vs[i*W+j].es.add(new Edge(vs[ni*W+nj],1));
                    }
                }
            }
            int res = minSteinerTree(vs,terminal) + 1;
            out.println(H*W-res);
            return true;
        }
        
        int INF = 1<<28;
        private int minSteinerTree(Vertex[] vs,int terminal) {
            int n = vs.length;
            int[][] best = new int[1<<terminal][n];
            ArrayUtils.fill(best, INF);
            PriorityQueue<Entry> que = new PriorityQueue<Entry>();
            for(int i=0;i<n;i++)if(vs[i].color >=0){
                que.offer(new Entry(1<<vs[i].color,i,0));
            }
            while(!que.isEmpty()){
                Entry cur = que.poll();
                if(cur.weight>best[cur.mask][cur.v])continue;
                if(cur.mask == (1<<terminal)-1)return cur.weight;
                best[cur.mask][cur.v]=cur.weight;
                // append
                for(Edge e : vs[cur.v].es){
                    int u = e.to.id;
                    Entry nEntry = new Entry(cur.mask,u,cur.weight + e.weight);
                    if(best[nEntry.mask][nEntry.v] > nEntry.weight){
                        best[nEntry.mask][nEntry.v] = nEntry.weight;
                        que.offer(nEntry);
                    }
                }
                // concatenate
                int comp = ((1<<terminal)-1) ^ cur.mask;
                for(int sub=comp;sub>0;sub = comp & sub-1){
                    int add = best[sub][cur.v];
                    if(add < INF){
                        Entry nEntry = new Entry(cur.mask | sub, cur.v, cur.weight + add);
                        if (best[nEntry.mask][nEntry.v] > nEntry.weight) {
                            best[nEntry.mask][nEntry.v] = nEntry.weight;
                            que.offer(nEntry);
                        }
                    }
                }
            }
            return -1;
        }
        class Entry implements Comparable<Entry>{
            int mask;
            int v;
            int weight;

            Entry(int mask, int v, int weight) {
                this.mask = mask;
                this.v = v;
                this.weight = weight;
            }

            public int compareTo(Entry o) {
                return weight - o.weight;
            }
        }

        class Vertex{
            ArrayList<Edge> es = new ArrayList<Edge>();
            int color;
            int id;
            Vertex(int id){
                this.id = id;
            }
        }
        class Edge{
            Vertex to;
            int weight;

            public Edge(Vertex to, int dist) {
                this.to = to;
                this.weight = dist;
            }
        }
    }

}
