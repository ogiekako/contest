package net.ogiekako.algorithm.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * 三角形関係のメソッドはここに入れる.
 * 大きくなってきたら整理.
 */
public class IntegralTriangle {
	public final long a,b,c;
	private IntegralTriangle(long a,long b,long c) {
		this.a=a;this.b=b;this.c=c;
	}
	@Override
	public String toString() {
		return "IntegralTriangle [a=" + a + ", b=" + b + ", c=" + c + "]";
	}
	
	
	/** 
	 * Euclid's formula  http://en.wikipedia.org/wiki/Pythagorean_triple#Generating_a_triple
	 * により,c <= N, a*a + b*b = c*c, gcd(a,b,c) = 1 (必然的に,おのおの互いに素.)
	 * を満たす 3つ組の配列を返す.
	 * a,bの大きさの関係は,どちらもありうる.
	 * a,c は奇数 bは偶数.
	 * c の素因数は,すべて4を法として1と合同.
	 * 
	 * O(N).
	 * N = 1000万 で, 1591579 パタン. 0.417s
	 * 
	 * @param upTo
	 * @return
	 */
	public static IntegralTriangle[] generatePrimitivePythagoreans(int upTo){
        int C = upTo;
		List<IntegralTriangle> list = new ArrayList<IntegralTriangle>();
		for(int n=1;2*n*n <= C;n++){
			for(int m=n+1;n*n + m*m <= C;m+=2){
				if(MathUtils.gcd(n, m)==1){
					long a = m*m - n*n;
					long b = 2*m*n;
					long c = m*m + n*n;
					list.add(new IntegralTriangle(a, b, c));
				}
			}
		}
		return list.toArray(new IntegralTriangle[0]);
	}
	
	/**
	 * 外接円の半径Rが整数で, R<=N, gcd(a,b,c,R) = 1, a<=b<=c
	 * を満たすような3角形の配列を返す.
	 * 
	 * 中心から辺に垂線を引き,頂点に線を引くことで,6つの3角形ができるが,
	 * これらはすべて辺の長さが整数となる.
	 * よって,R は直角三角形の斜辺となる.
	 * また,a,b,cは2の倍数で,a/2,b/2,c/2 は 直角三角形のいずれかの辺となる.
	 * これを利用して,Rをまず固定し,斜辺をRとするピタゴラス3つ組 列挙により解を求めている.
	 * (候補になるa/2 の値は,必ずしも規約ピタゴラス数として表れるとは限らないことに注意.)
	 * 
	 * N = 10000000
	 * で, 19454898 パタン. 98s かかる. メモリは1G.
	 * Verified at Project Euler 373
	 * @param N
	 * @return
	 */
	public static IntegralTriangle[] integralCircleRadiusUpto(int N){
		List<IntegralTriangle> list = new ArrayList<IntegralTriangle>();
		IntegralTriangle[] ps = IntegralTriangle.generatePrimitivePythagoreans(N);
		int[] is=new int[N+1];
		for(IntegralTriangle p : ps){
			is[(int)p.c]++;
		}
		int numCand = 0;
		for(int i:is)if(i>0)numCand++;
		int[] Rs=new int[numCand];
		numCand=0;
		for (int i = 0; i < N+1; i++) {
			if(is[i]>0){
				is[i]=numCand;
				Rs[numCand++]=i;
			}
		}
		@SuppressWarnings("unchecked")
		Set<Long>[] candSet = new TreeSet[numCand];
		for (int i = 0; i < numCand; i++) {
			candSet[i] = new TreeSet<Long>();
		}
		for (IntegralTriangle p : ps) {
			int c = (int)p.c;
			int i = is[c];
			candSet[i].add(p.a);candSet[i].add(p.b);candSet[i].add(p.c);
			for(int k : Rs){
				if(c*k > N)break;
				i = is[c*k];
				candSet[i].add(p.a*k);candSet[i].add(p.b*k);candSet[i].add(p.c*k);
			}
		}
		ps=null;
		for (int R : Rs) {
			Set<Long> cand = candSet[is[R]];
			long[] ls = tols(cand.toArray(new Long[0]));
			for (int i = 0; i < ls.length; i++) {
				for (int j = i; j < ls.length; j++) {
					long a = ls[i], b = ls[j];
					long a2 = (long)Math.round(Math.sqrt((long)R*R-a*a));
					long b2 = (long)Math.round(Math.sqrt((long)R*R-b*b));
					double A = Math.atan2(a2, a);
					double B = Math.atan2(b2, b);
					double C = Math.PI - A - B;
					long c = (long)Math.round(Math.sin(C) * R);
					if(b<=c && candSet[is[R]].contains(c)){
						IntegralTriangle t = new IntegralTriangle(2*a, 2*b, 2*c);
						double r = t.circumRadius();
						if(-1e-8 < R-r && R-r < 1e-8){// 1e-9 だと誤差死.
							long gcd = MathUtils.gcd(t.a, MathUtils.gcd(t.b, MathUtils.gcd(t.c, R)));
							if(gcd==1){
								list.add(t);
							}
						}
					}
				}
			}
			candSet[is[R]] = null;
		}
		return list.toArray(new IntegralTriangle[0]);
	}
	
	private static long[] tols(Long[] Ls) {
		long[] ls=new long[Ls.length];
		for (int i = 0; i < ls.length; i++) {
			ls[i]=Ls[i];
		}
		return ls;
	}
	
	/**
	 * 外接円の半径
	 * @return
	 */
	public double circumRadius(){
		double S = size();
		return (double)a*b*c/(4*S);
	}
	
	/**
	 * 面積
	 * @return
	 */
	public double size(){
		double s=(a+b+c)/2.0;
		double SS = s*(s-a)*(s-b)*(s-c);
		double S = Math.sqrt(SS);
		return S;
	}

    /**
     * 一番長い辺 <= upTo で,
     * 三辺が, a,a,a+1 or a,a,a-1 なる,面積が整数の三角形 を返す.
     * O(sqrt(N)).
     *
     * OEIS
     *
     * Euler094.
     * @param upTo
     * @return
     */
    public static IntegralTriangle[] almostEquilateralTriangles(long upTo){
        long N = upTo;
        List<IntegralTriangle> list = new ArrayList<IntegralTriangle>();
        for(long m=2;m*m<=N;m++){
            for(int d=-1;d<=1;d+=2){
                long D = 3L*m*m - d;
                if(MathUtils.isSquare(D)){
                    long sq = MathUtils.sqrt(D);
                    long n = 2L*m - sq;
                    long a = m*m + n*n;
                    if(a <= N && a+d <= N){
                        list.add(new IntegralTriangle(a,a,a+d));
                    }
                }

                if((m*m-d)%3==0){
                    D = (m*m-d)/3;
                    if(MathUtils.isSquare(D)){
                        long n = MathUtils.sqrt(D);
                        long a = m*m + n*n;
                        if(a <= N && a+d <= N){
                            list.add(new IntegralTriangle(a,a,a+d));
                        }
                    }
                }
            }
        }
        return list.toArray(new IntegralTriangle[0]);
    }
}
