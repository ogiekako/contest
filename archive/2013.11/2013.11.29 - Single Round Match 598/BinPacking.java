package src;

import java.util.Arrays;
public class BinPacking {
    public int minBins(int[] item) {
        Arrays.sort(item);
        int n = item.length;
        int res = 10000;
        int i = 0;
        while (i < n && item[i] == 100) i++;
        for (int j = 0; j * 3 <= i; j++) {
            res = Math.min(res, calc(item, n, j));
        }
        return res;
    }
    private int calc(int[] item, int n, int three) {
        int res = three;
        int i = res * 3;
        int j = n - 1;
        res += n - i;
        for (; i < j; i++) {
            for (; j > i; j--) {
                if (item[i] + item[j] <= 300) {
                    j--;
                    res--;
                    break;
                }
            }
        }
        return res;
    }
}
