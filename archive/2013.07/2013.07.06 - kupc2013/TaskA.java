package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N=in.nextInt(),Q=in.nextInt();
        String s="kogakubu10gokan";
        for(int i=0;i<N;i++){
            int q = in.nextInt();
            if(Q<q){
                out.println(s);
                return;
            }
            s = in.next();
        }
        out.println(s);
    }
}
