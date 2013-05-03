package net.ogiekako.algorithm.graph.problems;

public class PathWidth {
	/**
	 * グラフのpathwidth を,O(n 2^n) で求める.
	 * n=20 で,0.13s くらい.
	 * @param undirectedGraph
	 * @return
	 */
	public static int pathWidth(boolean[][] undirectedGraph){
		boolean[][] g=undirectedGraph;
		int n = g.length;
		int[] nei=new int[1<<n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(g[i][j]){
					nei[1<<i] |= 1<<j;
				}
			}
		}
		int[] dp=new int[1<<n];
		dp[0]=0;
		for (int i = 1; i < 1<<n; i++) {
			int k = Integer.lowestOneBit(i);
			nei[i] = (nei[k] | nei[i ^ k]) & ~i;
			int num = Integer.bitCount(nei[i])+1;
			dp[i] = Integer.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				if((i>>j&1)==1){
					dp[i] = Math.min(dp[i],Math.max(dp[i&~(1<<j)],num));
				}
			}
		}
		return dp[(1<<n)-1]-1;
	}
}
