package net.ogiekako.algorithm.math;

import java.math.BigInteger;

public class ExEuclid {
	// ax + by = gcd(a,b)
	// res = {x,y}
	public static int[] exGcd(int a,int b){
		if(a==0)return new int[]{0,1};
		int[] xy = exGcd(b%a, a);
		return new int[]{xy[1]-b/a*xy[0],xy[0]};
	}	
	public static long[] exGcd(long a,long b){
		if(a==0)return new long[]{0,1};
		long[] xy = exGcd(b%a, a);
		return new long[]{xy[1]-b/a*xy[0],xy[0]};
	}

    public static BigInteger[] exGcd(BigInteger a, BigInteger b) {
        if(a.equals(BigInteger.ZERO))return new BigInteger[]{BigInteger.ZERO, BigInteger.ONE};
        BigInteger[] xy = exGcd(b.mod(a),a);
        return new BigInteger[]{xy[1].subtract(b.divide(a).multiply(xy[0])),xy[0]};
    }
}
