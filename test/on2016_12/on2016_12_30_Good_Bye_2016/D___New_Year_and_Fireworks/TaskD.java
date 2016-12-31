package on2016_12.on2016_12_30_Good_Bye_2016.D___New_Year_and_Fireworks;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.HashSet;

public class TaskD {
    void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }
    class E {
        int d;
        int x,y;
        E(int d,int x,int y){
            this.d=d;this.x=x;this.y=y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            E e = (E) o;

            if (d != e.d) return false;
            if (x != e.x) return false;
            return y == e.y;

        }

        @Override
        public int hashCode() {
            int result = d;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }
    }
    int[] dx={1,1,1 ,0 ,-1,-1,-1,0};
    int[] dy={1,0,-1,-1,-1,0 ,1 ,1};
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] ts = new int[n];
        for (int i = 0; i < n; i++)
            ts[i] = in.nextInt();
        HashSet<E> cur = new HashSet<>(), nxt = new HashSet<>();
        cur.add(new E(1,250,250));
        boolean[][] map = new boolean[500][500];
        int res = 0;
        for(int t:ts) {
            nxt.clear();
            for(E e:cur){
                int x=e.x, y=e.y;
                for(int i=0;i<t;i++){
                    x += dx[e.d];
                    y += dy[e.d];
                    if (!map[x][y]) {
                        map[x][y] = true;
                        res++;
                    }
                }
                nxt.add(new E(e.d+7 & 7, x, y));
                nxt.add(new E(e.d+1 & 7, x, y));
            }
            HashSet<E> tmp=cur;cur=nxt;nxt=tmp;
        }
        out.println(res);
    }
}
