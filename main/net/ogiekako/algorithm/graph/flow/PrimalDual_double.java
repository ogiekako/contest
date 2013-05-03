package net.ogiekako.algorithm.graph.flow;
import java.util.*;
import static java.lang.Math.*;
public class PrimalDual_double{
	static final double INF=1L<<61;
	int n;
	V[] vs;
	V s,t;
	
	PrimalDual_double(int nn){
		n=nn;
		vs=new V[n];
		for(int i=0;i<n;i++)
			vs[i]=new V(i);
	}
	
	void make(int from,int to,int cap,double cost){
		E e=new E(vs[to],cap,cost);
		E r=new E(vs[from],0,-cost);
		e.rev=r;
		r.rev=e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}
	
	double minCostFlow(int from,int to,int flow){
		s=vs[from];
		t=vs[to];
		double cost=0;
		while(flow>0){
			for(V v:vs)
				v.cost=INF;
			Queue<E> q=new PriorityQueue<E>();
			s.cost=0;
			s.bef=null;
			t.bef=null;
			q.add(new E(s,0,0));
			while(!q.isEmpty()){
				V v=q.poll().to;
				if(v==t) break;
				for(E e:v.es){
					if(e.cap>0&&e.to.cost>e.cost+v.cost){
						e.to.cost=e.cost+v.cost;
						e.to.bef=e;
						q.add(new E(e.to,0,e.to.cost));
					}
				}
			}
			if(t.bef==null) return -1;
			double min=flow;
			for(E e=t.bef;e!=null;e=e.rev.to.bef){
				min=min(min,e.cap);
			}
			for(E e=t.bef;e!=null;e=e.rev.to.bef){
				e.cap-=min;
				cost+=e.cost*min;
				e.rev.cap+=min;
			}
			flow-=min;
		}
		return cost;
	}
	class V{
		int id;
		ArrayList<E> es=new ArrayList<E>();
		E bef;
		double cost;
		
		V(int id){
			this.id=id;
		}
		
	}

	class E implements Comparable<E>{
		E rev;
		V to;
		int cap;
		double cost;
		
		E(V to,int cap,double cost){
			this.to=to;
			this.cap=cap;
			this.cost=cost;
		}
		
		public int compareTo(E o){
			return Double.compare(cost,o.cost);
		}
	}
}