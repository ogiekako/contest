package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.interfaces.Function;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/11
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class Algorithm {
    /**
     * f(res) is maximal.
     * 一度も更新されなかった場合,exact に upperBound or lowerBound を返す.
     *
     * @param lowerBound
     * @param upperBound
     * @param concaveDownFunction
     * @return
     */
    public static double goldenSearch(double lowerBound,double upperBound,Function<Double, Double> concaveDownFunction){
        double phi = MathUtils.GOLDEN_RATIO;
        double lb = lowerBound,ub = upperBound;
        double m1 = (lb*phi + ub) / (1+phi);
        double m2 = (lb + ub*phi) / (1+phi);
        double v1 = concaveDownFunction.f(m1);
        double v2 = concaveDownFunction.f(m2);
        for(int i=0;i<150;i++){
            if(v1 < v2){// '<': maximum, '>': minimum
                lb = m1;m1 = m2;m2 = (lb + ub*phi) / (1+phi);
                v1 = v2;v2 = concaveDownFunction.f(m2);
            }else{
                ub = m2;m2 = m1;m1 = (lb*phi + ub) / (1+phi);
                v2 = v1;v1 = concaveDownFunction.f(m1);
            }
        }
        if(ub == upperBound)return ub;
        return lb;
    }

    public static interface BinSearchFilter {
        boolean isUpperBound(double value);
    }

    /**
     * 一度も更新されなかった場合,exact に upperBound or lowerBound を返す.
     *
     * @param lowerBound
     * @param upperBound
     * @param filter
     * @return
     */
    public static double binarySearch(double lowerBound, double upperBound, BinSearchFilter filter) {
        if (lowerBound > upperBound) throw new IllegalArgumentException();
        double initLowerBound = lowerBound;
        for (int i = 0; i < 100; i++) {
            double value = (upperBound + lowerBound) / 2;
            if (filter.isUpperBound(value)) upperBound = value;
            else lowerBound = value;
        }
        if (lowerBound == initLowerBound) return initLowerBound;
        return upperBound;
    }
}
