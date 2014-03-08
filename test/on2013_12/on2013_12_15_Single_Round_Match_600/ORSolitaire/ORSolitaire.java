package on2013_12.on2013_12_15_Single_Round_Match_600.ORSolitaire;



public class ORSolitaire {
    public int getMinimum(int[] numbers, int goal) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 31; i++) {
            if (goal << 31 - i < 0) {
                int val = 0;
                for (int number : numbers) {
                    if ((number & ~goal) == 0) {
                        if (number << 31 - i < 0) val++;
                    }
                }
                res = Math.min(res, val);
            }
        }
        return res;
    }
}
