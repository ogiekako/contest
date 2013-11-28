package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int[] xs = new int[100] ,ys =new int[100];
        out.println(100);
        for(int i=1;i<=100;i++){
            int x,y;
            if(i <= 49){
                x=1;
                y=i;
            }else if(i<=98){
                x=2;
                y=99-i;
            } else if(i==99){
                x=1;y=50;
            } else{
                x=2;y=50;
            }
            xs[i] = x;
            ys[i] = y;
            out.printFormat("%d %d\n", x,y);
        }
    }
}
