package net.ogiekako.algorithm.dataStructure;

class RMQ2D_BIT{
	long INF = 1L<<60;
	RMQ_BIT[] rmqs;
	RMQ2D_BIT(int h,int w){
		rmqs = new RMQ_BIT[h+1];
		for(int i=0;i<h+1;i++)rmqs[i] = new RMQ_BIT(w+1);
	}
	/**
	 * 最小値の更新.
	 * 増やすことはできないので注意.
	 * @param x
	 * @param y
	 * @param v
	 */
	void update(int x,int y,long v) {
		for(int j=x+1;j<rmqs.length;j+=j&-j) {
			rmqs[j].update(y,v);
		}
	}
	long getMin(int x,int y) {// [0,x) * [0,y)
		long res = INF;
		for(int i=x;i>0;i-=i&-i) {
			res = Math.min(res,rmqs[i].getMin(y));
		}
		return res;
	}
}