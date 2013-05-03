package net.ogiekako.algorithm.graph.denseGraph;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;
import net.ogiekako.algorithm.graph.flow.Dinic;
import net.ogiekako.algorithm.graph.denseGraph.GomoryHuTree.V;

public class GomoryHuTreeTest{
	
	@Test
	public void testCutTree(){
		Random random = new Random(120981029830L);
		int n = 100;
		GomoryHuTree instance = new GomoryHuTree();
		int m = 1000;
		int[][] cap = new int[n][n];
		int[] a=new int[m];
		int[] b=new int[m];
		for(int i=0;i<m;){
			a[i]=random.nextInt(n);
			b[i]=random.nextInt(n);
			if(a[i]==b[i] || cap[a[i]][b[i]]>0)continue;
			cap[b[i]][a[i]] = cap[a[i]][b[i]]=random.nextInt(1000)+1;
			i++;
		}
		int[][] flow = new int[n][n];
		for(int i=0;i<n;i++)for(int j=0;j<n;j++){
			Dinic din = new Dinic(n,m*2);
			for(int k=0;k<m;k++){
				din.make(a[k],b[k],cap[a[k]][b[k]]);
				din.make(b[k],a[k],cap[b[k]][a[k]]);
			}
			flow[i][j] = din.maxFlow(i,j);
			if(flow[i][j]==Integer.MAX_VALUE)flow[i][j] = instance.INF;
		}
		V[] vs = instance.cutTree(cap);
		int[][] result = new int[n][n];
		for(int i=0;i<n;i++)for(int j=0;j<n;j++)result[i][j] = instance.maxFlow(vs,i,j);
		for(int i=0;i<n;i++)assertArrayEquals(flow[i],result[i]);	
	}
}
