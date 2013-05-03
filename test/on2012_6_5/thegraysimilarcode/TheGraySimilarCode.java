package on2012_6_5.thegraysimilarcode;



import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.math.BigInteger;

public class TheGraySimilarCode {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        if(N>128){
            out.println("Yes");
        }else{
            long[] A = new long[N];
            for (int i = 0; i < N; i++) A[i] = gen(new BigInteger(in.next()));
            for (int i = 0; i < N; i++) for (int j = 0; j < i; j++) for (int k = 0; k < j; k++)
                for (int l = 0; l < k; l++) if((A[i] ^ A[j] ^ A[k] ^ A[l]) == 0){
                    out.println("Yes");return;
                }
            out.println("No");
        }
	}

    private long gen(BigInteger I) {
        long res = 0;
        for(int i=0;i<64;i++){
            if(I.testBit(i))res |= 1L<<i;
        }
        return res;
    }
}
