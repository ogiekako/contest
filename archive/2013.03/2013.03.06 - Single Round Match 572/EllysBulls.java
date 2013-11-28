package tmp;

import net.ogiekako.algorithm.dataStructure.intCollection.IntArray;

import java.util.HashMap;

public class EllysBulls {
    public String getNumber(String[] guesses, int[] bulls) {
        int n = guesses[0].length();
        int[] ten = new int[n];
        for (int i = 0; i < n; i++) ten[i] = i == 0 ? 1 : ten[i - 1] * 10;
        int a = n / 2, b = n - a;
        HashMap<IntArray, Integer> map = new HashMap<IntArray, Integer>();
        for (int i = 0; i < ten[a]; i++) {
            int[] cnt = calc(i, guesses, 0, a);
            IntArray arr = new IntArray(cnt);
            if (map.containsKey(arr)) map.put(arr, -1);
            else map.put(arr, i);
        }
        int res = -1;
        boolean amb = false;
        for (int i = 0; i < ten[b]; i++) {
            int[] cnt = calc(i, guesses, a, n);
            for (int j = 0; j < cnt.length; j++) cnt[j] = bulls[j] - cnt[j];
            IntArray arr = new IntArray(cnt);
            Integer A = map.get(arr);
            if (A != null) {
                if (A == -1 || res != -1) amb = true;
                res = A * ten[b] + i;
            }
        }
        if (amb) return "Ambiguity";
        if (res == -1) return "Liar";
        String R = "" + res;
        while (R.length() < n) R = "0" + R;
        return R;
    }

    private int[] calc(int num, String[] guesses, int s, int t) {
        int[] res = new int[guesses.length];
        for (int i = 0; i < guesses.length; i++) {
            int k = num;
            for (int j = t - 1; j >= s; j--) {
                int v = k % 10;
                if (guesses[i].charAt(j) - '0' == v) res[i]++;
                k /= 10;
            }
        }
        return res;
    }
}
