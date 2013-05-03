package on2012_6_4.pillars;



// Paste me into the FileEdit configuration dialog

public class Pillars {
   public double getExpectedLength(int w, int X, int Y) {
       double res = 0;
		for(int d = -100000;d <= 100000; d++){
            // x + d == y
            // 1 <= x <= X
            // 1 <= x + d <= Y
            // max(1,1-d) <= x <= min(X,Y-d).
            int cnt = Math.max(0, Math.min(X,Y-d) - Math.max(1,1-d) + 1);
            double dist = Math.hypot(d,w);
            res += dist * cnt / X / Y;
        }
       return res;
   }


}

