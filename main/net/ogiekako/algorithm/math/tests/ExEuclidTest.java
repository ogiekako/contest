package net.ogiekako.algorithm.math.tests;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import net.ogiekako.algorithm.math.ExEuclid;

public class ExEuclidTest {

	@Test
	public void testExGcd() {
		int a = 4, b = 6;
		int[] xy = ExEuclid.exGcd(a, b);
		assertEquals(2, a*xy[0] + b*xy[1]);

		a = 112098; b = 7192873;
		xy = ExEuclid.exGcd(a, b);
		assertEquals(1, a*xy[0] + b*xy[1]);
	}
	@Test
	public void testExGcd_Long() {
		long a = 419827419279172L, b = 9812741982749187L;
		long[] xy = ExEuclid.exGcd(a, b);
		assertEquals(1, a*xy[0] + b*xy[1]);
		
		a = 4198274192791721202L; b = 9129812741982749184L;
		xy = ExEuclid.exGcd(a, b);
		long g = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
		assertEquals(g, a*xy[0] + b*xy[1]);
	}

}
