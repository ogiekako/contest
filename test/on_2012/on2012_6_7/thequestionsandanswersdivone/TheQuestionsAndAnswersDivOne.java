package on_2012.on2012_6_7.thequestionsandanswersdivone;


// Paste me into the FileEdit configuration dialog

public class TheQuestionsAndAnswersDivOne {
    public int find(int questions, String[] answers) {
        int n = answers.length;
        int mask = 0;
        for (int i = 0; i < answers.length; i++) if (answers[i].charAt(0) == 'N') mask |= 1 << i;
        int[][] dp = new int[questions + 1][1 << n];
        dp[0][0] = 1;
        for (int i = 0; i < questions; i++)
            for (int j = 0; j < 1 << n; j++) {
                for (int k = 1; k < 1 << n; k++)
                    if ((j & k) == 0 && ((mask & k) == k || (mask & k) == 0)) dp[i + 1][j | k] += dp[i][j];
            }
        return dp[questions][(1 << n) - 1];
    }


}

