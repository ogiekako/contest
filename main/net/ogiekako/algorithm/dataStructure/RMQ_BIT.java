package net.ogiekako.algorithm.dataStructure;
import java.util.Arrays;

public class RMQ_BIT{
	long initValue;
	long[] bit;
	public RMQ_BIT(int n){
        this(n,0);
	}
    public RMQ_BIT(int n,long initValue){
        this.initValue = initValue;
        bit = new long[n+1];
        Arrays.fill(bit,initValue);
    }
	/**
	 * 最小値の更新.
	 * 増やすことはできないので注意.
	 * @param i
	 * @param v
	 */
	public void update(int i,long v) {
		for(int j=i+1;j<bit.length;j+=j&-j) {
			bit[j] = Math.min(bit[j],v);
		}
	}
	
	public long getMin(int right) {// [0,right)
		long res = initValue;
		for(int i=right;i>0;i-=i&-i) {
			res = Math.min(res,bit[i]);
		}
		return res;
	}
}