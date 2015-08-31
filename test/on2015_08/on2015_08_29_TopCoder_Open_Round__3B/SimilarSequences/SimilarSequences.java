package on2015_08.on2015_08_29_TopCoder_Open_Round__3B.SimilarSequences;



import java.util.*;

public class SimilarSequences {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 9);

    public int count(int[] seq, int bound) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int s : seq) set.add(s);
        int n = seq.length;
        HashSet<List<Integer>> removed = new HashSet<List<Integer>>();
        HashSet<List<Integer>> lists = new HashSet<List<Integer>>();
        for (int i = 0; i < n; i++) {
            List<Integer> l = new ArrayList<Integer>();
            for (int s : seq) l.add(s);
            l.remove(i);
            removed.add(l);
            for (int j = 0; j < n; j++) {
                for (int x : seq) {
                    List<Integer> l2 = new ArrayList<Integer>(l);
                    l2.add(j, x);
                    lists.add(l2);
                }
            }
        }
        long res = (long) (bound - set.size()) * n * removed.size();
        return (int) ((res + lists.size()) % MOD);
    }
}
