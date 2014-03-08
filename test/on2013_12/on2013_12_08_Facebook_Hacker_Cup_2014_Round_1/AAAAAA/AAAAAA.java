package on2013_12.on2013_12_08_Facebook_Hacker_Cup_2014_Round_1.AAAAAA;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class AAAAAA {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int H = in.nextInt(), W = in.nextInt();
        char[][] map = new char[H][W];
        for (int i = 0; i < H; i++) {
            map[i] = in.next().toCharArray();
        }
        int[][] to = new int[H][W];
        to[0][0] = 1;
        for(int i=0;i<H;i++)for(int j=0;j<W;j++)if(to[i][j] > 0){
            for(int d=0;d<2;d++){
                int nx = i + dx[d];
                int ny = j + dy[d];
                if (0 <= nx && nx < H && 0 <= ny && ny < W){
                    if(map[nx][ny] == '.') {
                        to[nx][ny] = to[i][j] + 1;
                    }
                }
            }
        }
        int[][] from = new int[H+1][W+1];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if(map[i][j] == '.')from[i][j] = 1;
            }
        }
        for(int i=H-1;i>=0;i--)for(int j=W-1;j>=0;j--)if(from[i][j] > 0) {
            for (int d = 2; d < 4; d++) {
                int nx = i + dx[d];
                int ny = j + dy[d];
                if (0 <= nx && nx < H && 0 <= ny && ny < W){
                    if(map[nx][ny] == '.') from[nx][ny] = Math.max(from[nx][ny], from[i][j] + 1);
                }
            }
        }
        debug("to");
        for(int[] i:to)debug(i);
        debug("from");
        for(int[] i:from)debug(i);

        int res = 0;
        for(int i=0;i<H;i++)for(int j=0;j<W;j++)if(map[i][j] == '.'){
            res = Math.max(res, to[i][j]);
            if(j > 0){
                int k = i;
                while (k >= 0 && map[k][j] == '.') {
                    if(to[i][j-1] > 0)
                        res = Math.max(res, to[i][j-1] + i-k+1 + from[k][j+1]);
                    k--;
                }
            }
            if(i > 0){
                int k = j;
                while(k >= 0 && map[i][k] == '.') {
                    if(to[i-1][j] > 0)
                        res = Math.max(res, to[i-1][j] + j-k+1 + from[i+1][k]);
                    k--;
                }
            }
        }
        out.printFormat("Case #%d: %d\n",testNumber,res);
    }
    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
}
