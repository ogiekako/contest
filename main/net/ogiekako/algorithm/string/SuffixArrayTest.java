/**
 * 
 */
package net.ogiekako.algorithm.string;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author ogiekako
 *
 */
public class SuffixArrayTest{
	SuffixArray instance = new SuffixArray();
	
	@Test(timeout = 2000)
	public final void testSuffixArray_log(){
		int n=800000;
		StringBuilder b = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(2)+'a'));
		b.append('$');
		String s = b.toString();

		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray_log(s);
		long time = System.currentTimeMillis()-start;
		System.out.println(time);
		assertTrue(time<2000);
		for(int i=0;i<n-1;i++) {
			assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
		}
	}
	
	/**
	 * Test method for {@link net.ogiekako.algorithm.string.SuffixArray#suffixArray(String)}.
	 */
	@Test
	public final void testSuffixArray_log2(){
		int n=10;
		StringBuilder b = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(2)+'a'));
		b.append(' ');
		String s = b.toString();

		s = "abdacadabra$";
		n = s.length();

		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray_log2(s);
		long time = System.currentTimeMillis()-start;
		assertTrue(time<1000);
		for(int i=0;i<n-1;i++) {
			assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
		}
	}

	@Test(timeout = 2000)
	public final void testSuffixArray_log2_2(){
		int n=300000;
		StringBuilder b = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(2)+'a'));
		b.append('$');
		String s = b.toString();

		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray_log2(s);
		long time = System.currentTimeMillis()-start;
		System.out.println(time);
		assertTrue(time<2000);
		for(int i=0;i<n-1;i++) {
			assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
		}
	}

	@Test
	public final void testSuffixArray_log2_3(){
		for(int o=0;o<100;o++){
			int n=1000;
			StringBuilder b = new StringBuilder();
			Random r = new Random(10928091284L);
			for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(2)+'a'));
			b.append('$');
			String s = b.toString();

			long start = System.currentTimeMillis();
			int[] SA = instance.suffixArray_log2(s);
			long time = System.currentTimeMillis()-start;
//			System.out.println(time);
			assertTrue(time<1000);
			for(int i=0;i<n-1;i++) {
				assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
			}
		}
	}

	/**
	 * Test method for {@link net.ogiekako.algorithm.string.SuffixArray#suffixArray(String)}.
	 */
	@Test
	public final void testSuffixArray(){
		int n=1000000;
		StringBuilder b = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(2)+'a'));
		b.append(' ');
		String s = b.toString();
		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray(s);
		long time = System.currentTimeMillis()-start;
		assertTrue(time<1000);
		for(int i=0;i<n-1;i++) {
			assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
		}
	}

	@Test
	public final void testSuffixArray2(){
		int n=1000000;
		StringBuilder b = new StringBuilder();
		for(int i=0;i<n-1;i++)b.append('a');
		b.append(' ');
		String s = b.toString();
		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray(s);
		long time = System.currentTimeMillis()-start;
		assertTrue(time<1000);
		int[] exp=new int[n];
		for(int i=0;i<n;i++)exp[i]=n-1-i;
		assertArrayEquals(exp,SA);
	}

	/**
	 * Test method for {@link net.ogiekako.algorithm.string.SuffixArray#suffixArray(String)}.
	 */
	@Test
	public final void testSuffixArray3(){
		int n=1000000;
		StringBuilder b = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)b.append((char)(r.nextInt(26)+'a'));
		b.append(' ');
		String s = b.toString();
		long start = System.currentTimeMillis();
		int[] SA = instance.suffixArray(s);
		long time = System.currentTimeMillis()-start;
		assertTrue(time<1000);
		for(int i=0;i<n-1;i++) {
			assertTrue(s.substring(SA[i]).compareTo(s.substring(SA[i+1])) < 0);
		}
	}
	/**
	 * Test method for {@link net.ogiekako.algorithm.string.SuffixArray#calcLCP(int[], String)}.
	 */
	@Test
	public final void testCalcLCP(){
		int n=1000000;
		StringBuilder sb = new StringBuilder();
		Random r = new Random(10928091284L);
		for(int i=0;i<n-1;i++)sb.append((char)(r.nextInt(2)+'a'));
		sb.append(' ');
		String s = sb.toString();
		int[] SA = instance.suffixArray(s);
		int[] LCP = instance.calcLCP(SA,s);
		for(int i=0;i<n-1;i++) {
			String a=s.substring(SA[i]);
			String b=s.substring(SA[i+1]);
			assertEquals(a.substring(0,LCP[i]),b.substring(0,LCP[i]));
			assertNotSame(a.substring(0,LCP[i]+1),b.substring(0,LCP[i]+1));
		}
	}
}
