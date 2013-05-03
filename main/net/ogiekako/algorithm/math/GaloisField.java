package net.ogiekako.algorithm.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * http://en.wikipedia.org/wiki/Factorization_of_polynomial_over_finite_field_and_irreducibility_tests#Irreducible_polynomials
 * を参考に.
 * 
 * 有限体の位数は,q = prime^n に限定される.
 * まず,n=1 ならば,mod prime における加減算とすれば良い.
 * n>1 のとき,有限体の元は,[0,prime) を係数とする,n-1 次以下の多項式の集合 として表せる.
 * 和は普通で, 積は,mod p(x) でとる.
 * ここで,p(x)は,irreducible polynomial と呼ばれるもので,
 * n次の多項式であって,これを法として, {1,x^1,... x^(q-2)} はすべて異なる.
 * このようなp(x) は乱択によってすぐに求まる.
 * 
 * q<=150 で動作することを確認した. ( http://atcoder.jp/problem/detail/134 )
 * 
 * @author ogiekako
 *
 */
public class GaloisField {
	
	static class Poly{
		int primeMod;
		int[] coeff; // coeff[0]x^0 + coeff[1]x^1 + ...

		public Poly(int primeMod, int[] coeff) {
			super();
			this.primeMod = primeMod;
			int deg = coeff.length-1;
			while(deg>=0 && coeff[deg]==0)deg--;
			this.coeff=new int[deg+1];
			System.arraycopy(coeff, 0, this.coeff, 0, deg+1);
		}

		public int value() {
			int res = 0;
			for(int i=0;i<coeff.length;i++){
				res *= primeMod;
				res += coeff[i];
			}
			return res;
		}

		public Poly add(Poly add) {
			int deg = Math.max(this.coeff.length,add.coeff.length);
			int[] ncf=new int[deg];
			for(int i=0;i<deg;i++){
				ncf[i] = (i<this.coeff.length ? this.coeff[i] : 0) + (i<add.coeff.length ? add.coeff[i] : 0);
				if(ncf[i]>=primeMod)ncf[i] -= primeMod;
			}
			return new Poly(primeMod, ncf);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(coeff);
			result = prime * result + primeMod;
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
			Poly other = (Poly) obj;
			if (!Arrays.equals(coeff, other.coeff))
				return false;
			if (primeMod != other.primeMod)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Poly [coeff=" + Arrays.toString(coeff) + ", primeMod=" + primeMod + "]";
		}

		static Poly findIrreduciblePolynomial(int p,int n){
			if(p<2)throw new RuntimeException();
			Random rnd = new Random(10283108420L);
			for(;;){
				int[] coeff=new int[n+1];
				coeff[n]=1;
				for(int i=0;i<n;i++){
					coeff[i] = rnd.nextInt(p);
				}
				Poly poly = new Poly(p, coeff);
				if(poly.isIrreduciblePolynomial())return poly;
			}
		}

		private boolean isIrreduciblePolynomial() {
			int n = coeff.length-1;
			int q = 1;
			for(int i=0;i<n;i++)q *= primeMod;
			HashSet<Poly> deja = new HashSet<Poly>();
			deja.add(new Poly(primeMod, new int[0]));
			int[] initCoeff = new int[coeff.length];
			initCoeff[0] = 1;
			Poly poly = new Poly(primeMod, initCoeff);
			Poly x = new Poly(primeMod, new int[]{0,1});
			for(int i=0;i<q-1;i++){
				if(deja.contains(poly))return false;
				deja.add(poly);
				poly = poly.mul(x);
				poly = poly.mod(this);
			}
			return true;
		}

		private Poly mod(Poly poly) {
			if(this.coeff.length < poly.coeff.length)return this;
			int a = this.coeff[this.coeff.length-1];
			int b = poly.coeff[poly.coeff.length-1];
			int inv = a * inv(b) % primeMod;
			int[] ncf = new int[this.coeff.length-poly.coeff.length+1];
			ncf[this.coeff.length-poly.coeff.length] = inv;
			Poly div = new Poly(primeMod, ncf);
			Poly sub = poly.mul(div);
			return sub(sub).mod(poly);
		}

		private int inv(int a) {
			return BigInteger.valueOf(a).modInverse(BigInteger.valueOf(primeMod)).intValue();
		}

		private Poly sub(Poly sub) {
			int deg = Math.max(this.coeff.length,sub.coeff.length);
			int[] ncf=new int[deg];
			for(int i=0;i<deg;i++){
				ncf[i] = (i<this.coeff.length ? this.coeff[i] : 0) - (i<sub.coeff.length ? sub.coeff[i] : 0);
				if(ncf[i]<0)ncf[i] += primeMod;
			}
			return new Poly(primeMod, ncf);
		}

		private Poly mul(Poly g) {
			if(primeMod != g.primeMod)throw new RuntimeException();
			int[] ncf=new int[this.coeff.length + g.coeff.length];
			for(int i=0;i<ncf.length;i++){
				for(int j=0;j<=i;j++)if(j<this.coeff.length && i-j <g.coeff.length){
					ncf[i] = (ncf[i] + this.coeff[j] * g.coeff[i-j]) % primeMod;
				}
			}
			return new Poly(primeMod, ncf);
		}
	}
}
