package net.ogiekako.algorithm.graph.denseGraph;

import static java.lang.Math.min;
import static java.util.Arrays.fill;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GomoryHuTree{
	int INF = 1<<28;
	/**
	 * 無向グラフのカット木をつくる.
	 * 与えるcapacity は対称でなければならないことに注意.
	 * @param capacity
	 * @return
	 */
	V[] cutTree(int[][] capacity){
		int n = capacity.length;
		int[][] flow=new int[n][n];
		int[] p=new int[n],prev=new int[n];
		int[] w=new int[n];
		for(int s=1;s<n;s++){
			int t=p[s];
			for(int i=0;i<n;i++)for(int j=0;j<n;j++)flow[i][j]=0;
			int total=0;
			for(;;){
				Queue<Integer> que = new LinkedList<Integer>();
				que.offer(s);
				fill(prev,-1);
				prev[s]=s;
				while(!que.isEmpty() && prev[t]<0){
					int u = que.poll();
					for(int i=0;i<n;i++)if(prev[i]<0 && capacity[u][i]-flow[u][i]>0){
						prev[i] = u;
						que.offer(i);
					}
				}
				if(prev[t]<0)break;
				int inc = INF;
				for(int j=t;prev[j]!=j;j=prev[j])
					inc = min(inc,capacity[prev[j]][j] - flow[prev[j]][j]);
				for(int j=t;prev[j]!=j;j=prev[j]){
					flow[prev[j]][j] += inc;
					flow[j][prev[j]] -= inc;					
				}
				total += inc;
			}
			w[s] = total;
			for(int u=0;u<n;u++)if(u!=s && prev[u]!=-1 && p[u]==t)
				p[u] = s;
			if(prev[p[t]]!=-1){
				p[s]=p[t];
				p[t]=s;
				w[s]=w[t];
				w[t]=total;
			}
		}
		V[] vs = new V[n];
		for(int i=0;i<n;i++)vs[i]=new V(i);
		for(int s=0;s<n;s++)if(s!=p[s]){
			vs[s].es.add(new E(vs[p[s]],w[s]));
			vs[p[s]].es.add(new E(vs[s],w[s]));
		}
		return vs;
	}
	int maxFlow(V[] vs,int u,int t){
		return maxFlow(vs,u,t,-1,INF);
	}
	
	private int maxFlow(V[] vs,int u,int t,int p,int w){
		if(u==t)return w;
		int d = INF;
		for(E e:vs[u].es)if(e.to.id != p){
			d = min(d,maxFlow(vs,e.to.id,t,u,min(w,e.w)));
		}
		return d;
	}
	
	class E{
		V to;
		int w;
		public E(V to,int w){
			super();
			this.to=to;
			this.w=w;
		}
		
	}
	class V{
		int id;
		ArrayList<E> es=new ArrayList<E>();
		V(int id){
			this.id=id;
		}
	}
}
