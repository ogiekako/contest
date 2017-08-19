package on2017_08.on2017_08_19_TopCoder_Open_Round__3B.RemoveCharacters;



import java.util.Arrays;

public class RemoveCharacters {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minimalDistinct(String A, String B) {
        int n = 26;
        int[] mask = new int[n];
        boolean zero = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String a = "";
                String b = "";
                for (char c: A.toCharArray()) {
                    if (c-'a' == i || c-'a' == j) a+=c;
                }
                for (char c: B.toCharArray()) {
                    if (c-'a' == i || c-'a' == j) b+=c;
                }
                if (a.equals(b)) {
                    mask[i] |= 1 << j;
                }
            }
            if (mask[i] << 31 - i < 0) zero = false;
        }
        if (zero) return n;
        return 26 - maxClique(mask, 0, 0);
    }

    private int maxClique(int[] mask, int i, int cur) {
        if (i == 26) {
            return Integer.bitCount(cur);
        }
        int res = maxClique(mask, i + 1, cur);
        if (mask[i] << 31 - i < 0 && (mask[i] & cur) == cur) {
            res = Math.max(res, maxClique(mask, i + 1, cur | 1 << i));
        }
        return res;
    }
}
