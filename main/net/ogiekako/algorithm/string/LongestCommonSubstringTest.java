package net.ogiekako.algorithm.string;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

public class LongestCommonSubstringTest{
	
	@Test @Ignore
	public final void testLongestCommonSubstring(){
		int n=100000;
		Random r = new Random(120219210948205378L);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<n;i++)sb.append(r.nextInt(3));
		String a = sb.toString();
		sb.setLength(0);
		for(int i=0;i<n;i++)sb.append(r.nextInt(3));
		String b = sb.toString();
		String lcs = LongestCommonSubstring.longestCommonSubstring(a,b);
		
	}
	
}
