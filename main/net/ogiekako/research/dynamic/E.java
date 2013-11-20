package net.ogiekako.research.dynamic;
import net.ogiekako.algorithm.utils.Pair;
public class E extends Pair<Integer, Integer> {
    public E(Integer first, Integer second) {
        super(first, second);
    }
    public static E of(int u,int v){
        return new E(u,v);
    }
}
