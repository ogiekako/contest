package on2012_4_26.taska;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskA {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[] d = new long[N];
        long[] l = new long[N];
        for (int i = 0; i < N; i++) {
            d[i] = in.nextLong();
            l[i] = in.nextLong();
        }
        long D = in.nextLong();
        long[] maxLen = new long[N];
        Queue<Entry> que = new PriorityQueue<Entry>();
        que.offer(new Entry(0,d[0]));
        maxLen[0] = d[0];
        while(!que.isEmpty()){
            Entry e = que.poll();
            if(d[e.id] + e.length >= D){
                out.printf("Case #%d: %s\n",testNumber,"YES");
                return;
            }
            int i = e.id;
            for(int j=i+1;j<N;j++){
                if(d[i] + e.length >= d[j]){
                    long len = Math.min(l[j], d[j]-d[i]);
                    if(maxLen[j] < len){
                        maxLen[j] = len;
                        que.offer(new Entry(j,len));
                    }
                }
            }
        }

                out.printf("Case #%d: %s\n",testNumber,"NO");
	}
    class Entry implements Comparable<Entry>{
        int id;
        long length;

        Entry(int id, long length) {
            this.id = id;
            this.length = length;
        }

        public int compareTo(Entry o) {
            return - Long.signum(length - o.length);
        }
    }
}
