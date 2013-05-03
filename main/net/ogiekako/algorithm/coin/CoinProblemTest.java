package net.ogiekako.algorithm.coin;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class CoinProblemTest{
	
	@Test
	public final void testMinimumCounterExample(){
		Random r = new Random(10210982L);
		for(int i=0;i<300;i++) {
			int n=r.nextInt(50)+1;
			long[] c=new long[n];
			for(int j=0;j<n;j++)c[n-1-j]=j==0 ? 1 : c[n-j] + r.nextInt(50) + 1;
			long w = minimumCounterExampleStupid(c);
			long w2 = CoinProblem.minimumCounterExample(c);
			assertEquals(w,w2);
		}
	}

	private long minimumCounterExampleStupid(long[] c){
		int n=c.length;
		for(int w=0;w<=c[0]+c[0];w++) {
			long g=0;
			for(long tmp:CoinProblem.greedy(c,w))g+=tmp;
			long[] dp=new long[w+1];
			Arrays.fill(dp,Long.MAX_VALUE);
			dp[0]=0;
			for(int i=0;i<n;i++)for(int j=0;j+c[i]<w+1;j++)if(dp[j]<Long.MAX_VALUE) {
				dp[j+(int)c[i]] = Math.min(dp[j+(int)c[i]],dp[j]+1);
			}
			if(dp[w]!=g)return w;
		}
		return -1;
	}
	
	
}
