package src;

import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;
public class GearsDiv1 {
    public int getmin(String color, String[] graph) {
        String s = "RGB";
        int res = Integer.MAX_VALUE;
        for(int i=0;i<s.length();i++)for(int j=i+1;j<s.length();j++){
            int n = 0, m = 0;
            for(char c:color.toCharArray()){
                if(c==s.charAt(i))n++;
                if(c==s.charAt(j))m++;
            }
            boolean[][] g = new boolean[n][m];
            n=0;
            for(int k=0;k<graph.length;k++)if(color.charAt(k) == s.charAt(i)){
                m = 0;
                for(int l=0;l<graph.length;l++)if(color.charAt(l) == s.charAt(j)){
                    g[n][m] = graph[k].charAt(l) == 'Y';
                    m++;
                }
                n++;
            }
            if(n == 0 || m == 0) res = 0;
            else res = Math.min(res, BipartiteGraphAlgorithm.maximumMatching(g));
        }
        return res;
    }
}
