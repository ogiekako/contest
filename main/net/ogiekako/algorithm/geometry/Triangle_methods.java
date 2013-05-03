package net.ogiekako.algorithm.geometry;

import static net.ogiekako.algorithm.EPS.*;

public class Triangle_methods {
	private Triangle_methods(){};	

    // 外心
    P circumcenter(P A, P B, P C) {// pku2957
        P AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
        return (A.add(B).sub(AB.rot90().mul(BC.dot(CA) / AB.det(BC)))).mul(0.5);
    }
    
    // AP:BP:CP = a:b:c.
	P[] ratioABC(P A,P B,P C,double a,double b,double c) {// CDF 2C
		if(eq(a,b) && eq(a,c)) {
			return new P[] { circumcenter(A,B,C)};
		}
		if(eq(a,b)) {
			double d=a;a=c;c=d;
			P p=A;A=C;C=p;
		}
		if(eq(a,c)) {
			double d=a;a=b;b=d;
			P p=A;A=B;B=p;
		}
		P ab1 = A.mul(b).add(B.mul(a)).div(a+b);
		P ab2 = A.mul(-b).add(B.mul(a)).div(a-b);
		P o1 = ab1.add(ab2).div(2);
		double r1 = o1.dist(ab1);
		P ac1 = A.mul(c).add(C.mul(a)).div(a+c);
		P ac2 = A.mul(-c).add(C.mul(a)).div(a-c);
		P o2 = ac1.add(ac2).div(2);
		double r2 = o2.dist(ac1);
		return Circle_methods.isCC(o1,r1,o2,r2,1e-6);
	}
}
