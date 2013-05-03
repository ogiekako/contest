package net.ogiekako.algorithm.math.tests;

import java.math.BigInteger;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import net.ogiekako.algorithm.math.Lucas;

public class LucasTest {
	
	@Test
	public void testLucas() {
		int MX = 2000;
		
		Random rnd = new Random(23980810274012478L);
		for(int i=0;i<50;i++){
			int mod = rnd.nextInt(50);
			if(!BigInteger.valueOf(mod).isProbablePrime(100)){
				i--;continue;
			}
			Lucas lu = new Lucas(mod);
			for (int j = 0; j < 50; j++) {
				int n = rnd.nextInt(MX);
				int k = rnd.nextInt(n+1);
				int res = lu.lucas(n, k);
				BigInteger exp = BigInteger.ONE;
				for (int l = 0; l < k; l++) {
					exp = exp.multiply(BigInteger.valueOf(n-l));
					exp = exp.divide(BigInteger.valueOf(l+1));
				}
				int expi = exp.mod(BigInteger.valueOf(mod)).intValue();
				Assert.assertEquals(expi, res);
			}
		}
	}
}
