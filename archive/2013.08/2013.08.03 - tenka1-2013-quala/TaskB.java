package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        int res=0;
        for(int i=0;i<N;i++){
            int s=0;
            for(int j=0;j<5;j++)s += in.nextInt();
            if(s < 20)res++;
        }
        out.println(res);
    }
}
