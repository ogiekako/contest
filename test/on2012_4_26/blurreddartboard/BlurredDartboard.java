package on2012_4_26.blurreddartboard;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;
import java.util.Random;

public class BlurredDartboard {
   public int minThrows(int[] points, int P) {
		int U = 0;
       for(int p:points)if(p==0)U++;
       int MX = 0;
       for(int p:points)MX = Math.max(p,MX);
       MX = Math.max(MX,1);
       if(U==0){
           return (P+MX-1)/MX;
       }
       int[] u = new int[U];
       U=0;
       for(int i=1;i<points.length+1;i++){
           boolean con = false;
           for(int p:points)if(p==i)con = true;
           if(!con){
               u[U++] = i;
           }
       }
       int[] sum = new int[U+1];
       for(int i=0;i<U;i++)sum[i+1] = sum[i] + u[i];

       int res = Integer.MAX_VALUE;
       int used = 0;
       for(int j=U;j>=0;j--){
           res = Math.min(res, used + (P+MX-1)/MX);
           if(j==0)break;
           used += P/sum[j] * j;
           P -= sum[j] * (P/sum[j]);
           res = Math.min(res, used+j);
       }
       return res;
   }

    public static void main(String[] args) {
        BlurredDartboard b
         =new BlurredDartboard();
        Random rnd = new Random();
        for(int o=0;o<500;o++){
            int n = 50;
            int N = 50;
            int d = rnd.nextInt(N);
            int[] ps = new int[n];
            for (int i = 0; i < n; i++){
                ps[i] = rnd.nextInt(N) < d ? i : 0;
            }
            int P = rnd.nextInt(1000000000);
            int res = b.minThrows(ps,P);
            int exp = b.solve2(ps, P);
            System.err.println(res+" "+exp+ " " + Arrays.toString(ps));
            if(res!=exp)throw new AssertionError();
        }
    }

    private int solve2(int[] p, int x) {
        int n = p.length;
        boolean[] u = new boolean[n+1];
        int k=0;
        for (int i = 0; i < n; i++) {
            u[p[i]] = true;
            if(p[i] == 0){
                k++;
            }
        }
        int[] a = new int[k];
        k=0;
        int max=0;
        for(int i=1;i<=n;i++){
            if(!u[i]){
                a[k++] = i;
            }else{
                max = i;
            }
        }
        long l=0;
        long r=x+1;
        while(r-l>1){
            long m=(r+l)/2;
            long mmax=0;
            mmax=count(m,0,max,a);
            for (int i = 0; i < 2 * a.length && i <= m; i++){
                mmax = Math.max(mmax,count(i,m-i,max,a));
                mmax = Math.max(mmax,count(m-i,i,max,a));
            }
            if(mmax >= x){
                r=m;
            }else{
                l=m;
            }
        }

        return (int) r;
    }

    private long count(long m, long l, int max, int[] a) {
        long cnt = m * max;
        if(a.length>0){
            long c=l/a.length;
            for(int i=0;i<a.length;i++){
                cnt+=a[i]*c;
                l-=c;
            }
            for (int i = 0; i < l; i++){
                cnt += a[i];
            }
        }
        return cnt;
    }
}

