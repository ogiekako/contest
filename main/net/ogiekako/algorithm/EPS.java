package net.ogiekako.algorithm;

public class EPS {
    public static double EPS = 1e-9;
    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
    public static boolean le(double a, double b) {
        return a < b + EPS;
    }
    public static double get() {
        return EPS;
    }
}
