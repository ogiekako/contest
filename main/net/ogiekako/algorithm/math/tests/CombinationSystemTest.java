package net.ogiekako.algorithm.math.tests;

import java.math.BigInteger;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import net.ogiekako.algorithm.math.CombinationSystem;

public class CombinationSystemTest {
	
	@Test
	public void testChoose() {
		int MX = 3000;
		Random rnd = new Random(123490812048L);
		for (int o = 0; o < 50; o++) {
			int primeMod = 1;
			while(!BigInteger.valueOf(primeMod).isProbablePrime(100))primeMod = rnd.nextInt(MX);
			int mx = rnd.nextInt(primeMod-1)+1;
			CombinationSystem cs = new CombinationSystem(mx, primeMod);
			for (int q = 0; q < 50; q++) {
				int n = rnd.nextInt(mx);
				int k = rnd.nextInt(n+1);
				BigInteger exp = BigInteger.ONE;
				for (int l = 0; l < k; l++) {
					exp = exp.multiply(BigInteger.valueOf(n-l));
					exp = exp.divide(BigInteger.valueOf(l+1));
				}
				long expi = exp.mod(BigInteger.valueOf(primeMod)).intValue();
				long res = cs.choose(n, k);
				Assert.assertEquals(expi, res);
			}
		}
	}
	
}
