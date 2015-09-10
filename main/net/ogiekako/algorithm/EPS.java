package net.ogiekako.algorithm;

public class EPS {
    private static double eps = 1e-9;

    public static boolean eq(double a, double b) {
        double absoluteError = Math.abs(a - b);
        if (absoluteError < eps) return true;
        double relativeError = Math.abs(absoluteError / a);
        return relativeError < eps;
    }

    public static boolean le(double a, double b) {
        return a - b < eps;
    }

    public static double get() {
        return eps;
    }

    public static void set(double eps) {
        EPS.eps = eps;
    }

    public static boolean lt(double a, double b) {
        return a < b - eps;
    }

    public static int signum(double d) {
        return d < -eps ? -1 : d > eps ? 1 : 0;
    }

    public static boolean isZero(double x) {
        return eq(x, 0);
    }
}
