package on2012_6_7.dom2012c;



import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Dom2012C {
    int[] dx = {0, 1, -1, 0};
    int[] dy = {1, 0, 0, -1};

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        Dice[] states = new Dice(new int[]{1, 5, 3, 4, 2, 6}).allState();
        for (; ; ) {
            int n = in.nextInt();
            if (n == 0) return;
            int[][] height = new int[300][300];
            int[][] face = new int[300][300];
            for (int i = 0; i < n; i++) {
                int top = in.nextInt(), front = in.nextInt();
                Dice di = null;
                for(Dice tmp:states)if(tmp.is[TOP]==top && tmp.is[FRONT]==front){
                    di = new Dice(tmp.is);
                }
                int x=150,y=150;
                for(;;){
                    int j;
                    for(j=6;j>=4;j--){
                        int dir;
                        for(dir=0;;dir++)if(di.is[dir] == j)break;
                        if(dir==TOP || dir==BOTTOM)continue;
                        int d = dir - 1;
                        int nx = x+dx[d], ny = y+dy[d];
                        if(height[x][y] > height[nx][ny]){
                            x=nx;y=ny;
                            if(dir == RIGHT)di.t2r();
                            if(dir == LEFT){di.t2r();di.t2r();di.t2r();}
                            if(dir == FRONT)di.t2f();
                            if(dir == BACK){di.t2f();di.t2f();di.t2f();}
                            break;
                        }
                    }
                    if(j==3)break;
                }
                height[x][y]++;
                face[x][y] = di.is[TOP];
            }
            int[] res = new int[6];
            for (int i = 0; i < 300; i++) for (int j = 0; j < 300; j++)if(face[i][j] > 0)res[face[i][j]-1]++;
            for (int i = 0; i < 6; i++){
                out.printf("%d%c",res[i],i==5?'\n':' ');
            }
        }
    }

    static int TOP = 0, RIGHT = 1, FRONT = 2, BACK = 3, LEFT = 4, BOTTOM = 5;

    class Dice {
        int[] is;

        Dice(int[] is) {
            this.is = is.clone();
        }

        void t2f() {
            roll(TOP, FRONT, BOTTOM, BACK);
        }

        void t2r() {
            roll(TOP, RIGHT, BOTTOM, LEFT);
        }

        void f2r() {
            roll(FRONT, RIGHT, BACK, LEFT);
        }

        void roll(int a, int b, int c, int d) {
            int tmp = is[d]; is[d] = is[c]; is[c] = is[b]; is[b] = is[a]; is[a] = tmp;
        }

        Dice[] allState() {
            Dice[] res = new Dice[24];
            for (int i = 0; i < 6; i++) {
                if (i % 2 == 0) t2f();
                else t2r();
                for (int j = 0; j < 4; j++) {
                    f2r();
                    res[i * 4 + j] = new Dice(is);
                }
            }
            return res;
        }
    }
}
