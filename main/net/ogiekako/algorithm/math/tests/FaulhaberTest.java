package net.ogiekako.algorithm.math.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import net.ogiekako.algorithm.math.Faulhaber;

public class FaulhaberTest {

	@Test
	public void testS() {
		Faulhaber ff = new Faulhaber();
		M = ff.M;
		Random r = new Random(109830218L);
		for (int i = 0; i < 1000; i++){
			int n = r.nextInt(10000);
			int k = r.nextInt(2000);
			int exp = SStupid(n,k);
			int res = ff.S(n, k);
			assertEquals(exp, res);
		}
		for (int n = 0; n <= 10; n++)for (int k = 0; k <= 10; k++){
			assertEquals(SStupid(n, k), ff.S(n, k));
		}
	}
	
	int M;
	private int SStupid(int n, int k) {
		int res = 0;
		for (int i = 0; i < n+1; i++){
			res += pow(i,k);
			if(res>M)res-=M;
		}
		return res;
	}
	private long pow(long n, int k) {
		if(k==0)return 1;
		long n2 = pow(n,k>>1);
		return (k&1)==0 ? n2*n2%M : n2*n2%M*n%M;
	}

}
