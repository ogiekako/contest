package tmp;

// Paste me into the FileEdit configuration dialog

public class ImportantSequence {
    public int getCount(int[] B, String operators) {
        long k = 0, l = Long.MAX_VALUE;
        boolean plus = true;
        long add = 0;
        for (int i = 0; i < B.length; i++) {
            if (operators.charAt(i) == '+') {
                plus = !plus;
                add = B[i] - add;
            } else {
                add -= B[i];
            }
            if (plus) {
                k = Math.min(k, add);
            } else {
                l = Math.min(l, add);
            }
        }
        return l == Long.MAX_VALUE ? -1 : (int) Math.max(l - Math.max(0, -k) - 1, 0);
    }
}

