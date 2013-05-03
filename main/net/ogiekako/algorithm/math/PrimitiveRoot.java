package net.ogiekako.algorithm.math;

import java.util.Random;

public class PrimitiveRoot{
	/**
	 * 素数pの原始根を求める.
	 * ランダムでも十分な確率で生成できるらしい.
	 * TODO オーダーを調べる.
	 * @param p 素数
	 * @return pの原始根を求める.
	 */
	long primitiveRoot(int p) {
		Random rand = new Random();
		long q = rand.nextInt(p);
		while (!check(q, p)) q = rand.nextInt(p);
		return q;
	}
	
	long pow(long n,long p,long MOD){
		if(p==0)return 1%MOD;
		long m=pow(n,p>>1,MOD);
		return (p&1)==0 ? m*m%MOD : m*m%MOD*n%MOD;
	}
	boolean check(long q, long p) {
		if (q <= 1) return false;
		for (long i = 2; i * i <= p - 1; i++) if ((p - 1) % i == 0) {
			if (pow(q, i, p) == 1) return false;
			if (pow(q, (p - 1) / i, p) == 1) return false;
		}
		return true;
	}
}
