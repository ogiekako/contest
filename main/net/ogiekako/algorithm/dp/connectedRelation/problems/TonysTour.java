package net.ogiekako.algorithm.dp.connectedRelation.problems;
import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.util.*;
import java.util.Map.Entry;
/**
 * PKU1739 Tony's Tour
 * 
 * 左下から出発し,右下に到着する全てのマスを通るパスの数を求めよ.
 * 
 */
public class TonysTour {
	public static void main(String[]args){
		new TonysTour().run();
	}

	private void run() {
		Scanner sc = new Scanner(System.in);
		for(;;){
			h=sc.nextInt();w=sc.nextInt();
			if((h|w)==0)return;
			cs=new char[h][w];
			for(int i=0;i<h;i++)cs[i]=sc.next().toCharArray();
			long res = solve();
			System.out.println(res);
		}
	}
	int h,w;
	char[][]cs;
	@SuppressWarnings("unchecked")
	private long solve() {
		int[] is=new int[w];
		fill(is,-1);
		E init = new E(is,new boolean[w],new boolean[w]);
		HashMap<E, Long>[] dp=new HashMap[2];
		for(int i=0;i<2;i++)dp[i]=new HashMap<E, Long>();
		int cur=0,nxt=1;
		dp[cur].put(init,1L);
		for(int i=0;i<h;i++)for(int j=0;j<w;j++){
			dp[nxt].clear();
			for(Entry<E, Long> entry : dp[cur].entrySet()){
				E e = entry.getKey();
				long val = entry.getValue();
				if(cs[i][j]=='#'){
					E ne = e.nxt(i, j, false, false, false);
					if(ne!=null){
						if(!dp[nxt].containsKey(ne))dp[nxt].put(ne, 0L);
						dp[nxt].put(ne, dp[nxt].get(ne)+val);
					}
				}else{
					for(int k=0;k<2;k++)for(int l=0;l<2;l++){
						E ne = e.nxt(i, j, true, k==0, l==0);
						if(ne!=null){
							if(!dp[nxt].containsKey(ne))dp[nxt].put(ne, 0L);
							dp[nxt].put(ne, dp[nxt].get(ne)+val);
						}
					}
				}
			}
			int tmp=cur;cur=nxt;nxt=tmp;
		}
		for(Entry<E, Long> entry:dp[cur].entrySet()){
			E e = entry.getKey();
			is = e.is;
			boolean ok=true;
			if(is[0]!=0 || is[is.length-1]!=0)ok=false;
			if(!e.downs[0] || !e.downs[is.length-1])ok=false;
			for(int i=1;i<is.length-1;i++)if(is[i]!=-1)ok=false;
			if(ok){
				return entry.getValue();
			}
		}
		return 0;
	}
	
	class E{
		int[] is;
		boolean[] rights;
		boolean[] downs;
		public E(int[] is, boolean[] rights, boolean[] downs) {
			super();
			this.is = is;
			this.rights = rights;
			this.downs = downs;
		}

		E nxt(int x,int y,boolean put,boolean right,boolean down){
			int[] nis=is.clone();
			boolean[] nrights = rights.clone();
			boolean[] ndowns = downs.clone();
			if(!put){
				if(y>0 && is[y-1]>=0 && rights[y-1])return null;
				if(x>0 && is[y]>=0 && downs[y])return null;
				nis[y] = -1;
				nrights[y]=false;
				ndowns[y]=false;
			}else{
				if(y==w-1 && right)return null;
				int deg = 0;
				int a=-1;
				int b=-1;
				if(y>0 && is[y-1]>=0 && rights[y-1]){
					deg++;
					a=is[y-1];
					nrights[y-1]=false;
				}
				if(x>0 && is[y]>=0 && downs[y]){
					deg++;
					b=is[y];
				}
				if(right)deg++;
				if(down)deg++;
				if(deg!=2)return null;
				nrights[y]=right;
				ndowns[y]=down;
				if(a>=0 && b>=0){
					if(a==b)return null;
					nis[y]=a;
					for(int i=0;i<nis.length;i++)if(nis[i]==b)nis[i]=a;
				}else if(a>=0 || b>=0)nis[y]=max(a,b);
				else nis[y]=nis.length;
			}
			for(int i=0;i<nis.length;i++){
				if(!nrights[i] && !ndowns[i])nis[i]=-1;
			}
			norm(nis);
			return new E(nis, nrights, ndowns);
		}
		
		void norm(int[] is){
			int m=0;
			for(int i:is)m=max(m,i);
			m++;
			int[]js=new int[m];
			fill(js,-1);
			m=0;
			for(int i=0;i<is.length;i++){
				if(is[i]>=0){
					if(js[is[i]]==-1)js[is[i]]=m++;
					is[i]=js[is[i]];
				}
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(downs);
			result = prime * result + Arrays.hashCode(is);
			result = prime * result + Arrays.hashCode(rights);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			E other = (E) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Arrays.equals(downs, other.downs))
				return false;
			if (!Arrays.equals(is, other.is))
				return false;
			if (!Arrays.equals(rights, other.rights))
				return false;
			return true;
		}

		private TonysTour getOuterType() {
			return TonysTour.this;
		}

		@Override
		public String toString() {
			return "E [downs=" + Arrays.toString(downs) + ", is=" + Arrays.toString(is) + ", rights="
					+ Arrays.toString(rights) + "]";
		}
	}
}
