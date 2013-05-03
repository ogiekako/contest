package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.EPS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Poly<V extends Ring<V>> implements Ring<Poly<V>> {
    Object[] a;
    int degree;

    public int degree() {
        return degree;
    }

    public boolean isZero() {
        return degree < 0;
    }

    public Poly<V> zero() {
        return new Poly<V>(new Object[0]);
    }

    public Poly(V[] a) {
        degree = a.length - 1;
        while (degree >= 0 && a[degree].isZero()) degree--;
        this.a = new Object[degree + 1];
        for (int i = 0; i < this.a.length; i++) this.a[i] = a[i];
    }

    private Poly(Object[] a) {
        degree = a.length - 1;
        while (degree >= 0 && ((Ring) a[degree]).isZero()) degree--;
        this.a = new Object[degree + 1];
        for (int i = 0; i < this.a.length; i++) this.a[i] = a[i];
    }

    public Poly<V> mul(V d) {
        Object[] nA = new Object[a.length];
        for (int i = 0; i < a.length; i++){
            nA[i] = ((V) a[i]).mul(d);
        }
        return new Poly(nA);
    }

    public void shift() {
        if (isZero()) return;
        V instance = get(0);
        for (int i = 0; i < degree; i++) set(i,get(i+1));
        set(degree, instance.zero());
        degree--;
    }

    public V get(int i) {
        return (V) a[i];
    }

    public void set(int i,V value){
        a[i] = value;
    }

    public double v(int i) {
        return isZero() ? 0 : ((Irr) a[i]).value;
    }

    @Override
    public String toString() {
        if (degree < 0) return "0";
        List<String> list = new ArrayList<String>();
        for (int i = degree; i >= 0; i--) {
            if (get(i).isZero()) continue;
            String s = "";
            if (((Irr) a[i]).value > 0) s += "+";
            else s += "-";
            s += Math.abs(Math.abs(v(i)) - 1) < EPS.EPS && i > 0 ? "" : String.format("%.2f", Math.abs(v(i)));
            while (s.endsWith("0") || s.endsWith(".")) s = s.substring(0, s.length() - 1);
            if (i == 0) s += "";
            else if (i == 1) s += "x";
            else s += "x^" + i;
            list.add(s);
        }
        if (list.isEmpty()) return "0";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.charAt(0) == '+' && i == 0) s = s.substring(1);
            res.append(s);
        }
        return res.toString();
    }

    public static Poly<Irr> one() {
        Irr[] a = new Irr[]{Irr.of(1)};
        return new Poly(a);
    }

    public Poly<V> minus(Poly<V> other) {
        Object[] res = new Object[Math.max(degree, other.degree) + 1];
        if (res.length == 0) return new Poly<V>(res);
        V instance = degree >= 0 ? get(0) : other.get(0);
        for (int i = 0; i < res.length; i++) {
            res[i] = (i <= degree ? get(i) : instance.zero()).add((i <= other.degree ? other.get(i) : instance.zero()).addInv());
        }
        return new Poly(res);
    }

    public Poly<V> mul(Poly<V> other) {
        if(isZero() && other.isZero())return zero();
        Object[] res = new Object[degree + other.degree + 1];
        V instance = degree() >= 0 ? get(0) : other.get(0);
        for(int i=0;i<res.length;i++)res[i] = instance.zero();
        for (int i = 0; i <= degree; i++)
            for (int j = 0; j <= other.degree; j++) {
                V a = (V)res[i+j];
//                debug("a",a);
                V b = get(i).mul(other.get(j));
//                debug("b",b);
                res[i + j] = a.add(b);
//                debug("rs",res[i+j]);
            }
        return new Poly<V>(res);
    }
    static void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }

    public Poly<V> add(Poly<V> other) {
        Object[] res = new Object[Math.max(degree, other.degree) + 1];
        if (res.length == 0) return new Poly<V>(res);
        V instance = degree >= 0 ? get(0) : other.get(0);
        for (int i = 0; i < res.length; i++) {
            res[i] = (i <= degree ? get(i) : instance.zero()).add((i <= other.degree ? other.get(i) : instance.zero()));
        }
        return new Poly(res);
    }

    public Poly<V> addInv() {
        Object[] res = new Object[degree + 1];
        for (int i = 0; i < res.length; i++) res[i] = get(i).addInv();
        return new Poly<V>(res);
    }

    public Poly<V> shifted() {
        Poly<V> res = new Poly(a.clone());
        res.shift();
        return res;
    }

    public static Poly<Irr> x() {
        Poly res = new Poly(new Irr[]{Irr.of(0), Irr.of(1)});
        return res;
    }

//    public Poly mul(Poly other) {
//        return times(other);
//    }

//    public Poly add(Poly other) {
//        return plus(other);
//    }


    public static Poly<Irr> constant(double value) {
        return new Poly(new Irr[]{Irr.of(value)});
    }


//    public Poly gcd(Poly other) {
//
//    }
}
