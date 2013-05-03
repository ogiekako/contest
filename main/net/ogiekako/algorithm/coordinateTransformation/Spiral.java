package net.ogiekako.algorithm.coordinateTransformation;

/**
 * spiral is as following
 * 
 *   9...
 * 	 812
 *	 703
 *   654
 *  
 *     y ->
 *   x
 *   |  xy coordinates.
 *   v
 * 
 * 0 in spiral corresponds to (0,0) in xy coordinates.
 */    

public class Spiral{
	private Spiral() {};
	
	public static long convert(P e) {// xy -> spiral O(1) 
		long x=e.x,y=e.y;
		if(x+y>=0) {
			if(x-y>=0) {
				return 4*x*x+(x-y);
			}else {
				return 4*y*y+(x-y);
			}
		}else{
			if(x-y+1>=0) {
				return (2*y-1)*(2*y-1) - (x-y+1);
			}else {
				return (2*x+1)*(2*x+1) - (x-y+1);
			}
		}
	}
	public static P invert(long i) {// spiral -> xy O(1)
		assert i>=0;
		long d = sqrt(i);
		if(i>d*d+d) {
			if((d&1)==1) {
				return new P(-d/2-1+i-d*d-d,d/2+1);
			}else {
				return new P(d/2-i+d*d+d,-d/2);
			}
		}else {
			if((d&1)==1) {
				return new P(-d/2-1,i-d*d-d/2);
			}else {
				return new P(d/2,-i+d*d+d/2);
			}
		}
	}
	
	private static long sqrt(long l) {
		assert l>=0;
		long d = (long)(Math.sqrt(l))+1;
		while(d*d>l)d--;
		return d;
	}
	
	public static void main(String[] args) {
		int n=5;
		long[][] is=new long[n*2-1][n*2-1];
		for(int i=-n+1;i<n;i++)for(int j=-n+1;j<n;j++) {
			is[i+n-1][j+n-1] = convert(new P(i,j));
		}
		for(int i=0;i<n*2-1;i++){
			for(int j=0;j<n*2-1;j++) {
				System.out.printf("%3d ",is[i][j]);
			}
			System.out.println();
		}
		
		for(int i=-n+1;i<n;i++)for(int j=-n+1;j<n;j++) {
			long p = is[i+n-1][j+n-1];
			P e = invert(p);
			assert e.x==i && e.y==j;
		}
		
		java.util.Random r = new java.util.Random();
		for(int i=0;i<100000000;i++) {
			long l = Math.abs(r.nextLong());
			assert convert(invert(l))==l : l;
		}
	}
}
