package net.ogiekako.research.dynamic;
import junit.framework.Assert;
import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.RBTreeSet;
import net.ogiekako.algorithm.utils.Asserts;

import java.util.*;
public class ClusterVertexDeletion implements DynamicGraphAlgorithm {
    boolean DEBUG = false;
    boolean LOG = true;
    int n;
    Random random = new Random(190357890123750961L);
    _UndirectedGraph G = new _UndirectedGraph();
    RBTreeSet<Integer> X = newSetInstance();// [0,n)
    RBTreeSet<Integer> R = newSetInstance();
    RBTreeSet<Integer> L = newSetInstance(); // [n, 3n) set of labels
    Map<E, RBTreeSet<Integer>> PPlus = new HashMap<E, RBTreeSet<Integer>>();//
    Map<E, RBTreeSet<Integer>> PMinus = new HashMap<E, RBTreeSet<Integer>>();
    int[] l_; // l_v = the label of the cluster that v belongs to if v∈ V', -1 otherwise.
    Map<Integer, RBTreeSet<Integer>> C = new HashMap<Integer, RBTreeSet<Integer>>();
    _UndirectedGraph H = new _UndirectedGraph();

    RBTreeSet<Integer> newSetInstance() {
        return new RBTreeSet<Integer>();
    }

    @Override
    public void init(int n) {
        this.n = n;
        l_ = new int[n];
        G.init(n);
        H.init(3 * n);
        for (int u = 0; u < n; u++) {
            R.add(u);
            int l = createLabel();
            l_[u] = l;
            C.put(l, singleton(u));
        }
    }

    RBTreeSet<Integer> singleton(int u) {
        RBTreeSet<Integer> set = newSetInstance();
        set.add(u);
        return set;
    }

    private int createLabel() {
        int l = random.nextInt(2 * n) + n;
        if (!L.contains(l)) {
            L.add(l);
            return l;
        }
        return createLabel();
    }
    @Override
    public void add(int u, int v) {
        if (!X.contains(u)) moveToX(u);
        if (!X.contains(v)) moveToX(v);
        // update G
        G.add(u, v);
        computeAndUpdate();

        assertValidCondition();
    }
    private void moveToX(int u) {
        if (LOG) System.err.println("move " + u + " to X.");
        Assert.assertTrue(R.contains(u));
        int l = l_[u];
        Assert.assertTrue(L.contains(l));
        List<Integer> N = new ArrayList<Integer>(H.N(l));
        for (int y : N) { // y is adjacent to the cluster Cl.
            Assert.assertTrue(X.contains(y));
            E e = E.of(y, l);
            Assert.assertTrue(PPlus.containsKey(e));
            Set<Integer> PPlusE = PPlus.get(e);
            Set<Integer> PMinusE = PMinus.get(e);
            // PPlusE ∪ PMinusE == Cl
            if (PPlusE.contains(u)) {
                Assert.assertTrue(PMinusE == null || !PMinusE.contains(u));
                PPlusE.remove(u);
                if (PPlusE.isEmpty()) { // y is no longer adjacent to the cluster Cl.
                    PPlus.remove(e);
                    if (PMinus.containsKey(e)) PMinus.remove(e);
                    H.remove(y, l);
                }
            } else {
                Assert.assertTrue(PMinus.containsKey(e));
                removeConvertingEmptyToNull(PMinus, e, u);
            }
        }
        Assert.assertFalse(PPlus.containsKey(E.of(u, l)));
        RBTreeSet<Integer> Nu = C.get(l).copy();
        for (int x : X) if (G.contains(x, u)) Nu.add(x);
        Nu.remove(u);
        PPlus.put(E.of(u, l), Nu);

        for (int x : X) {
            if (G.contains(x, u)) {
                PPlus.get(E.of(u, l)).remove(x);
            }
        }
        if (PPlus.get(E.of(u, l)) == null || PPlus.get(E.of(u, l)).isEmpty()) PPlus.remove(E.of(u, l));
        if (C.get(l).size() > 1)
            H.add(u, l);

        // update R,X,l_ and C.
        R.remove(u);
        X.add(u);
        l_[u] = -1;
        C.get(l).remove(u);
        if (C.get(l).isEmpty()) {
            L.remove(l);
            C.remove(l);
        }
        if (LOG) System.err.println("end move to X.");
        assertValidCondition();
    }

    void computeAndUpdate() {   // O(k^6)
        if (LOG) System.err.println("computeAndUpdate");
        int k = X.size();
        _UndirectedGraph GPrime = new _UndirectedGraph();  // Kernel
        Set<Integer> VPrimeSet = newSetInstance();
        for (int x : X) {// O(k)
            if (H.d(x) > k) continue; // if dH(x) > k, at least k vertices must be moved to X when x is removed from X.
            int S = 0;
            for (int l : H.N(x)) { // O(k^2)
                E e = E.of(x, l);
                Set<Integer> PPlusE = PPlus.get(e);
                Set<Integer> PMinusE = PMinus.get(e);
                if (PMinusE != null) {
                    int min = Math.min(PPlusE.size(), PMinusE.size());
                    S += min;
                }
            }
            if (S < k) {
                VPrimeSet.add(x);
                for (int l : H.N(x)) { // O(k^2)
                    E e = E.of(x, l);
                    Set<Integer> PPlusE = PPlus.get(e);
                    Set<Integer> PMinusE = PMinus.get(e);
                    addOnly(PPlusE, VPrimeSet, k);// O(k^3)
                    if (PMinusE != null) addOnly(PMinusE, VPrimeSet, k);
                }
            }
        }
        Integer[] VPrime = VPrimeSet.toArray(new Integer[VPrimeSet.size()]);
        if (LOG) System.err.println("end computing V'. Size of V' = " + VPrime.length);
        Arrays.sort(VPrime);
        int nPrime = VPrime.length;
        GPrime.init(nPrime);
        for (int i = 0; i < nPrime; i++)
            for (int j = 0; j < i; j++) {// O(k^6)
                if (G.contains(VPrime[i], VPrime[j])) {
                    GPrime.add(i, j);
                }
            }
        Set<Integer> XPrime = newSetInstance();
        for (int i : computeNaive(GPrime)) XPrime.add(VPrime[i]);
        if (LOG) {
            System.err.println("G' = " + GPrime);
            Set<Integer> Sol = newSetInstance();
            Sol.addAll(XPrime);
            for (int x : X) if (!VPrimeSet.contains(x)) Sol.add(x);
            System.err.println("New solution = " + Sol);
        }
        for (int x : XPrime) {
            if (R.contains(x)) {
                moveToX(x);
                assertValidCondition();
            }
        }
        for (int x : new ArrayList<Integer>(X))
            if (VPrimeSet.contains(x) && !XPrime.contains(x)) {
                moveToR(x);
                assertValidCondition();
            }
    }
    private void moveToR(int x) {
        if (LOG) System.err.println("move " + x + " to R.");
        Set<Integer> NHx = H.N(x);
        if (DEBUG) Assert.assertTrue(NHx.size() <= 1);
        // 1 and 2
        int l;
        if (NHx.isEmpty()) { // x is a new singleton cluster, and l is a new label.
            l = createLabel();
            for (int y : X)
                if (G.contains(x, y)) { // y∈N_{G[X]}(x)
                    E e = E.of(y, l);
                    addConsideringNullEmpty(PPlus, e, x);
                    H.add(y, l);
                }
        } else {
            l = getContentAssertingSingleton(NHx);
            Set<Integer> Y = newSetInstance();
            for (int y : H.N(l)) if (y != x) Y.add(y);
            for (int y : X) if (G.contains(x, y)) Y.add(y);
            for (int y : Y) {
                E e = E.of(y, l);
                if (G.contains(x, y)) {
                    addConsideringNullEmpty(PPlus, e, x);
                    if (!H.contains(y, l)) {
                        H.add(y, l);
                        if (!C.get(l).isEmpty())
                            PMinus.put(e, C.get(l).copy());
                    }
                } else {
                    addConsideringNullEmpty(PMinus, e, x);
                }
            }
            PPlus.remove(E.of(x, l));
            if (DEBUG) Assert.assertFalse(E.of(x, l) + " " + PMinus.get(E.of(x, l)), PMinus.containsKey(E.of(x, l)));
            H.remove(x, l);
        }

        // update R,X, l_, and C
        R.add(x);
        X.remove(x);
        l_[x] = l;
        addConsideringNullEmpty(C, l, x);
        if (LOG) System.err.println("end move to R");
    }
    private <V> V getContentAssertingSingleton(Set<V> set) {
        if (set.size() != 1) throw new AssertionError(set);
        //noinspection LoopStatementThatDoesntLoop
        for (V value : set) return value;
        throw new AssertionError();
    }
    private <K> void addAssertingNotNull(Map<K, Set<Integer>> eToSet, K e, int u) {
        if (!eToSet.containsKey(e)) throw new AssertionError(e + ", " + u);
        eToSet.get(e).add(u);
    }
    private <K> void addConsideringNullEmpty(Map<K, RBTreeSet<Integer>> eToSet, K e, int u) {
        if (!eToSet.containsKey(e)) eToSet.put(e, newSetInstance());
        eToSet.get(e).add(u);
    }
    private <K> void removeConvertingEmptyToNull(Map<K, ? extends Set<Integer>> eToSet, K e, int u) {
        eToSet.get(e).remove(u);
        if (eToSet.get(e).isEmpty()) eToSet.remove(e);
    }

    private List<Integer> getAny(Collection<Integer> collection, int k) {
        int count = 0;
        List<Integer> set = new ArrayList<Integer>();
        for (int value : collection) {
            if (count >= k) break;
            set.add(value);
            count++;
        }
        return set;
    }

    private void addOnly(Set<Integer> from, Set<Integer> to, int k) {
        int count = 0;
        for (int u : from) {
            to.add(u);
            count++;
            if (count >= k) break;
        }
    }

    void assertValidCondition() {
        if (!DEBUG) return;
        // X∩V' = ∅, X∪V' = V = [n].
        Assert.assertTrue(Collections.disjoint(X, R));
        Assert.assertEquals(X.size() + R.size(), n);
        Asserts.assertAllBetween(X, 0, n);
        Asserts.assertAllBetween(R, 0, n);

        // L and l_ are valid
        Asserts.assertAllBetween(L, n, 3 * n);
        for (int u : R) Assert.assertTrue(L.contains(l_[u]));
        for (int x : X) Assert.assertEquals(l_[x], -1);
        for (int l : L) Asserts.assertContains(l_, l);
        for (int u : R)
            for (int v : R)
                if (u == v || G.contains(u, v)) Assert.assertEquals(l_[u], l_[v]);
                else Assert.assertNotSame(l_[u], l_[v]);

        // C is valid
        int sizeC = 0;
        for (Map.Entry<Integer, ? extends Set<Integer>> entry : C.entrySet()) {
            int l = entry.getKey();
            Assert.assertTrue(L.contains(l));
            Set<Integer> Cl = entry.getValue();
            sizeC += Cl.size();
            for (int u : Cl) Assert.assertEquals(l_[u], l);
        }
        Assert.assertEquals(sizeC, R.size());

        // H is valid
        for (int u : R) Assert.assertEquals(H.d(u), 0);
        for (int l = n; l < 3 * n; l++)
            if (!L.contains(l)) Assert.assertEquals(H.d(l), 0);
            else X.containsAll(H.N(l));
        for (int x : X) {
            Set<Integer> expectedLx = newSetInstance();
            for (int u : G.N(x)) if (R.contains(u)) expectedLx.add(l_[u]);
            Assert.assertEquals(expectedLx, H.N(x));
        }

        // PPlus and PMinus are valid
        Map<E, Set<Integer>> expectedPPlus = new HashMap<E, Set<Integer>>();
        Map<E, Set<Integer>> expectedPMinus = new HashMap<E, Set<Integer>>();
        for (int x : X)
            for (int l : H.N(x)) {
                E e = E.of(x, l);
                expectedPPlus.put(e, newSetInstance());
                for (int u : R)
                    if (l_[u] == l) {
                        if (G.contains(x, u)) expectedPPlus.get(e).add(u);
                        else {
                            if (!expectedPMinus.containsKey(e)) expectedPMinus.put(e, newSetInstance());
                            expectedPMinus.get(e).add(u);
                        }
                    }
            }
        Assert.assertEquals(expectedPPlus, PPlus);
        Assert.assertEquals(expectedPMinus, PMinus);
    }

    @Override
    public void remove(int u, int v) {
        if (R.contains(u)) moveToX(u);
        if (R.contains(v)) moveToX(v);
        G.remove(u, v);
        computeAndUpdate();
        assertValidCondition();
    }
    @Override
    public int compute() {
        return X.size();
    }
    public Set<Integer> computeNaive(_UndirectedGraph G) {
        for (int K = 0; ; K++) {
            Set<Integer> res = newSetInstance();
            if (possible(G, K, res)) return res;
        }
    }
    private boolean possible(_UndirectedGraph G, int K, Set<Integer> removed) {
        if (removed.size() > K) return false;
        boolean contains = false;
        int n = G.size();
        for (int i = 0; i < n; i++)
            if (!removed.contains(i)) {
                for (int j = 0; j < n; j++)
                    if (i != j && !removed.contains(j) && G.contains(i, j)) {
                        for (int k = 0; k < n; k++)
                            if (i != k && j != k && !removed.contains(k) && G.contains(i, k) && !G.contains(j, k)) {
                                contains = true;
                                for (int r : new int[]{i, j, k}) {
                                    removed.add(r);
                                    if (possible(G, K, removed)) return true;
                                    removed.remove(r);
                                }
                            }
                    }
            }
        return !contains;
    }
}
