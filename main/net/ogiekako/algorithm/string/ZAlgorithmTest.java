package net.ogiekako.algorithm.string;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class ZAlgorithmTest {
	
	@Test
	public void testZAlgorithm() {
		Random rnd = new Random(12092348L);
		for (int i = 0; i < 500; i++) {
			StringBuilder b=new StringBuilder();
			for (int j = 0; j < 5000; j++) {
				b.append((char)(rnd.nextInt(3)+'A'));
			}
			String s=b.toString();
			int[] z = ZAlgorithm.zAlgorithm(s);
			int[] exp = zAlgorithmStupid(s);
			Assert.assertArrayEquals(exp, z);
		}
	}

	private int[] zAlgorithmStupid(String s) {
		int[]z=new int[s.length()];
		z[0]=0;
		for (int i = 1; i < z.length; i++) {
			int j=0;
			while(i+j<z.length && s.charAt(j)==s.charAt(i+j))j++;
			z[i]=j;
		}
		return z;
	}
	
}
