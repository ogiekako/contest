package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.utils.interfaces.Classifiable;

import java.util.*;

public class ArrayUtils {
    private static Random random;

    public static <T> T[] createArray(int length, T[] sampleArray) {
        T[] empty = Arrays.copyOf(sampleArray, 0);
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < length; i++) {
            list.add(null);
        }
        return list.toArray(empty);
    }

    /**
     * @param entries
     * @param <T>
     * @return
     */
    public static <T extends Classifiable> T[][] classify(T[] entries, T[][] dummyDoubleArray) {
        if (entries.length == 0) {
            //noinspection unchecked
            return (T[][]) new Object[0][0];
        }
        T[][] emptyDoubleArray = Arrays.copyOf(dummyDoubleArray, 0);
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (T entry : entries) {
            set.add(entry.getKey());
        }
        int[] keys = Cast.toInt(set);
        int[] ids = new int[entries.length];
        int[] counts = new int[keys.length];
        for (int i = 0; i < entries.length; i++) {
            int id = Arrays.binarySearch(keys, entries[i].getKey());
            ids[i] = id;
//            counts[id]++;
        }
        List<List<T>> idToEntryList = new ArrayList<List<T>>();
//        T[][] idToEntries = createArray(keys.length);
        for (int i = 0; i < keys.length; i++) {
            idToEntryList.add(new ArrayList<T>());
//            idToEntries[i] = createArray(counts[i], entries[0]);
//            counts[i] = 0;
        }
        for (int i = 0; i < entries.length; i++) {
            int id = ids[i];
            idToEntryList.get(id).add(entries[i]);
//            idToEntries[id][counts[id]++] = entries[i];
        }
        List<T[]> result1 = new ArrayList<T[]>();
        for (int i = 0; i < idToEntryList.size(); i++) {
            result1.add((T[]) idToEntryList.get(0).toArray((T[]) new Object[0]));
        }
        return null;
    }

    /*
   res[i] = { j | comp[j] = i } (increasing order)
    */
    public static int[][] classify(int[] comp) {
        int n = comp.length;
        int numComp = max(comp) + 1;
        int[] counts = new int[numComp];
        for (int aComp : comp) counts[aComp]++;
        int[][] res = new int[numComp][];
        for (int i = 0; i < numComp; i++) {
            res[i] = new int[counts[i]];
            counts[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int c = comp[i];
            res[c][counts[c]++] = i;
        }
        return res;
    }

    public static void shuffle(int[] S) {
        Random rnd = random == null ? (random = new Random()) : random;
        shuffle(S, rnd);
    }

    public static void shuffle(long[] S) {
        Random rnd = random == null ? (random = new Random()) : random;
        shuffle(S, rnd);
    }

    public static <T extends Comparable<T>> void shuffle(T[] S) {
        Random rnd = random == null ? (random = new Random()) : random;
        shuffle(S, rnd);
    }

    public static int[] shuffled(int[] S) {
        S = S.clone();
        shuffle(S);
        return S;
    }

    public static long[] shuffled(long[] S) {
        S = S.clone();
        shuffle(S);
        return S;
    }

    public static void shuffle(int[] S, Random rnd) {
        for (int i = S.length; i > 1; i--)
            swap(S, i - 1, rnd.nextInt(i));
    }

    public static void shuffle(long[] S, Random rnd) {
        for (int i = S.length; i > 1; i--)
            swap(S, i - 1, rnd.nextInt(i));
    }

    public static <T extends Comparable<T>> void shuffle(T[] S, Random rnd) {
        for (int i = S.length; i > 1; i--)
            swap(S, i - 1, rnd.nextInt(i));
    }

    public static void swap(String[] ss, int i, int j) {
        String tmp = ss[i];
        ss[i] = ss[j];
        ss[j] = tmp;
    }

    public static void fill(int[][] array, int value) {
        for (int[] a : array) Arrays.fill(a, value);
    }

    public static void fill(long[][] array, long value) {
        for (long[] a : array) Arrays.fill(a, value);
    }

    public static long sum(int[] is) {
        long res = 0;
        for (int i : is) {
            res += i;
        }
        return res;
    }

    public static boolean[] append(boolean[] array, boolean value) {
        boolean[] res = new boolean[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = value;
        return res;
    }

    public static int[] appended(int[] array, int value) {
        int[] res = new int[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = value;
        return res;
    }

    public static long[] append(long[] array, long value) {
        long[] res = new long[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = value;
        return res;
    }

    public static double[] append(double[] array, double value) {
        double[] res = new double[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = value;
        return res;
    }

    public static char[] append(char[] array, char value) {
        char[] res = new char[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = value;
        return res;
    }

    public static <V> V[] append(V[] array, V value) {
        ArrayList<V> list = new ArrayList<V>(array.length + 1);
        Collections.addAll(list, array);
        list.add(value);
        return list.toArray(array);
    }

    public static int[] removeIndex(int[] is, int index) {
        if (index < 0 || index >= is.length) throw new IndexOutOfBoundsException(is.length + " " + index);
        int[] res = new int[is.length - 1];
        System.arraycopy(is, 0, res, 0, index);
        System.arraycopy(is, index + 1, res, index, res.length - index);
        return res;
    }

    public static String[] removeIndex(String[] words, int index) {
        String[] res = new String[words.length - 1];
        System.arraycopy(words, 0, res, 0, index);
        System.arraycopy(words, index + 1, res, index, res.length - index);
        return res;
    }

    public static int[] removeAll(int[] is, int target) {
        int m = 0;
        for (int i : is) if (i != target) m++;
        int[] res = new int[m];
        m = 0;
        for (int i : is) if (i != target) res[m++] = i;
        return res;
    }

    public static <T> T[] removeAll(T[] array, T target, T[] emptyArray) {
        List<T> res = new ArrayList<T>();
        for (T value : array)
            if (value == null ? target != null : !value.equals(target)) res.add(value);
        return res.toArray(emptyArray);
    }

    public static void fill(int[][][][][][] array, int value) {
        for (int[][][][][] a : array) fill(a, value);
    }

    public static void fill(int[][][][][] array, int value) {
        for (int[][][][] a : array) fill(a, value);
    }

    public static void fill(int[][][][] array, int value) {
        for (int[][][] a : array) fill(a, value);
    }

    public static void fill(int[][][] array, int value) {
        for (int[][] a : array) fill(a, value);
    }

    public static int max(int... is) {
        int res = Integer.MIN_VALUE;
        for (int i : is) res = Math.max(res, i);
        return res;
    }

    public static long max(long[] is) {
        long res = Long.MIN_VALUE;
        for (long i : is) res = Math.max(res, i);
        return res;
    }

    public static double max(double[] is) {
        double res = Double.NEGATIVE_INFINITY;
        for (double i : is) res = Math.max(res, i);
        return res;
    }

    public static int maxIndex(int[] a) {
        return indexOf(a, max(a), 0);
    }

    public static int maxIndex(long[] a) {
        return indexOf(a, max(a), 0);
    }

    public static int maxIndex(double[] a) {
        return indexOf(a, max(a), 0);
    }

    public static int[][] part(int[][] array, int x1, int y1, int x2, int y2) {
        int[][] res = new int[x2 - x1][y2 - y1];
        for (int i = x1; i < x2; i++) {
            System.arraycopy(array[i], y1, res[i - x1], 0, y2 - y1);
        }
        return res;
    }

    public static boolean equals(boolean[][] a, boolean[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static boolean equals(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static boolean equals(long[][] a, long[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static boolean equals(double[][] a, double[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static boolean equals(char[][] a, char[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static int[] subArray(int[] array, int from) {
        int[] res = new int[array.length - from];
        System.arraycopy(array, from, res, 0, array.length - from);
        return res;
    }

    public static int[] subArray(int[] array, int from, int to) {
        int[] res = new int[to - from];
        System.arraycopy(array, from, res, 0, to - from);
        return res;
    }

    /**
     * res[0] = array[i]
     * rotateLeft({1,2,3},1) = {2,3,1}.
     * O(n).
     *
     * @param array
     * @param i
     */
    public static void rotateLeft(int[] array, int i) {
        if (i < 0 || i >= array.length) throw new IllegalArgumentException();
        if (i == 0) return;
        int to = array.length;
        for (int j = 0; j < to; j++) {
            int tmp = array[j];
            int cur = j;
            int nxt = j + i;
            if (nxt >= array.length) nxt -= array.length;
            while (nxt != j) {
                if (to > nxt) to = nxt;
                array[cur] = array[nxt];
                cur = nxt;
                nxt += i;
                if (nxt >= array.length) nxt -= array.length;
            }
            array[cur] = tmp;
        }
    }

    public static int[] concatenate(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }

    public static int min(int... array) {
        int res = Integer.MAX_VALUE;
        for (int i : array) res = Math.min(res, i);
        return res;
    }

    public static Pair<Integer, Integer>[] makeValueIndexPairs(int[] array) {
        @SuppressWarnings("unchecked")
        Pair<Integer, Integer>[] res = Arrays.copyOf(new Pair[0], array.length);
        for (int i = 0; i < res.length; i++) {
            res[i] = new Pair<Integer, Integer>(array[i], i);
        }
        return res;
    }

    public static int min(int[] array, int from, int to) {
        int res = Integer.MAX_VALUE;
        for (int i = from; i < to; i++) res = Math.min(res, array[i]);
        return res;
    }

    public static boolean contains(boolean[] array, boolean value) {
        for (boolean b : array) if (b == value) return true;
        return false;
    }

    public static boolean contains(int[] array, int value) {
        for (int b : array) if (b == value) return true;
        return false;
    }

    public static boolean contains(long[] array, long value) {
        for (long b : array) if (b == value) return true;
        return false;
    }

    public static boolean contains(char[] array, char value) {
        for (char b : array) if (b == value) return true;
        return false;
    }

    public static <V> boolean contains(V[] array, V value) {
        for (V b : array) if (b.equals(value)) return true;
        return false;
    }

    public static boolean allOf(int[] array, int value) {
        for (int i : array) if (i != value) return false;
        return true;
    }

    public static int sumInt(int[] array) {
        int res = 0;
        for (int i : array) res += i;
        return res;
    }

    public static void fill(long[][][][][][] array, long value) {
        for (long[][][][][] l : array) fill(l, value);
    }

    private static void fill(long[][][][][] array, long value) {
        for (long[][][][] l : array) fill(l, value);
    }

    public static void fill(long[][][][] array, long value) {
        for (long[][][] l : array) fill(l, value);
    }

    public static void fill(long[][][] array, long value) {
        for (long[][] l : array) fill(l, value);
    }

    public static <V> void fill(V[][] array, V value) {
        for (V[] l : array) Arrays.fill(l, value);
    }

    /**
     * 辞書順比較.
     * 終端記号は何よりも早い.
     *
     * @param as
     * @param bs
     * @return
     */
    public static int compare(int[] as, int[] bs) {
        for (int i = 0; i < as.length && i < bs.length; i++) {
            if (as[i] != bs[i]) {
                return compare(as[i], bs[i]);
            }
        }
        return compare(as.length, bs.length);
    }

    private static int compare(int a, int b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }


    public static void decreaseByOne(int[]... arrays) {
        for (int[] array : arrays) {
            for (int i = 0; i < array.length; i++) array[i]--;
        }
    }

    public static void arraycopy(boolean[][] src, boolean[][] dest, int x0, int y0) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[x0 + i], y0, src[i].length);
        }
    }

    public static void arraycopy(int[][] src, int[][] dest, int x0, int y0) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i + x0], y0, src[i].length);
        }
    }

    public static void arraycopy(long[][] src, long[][] dest, int x0, int y0) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i + x0], y0, src[i].length);
        }
    }

    public static void swap(long[] array, int i, int j) {
        long tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static int count(boolean[] bs, boolean value) {
        int res = 0;
        for (boolean b : bs) if (b == value) res++;
        return res;
    }

    public static int count(boolean[][] arrays, boolean value) {
        int res = 0;
        for (boolean[] b : arrays) res += count(b, value);
        return res;
    }

    public static int[] compressed(int[] X) {
        int[] res = X.clone();
        compress(res);
        return res;
    }

    public static void compress(int[] X) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int x : X) set.add(x);
        int[] xs = Cast.toInt(set);
        for (int i = 0; i < X.length; i++) {
            X[i] = Arrays.binarySearch(xs, X[i]);
        }
    }

    public static void fill(char[][] cs, char c) {
        for (char[] array : cs) Arrays.fill(array, c);
    }

    public static boolean[][] clone(boolean[][] bs) {
        boolean[][] res = new boolean[bs.length][];
        for (int i = 0; i < res.length; i++) res[i] = bs[i].clone();
        return res;
    }

    public static long[] pushFront(long elem, long[] array) {
        long[] res = new long[1 + array.length];
        res[0] = elem;
        System.arraycopy(array, 0, res, 1, array.length);
        return res;
    }

    public static long[] pushBack(long[] array, long elem) {
        long[] res = new long[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[array.length] = elem;
        return res;
    }

    public static char[] reversed(char[] cs) {
        char[] res = new char[cs.length];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = cs[i];
        }
        return res;
    }

    public static int lastIndexOf(char[] row, char target, int from) {
        for (int i = from; i >= 0; i--) if (row[i] == target) return i;
        return -1;
    }

    public static long[] shuffledArray(long[] ls) {
        long[] res = ls.clone();
        shuffle(res);
        return res;
    }

    public static long[] subArray(long[] s, int from, int to) {
        long[] res = new long[to - from];
        System.arraycopy(s, from, res, 0, to - from);
        return res;
    }

    public static int indexOf(int[] a, int target, int from) {
        for (int i = from; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    public static int indexOf(long[] a, long target, int from) {
        for (int i = from; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    public static int indexOf(double[] a, double target, int from) {
        for (int i = from; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    public static long[] masked(long[] ls, int mask) {
        long[] res = new long[Integer.bitCount(mask)];
        for (int i = 0, j = 0; i < ls.length; i++) {
            if ((mask >> i & 1) == 1) res[j++] = ls[i];
        }
        return res;
    }

    public static void fill(double[][] array, double d) {
        for (double[] ds : array) Arrays.fill(ds, d);
    }

    public static long sum(long[] ls) {
        long sum = 0;
        for (long l : ls) sum += l;
        return sum;
    }

    public static long[][] part(long[][] array, int x1, int y1, int x2, int y2) {
        long[][] res = new long[x2 - x1][y2 - y1];
        for (int i = x1; i < x2; i++) {
            System.arraycopy(array[i], y1, res[i - x1], 0, y2 - y1);
        }
        return res;
    }

    public static void sort(int[] a) {
        shuffle(a);
        Arrays.sort(a);
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        shuffle(a);
        Arrays.sort(a);
    }

    public static void sortBy(int[] a, double[] b) {
        @SuppressWarnings("unchecked") Pair<Integer, Double>[] ps = new Pair[a.length];
        for (int i = 0; i < a.length; i++) ps[i] = Pair.of(a[i], b[i]);
        sort(ps);
        for (int i = 0; i < a.length; i++) {
            a[i] = ps[i].first;
            b[i] = ps[i].second;
        }
    }

    public static void sortBy(int[] a, int[] b) {
        List<Pair<Integer, Integer>> ps = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < a.length; i++) ps.add(Pair.of(a[i], b[i]));
        Collections.sort(ps);
        for (int i = 0; i < a.length; i++) {
            a[i] = ps.get(i).first;
            b[i] = ps.get(i).second;
        }
    }

    public static void sortBy(int[] a, long[] b) {
        @SuppressWarnings("unchecked") Pair<Integer, Long>[] ps = new Pair[a.length];
        for (int i = 0; i < a.length; i++) ps[i] = Pair.of(a[i], b[i]);
        sort(ps);
        for (int i = 0; i < a.length; i++) {
            a[i] = ps[i].first;
            b[i] = ps[i].second;
        }
    }

    public static void sortBy(long[] a, double[] b) {
        @SuppressWarnings("unchecked") Pair<Long, Double>[] ps = new Pair[a.length];
        for (int i = 0; i < a.length; i++) ps[i] = Pair.of(a[i], b[i]);
        sort(ps);
        for (int i = 0; i < a.length; i++) {
            a[i] = ps[i].first;
            b[i] = ps[i].second;
        }
    }

    /**
     * @param is - assert is[i] <= is.length for every i
     *           is[i] -> id[is[i]] where is[i] >= 0
     *           is[i] where is[i] < 0
     */
    public static void normalize(int[] is) {
        int[] id = new int[is.length + 1];
        Arrays.fill(id, -1);
        int p = 0;
        for (int i : is) if (i >= 0 && id[i] == -1) id[i] = p++;
        for (int i = 0; i < is.length; i++) if (is[i] >= 0) is[i] = id[is[i]];
    }

    public static char[] subArray(char[] array, int from, int to) {
        char[] res = new char[to - from];
        System.arraycopy(array, from, res, from - from, to - from);
        return res;
    }

    public static void sort(long[] a) {
        shuffle(a);
        Arrays.sort(a);
    }

    public static void swap(int[] is, int i, int j) {
        int t = is[i];
        is[i] = is[j];
        is[j] = t;
    }

    public static <T> void swap(T[] vs, int i, int j) {
        T tmp = vs[i];
        vs[i] = vs[j];
        vs[j] = tmp;
    }


    public static void rev(int[] is, int from, int to) {
        while (from < --to)
            swap(is, from++, to);
    }

    public static void swap(char[] is, int i, int j) {
        char t = is[i];
        is[i] = is[j];
        is[j] = t;
    }

    public static void rev(char[] is, int s, int t) {
        while (s < --t)
            swap(is, s++, t);
    }

    public static void shuffle(char[] cs) {
        int[] is = new int[cs.length];
        for (int i = 0; i < cs.length; i++) is[i] = cs[i];
        shuffle(is);
        for (int i = 0; i < cs.length; i++) cs[i] = (char) is[i];
    }

    /**
     * @param n - size of the array
     * @return res[i] = i
     */
    public static int[] createOrder(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = i;
        return res;
    }

    /**
     * @param cur         - current array
     * @param permutation - permutation
     * @return - res[permutation[i]] = cur[i]
     */
    public static int[] multiplyReversePermutations(int[] cur, int[] permutation) {
        int[] res = new int[cur.length];
        for (int i = 0; i < cur.length; i++) res[permutation[i]] = cur[i];
        return res;
    }

    /**
     * @param cur         - current array
     * @param permutation - permutation
     * @return - res[i] = cur[permutation[i]]
     */
    public static int[] multiplyPermutations(int[] cur, int[] permutation) {
        int[] res = new int[cur.length];
        for (int i = 0; i < cur.length; i++) res[i] = cur[permutation[i]];
        return res;
    }

    /**
     * @param n - size of the result array
     * @return - res[i] = n-1-i
     */
    public static int[] createReversedOrder(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = n - 1 - i;
        return res;
    }

    public static void reverse(int[] is) {
        int n = is.length;
        for (int i = 0; i * 2 < n; i++) {
            int tmp = is[i];
            is[i] = is[n - 1 - i];
            is[n - 1 - i] = tmp;
        }
    }

    public static int[] reversed(int[] is) {
        is = is.clone();
        reverse(is);
        return is;
    }

    public static <T> void reverse(T[] is) {
        int n = is.length;
        for (int i = 0; i * 2 < n; i++) {
            T tmp = is[i];
            is[i] = is[n - 1 - i];
            is[n - 1 - i] = tmp;
        }
    }

    public static boolean[] copyOf(boolean[] array, int newLength) {
        boolean[] copy = new boolean[newLength];
        System.arraycopy(array, 0, copy, 0, Math.min(array.length, newLength));
        return copy;
    }

    public static int[] copyOf(int[] array, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(array, 0, copy, 0, Math.min(array.length, newLength));
        return copy;
    }

    public static long[] copyOf(long[] array, int newLength) {
        long[] copy = new long[newLength];
        System.arraycopy(array, 0, copy, 0, Math.min(array.length, newLength));
        return copy;
    }

    public static double[] copyOf(double[] array, int newLength) {
        double[] copy = new double[newLength];
        System.arraycopy(array, 0, copy, 0, Math.min(array.length, newLength));
        return copy;
    }

    public static <V> V[] copyOf(V[] array, int newLength, V[] empty) {
        ArrayList<V> list = new ArrayList<V>(newLength);
        for (int i = 0; i < array.length && newLength > 0; i++) {
            list.add(array[i]);
            newLength--;
        }
        while (newLength-- > 0) list.add(null);
        return list.toArray(empty);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void show(boolean[][] graph) {
        for (boolean[] bs : graph) {
            for (int i = 0; i < bs.length; i++) {
                System.err.printf("%d%c", bs[i] ? 1 : 0, i == bs.length - 1 ? '\n' : ' ');
            }
        }
    }

    public static boolean nextPermutation(int[] is) {
        return Permutation.nextPermutation(is);
    }

    public static long[][] deepClone(long[][] a) {
        long[][] res = new long[a.length][];
        for (int i = 0; i < res.length; i++) res[i] = a[i].clone();
        return res;
    }

    public static long[] createGeometricProgression(int a, int b, int n) {
        long[] res = new long[n];
        for (int i = 0; i < n; i++) res[i] = i == 0 ? a : res[i - 1] * b;
        return res;
    }

    public static long[] createGeometricProgression(int a, int b, int n, int MOD) {
        long[] res = new long[n];
        for (int i = 0; i < n; i++) res[i] = (i == 0 ? a : res[i - 1] * b) % MOD;
        return res;
    }
}
