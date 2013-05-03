package on2013_04.on2013_04_21_2013_TopCoder_Open_Algorithm.FruitTrees;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Random;

public class FruitTrees {
    public int maxDist(int apple, int kiwi, int grape) {
        int[] ab = calc(apple, kiwi);
        int[] ac = calc(apple, grape);
        int[] bc = calc(kiwi, grape);
        int res = 0;
        for(int x=0;x<=2000;x++)for(int y=x;y<=2000;y++){
            res = Math.max(res, Math.min(ab[x], Math.min(ac[y], bc[y - x])));
        }
        return res;
    }

    private int[] calc(int a, int b) {
        int[] res = new int[2001];
        for(int x=0;x<=2000;x++){
            int cur = x;
            res[x] = Integer.MAX_VALUE;
            for(int i=0;i<=2000;i++){
                int d = cur / a;
                res[x] = Math.min(res[x], Math.min(cur - d * a, (d+1) * a - cur));
                cur += b;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(120410248L);
        for(;;){
            System.err.print(".");
            int a=rnd.nextInt(2000) + 1, b =rnd.nextInt(2000) + 1, c=rnd.nextInt(2000) + 1;
            if(rnd.nextBoolean()){
                a = rnd.nextInt(10)+1;
                b = rnd.nextInt(10)+1;
                c = rnd.nextInt(10)+1;
            }
            int exp = new FruitTrees().maxDist(a,b,c);
            int res = new FruitTrees().solve(a,b,c);
            if(res != exp){
                System.err.println(a+" "+b+" "+c);
                throw new AssertionError();
            }
        }
    }

    private int solve(int a, int b, int c) {
//        if(a<b){int tmp = a; a = b; b = tmp;}
//        if(a<c){int tmp = a; a = c; c = tmp;}
//        if(b<c){int tmp = b; b = c; c = tmp;}
//        int x= MathUtils.gcd(a,b);
//        int y=MathUtils.gcd(a,c);
//        int z=MathUtils.gcd(b,c);
//        int res=0;
//        for(int i=1;i<a;i++)for(int j=i+1;j<a;j++){
//            int p=i%x;p=Math.min(p,(a-p)%x);
//            int q=j%y;q=Math.min(q,(a-q)%y);
//            int s=(j-i)%z;s=Math.min(s,(b-s)%z);
//            res=Math.max(res,Math.min(Math.min(p,q),s));
//        }
//        return res;

//        int ak = gcd(a,b);
//        int ag = gcd(a,c);
//        int kg= gcd(b,c);
//        if(ak==ag && ag==kg)return ak/3;
//        return Math.min(Math.min(ak,ag),kg)/2;

        int res = 0;
        for (int i = 0; i < b; i++){
            for (int j = 0; j < c; j++){
                int dist = Integer.MAX_VALUE;
                int t=-1;
                t=dist(a,0,b,i);
                dist = Math.min(dist,t);
                t=dist(a,0,c,j);
                dist = Math.min(dist,t);
                t=dist(b,i,c,j);
                dist = Math.min(dist,t);
                res=res>dist?res : dist;
            }
        }
        return res;
    }

    private int dist(int x, int n, int y, int m) {
        int a=gcd(x,y);
        int b=n-m;b=b<0?m-n : b;
        b%=a;
        int c=b-a;c=c<0?a-b : c;
        return b<c?b : c;
    }

    int gcd(int x,int y){
        return MathUtils.gcd(x,y);
    }
}
