package net.ogiekako.algorithm.string;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LongestCommonSubstring{
	/**
	 * a,bの,一致する最長の部分文字列を返す.
	 *
	 * O(n)
	 * @param a - first string
	 * @param b - second string
	 * @return longest common substring of a and b.
	 */
	public static String longestCommonSubstring(String a,String b) {
		return longestCommonSubstring(new String[]{a, b}, 2);
	}

	/**
	 * @param ss
	 * @param K
	 * @return
	 */
	public static String longestCommonSubstring(String[] ss, int K){
		int n = ss.length;
		for(String s:ss)for(char c:s.toCharArray())if(c<=n-1)throw new IllegalStateException();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<n;i++) {
			sb.append(ss[i]);
			sb.append((char)(n-1-i));
		}
		String s = sb.toString();
		if(s.contains(""+(char)(n-1)))throw new IllegalStateException();
//		int[] SA = SuffixArray.suffixArray(s);
//		int[] lcp = SuffixArray.calcLCP(SA,s);
        throw new NotImplementedException();
	}
}
