package net.ogiekako.algorithm.dataStructure;

public class RangeMaximumQuery{
	int[] v;
	
	public RangeMaximumQuery(int size){
		v=new int[size];
	}
	
	public void update(int at,int what){
		if(at<0) at=0;
		while(at<v.length){
			v[at]=Math.max(v[at],what);
			at=at|(at+1);
		}
	}
	
	public int getMax(int right){// [0,right]
		int res=0;
		while(right>=0){
			res=Math.max(res,v[right]);
			right=(right&(right+1))-1;
		}
		return res;
	}
}
