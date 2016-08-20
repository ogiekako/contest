package net.ogiekako.algorithm.utils;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class StopwatchTest {

    @Test
    public void summary() throws Exception {
        FakeStopwatch stopwatch = new FakeStopwatch();
        stopwatch.setCurrentTimeMillis(10);
        stopwatch.tick("AAA");
        stopwatch.tick("B");
        stopwatch.tick("CC");
        stopwatch.setCurrentTimeMillis(60);
        stopwatch.tack("AAA");
        stopwatch.setCurrentTimeMillis(1010);
        stopwatch.tack("B");
        stopwatch.tack("CC");
        stopwatch.setCurrentTimeMillis(101000);
        stopwatch.tick("CC");
        stopwatch.setCurrentTimeMillis(160000);
        stopwatch.tack("CC");
        String expect = "" +
                " CC: 1m\n" +
                "  B: 1.00s\n" +
                "AAA: 50ms\n";
        String actual = stopwatch.summary();
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void sparcification() {
        FakeStopwatch stopwatch;
        stopwatch = new FakeStopwatch();
        for (int i = 0; i < 150 * 1000; i++) {
            stopwatch.setCurrentTimeMillis(i * 2);
            stopwatch.tick("A");
            stopwatch.setCurrentTimeMillis(i * 2 + 1);
            stopwatch.tack("A");
        }
        Assert.assertThat(stopwatch.summary(), Matchers.equalTo("A: 2m\n"));

        stopwatch = new FakeStopwatch();
        for (int i = 0; i < 5; i++) {
            stopwatch.tick("A");
            stopwatch.tack("A");
        }
        Assert.assertThat(stopwatch.numSystemCalls, Matchers.equalTo(10));
    }

    @Test(timeout = 2000)
    public void speed() {
        Stopwatch stopwatch = Stopwatch.create();
        for (int i = 0; i < 1e7; i++) {
            stopwatch.tick("A");
            stopwatch.tack("A");
        }
    }

    static class FakeStopwatch extends Stopwatch {
        long now = 0;
        int numSystemCalls = 0;

        @Override
        long nanoTime() {
            numSystemCalls++;
            return now;
        }

        void setCurrentTimeMillis(long timeMillis) {
            this.now = timeMillis * 1000000;
        }
    }
}