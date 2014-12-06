package src;

import java.util.HashMap;
import java.util.Map;

public class BoardFolding {
    public int howMany(int N, int M, String[] compressedPaper) {
        char[][] map = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = (char)('0'+(tonumber(compressedPaper[i].charAt(j / 6)) >> (j % 6)) % 2);
            }
        }
        debug(map);
        int a = solve(map);
        debug("A",a);
        int b = solve(tr(map));
        return a*b;
    }
    void debug(Object... os){
//        System.err.println(Arrays.deepToString(os));
    }

    private char[][] tr(char[][] map) {
        char[][]res=new char[map[0].length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                res[j][i]=map[i][j];
            }
        }
        return res;
    }

    private int solve(char[][] map) {
        int n = map.length, m = map[0].length;
        Map<String, Integer> toInt = new HashMap<>();
        int p = 0;
        int[] row = new int[n];
        for (int i = 0; i < n; i++) {
            String s = new String(map[i]);
            if(toInt.containsKey(s))row[i]=toInt.get(s);
            else{
                row[i]=p;
                toInt.put(s,p);
                p++;
            }
        }
        debug("row",row);
        return solve(row);
    }

    int n;
    int res;
    boolean[][] foldable;// mid, from
    private int solve(int[] row) {
        n=row.length;
        res=0;
        foldable=new boolean[n][n+1];
        for (int i = 1; i < n; i++) {
            for (int j = 0;; j++) {
                int l=i-1-j;
                int r=i+j;
                if(l>=0 && r<n && row[l]==row[r]){
                    foldable[i][l]=foldable[i][r+1]=true;
                } else {
                    break;
                }
            }
        }
        res=0;
        boolean[][]memo=new boolean[n][n+1];
        recur(memo,row,0,n);
        return res;
    }

    private void recur(boolean[][] memo, int[] row, int l, int r) {
        if(memo[l][r])return;
        debug(l,r);
        res++;
        memo[l][r]=true;
        for(int m=l+1;m<r;m++){
            if(foldable[m][l])recur(memo,row,m,r);
            if(foldable[m][r])recur(memo,row,l,m);
        }
    }

    private int tonumber(char c) {
        if('0'<=c&&c<='9')return c-'0';
        if('a'<=c&&c<='z')return c-'a'+10;
        if('A'<=c&&c<='Z')return c-'A'+36;
        if(c=='#')return 62;
        if(c=='@')return 63;
        throw new AssertionError();
    }

    public static void main(String[] args) {
        int N = 250, M = 250;
        String[] s = new String[N];
        for (int i = 0; i < N; i++) {
            s[i]="";
            for (int j = 0; j*6 < M; j++) {
                s[i]+='@';
            }
        }
        int res = new BoardFolding().howMany(N, M, s);
        System.out.println(res);// 984390625
    }
}
