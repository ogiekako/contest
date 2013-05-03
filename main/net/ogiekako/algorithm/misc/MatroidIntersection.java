package net.ogiekako.algorithm.misc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MatroidIntersection {
    /*
    Edmonds のマトロイド交差アルゴリズムを使って, 2つのマトロイドの共通部分集合で,最大の大きさのものを求める.

     */
    public static boolean[] intersection(Matroid F1, Matroid F2) {
        if (F1.size() != F2.size()) throw new IllegalArgumentException();
        int n = F1.size();
        boolean[] X = new boolean[n];
        //noinspection StatementWithEmptyBody
        while (increment(X, F1, F2)) ;
        return X;
    }

    /*
    x \in X, y \in E-X
    X + y - x \in F2 : (y,x)
    X - x + y \in F1 : (x,y) を枝
    
    X + y \in F1 -> y \in S_X
    X + y \in F2 -> y \in T_X として, S_XからT_Xに行く最短路を求める.
     */
    private static boolean increment(boolean[] X, Matroid F1, Matroid F2) {
        int n = X.length;
        Queue<Integer> que = new LinkedList<Integer>();
        int[] bef = new int[n];
        boolean[] T = new boolean[n];
        Arrays.fill(bef, -1);
        for (int i = 0; i < n; i++) if (!X[i]) {
            X[i] = true;
            if (F1.isIndependentSet(X)) {
                bef[i] = -2;
                que.offer(i);
            }
            if (F2.isIndependentSet(X)) T[i] = true;
            X[i] = false;
        }
        int last = -1;
        while (!que.isEmpty()) {
            int i = que.poll();
            if (T[i]) {
                last = i; break;
            }
            X[i] ^= true;
            for (int j = 0; j < n; j++)
                if (bef[j] == -1 && X[j] == X[i]) {
                    X[j] ^= true;
                    if ((X[i] ? F2 : F1).isIndependentSet(X)) {
                        que.offer(j);
                        bef[j] = i;
                    }
                    X[j] ^= true;
                }
            X[i] ^= true;
        }
        if (last == -1) return false;
        for (int i = last; i >= 0; i = bef[i])
            X[i] ^= true;
        return true;
    }
}

interface Matroid {
    boolean isIndependentSet(boolean[] X);

    int size();
}
