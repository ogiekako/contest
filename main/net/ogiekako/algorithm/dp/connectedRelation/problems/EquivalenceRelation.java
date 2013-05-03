package net.ogiekako.algorithm.dp.connectedRelation.problems;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class EquivalenceRelation {
    protected int[] relation;
    public EquivalenceRelation(int[] _relation){
        relation = _relation.clone();
        ArrayUtils.normalize(relation);
    }
    public static EquivalenceRelation[] createAllRelation(int n){
        List<EquivalenceRelation> list = new ArrayList<EquivalenceRelation>();
        int[] is = new int[n];
        dfs(list,is,0,0);
        return list.toArray(new EquivalenceRelation[list.size()]);
    }

    private static void dfs(List<EquivalenceRelation> list, int[] is, int p, int to) {
        if(p==is.length){
            list.add(EquivalenceRelation.of(is));
            return;
        }
        for(int i=0;i<=to;i++){
            is[p] = i;
            dfs(list,is,p+1,Math.max(to,i+1));
        }
    }

    public static EquivalenceRelation of(int[] relation) {
        return new EquivalenceRelation(relation);
    }
}
