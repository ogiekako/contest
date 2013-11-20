package on2013_09.on2013_09_27_TopCoder_SRM__591.TheArithmeticProgression;


public class TheArithmeticProgression {
    public double minimumChange(int a, int b, int c) {
        double nA = b - (c - b);
        double nB = a + (c - a) / 2.0;
        double nC = b + (b - a);
        double dA = Math.abs(nA - a);
        double dB = Math.abs(nB - b);
        double dC = Math.abs(nC - c);
        return Math.min(Math.min(dA, dB), dC);
    }
}
