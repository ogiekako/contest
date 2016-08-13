package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.utils.counter.Counter;
import net.ogiekako.algorithm.utils.counter.HashCounter;

import java.util.*;

/**
 * Useful for finding bottleneck of programs.
 * <p/>
 * Do sparsification with exponential back-off strategy.
 */
public class Stopwatch {
    // Visible for testing
    Stopwatch() {
    }

    private static final double MULTIPLICATION_FACTOR = 1.01;
    private Counter<String> accumulator = new HashCounter<String>();
    /**
     * The number of tick-s when the previous accumulation was performed.
     */
    private Counter<String> previousCount = new HashCounter<String>();
    private Counter<String> totalCount = new HashCounter<String>();

    long nanoTime() {
        return System.nanoTime();
    }

    /**
     * Start a timer for the key. You should call tack(key) once after you called
     * this method. Calling tick twice in a row would produce an unexpected result.
     * <p>Tested</p>
     */
    public void tick(String key) {
        long total = totalCount.add(key, 1);
        long previous = previousCount.get(key);
        if (previous * MULTIPLICATION_FACTOR > total) {
            return;
        }
        long mul = total - previous;
        accumulator.add(key, -nanoTime() * mul);
    }

    /**
     * Stop a timer for the key and add the elapsed time to the accumulator.
     * You should have called tick(key) once before you call this method.
     * Calling tack twice in a row would produce an unexpected result.
     * <p>Tested</p>
     */
    public void tack(String key) {
        long total = totalCount.get(key);
        long previous = previousCount.get(key);
        if (previous * MULTIPLICATION_FACTOR > total) {
            return;
        }
        long mul = total - previous;
        accumulator.add(key, nanoTime() * mul);
        previousCount.put(key, total);
    }

    /**
     * Return the summary sorted by the elapsed time in descending order.
     * <p>Tested</p>
     */
    public String summary() {
        List<Map.Entry<String, Long>> es = new ArrayList<Map.Entry<String, Long>>(accumulator.entrySet());
        Collections.sort(es, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return -Long.compare(o1.getValue(), o2.getValue());
            }
        });
        int longest = 0;
        for (Map.Entry<String, Long> e : es) {
            longest = Math.max(longest, e.getKey().length());
        }
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, Long> e : es) {
            String key = e.getKey();
            long nanoTime = e.getValue();
            String row = String.format("%" + longest + "s: %s", key, beautify(nanoTime));
            b.append(row).append('\n');
        }
        return b.toString();
    }

    private String beautify(long nanoTime) {
        long millis = nanoTime / 1000000;
        if (millis < 1000) {
            return millis + "ms";
        }
        long sec = millis / 1000;
        if (sec < 60) {
            return sec + "s";
        }
        long min = sec / 60;
        return min + "m";
    }

    public static Stopwatch create() {
        return new Stopwatch();
    }

    public static Stopwatch createDummy() {
        return DUMMY;
    }

    private static final Stopwatch DUMMY = new Stopwatch() {
        @Override
        public void tick(String key) {
        }

        @Override
        public void tack(String key) {
        }

        @Override
        public String summary() {
            return "";
        }
    };
}
