package net.ogiekako.algorithm.dataStructure;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;


public class RMQTest{
	@Test public void queryTest() {
		int n=30;
		RMQ rmq=new RMQ(n);
		int[] is=new int[n];
		for(int i=0;i<n;i++)rmq.update(i,0);
		Random r = new Random(1920129830L);
		for (int i = 0; i < 10000; i++) {
			int q = r.nextInt(2);
			if(q==0){
				int j = r.nextInt(n);
				int v = r.nextInt(100);
				rmq.update(j, v);
				is[j] = v;
			}else{
				int a = r.nextInt(n);
				int b = r.nextInt(n+1);
				int v = rmq.query(a, b);
				int exp = Integer.MAX_VALUE;
				for (int j = a; j < b; j++) {
					exp = Math.min(exp,is[j]);
				}
//				System.out.println(exp);
				assertEquals(exp,v);
			}
		}
	}
}
