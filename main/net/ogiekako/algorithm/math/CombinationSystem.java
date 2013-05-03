package net.ogiekako.algorithm.math;

public class CombinationSystem {
	public long[] facts;
	public long[] ifacts;
	private int primeMod;
	public CombinationSystem(int mx,int primeMod) {
		if(mx>=primeMod)throw new IllegalArgumentException();
		this.primeMod = primeMod;
		facts=new long[mx+1];
		ifacts=new long[mx+1];
		for (int i = 0; i < mx+1; i++) {
			facts[i] = (i==0 ? 1 : facts[i-1] * i) % primeMod;
		}
		for (int i = mx; i >= 0; i--) {
			ifacts[i] = i==mx ? pow(facts[i],primeMod-2,primeMod) : ifacts[i+1] * (i+1) % primeMod;
		}
	}
	private long pow(long n, int p, int primeMod) {
		if(n>=primeMod)n %= primeMod;
		long res = 1;
		for(;p>0;p>>=1,n = n*n % primeMod){
			if((p&1)==1)res = res * n % primeMod;
		}
		return res;
	}
	public long choose(int n,int k){
		return facts[n] * ifacts[k] % primeMod * ifacts[n-k] % primeMod;
	}
}
