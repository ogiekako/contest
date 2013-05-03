package net.ogiekako.algorithm.string;

public class ZAlgorithm {
	/**
	 * z[i] は, s[0..], s[i..] の最長共通prefix の長さを表す.
	 * ただし,z[0] = 0.
     *
	 * @param s
	 * @return
	 */
	public static int[] zAlgorithm(CharSequence s){
		int n=s.length();
		int[]z=new int[n];
		z[0]=0;
		int left=0,right=0;
		/* left : z[left] が(left<iで)最大なるleft. z[left] = right - left.
		right-leftまでの長さはすでに計算ずみなので, z[i+1] を計算したくて, i + z[i] < rightなら, すでに計算済みである点がポイント.
		*/
		for (int i = 1; i < s.length(); i++) {
			if(i>right){
				left=i;right=i;
				while(right<n && s.charAt(right-left)==s.charAt(right))right++;
				z[i]=right-left;
			}else if(z[i-left] < right-i){
				z[i]=z[i-left];
			}else{
				left=i;
				while(right<n && s.charAt(right-left)==s.charAt(right))right++;
				z[i]=right-left;
			}
		}
		return z;
	}
}
