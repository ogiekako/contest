package on2014_02.on2014_02_08_Single_Round_Match_608.BigO;



import java.util.Arrays;
public class BigO {
    public int minK(String[] graph) {
        int n = graph.length;
        boolean[][] g = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = graph[i].charAt(j) == 'Y';
            }
        }
        boolean[][] nei = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            nei[i][i] = true;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nei[i][j] |= g[i][j];
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    nei[i][j] |= nei[i][k] && nei[k][j];
                }
            }
        }
        int[] comp = new int[n];
        Arrays.fill(comp, -1);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (comp[i] == -1) {
                for (int j = 0; j < n; j++) {
                    if (nei[i][j] && nei[j][i]) comp[j] = m;
                }
                m++;
            }
        }
        debug("m", m);
        if(m==n)return 0;
        int[] size = new int[m];
        for (int c = 0; c < m; c++) {
            int e = 0;
            int v = 0;
            for (int i = 0; i < n; i++) {
                if(comp[i] == c) v++;
                for (int j = 0; j < n; j++) {
                    if(comp[i] == c && comp[j] == c && g[i][j]) e++;
                }
            }
            if(e > v) {
                debug(e,v);
                return -1;
            }
            size[c] = v;
        }
        debug("comp",comp);
        int[][] dist = new int[m][m];
        for(int i=0;i<m;i++)Arrays.fill(dist[i], -100);
//        for(int i=0;i<m;i++)dist[i][i] = 0;
        for(int i=0;i<n;i++)for(int j=0;j<n;j++)if(comp[i]!=comp[j] && nei[i][j]){
            dist[comp[i]][comp[j]] = size[comp[i]]==1 ? 0 : 1;
        }
        for(int k=0;k<m;k++)for(int i=0;i<m;i++)for(int j=0;j<m;j++)dist[i][j] = Math.max(dist[i][j],dist[i][k] + dist[k][j]);
        int res = 0;
        for(int i=0;i<m;i++)for(int j=0;j<m;j++)if(size[i]>1 && size[j]>1)res=Math.max(res,dist[i][j]);
        return res;
    }
    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
    public static void main(String[] args) {
        int n = 50;
        boolean[][] graph = new boolean[n][n];
        for(int i=0;i<n;i+=2)graph[i][i+1]=graph[i+1][i]=true;
        for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)graph[i][j]=true;
        String[] S = new String[n];
        for (int i = 0; i < n; i++) {
            S[i]="";
            for (int j = 0; j < n; j++) {
                S[i] += graph[i][j] ? 'Y' : 'N';
            }
        }
        System.out.print("{");
        for(int i=0;i<n;i++) System.out.print("\"" + S[i] + "\"" + (i==n-1 ? "}\n" : ","));
    }
}
