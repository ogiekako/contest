package net.ogiekako.algorithm.graph.flow;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

import java.util.LinkedList;
import java.util.Queue;
//O(V^2 E)
public class Dinic {
	int[] cap,flow,to,prev,last,level,used;
	int E = 0;

	/**
	 * @param n
	 *            - num node
	 * @param maxE
	 *            - num edge
	 */
	public Dinic(int n, int maxE) {
		cap = new int[maxE * 2];
		flow = new int[maxE * 2];
		to = new int[maxE * 2];
		prev = new int[maxE * 2];
		last = new int[n];
		level = new int[n];
		used = new int[n];
		fill(prev, -1);
		fill(last, -1);
	}
	
	public void make(int s, int t, int cap) {
		this.cap[E] = cap;
		to[E]=t;
		prev[E] = last[s];
		last[s] = E;
		E++;
		to[E]=s;
		prev[E] = last[t];
		last[t] = E;
		E++;
	}

	public int maxFlow(int s, int t, int limit) {
		int flow=0;
		while (limit > 0 && bfs(s, t)) {
			System.arraycopy(last, 0, used, 0, last.length);
			int tmp;
			while ((tmp = dfs(s, t, limit)) > 0) {
				limit -= tmp;
				flow += tmp;
			}
		}
		return flow;
	}

	private int dfs(int s, int t, int limit) {
		if (s == t) return limit;
		for (int i = used[s]; i >= 0; i = used[s] = prev[i])if (level[s] < level[to[i]] && cap[i] > flow[i]) {
			int tmp = dfs(to[i], t, min(cap[i]-flow[i],limit));
			if (tmp > 0) {
				flow[i] += tmp;
				flow[i ^ 1] -= tmp;
				return tmp;
			}
		}
		return 0;
	}

	private boolean bfs(int s, int t) {
		fill(level, -1);
		Queue<Integer> que = new LinkedList<Integer>();
		que.offer(s);
		level[s] = 0;
		while (!que.isEmpty()) {
			s = que.poll();
			if (s == t)
				return true;
			for (int i = last[s]; i >= 0; i = prev[i]){
				if (level[to[i]] == -1 && cap[i] > flow[i]) {
					level[to[i]] = level[s] + 1;
					que.offer(to[i]);
				}
			}
		}
		return false;
	}

	public int maxFlow(int s, int t) {
		return maxFlow(s, t, Integer.MAX_VALUE);
	}

	// TCO09R2 1000
	boolean decrease(int s, int t, int e, int dec) {
		e*=2;
		assert to[e]==t;
		if (cap[e] - flow[e] >= dec) {
			cap[e] -= dec;
			return true;
		}
		cap[e] -= dec;
		flow[e] -= dec;
		flow[e ^ 1] += dec;
		int tmp = maxFlow(s, t, dec);
		if (tmp == dec)
			return true;
		cap[e] += dec;
		flow[e] += dec-tmp;
		flow[e ^ 1] -= dec-tmp;
		return false;
	}
}