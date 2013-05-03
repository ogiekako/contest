package net.ogiekako.algorithm.graph.flow;

import java.util.Arrays;

public class Hungarian{
    /**
     * 2部グラフの最大重み完全マッチングを求める.
     * O(n^3).
     * from Spaghetti source. Thanks to maehara-san.
     * @param bipartiteGraph
     * @return
     */
	public static int hungarian(int[][] bipartiteGraph){// PKU2195
        int[][] g=bipartiteGraph;
		int n=g.length;
		int[] fx=new int[n],fy=new int[n];
		Arrays.fill(fx,Integer.MAX_VALUE/2);
		int[] x=new int[n],y=new int[n];
		Arrays.fill(x,-1);
		Arrays.fill(y,-1);
		for(int i=0;i<n;++i)for(int j=0;j<n;++j)fx[i]=Math.max(fx[i],g[i][j]);
		for(int i=0;i<n;){
			int[] t=new int[n],s=new int[n+1];
			Arrays.fill(t,-1);
			Arrays.fill(s,i);
			int q=0;
			for(int p=0;p<=q&&x[i]<0;++p)for(int k=s[p],j=0;j<n&&x[i]<0;++j)if(fx[k]+fy[j]==g[k][j]&&t[j]<0){
				s[++q]=y[j];
				t[j]=k;
				if(s[q]<0)for(p=j;p>=0;j=p){
					y[j]=k=t[j];
					p=x[k];
					x[k]=j;
				}
			}
			if(x[i]<0){
				int d=Integer.MAX_VALUE/2;
				for(int k=0;k<=q;++k)for(int j=0;j<n;++j)if(t[j]<0)d=Math.min(d,fx[s[k]]+fy[j]-g[s[k]][j]);
				for(int j=0;j<n;++j)fy[j]+=(t[j]<0?0:d);
				for(int k=0;k<=q;++k)fx[s[k]]-=d;
			}else ++i;
		}
		int ret=0;
		for(int i=0;i<n;++i)ret+=g[i][x[i]];
		return ret;
	}
	
	public static long hungarian(long[][] a){// PKU2195
		int n=a.length;
		long[] fx=new long[n],fy=new long[n];
		Arrays.fill(fx,Long.MAX_VALUE/2);
		int[] x=new int[n],y=new int[n];
		Arrays.fill(x,-1);
		Arrays.fill(y,-1);
		for(int i=0;i<n;++i)for(int j=0;j<n;++j)fx[i]=Math.max(fx[i],a[i][j]);
		for(int i=0;i<n;){
			int[] t=new int[n],s=new int[n+1];
			Arrays.fill(t,-1);
			Arrays.fill(s,i);
			int q=0;
			for(int p=0;p<=q&&x[i]<0;++p)for(int k=s[p],j=0;j<n&&x[i]<0;++j)if(fx[k]+fy[j]==a[k][j]&&t[j]<0){
				s[++q]=y[j];
				t[j]=k;
				if(s[q]<0)for(p=j;p>=0;j=p){
					y[j]=k=t[j];
					p=x[k];
					x[k]=j;
				}
			}
			if(x[i]<0){
				long d=Long.MAX_VALUE/2;
				for(int k=0;k<=q;++k)for(int j=0;j<n;++j)if(t[j]<0)d=Math.min(d,fx[s[k]]+fy[j]-a[s[k]][j]);
				for(int j=0;j<n;++j)fy[j]+=(t[j]<0?0:d);
				for(int k=0;k<=q;++k)fx[s[k]]-=d;
			}else ++i;
		}
		long ret=0;
		for(int i=0;i<n;++i)ret+=a[i][x[i]];
		return ret;
	}
}
