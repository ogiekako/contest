package net.ogiekako.algorithm.dice;

import java.util.Arrays;

public class Dice{
	static final int TOP=0;
	static final int RIGHT=1;
	static final int FRONT=2;
	static final int BACK=3;
	static final int LEFT=4;
	static final int BOTTOM=5;
	int[] is;
//    public Dice(int[] is){
//   		this.is=is.clone();
//   	}
    public Dice(int... is){
   		this.is=is.clone();
   	}
	public boolean eq(Dice di) {// 100
		boolean res=false;
		for(int i=0;i<6;i++) {
			if((i&1)==0)top2right();
			else top2front();
			for(int j=0;j<4;j++) {
				front2right();
				if(Arrays.equals(is,di.is))res=true;
			}
		}
		return res;
	}
	void top2front() {
		roll(TOP,FRONT,BOTTOM,BACK);
	}
	void top2right() {
		roll(TOP,RIGHT,BOTTOM,LEFT);
	}
	void front2right() {
		roll(FRONT,RIGHT,BACK,LEFT);
	}
	private void roll(int a,int b,int c,int d){
		int tmp=is[d];
		is[d]=is[c];
		is[c]=is[b];
		is[b]=is[a];
		is[a]=tmp;
	}
    Dice[] allState(){
        Dice[] res = new Dice[24];
        for (int i = 0; i < 6; i++){
            if(i%2==0)top2front();
            else top2right();
            for (int j = 0; j < 4; j++){
                front2right();
                res[i*4+j] = new Dice(is);
            }
        }
        return res;
    }
}