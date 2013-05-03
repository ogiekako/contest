package net.ogiekako.algorithm;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/10
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class EPS {
    public static double EPS = 1e-9;
    public static boolean eq(double a,double b){
        return Math.abs(a-b) < EPS;
    }
    public static boolean le(double a,double b){
        return a < b+EPS;
    }
}
