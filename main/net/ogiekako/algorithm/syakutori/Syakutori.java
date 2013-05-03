package net.ogiekako.algorithm.syakutori;

import net.ogiekako.algorithm.utils.Pair;

public class Syakutori{
	/**
	 * a[i] && b[j] なる abs(i-j) の最小値を尺取り法により求める.
	 * 普通は,a.length == b.length で使うはずだが, そうでなくてもOK.
	 * O(n)
	 * @param a
	 * @param b
	 * @return a[i] && b[j] なる abs(i-j) の最小値. なければ Integer.MAX_VALUE
	 */
	int minDist(boolean[] a,boolean[] b){
		if(a.length != b.length)throw new RuntimeException();
		int i=0,j=0;
		int res = Integer.MAX_VALUE;
		while(i<a.length && !a[i])i++;
		for(;;){
			if(i==a.length)break;
			while(j<=i || j<b.length && !b[j]){
				if(b[j])res=Math.min(res,i-j);
				j++;
			}
			if(j==b.length)break;
			while(i<=j || i<a.length && !a[i]){
				if(a[i])res=Math.min(res,j-i);
				i++;
			}
		}
		return res;
	}

    /*
     * | A[i] - B[j] | が最小となる (i,j) を返す.
     */
    public static Pair<Integer, Integer> nearest(long[] sortedA, long[] sortedB,int fromA,int fromB) {
        long[] A = sortedA, B = sortedB;
        long maxValue = Long.MAX_VALUE;
        int ri=-1,rj=-1;
        for(int i=fromA,j=fromB;i<A.length && j<B.length;){
            long value = Math.abs(A[i]-B[j]);
            if(maxValue > value){
                maxValue = value;
                ri=i;rj=j;
            }
            if(A[i] < B[j]){
                i++;
            }else{
                j++;
            }
        }
        return new Pair<Integer, Integer>(ri,rj);
    }
}
