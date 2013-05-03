package net.ogiekako.algorithm.dataStructure;
public class BIT2D{
	BIT[] bits;
	BIT2D(int h,int w){
		bits=new BIT[h+1];
		for(int i=0;i<h+1;i++)bits[i]=new BIT(w);
	}
	long sum(int x1,int y1,int x2,int y2) {// [x1,x2) * [y1,y2)
		if(x1>0)return sum(0,y1,x2,y2) - sum(0,y1,x1,y2);
		if(y1>0)return sum(x1,0,x2,y2) - sum(x1,0,x2,y1);
		long res = 0;
		for(int i=x2;i>0;i-=i&-i){
			res+=bits[i].sum(y1,y2);
		}
		return res;
	}
	
	void add(int x,int y,long val){
		for(int i=x+1;i<bits.length;i+=i&-i){
			bits[i].add(y,val);
		}
	}
	void set(int x,int y,long val){
		val-=sum(x,y,x+1,y+1);
		add(x,y,val);
	}
}
