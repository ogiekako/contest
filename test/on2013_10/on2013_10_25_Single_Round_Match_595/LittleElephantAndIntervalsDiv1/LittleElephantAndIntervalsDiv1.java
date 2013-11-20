package on2013_10.on2013_10_25_Single_Round_Match_595.LittleElephantAndIntervalsDiv1;


import java.util.Arrays;
public class LittleElephantAndIntervalsDiv1 {
    public long getNumber(int M, int[] L, int[] R) {
        int[] last = new int[M];
        Arrays.fill(last, -1);
        for (int i = 0; i < L.length; i++) {
            for (int j = L[i] - 1; j < R[i]; j++) {
                last[j] = i;
            }
        }
        int cnt = 0;
        for (int i = 0; i < L.length; i++) {
            for (int l : last) {
                if (l == i) {
                    cnt++; break;
                }
            }
        }
        return 1L << cnt;
    }
}
