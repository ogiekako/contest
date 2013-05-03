package on2012_5_26.pillars;



// Paste me into the FileEdit configuration dialog

import java.util.Random;

public class Pillars {
   public double getExpectedLength(int w, int x, int y) {
       double res = 0;
		for(int d=-200000;d<=200000;d++){
            int from = Math.max(1,1-d);
            int to = Math.min(x,y-d);
            res += Math.max(0,to-from+1) * Math.sqrt((long)w*w+(long)d*d);
        }
       return res / x / y;
   }

    public static void main(String[] args) {
        Random rnd = new Random(12049812048L);
        for(;;){
            System.err.print(".");
            int w = rnd.nextInt(1000)+1;
            int x = rnd.nextInt(100000)+1;
            int y = rnd.nextInt(100000)+1;
//            y = 100000;
            double res = new Pillars().getExpectedLength(w,x,y);
            double exp = new Pillars().solve(w,x,y);
            if(Math.abs(res-exp) > 1e-9 && Math.abs(1-(res/exp)) > 1e-9 && Math.abs(1-(exp/res)) > 1e-9){
                throw new AssertionError(w+" "+x+" "+y);
            }
//            System.err.println(res +" "+exp);
//            System.err.println(Math.abs(res-exp));
//            System.err.println(res/exp +" "+exp/res);
//            Assert.assertEquals(res,exp,1e-9);
        }
    }

    private double solve(int w, int x, int y) {
       double res = 0;
        long all = (long)x*y;
		for(int d=-200000;d<=200000;d++){
            int from = Math.max(1,1-d);
            int to = Math.min(x,y-d);
            res += (double)Math.max(0,to-from+1) / all * Math.hypot(w,d);
        }
       return res;
    }
}

