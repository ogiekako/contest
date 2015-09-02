package net.ogiekako.algorithm;

public class EPS {
    public static double EPS = 1e-9;
    public static boolean eq(double a, double b) {
        double absoluteError = Math.abs(a - b);
        if (absoluteError < EPS) return true;
        double relativeError = Math.abs(absoluteError / a);
        return relativeError < EPS;
    }
    public static boolean le(double a, double b) {
        return a < b + EPS;
    }
    public static double value() {
        return EPS;
    }
    public static void set(double eps) {
        EPS = eps;
    }
    public static boolean lt(double a, double b) {
        return a < b - EPS;
    }

    public static int signum(double d) {
        return d < -EPS ? -1 : d > EPS ? 1 : 0;
    }
}
