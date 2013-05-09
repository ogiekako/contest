package net.ogiekako.algorithm.math;

import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class RhoSequence {
    public final int head;
    public final int period;
    final int[] S;

    public RhoSequence(int head, int period, int[] S) {
        this.head = head;
        this.period = period;
        this.S = S;
    }

    public static RhoSequence generate(Function function) {
        Map<Integer, Integer> dejavu = new HashMap<Integer, Integer>();

        int head, period;
        for (int i = 0, value = -1; ; i++) {
            value = i == 0 ? function.first() : function.next(value);
            if (dejavu.containsKey(value)) {
                head = dejavu.get(value);
                period = i - head;
                break;
            } else {
                dejavu.put(value, i);
            }
        }
        int[] S = new int[head + period];
        for (int i = 0; i < S.length; i++) {
            S[i] = i == 0 ? function.first() : function.next(S[i - 1]);
        }
        return new RhoSequence(head, period, S);
    }

    public long calcSumOfMinOfAllSubrange(long length) {
        if (head + period > length || head == 0 && period + period > length) {
            long res = 0;
            Pair<Integer, Integer>[] ps = ArrayUtils.createArray((int) length, new Pair<Integer, Integer>(0, 0));
            for (int i = 0; i < length; i++) {
                int value = i < head ? S[i] : S[head + (i - head) % period];
                ps[i] = new Pair<Integer, Integer>(value, i);
            }
            Arrays.sort(ps);
            TreeSet<Integer> set = new TreeSet<Integer>();
            set.add(-1);
            set.add((int) length);
            for (Pair<Integer, Integer> p : ps) {
                int id = p.second;
                int value = p.first;
                int lower = set.lower(id);
                int higher = set.higher(id);
                // (lower,id] * (id,higher]
                res += (long) value * (id - lower) * (higher - id);
                set.add(id);
            }
            return res;
        } else if (head == 0) {
            long k = length / period;
            if (k < 2) throw new AssertionError();
            long res = MathUtils.sum(length - k * period + 1, length - period + 1) * ArrayUtils.min(S);
            Pair<Integer, Integer>[] ps = ArrayUtils.makeValueIndexPairs(S);
            Arrays.sort(ps);
            TreeSet<Integer> set = new TreeSet<Integer>();
            for (Pair<Integer, Integer> p : ps) {
                int id = p.second;
                int value = p.first;
                if (set.isEmpty()) {
                    res += (k - 1) * value * ((long) period * (period - 1) / 2 + period);
                } else {
                    Integer l = set.lower(id);
                    int lower = l != null ? l : set.last() - period;
                    Integer h = set.higher(id);
                    int higher = h != null ? h : set.first() + period;
                    res += (k - 1) * value * (id - lower) * (higher - id);
                }
                set.add(id);
            }
            return res + calcSumOfMinOfAllSubrange(length - (k - 1) * period);
        } else {
            long res = 0;
            // [0,h) * (i,n]
            TreeSet<Long> set = new TreeSet<Long>();
            set.add(-1L);
            set.add(length);
            Pair<Integer, Integer>[] ps = ArrayUtils.makeValueIndexPairs(S);
            Arrays.sort(ps);
            for (Pair<Integer, Integer> p : ps) {
                long id = p.second;
                long value = p.first;
                if (id < head) {
                    long lower = set.lower(id);
                    long higher = set.higher(id);
                    // (lower,id] * (id,higher]
                    res += value * (id - lower) * (higher - id);
                } else {
                    long lower = set.lower(id);
                    long higher = set.higher(id);
                    // (lower,h) * (id,higher]
                    res += value * Math.max(head - lower - 1, 0) * (higher - id);
                }
                set.add(id);
            }
            return res + subSequence(head).calcSumOfMinOfAllSubrange(length - head);
        }
    }

    public RhoSequence subSequence(int from) {
        if (from <= head) {
            int nHead = head - from;
            int[] nS = ArrayUtils.subArray(S, from);
            return new RhoSequence(nHead, period, nS);
        } else {
            int[] nS = ArrayUtils.subArray(S, head);
            ArrayUtils.rotateLeft(nS, (from - head) % period);
            return new RhoSequence(0, period, nS);
        }
    }

    public int get(int i) {
        if (i < head) return S[i];
        return S[head + (i - head) % period];
    }


    public static interface Function {
        int first();

        int next(int before);
    }
}
