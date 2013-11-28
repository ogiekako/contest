package src;

import java.util.BitSet;
public class LittleElephantAndRGB {
    int n;
    BitSet s;
    int minGreen;
    public long getNumber(String[] list, int minGreen) {
        s = toBs(list);
        this.minGreen = minGreen;
        return solve();
    }
    private long solve(){
        long[] plus = new long[n];
        int[][] minus = new int[n][minGreen];
        for (int i = 0; i < n; i++) {
            int cont = 0;
            int init = 0;
            for (int j = i; j < n; j++) {
                if (s.get(j)) {
                    if (init == j - i) init++;
                    cont++;
                    if (cont < minGreen) {
                        minus[i][init]++;
                    } else {
                        plus[i]++;
                    }
                } else {
                    if (cont < minGreen) {
                        cont = 0;
                        minus[i][init]++;
                    } else {
                        plus[i]++;
                    }
                }
            }
        }
        long[] plus2 = new long[n];
        int[][] minus2 = new int[n][minGreen];
        for (int i = 0; i < n; i++) {
            int cont = 0;
            int init = 0;
            for (int j = i; j >= 0; j--) {
                if (s.get(j)) {
                    if (init == i - j) init++;
                    cont++;
                    if (cont < minGreen) {
                        minus2[i][init]++;
                    } else {
                        plus2[i]++;
                    }
                } else {
                    if (cont < minGreen) {
                        cont = 0;
                        minus2[i][init]++;
                    } else {
                        plus2[i]++;
                    }
                }
            }
        }
        long res = 0;
        long sumPlus = 0;
        long all = 0;
        long[] sumMinus = new long[minGreen];
        for (int i = 1; i < n; i++) {
            sumPlus += plus2[i - 1];
            all += plus2[i - 1];
            for (int j = 0; j < minGreen; j++) {
                sumMinus[j] += minus2[i - 1][j];
                all += minus2[i - 1][j];
            }
            res += plus[i] * all;
            for (int j = 0; j < minGreen; j++) {
                res += minus[i][j] * sumPlus;
            }
            long sum = 0;
            for (int j = 0; j < minGreen; j++) {
                res += minus[i][j] * sum;
                sum += sumMinus[minGreen - 1 - j];
            }
        }
        return res;
    }
    private BitSet toBs(String[] list) {
        StringBuilder b = new StringBuilder();
        for (String s : list) b.append(s);
        String s = b.toString();
        n = s.length();
        BitSet res = new BitSet();
        for(int i=0;i<s.length();i++)if(s.charAt(i) == 'G')res.set(i);
        return res;
    }
}
