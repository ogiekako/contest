package src;

import java.util.Arrays;
import java.util.Random;

public class BuildingTowersEasy {
    public int maxHeight(int N, int[] x, int[] t) {
        int res = 0;
        for (int i=1;i<=N;i++){
            int val = i-1;
            for(int j=0;j<x.length;j++){
                int d = Math.abs(x[j]-i);
                val = Math.min(val, t[j] + d);
            }
            res = Math.max(res,val);
        }
        return res;
    }

    public static void main(String[] args) {
        int N = 100_000;
        Random rnd = new Random(20491284L);
        int[] x = new int[50];
        int[] t = new int[50];
        for (int i = 0; i < x.length; i++) {
            x[i] = rnd.nextInt(N) + 1;
            t[i] = rnd.nextInt(N) + 1;
        }
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(t));
    }
}
