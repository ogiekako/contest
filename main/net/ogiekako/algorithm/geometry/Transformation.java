package net.ogiekako.algorithm.geometry;

import static net.ogiekako.algorithm.EPS.EPS;

public class Transformation {
	/**
	 * 原点に対する逆変換を施す.
	 * この変換によって,
	 * 原点を通る円は,直線に移され,その内部は原点を含まない半平面に移される.
	 * 原点を通らない円は,原点を通らない円に移される.(中心は中心に移されるとは限らない)
	 * 
	 * old.GCJ 2010 roundD
	 * http://en.wikipedia.org/wiki/Inversive_geometry
	 * @param p
	 * @return
	 */
	public P inverse(P p){
		double d = p.norm();
		if(d<EPS)throw new RuntimeException();
		return p.mul(1.0/d/d);
	}
}
