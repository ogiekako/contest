package src;

import net.ogiekako.algorithm.math.MathUtils;
public class LCMSet {
    public String equal(int[] A, int[] B) {
        if(!valid(A,B)) return "Not equal";
        if(!valid(B,A)) return "Not equal";
        return "Equal";
    }
    private boolean valid(int[] A, int[] B) {
        for(int a:A){
            int lcm = 1;
            for(int b:B){
                if(a%b==0)lcm = (int) MathUtils.lcm(lcm, b);
            }
            if(lcm != a)return false;
        }
        return true;
    }
}
