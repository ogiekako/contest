package on_2012.on2012_6_9.theblackjackdivone;


// Paste me into the FileEdit configuration dialog

public class TheBlackJackDivOne {
    public double expected(String[] cards) {
        int[] rest = new int[12];
        for (int i = 2; i <= 9; i++) rest[i] += 4;
        rest[10] += 4 * 4;
        rest[11] += 4;
        int have = 0;
        for (String s : cards) {
            char c = s.charAt(0);
            if (Character.isDigit(c)) {
                rest[c - '0']--;
                have += c - '0';
            } else if (c == 'A') {
                have += 11;
                rest[11]--;
            } else {
                have += 10;
                rest[10]--;
            }
        }
        if (have >= 21) return 0;
        return dfs(52 - cards.length, rest, have);
    }

    private double dfs(int restCnt, int[] rest, int have) {
        if (have >= 21) return 0;
        double res = 0;
        for (int i = 0; i < rest.length; i++)
            if (rest[i] > 0) {
                int cur = rest[i];
                rest[i]--;
                res += (1 + dfs(restCnt - 1, rest, have + i)) * cur / restCnt;
                rest[i]++;
            }
        return res;
    }


}

