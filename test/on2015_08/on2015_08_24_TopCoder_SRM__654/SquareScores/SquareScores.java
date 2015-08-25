package on2015_08.on2015_08_24_TopCoder_SRM__654.SquareScores;



import java.util.Arrays;

public class SquareScores {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double calcexpectation(int[] _p, String s) {
        double[] p = new double[_p.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = _p[i] / 100.0;
        }
        double res = 0;
        for (int i = 0; i < s.length(); i++) {
            double[] prob = new double[p.length];
            Arrays.fill(prob, 1);
            for (int j = i; j < s.length(); j++) {
                for (int k = 0; k < p.length; k++) {
                    prob[k] *= s.charAt(j) == '?' ? p[k] : s.charAt(j) - 'a' == k ? 1 : 0;
                    res += prob[k];
                }
            }
        }
        return res;
    }
}
