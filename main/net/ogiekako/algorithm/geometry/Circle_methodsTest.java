package net.ogiekako.algorithm.geometry;

import org.junit.Ignore;
import org.junit.Test;


public class Circle_methodsTest{
	@Ignore
	@Test
	public void testIsCC(){
		Vis vis = new Vis();
		P p1 = new P(100,100);
		P p2 = new P(100,50);
		double r1 = 60;
		double r2 = 20;
		vis.addCircle(p1,r1);
		vis.addCircle(p2,r2);
		P[] cc = Circle_methods.isCC(p1,r1,p2,r2,1e-7);
		vis.addCircle(cc[0],2);
		vis.addEdge(cc[0],cc[1]);
	}

	@Ignore
	@Test
	public void testIsCL() {
		Vis vis = new Vis();
		P o = new P(100,100);
		double r = 50;
		P p1 = new P(110,0);
		P p2 = new P(110,200);
		vis.addCircle(o,r);
		vis.addEdge(p1,p2);
		vis.addCircle(p1,2);
		P[] cc = Circle_methods.isCL(o,r,p1,p2);
		vis.addCircle(cc[0],2);
		vis.addCircle(cc[1],2);
		vis.addEdge(cc[0],cc[1]);
	}
	@Ignore
	@Test
	public void testTanCP() {
		Vis vis = new Vis();
		P o = new P(100,100);
		double r = 50;
		P p = new P(160,20);
		
		P[] is = Circle_methods.tanCP(o,r,p);
		vis.addCircle(o,r);
		vis.addEdge(p,is[0].add(is[0].sub(p)));
		vis.addEdge(p,is[1].add(is[1].sub(p)));
		for(;;);
//		vis.addCircle(cc[0],2);
//		vis.addCircle(cc[1],2);
//		vis.addEdge(cc[0],cc[1]);
	}
}
