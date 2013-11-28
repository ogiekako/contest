package tmp;

public class TheNumberGameDivOne {
    public String find(long n) {
        for (int i = 1; i <= 62; i += 2) {
            System.out.println(1L << i);
            if ((1L << i) == n) {
                return "Brus";
            }
        }
        return (n % 2 == 0) ? "John" : "Brus";
    }

    int[] dp;

    private boolean solve(int n) {
        if (dp[n] != 0) return dp[n] > 0 ? true : false;
        dp[n] = -1;
        for (int j = 2; j < n; j++)
            if (n % j == 0) {
                if (!solve(n - j)) dp[n] = 1;
            }
        return dp[n] == 1;
    }

    public static void main(String[] args) {
        new TheNumberGameDivOne().test();
    }

    private void test() {
        for (int i = 1; i <= 1000; i++) {
            dp = new int[i + 1];
            boolean res = find(i).charAt(0) == 'J';
            if (res != solve(i)) throw new AssertionError();
            if (solve(i)) {
                System.out.print(i + ",");
            }
        }
    }
}
