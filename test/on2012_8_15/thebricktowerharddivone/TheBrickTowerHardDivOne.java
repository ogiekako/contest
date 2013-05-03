package on2012_8_15.thebricktowerharddivone;



// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.TreeSet;

public class TheBrickTowerHardDivOne {
    private static final long MOD = 1234567891;

    public int find(int C, int K, long H) {
		State[] states = gen();
       int n = states.length * (K+1);
       long[] x = new long[n];
       for (int i = 0; i < states.length; i++){
           int add = 0;
           int[] is = states[i].is;
           if(is[0]==is[1])add++;
           if(is[0]==is[2])add++;
           if(is[1]==is[3])add++;
           if(is[2]==is[3])add++;
           long way = 1;
           for (int j = 0; j < states[i].numColor; j++) way *= C-j;
           if(add <= K){
               int id = i * (K+1) + add;
               x[id] += way % MOD;
           }
       }
        long[][] A = new long[n][n];
        for (int i = 0; i < n; i++) A[i][i] = 1;
        for (int k = 0; k < 4; k++){
            long[][] B = new long[n][n];
            for (int i = 0; i < states.length; i++){
                State s = states[i];
                for(int j=0;j<s.numColor+1;j++){
                    int[] is = s.is.clone();
                    int add = 0;
                    if(is[k]==j)add++;
                    is[k] = j;
                    if(k==1){
                        if(is[0]==is[1])add++;
                    }else if(k==2){
                        if(is[0]==is[2])add++;
                    }else if(k==3){
                        if(is[1]==is[3])add++;
                        if(is[2]==is[3])add++;
                    }
                    long way = j<s.numColor ? 1 : Math.max(0,C-s.numColor);
                    int q = Arrays.binarySearch(states, new State(is));
                    for(int p=0;p+add<=K;p++){
                        int from = i * (K+1) + p;
                        int to = q * (K+1) + p + add;
                        B[to][from] += way;
                        if(B[to][from] >= MOD)B[to][from] -= MOD;
                    }
                }
            }
            A = Matrix.mul(B,A, (int) MOD);
        }
        x = Matrix.sumPowered(A, H, x, (int) MOD);
        long res = 0;
        for(long y:x)res += y;
        return (int) (res%MOD);
    }

    private State[] gen() {
        TreeSet<State> set = new TreeSet<State>();
        for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) for (int k = 0; k < 4; k++)
            for (int h = 0; h < 4; h++){
                int[] is = {i,j,k,h};
                set.add(new State(is));
            }
        return set.toArray(new State[0]);
    }


    private class State implements Comparable<State>{
        int[] is;
        int numColor;
        State(int[] is){
            ArrayUtils.normalize(is);
            this.is = is;
            numColor = ArrayUtils.max(is) + 1;
        }

        public int compareTo(State o) {
            return ArrayUtils.compare(is, o.is);
        }
    }
}

