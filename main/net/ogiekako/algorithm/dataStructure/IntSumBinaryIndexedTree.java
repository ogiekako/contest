package net.ogiekako.algorithm.dataStructure;

/**
 * <p>
 * 各要素の値が何度も変化するときに、変化後の区間の和を高速に求められる。<br></br>
 * 1. 要素に対する加算 : O(log n) <br></br>
 * 2. 適当な区間内の和 : O(log n) <br></br>
 * </p>
 * sum (s,t) : is[s] ... is[t-1] の和 (右端を含まない).
 * add (id,val) : is[id] にval を足す.
 * set (id,val) : is[id] にval をセット.
 * kth (k) : add(id, val) を idをval個追加と考え，multisetとみなした時 k番目 (0-origin)の要素
 * O(log n)
 *
 * @author ogiekako
 */

public class IntSumBinaryIndexedTree {
    long[] is;
    long[] us;

    public IntSumBinaryIndexedTree(int n) {
        if (n <= 1) n = 2;
        n = Integer.highestOneBit(n - 1) << 1;
        is = new long[n + 1];
    }

    public long sum(int s, int t) {// [s,t)
        if (s > 0) return sum(0, t) - sum(0, s);
        long res = 0;
        for (int i = t; i > 0; i -= i & -i) {
            res += is[i];
        }
        return res;
    }

    public void add(int id, long val) {
        for (int i = id + 1; i < is.length; i += i & -i) {
            is[i] += val;
        }
    }

    public void set(int id, long val) {
        val -= sum(id, id + 1);
        add(id, val);
    }

    /*
    find the k-th smallest element of the multiset.
    there should not be negative number of elements.
     */
    public int kth(long k) {
        int s = 0;
        for (int i = is.length / 2; i > 0; i >>= 1) {
            if (k >= is[s | i]) {
                s |= i;
                k -= is[s | i];
            }
        }
        return s;
    }
}
